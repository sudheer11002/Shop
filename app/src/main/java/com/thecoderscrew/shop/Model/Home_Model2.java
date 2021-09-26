package com.thecoderscrew.shop.Model;

public class Home_Model2 {
    private String productname, material, capacity, color, pattern, price, discount, deliveryprice, first_image,second_image;

    Home_Model2() {
    }

    public Home_Model2(String productname, String material, String capacity, String color, String pattern, String price, String discount, String deliveryprice,String first_image , String second_image) {
        this.productname = productname;
        this.material = material;
        this.capacity = capacity;
        this.color = color;
        this.pattern = pattern;
        this.price = price;
        this.discount = discount;
        this.deliveryprice = deliveryprice;
        this.first_image=first_image;
        this.second_image=second_image;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDeliveryprice() {
        return deliveryprice;
    }

    public void setDeliveryprice(String deliveryprice) {
        this.deliveryprice = deliveryprice;
    }

    public String getFirst_Image() { return first_image; }

    public void setFirst_Image(String first_image) { this.first_image = first_image; }

    public String getSecond_Image() { return second_image; }

    public void setSecond_Image(String second_image) { this.second_image = second_image; }
}
