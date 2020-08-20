package com.codingburg.eshop.cart;

public class CardData {
    String totalprice;
    String image;
    String id;
    String name;
    String category;
    String quantity;
    String key;



    public CardData() {
    }
    public CardData(String totalprice, String image, String id, String name, String category, String quantity, String key) {
        this.totalprice = totalprice;
        this.image = image;
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.key = key;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
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
