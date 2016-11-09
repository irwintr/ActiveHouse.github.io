package io.github.activehouse;

public class Room {
    private String name, timeOn, timeOff, timeUpdated;
    private int roomID, gas, lightStatus;
    private double temp, humidity, luminosity;

    public Room(String name, String timeOn, String timeOff, String timeUpdated, int roomID, int gas, int lightStatus, double temp, double humidity, double luminosity) {
        this.name = name;
        this.timeOn = timeOn;
        this.timeOff = timeOff;
        this.timeUpdated = timeUpdated;
        this.roomID = roomID;
        this.gas = gas;
        this.lightStatus = lightStatus;
        this.temp = temp;
        this.humidity = humidity;
        this.luminosity = luminosity;
    }

    public Room() {

    }


    //Getters and Setters

    public String getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(String timeOn) {
        this.timeOn = timeOn;
    }

    public String getTimeOff() {
        return timeOff;
    }

    public void setTimeOff(String timeOff) {
        this.timeOff = timeOff;
    }

    public String getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(String timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public int getLightStatus() {
        return lightStatus;
    }

    public void setLightStatus(int lightStatus) {
        this.lightStatus = lightStatus;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(double luminosity) {
        this.luminosity = luminosity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
