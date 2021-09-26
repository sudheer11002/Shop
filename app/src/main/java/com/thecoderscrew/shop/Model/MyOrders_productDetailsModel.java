package com.thecoderscrew.shop.Model;

public class MyOrders_productDetailsModel {
  String title, size, color, discount, price, quantity,first_image;

    public MyOrders_productDetailsModel(String title, String size, String color, String discount, String price, String quantity, String first_image) {
        this.title = title;
        this.size = size;
        this.color = color;
        this.discount = discount;
        this.price = price;
        this.quantity = quantity;
        this.first_image = first_image;
    }

    public MyOrders_productDetailsModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFirst_image() {
        return first_image;
    }

    public void setFirst_image(String first_image) {
        this.first_image = first_image;
    }
}
