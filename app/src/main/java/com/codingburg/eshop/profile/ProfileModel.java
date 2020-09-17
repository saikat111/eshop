package com.codingburg.eshop.profile;

public class ProfileModel {
    String ordernumber;
    String totaltk;
    String status;
    String date;

    public ProfileModel() {
    }

    public ProfileModel(String ordernumber, String totaltk, String status, String date) {
        this.ordernumber = ordernumber;
        this.totaltk = totaltk;
        this.status = status;
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getTotaltk() {
        return totaltk;
    }

    public void setTotaltk(String totaltk) {
        this.totaltk = totaltk;
    }
}
