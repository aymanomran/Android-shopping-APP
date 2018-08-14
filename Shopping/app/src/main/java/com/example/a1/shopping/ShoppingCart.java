package com.example.a1.shopping;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by 1 on 12/14/2017.
 */

public class ShoppingCart extends Application {
    static  public Integer UserName;
 static public ArrayList<Product> products=new ArrayList<>();
 static   public   Integer totalPrice=0;
    public ShoppingCart() {
    }

    public ShoppingCart(ArrayList<Product> products, Integer totalPrice,Integer userName) {
        this.products = products;
        this.totalPrice = totalPrice;
        this.UserName=userName;
    }

    public  Integer getUserName() {
        return UserName;
    }

    public  void setUserName(Integer userName) {
       this.UserName = userName;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}
