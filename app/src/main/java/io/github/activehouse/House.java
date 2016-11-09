package io.github.activehouse;

import java.util.ArrayList;

public class House {
    private ArrayList<Room> rooms;
    private int houseID;
    private String houseName;
    private double waterCurrent, waterAverage, powerCurrent, powerAverage;

    public House(ArrayList<Room> rooms, int houseID, String houseName, double waterCurrent, double waterAverage, double powerCurrent, double powerAverage) {
        this.rooms = rooms;
        this.houseID = houseID;
        this.houseName = houseName;
        this.waterCurrent = waterCurrent;
        this.waterAverage = waterAverage;
        this.powerCurrent = powerCurrent;
        this.powerAverage = powerAverage;
    }
    public House() {

    }

    //Getters and Setters

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public int getHouseID() {
        return houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public double getWaterCurrent() {
        return waterCurrent;
    }

    public void setWaterCurrent(double waterCurrent) {
        this.waterCurrent = waterCurrent;
    }

    public double getWaterAverage() {
        return waterAverage;
    }

    public void setWaterAverage(double waterAverage) {
        this.waterAverage = waterAverage;
    }

    public double getPowerCurrent() {
        return powerCurrent;
    }

    public void setPowerCurrent(double powerCurrent) {
        this.powerCurrent = powerCurrent;
    }

    public double getPowerAverage() {
        return powerAverage;
    }

    public void setPowerAverage(double powerAverage) {
        this.powerAverage = powerAverage;
    }
}
