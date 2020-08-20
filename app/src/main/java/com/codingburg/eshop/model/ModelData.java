package com.codingburg.eshop.model;

public class ModelData {
    String price;
    String image;
    String id;
    String category;

    public ModelData(String price, String image, String id, String category) {
        this.price = price;
        this.image = image;
        this.id = id;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ModelData() {
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

    @Override
    public String toString() {
        return "ModelData{" +
                "price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
