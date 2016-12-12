package io.github.activehouse.test;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Oliver on 2016-12-12.
 */
import java.util.ArrayList;

import io.github.activehouse.Room;

public class HouseTest {
    private ArrayList<Room> rooms;
    private int houseID;
    private String houseName;
    private double waterCurrent, waterAverage, powerCurrent, powerAverage;

    public HouseTest(ArrayList<Room> rooms, int houseID, String houseName, double waterCurrent, double waterAverage, double powerCurrent, double powerAverage) {
        this.rooms = rooms;
        this.houseID = houseID;
        this.houseName = houseName;
        this.waterCurrent = waterCurrent;
        this.waterAverage = waterAverage;
        this.powerCurrent = powerCurrent;
        this.powerAverage = powerAverage;
    }

    public HouseTest() {
        this.rooms = new ArrayList<Room>();
        this.houseID = 0;
        this.houseName = "";
        this.waterCurrent = 0;
        this.waterAverage = 0;
        this.powerCurrent = 15;
        this.powerAverage = 0;
    }


    @Test
    public double setPowerCurrentTest(double powerCurrent) throws Exception {
        this.powerCurrent = powerCurrent;
        if(powerCurrent == 15)
        {
            return powerCurrent;
        }

    }
}