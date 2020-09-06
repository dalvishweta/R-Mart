package com.rmart.inventory.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.APIUnitMeasureResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class UnitObject implements Serializable {
    ArrayList<APIUnitMeasureResponse> availableUnits;
    int minDiscount;
    int maxDiscount;

    int id;
    String unitType;
    String unitMeasure;
    String unitValue = "";

    @SerializedName("unit_name")
    @Expose
    String displayUnitValue;

    @SerializedName("selling_price")
    @Expose
    String finalCost;
    @SerializedName("unit_price")
    @Expose
    String actualCost;
    @SerializedName("discount")
    @Expose
    String discount;
    boolean isActive;
    String productStatus;

    @SerializedName("stock_id")
    @Expose
    String productStatusID;

    public UnitObject(ArrayList<APIUnitMeasureResponse> availableUnits1) {
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

    public String getProductStatusID() {
        return productStatusID;
    }

    public void setProductStatusID(String productStatusID) {
        this.productStatusID = productStatusID;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getDisplayUnitValue() {
        return displayUnitValue;
    }

    public void setDisplayUnitValue(String displayUnitValue) {
        this.displayUnitValue = displayUnitValue;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
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

    public ArrayList<APIUnitMeasureResponse> getAvailableUnits() {
        return availableUnits;
    }

    public void setAvailableUnits(ArrayList<APIUnitMeasureResponse> availableUnits) {
        this.availableUnits = availableUnits;
    }
}
