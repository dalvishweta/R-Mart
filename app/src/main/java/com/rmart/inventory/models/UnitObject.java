package com.rmart.inventory.models;

import java.io.Serializable;
import java.util.ArrayList;

public class UnitObject implements Serializable {
    ArrayList<String> availableUnits;
    int minDiscount;
    int maxDiscount;

    int id;
    String unitType;
    String unitValue;
    String finalCost;
    String actualCost;
    String discount;
    boolean isActive;

    public UnitObject(int k) {

        id = 101;
        unitType = "KG";
        unitValue = k+"";
        finalCost = "800Rs";
        actualCost = "1000Rs";
        discount = "20";
        isActive = true;
    }
    public UnitObject(ArrayList<String> availableUnits1) {
        this.availableUnits = availableUnits1;
        id = 101;
        unitType = "";
        unitValue = "";
        finalCost = "";
        actualCost = "";
        discount = "";
        isActive = false;
    }
    public UnitObject() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    public String getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(String finalCost) {
        this.finalCost = finalCost;
    }

    public String getActualCost() {
        return actualCost;
    }

    public void setActualCost(String actualCost) {
        this.actualCost = actualCost;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public ArrayList<String> getAvailableUnits() {
        return availableUnits;
    }

    public void setAvailableUnits(ArrayList<String> availableUnits) {
        this.availableUnits = availableUnits;
    }

    public int getMinDiscount() {
        return minDiscount;
    }

    public void setMinDiscount(int minDiscount) {
        this.minDiscount = minDiscount;
    }

    public int getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(int maxDiscount) {
        this.maxDiscount = maxDiscount;
    }
}
