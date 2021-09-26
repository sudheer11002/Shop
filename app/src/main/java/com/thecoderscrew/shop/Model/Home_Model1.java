package com.thecoderscrew.shop.Model;

public class Home_Model1 {
    private  String productname, material, sleevetype, pattern, price, discount, deliveryprice, category, necktype, size, color , first_image,second_image,clickedstatus;

    public Home_Model1(String productname, String material, String sleevetype, String pattern, String price, String discount, String deliveryprice, String category, String necktype, String size, String color ,String first_image , String second_image,String clickedstatus) {
        this.productname = productname;
        this.material = material;
        this.sleevetype = sleevetype;
        this.pattern = pattern;
        this.price = price;
        this.discount = discount;
        this.deliveryprice = deliveryprice;
        this.category = category;
        this.necktype = necktype;
        this.size = size;
        this.color = color;
        this.first_image=first_image;
        this.second_image=second_image;
        this.clickedstatus=clickedstatus;
    }

    public Home_Model1() {
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

    public String getSleevetype() {
        return sleevetype;
    }

    public void setSleevetype(String sleevetype) {
        this.sleevetype = sleevetype;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNecktype() {
        return necktype;
    }

    public void setNecktype(String necktype) {
        this.necktype = necktype;
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

    public String getFirst_Image() { return first_image; }

    public void setFirst_Image(String first_image) {
        this.first_image = first_image;
    }

    public String getSecond_Image() {
        return second_image;
    }

    public void setSecond_Image(String second_image) {
        this.second_image = second_image;
    }

    public String getClickedstatus() {
        return clickedstatus;
    }

    public void setClickedstatus(String clickedstatus) {
        this.clickedstatus = clickedstatus;
    }
}
