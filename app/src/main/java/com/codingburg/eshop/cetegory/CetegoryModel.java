package com.codingburg.eshop.cetegory;

public class CetegoryModel {

    String id;
    String name;
    String category;
    String image;

    public CetegoryModel() {
    }

    public CetegoryModel(String id, String name, String category, String image) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
