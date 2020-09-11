package com.codingburg.eshop.productviewmodel;

import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ProductViewModel {
    String price;
    String image;
    String id;
    String name;
    String category;
    String time;
    String location;
    String previousprice;

    public ProductViewModel() {
    }

    public ProductViewModel(String price, String image, String id, String name, String category, String time, String location,  String previousprice) {
        this.price = price;
        this.image = image;
        this.id = id;
        this.name = name;
        this.category = category;
        this.time = time;
        this.location = location;
        this.previousprice = previousprice;
    }

    public String getPreviousprice() {
        return previousprice;
    }

    public void setPreviousprice(String previousprice) {
        this.previousprice = previousprice;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ProductViewModel(FirebaseRecyclerOptions<ProductViewModel> options3) {
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
        return "ProductViewModel{" +
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
