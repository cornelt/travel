package com.alex.travel.derby.views.treavel;

import com.alex.travel.derby.data.entity.Travel;
import com.alex.travel.derby.data.service.TravelService;
import com.alex.travel.derby.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.List;

@PageTitle("Treavel")
@Route(value = "treavel", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class TreavelView extends Main implements HasComponents, HasStyle {

    private OrderedList imageContainer;
    private TravelService travelService;
    public TreavelView(TravelService travelService) {
        this.travelService=travelService;
        constructUI();
        imageContainerCreation(travelService.getAll());
    }

    private void constructUI() {
        addClassNames("treavel-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames("items-center", "justify-between");

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Travel offer");
        header.addClassNames("mb-0", "mt-xl", "text-3xl");
        Paragraph description = new Paragraph("Royalty free photos and pictures, courtesy of Unsplash");
        description.addClassNames("mb-xl", "mt-0", "text-secondary");
        headerContainer.add(header, description);

        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Sort by");
        sortBy.setItems("Popularity", "Newest first", "Oldest first");
        sortBy.setValue("Popularity");
        TextField searchField = new TextField();
        searchField.setLabel("Search by tag");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        Button searchButton=new Button("Search", e -> {
           // String t =searchField.getValue();
            if(searchField.getValue()!=null) {
                imageContainerCreation(travelService.findInTag(searchField.getValue().toLowerCase()));
            }
        });
        add(searchField);

        imageContainer = new OrderedList();
        imageContainer.addClassNames("gap-m", "grid", "list-none", "m-0", "p-0");

        container.add(header,searchField,searchButton, sortBy);
        add(container, imageContainer);

    }
    private void imageContainerCreation(List<Travel> travels){
        imageContainer.removeAll();
        travels.stream().forEach(x->imageContainer.add(new TreavelViewCard(x)));

    }
}