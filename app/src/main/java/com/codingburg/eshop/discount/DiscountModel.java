package com.codingburg.eshop.discount;

public class DiscountModel {
    String price;
    String image;
    String id;
    String name;
    String category;
    String time;
    String location;
    public DiscountModel() {
    }
    public DiscountModel(String price, String image, String id, String name, String category, String time, String location) {
        this.price = price;
        this.image = image;
        this.id = id;
        this.name = name;
        this.category = category;
        this.time = time;
        this.location = location;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    @Override
    public String toString() {
        return "DiscountModel{" +
                "price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
