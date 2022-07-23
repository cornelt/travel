package com.alex.travel.derby.views.register;

import com.alex.travel.derby.data.entity.User;
import com.alex.travel.derby.data.service.AuthService;
import com.alex.travel.derby.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("Register")
@Route(value = "register", layout = MainLayout.class)
public class RegisterView extends VerticalLayout {

    public RegisterView(AuthService authService) {
        setSpacing(false);
        if(VaadinSession.getCurrent().getAttribute(User.class)==null) {
            var userNameTextFiend = new TextField("User name");
            var passwordField = new PasswordField("Password");
            add(new H2("Register"),
                    userNameTextFiend,
                    passwordField,
                    new Button("Register", e -> {
                        try {
                            authService.register(userNameTextFiend.getValue(), passwordField.getValue());
                            UI.getCurrent().navigate("login");
                        } catch (Exception ex) {
                            Notification.show("User exist in db");
                        }
                    }));
        }else{
            add(new H2("You have user "+((User)VaadinSession.getCurrent().getAttribute(User.class)).getUserName()));
        }
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
