//Active Applications
//Active House Project

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
        this.rooms = new ArrayList<Room>();
        this.houseID = 0;
        this.houseName = "";
        this.waterCurrent = 0;
        this.waterAverage = 0;
        this.powerCurrent = 0;
        this.powerAverage = 0;
    }

    //Getters and Setters

    public Room getRoom(int roomID) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomID() == roomID) {
                return rooms.get(i);
            }
        }
        return new Room();
    }

    public void addRoom(Room newRoom) {
        rooms.add(newRoom);
    }

    public double getAverageTemp() {
        double temp = 0;
        for (int i = 0; i < rooms.size(); i++) {
            temp = temp + rooms.get(i).getTemp();
        }
        temp = temp / rooms.size();
        temp = round(temp, 1);
        return temp;
    }

    public double getAverageHumidity() {
        double humidity = 0;
        for (int i = 0; i < rooms.size(); i++) {
            humidity = humidity + rooms.get(i).getHumidity();
        }
        humidity = humidity / rooms.size();
        humidity = round(humidity, 1);
        return humidity;
    }

    public int getLightsOn() {
        int lights = 0;
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).isLightStatus()) {
                lights++;
            }
        }
        return lights;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }



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
