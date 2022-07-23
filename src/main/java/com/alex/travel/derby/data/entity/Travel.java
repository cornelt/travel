package com.alex.travel.derby.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity()
public class Travel {
    @Id
    @GeneratedValue
    private Long id;
    private String imageUrl;
    private String travelText;
    private String title;
    private String price;
    private String descriptions;
    private String tags;

    public Travel(String imageUrl, String text, String title, String price, String descriptions, String tags) {
        this.imageUrl = imageUrl;
        this.travelText = text;
        this.title = title;
        this.price = price;
        this.descriptions = descriptions;
        this.tags = tags;
    }

    public Travel(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTravelText() {
        return travelText;
    }

    public void setTravelText(String travelText) {
        this.travelText = travelText;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Travel travel = (Travel) o;
        return Objects.equals(id, travel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
