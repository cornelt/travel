package com.alex.travel.derby.views.admin;

import com.alex.travel.derby.data.entity.Role;
import com.alex.travel.derby.data.entity.User;
import com.alex.travel.derby.data.service.UserService;
import com.alex.travel.derby.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Admin")
@Route(value = "admin/:userID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class AdminView extends Div implements BeforeEnterObserver {

    private final String USER_ID = "userID";
    private final String USER_EDIT_ROUTE_TEMPLATE = "admin/%s/edit";

    private Grid<User> grid = new Grid<>(User.class, false);
    private TextField userId;
    private TextField userName;

    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField phone;
    private DatePicker dateOfBirth;
    private TextField occupation;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<User> binder;

    private User user;
    private User sessionUser;

    private final UserService userRepository;

    @Autowired
    public AdminView(UserService userRepository) {
        sessionUser = VaadinSession.getCurrent().getAttribute(User.class);
        this.userRepository = userRepository;
        addClassNames("admin-view");

        // Create UI

        SplitLayout splitLayout = new SplitLayout();

        if (sessionUser != null && sessionUser.getRole() == Role.ADMIN) {
            add(splitLayout);
            createGridLayout(splitLayout);
            createEditorLayout(splitLayout);

            // Configure Grid
            grid.addColumn("id").setAutoWidth(true);
            grid.addColumn("userName").setAutoWidth(true);
            grid.addColumn("firstName").setAutoWidth(true);
            grid.addColumn("lastName").setAutoWidth(true);
            grid.addColumn("email").setAutoWidth(true);
            grid.addColumn("phone").setAutoWidth(true);
            grid.addColumn("dateOfBirth").setAutoWidth(true);
            grid.addColumn("occupation").setAutoWidth(true);
            grid.setItems(userRepository.getAll());
            //    grid.se
            // grid.setItems(new ListDataProvider<>(userRepository.getAll()));
            grid.setItems(query -> userRepository.list(
                            PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                    .stream());
            grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

            // when a row is selected or deselected, populate form
            grid.asSingleSelect().addValueChangeListener(event -> {
                if (event.getValue() != null) {
                    UI.getCurrent().navigate(String.format(USER_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
                } else {
                    clearForm();
                    UI.getCurrent().navigate(AdminView.class);
                }
            });

            // Configure Form
            binder = new BeanValidationBinder<>(User.class);

            // Bind fields. This is where you'd define e.g. validation rules

            binder.bindInstanceFields(this);

            cancel.addClickListener(e -> {
                clearForm();
                refreshGrid();
            });

            save.addClickListener(e -> {
                try {
                    if (this.user == null) {
                        this.user = new User();
                    }
                    binder.writeBean(this.user);

                    userRepository.update(this.user);
                    clearForm();
                    refreshGrid();
                    Notification.show("SamplePerson details stored.");
                    UI.getCurrent().navigate(AdminView.class);
                } catch (ValidationException validationException) {
                    Notification.show("An exception happened while trying to store the samplePerson details.");
                }
            });
        }
        if (sessionUser != null && sessionUser.getRole() != Role.ADMIN) {
            add(splitLayout);

            createEditorLayout(splitLayout);

            binder = new BeanValidationBinder<>(User.class);

            // Bind fields. This is where you'd define e.g. validation rules

            binder.bindInstanceFields(this);
            populateForm(sessionUser);

            cancel.addClickListener(e -> {
                clearForm();
            });

            save.addClickListener(e -> {
                try {
                    if (this.user == null) {
                        this.user = new User();
                    }
                    binder.writeBean(this.user);

                    userRepository.update(this.user);
                    clearForm();
                    Notification.show("SamplePerson details stored.");
                    UI.getCurrent().navigate(AdminView.class);
                } catch (ValidationException validationException) {
                    Notification.show("An exception happened while trying to store the samplePerson details.");
                }
            });

        }
            if(sessionUser==null){
            add(new H2("Acces denied" ));
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> samplePersonId = event.getRouteParameters().get(USER_ID).map(Long::parseLong);
        if (samplePersonId.isPresent()) {
            Optional<User> samplePersonFromBackend = userRepository.get(samplePersonId.get());
            if (samplePersonFromBackend.isPresent()) {
                populateForm(samplePersonFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested user was not found, ID = %s", 1), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(AdminView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        userName = new TextField("user Name");
        firstName = new TextField("First Name");
        lastName = new TextField("Last Name");
        email = new TextField("Email");
        phone = new TextField("Phone");
        dateOfBirth = new DatePicker("Date Of Birth");
        occupation = new TextField("Occupation");
        Component[] fields = new Component[]{userName, firstName, lastName, email, phone, dateOfBirth, occupation};

        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(User value) {
        this.user = value;
        binder.readBean(this.user);

    }
}
