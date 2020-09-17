package com.codingburg.eshop.comment;

public class CommentData {
    String name;
    String image;
    String post;
    String key;
    String userid;
    String date;

    public CommentData(String name, String image, String post, String key, String userid, String date) {
        this.name = name;
        this.image = image;
        this.post = post;
        this.key = key;
        this.userid = userid;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public CommentData() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "ModelData{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", post='" + post + '\'' +
                ", key='" + key + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
