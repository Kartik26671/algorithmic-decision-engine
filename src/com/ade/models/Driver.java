package com.ade.models;

public class Driver {
    public int id;
    public double latitude;
    public double longitude;
    boolean available;

    public Driver(int id, double latitude, double longitude, boolean available) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }
}