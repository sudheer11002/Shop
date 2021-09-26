package com.thecoderscrew.shop.Model;

public class MyOrders_Model {

    MyOrders_deliveryAddressModel deliveryAddress;
    MyOrders_orderDetailsModel orderDetails;
    MyOrders_productDetailsModel productDetails;

    public MyOrders_Model() {
    }

    public MyOrders_Model(MyOrders_deliveryAddressModel deliveryAddress, MyOrders_orderDetailsModel orderDetails, MyOrders_productDetailsModel productDetails) {
        this.deliveryAddress = deliveryAddress;
        this.orderDetails = orderDetails;
        this.productDetails = productDetails;
    }

    public MyOrders_deliveryAddressModel getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(MyOrders_deliveryAddressModel deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public MyOrders_orderDetailsModel getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(MyOrders_orderDetailsModel orderDetails) {
        this.orderDetails = orderDetails;
    }

    public MyOrders_productDetailsModel getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(MyOrders_productDetailsModel productDetails) {
        this.productDetails = productDetails;
    }
}
