package com.thecoderscrew.shop.Model;

public class MyOrders_orderDetailsModel {


    String customerID,orderID,paymentMode,time,totalPrice,date;

    public MyOrders_orderDetailsModel() {
    }

    public MyOrders_orderDetailsModel(String customerID, String orderID, String paymentMode, String time, String totalPrice, String date) {
        this.customerID = customerID;
        this.orderID = orderID;
        this.paymentMode = paymentMode;
        this.time = time;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public String getCustomerID() {
        return customerID;
    }
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
