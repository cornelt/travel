package com.alex.travel.derby.views.login;

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

@PageTitle("Login")
@Route(value = "login", layout = MainLayout.class)
public class LoginView extends VerticalLayout {

    public LoginView(AuthService authService) {
        if(VaadinSession.getCurrent().getAttribute(User.class)==null) {
            var userNameTextFiend = new TextField("User name");
            var passwordField = new PasswordField("Password");
            add(new H2("Welcome"),
                    userNameTextFiend,
                    passwordField,
                    new Button("Login", e -> {
                        try {
                            authService.authentificate(userNameTextFiend.getValue(), passwordField.getValue());
                            UI.getCurrent().navigate("treavel");
                        } catch (Exception ex) {
                            Notification.show("Incorect username or password");
                        }
                    }));
        }else{
            add(new H2("Logout for "+((User)VaadinSession.getCurrent().getAttribute(User.class)).getUserName()),
                    new Button("Logout", e -> {
                        try {
                            VaadinSession.getCurrent().setAttribute(User.class,null);
                            UI.getCurrent().navigate("treavel");

                        } catch (Exception ex) {
                            Notification.show("Incorect username or password");
                        }
                    }));
        }

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "left");
    }

}
