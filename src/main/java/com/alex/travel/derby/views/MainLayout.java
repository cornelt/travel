package com.alex.travel.derby.views;


import com.alex.travel.derby.components.appnav.AppNav;
import com.alex.travel.derby.components.appnav.AppNavItem;
import com.alex.travel.derby.data.entity.User;
import com.alex.travel.derby.views.about.AboutView;
import com.alex.travel.derby.views.admin.AdminView;
import com.alex.travel.derby.views.login.LoginView;
import com.alex.travel.derby.views.register.RegisterView;
import com.alex.travel.derby.views.treavel.TreavelView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H1 viewTitle;
    private AppNav nav;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassNames("view-toggle");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");
        nav = new AppNav();
        viewTitle = new H1();
        viewTitle.addClassNames("view-title");

        Header header = new Header(toggle, viewTitle);
        header.addClassNames("view-header");
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("Travel");
        appName.addClassNames("app-name");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName,
                createNavigation(), createFooter());
        section.addClassNames("drawer-section");
        return section;
    }

    public AppNav createNavigation() {
        nav.addClassNames("app-nav");
        nav.addItem(new AppNavItem("Treavel", TreavelView.class, "la la-th-list"));

        nav.addItem(new AppNavItem("Login", LoginView.class, "la la-file"));
        nav.addItem(new AppNavItem("Register", RegisterView.class, "la la-file"));
        nav.addItem(new AppNavItem("Admin", AdminView.class, "la la-columns"));
        nav.addItem(new AppNavItem("About", AboutView.class, "la la-file"));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("app-nav-footer");

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
