package com.alex.travel.derby.views.treavel;

import com.alex.travel.derby.data.entity.Travel;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;

public class TreavelViewCard extends ListItem {
    public TreavelViewCard(Travel travel) {
        addClassNames("bg-contrast-5", "flex", "flex-col", "items-start", "p-m", "rounded-l");

        Div div = new Div();
        div.addClassNames("bg-contrast", "flex items-center", "justify-center", "mb-m", "overflow-hidden",
                "rounded-m w-full");
        div.setHeight("160px");

        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(travel.getImageUrl());
        image.setAlt(travel.getTravelText());

        div.add(image);

        Span header = new Span();
        header.addClassNames("text-xl", "font-semibold");
        header.setText(travel.getTitle());

        Span subtitle = new Span();
        subtitle.addClassNames("text-s", "text-secondary");
        subtitle.setText(travel.getPrice());

        Paragraph description = new Paragraph(
                travel.getDescriptions());
        description.addClassName("my-m");

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.setText(travel.getTags());

        add(div, header, subtitle, description, badge);
    }

}
