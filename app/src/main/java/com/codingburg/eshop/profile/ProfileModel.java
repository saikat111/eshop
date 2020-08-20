package com.codingburg.eshop.profile;

public class ProfileModel {
    String ordernumber;
    String totaltk;
    String status;

    public ProfileModel() {
    }

    public ProfileModel(String ordernumber, String totaltk, String status) {
        this.ordernumber = ordernumber;
        this.totaltk = totaltk;
        this.status = status;
    }

    public String getStatus() {
        return status;
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
