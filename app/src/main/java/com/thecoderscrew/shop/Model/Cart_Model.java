package com.thecoderscrew.shop.Model;

public class Cart_Model {

    private String title, size, price, material, discount, color, val ,first_image;

    Cart_Model() {
    }

    public Cart_Model(String title, String size, String price, String material, String discount, String color, String val , String first_image) {
        this.title = title;
        this.size = size;
        this.price = price;
        this.material = material;
        this.discount = discount;
        this.color = color;
        this.val = val;
        this.first_image=first_image;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getFirst_image()
    {
        return first_image;
    }

    public void setFirst_image(String first_image)
    {
        this.first_image=first_image;
    }
}
