package com.ade.models;

public class Order {
    public int id;
    public double latitude;
    public double longitude;
    public String status;
    public int deliveryTime;
    public int locationNode;

    public Order(int id, double latitude, double longitude, String status) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.deliveryTime = deliveryTime;
        this.locationNode = locationNode;
    }
}