package com.codingburg.eshop.model;

public class ModelDataList {
    String price;
    String image;
    String id;
    String name;
    String category;

    public ModelDataList(String price, String image, String id, String name, String category) {
        this.price = price;
        this.image = image;
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ModelDataList() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ModelDataList{" +
                "price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
