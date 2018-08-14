package com.example.a1.shopping;

/**
 * Created by 1 on 12/14/2017.
 */

public class Product {
    String Name;
    Integer Price,Quantity;

    public Product() {
    }

    public Product(String name, Integer price, Integer quantity) {
        Name = name;
        Price = price;
        Quantity = quantity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }
}
