package com.example.caisseapp;

public class Plat {
    private  String platName ;
    private  int  price ;
    private  int quantity;

    public Plat(String platName, int price, int quantity) {
        this.platName = platName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
