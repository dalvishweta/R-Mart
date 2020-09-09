package com.rmart.inventory.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.APIUnitMeasureResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class UnitObject implements Serializable {
    @SerializedName("unit_name")
    @Expose
    String displayUnitValue;

    @SerializedName("unit_unique_id")
    @Expose
    String unitUniqueID;

    @SerializedName("selling_price")
    @Expose
    String finalCost;

    @SerializedName("quantity")
    @Expose
    String quantity;

    @SerializedName("unit_price")
    @Expose
    String actualCost;

    @SerializedName("discount")
    @Expose
    String discount;

    @SerializedName("unit_stock_status_id")
    @Expose
    String unitStockStatusID;

    @SerializedName("stock_id")
    @Expose
    String productStatusID;

    @SerializedName("unit_number")
    @Expose
    String unit_number;

    boolean isActive;
    String productStatus;
    int minDiscount;
    int maxDiscount;
    int id;
    String unitType;
    String unitMeasure;
    ArrayList<APIUnitMeasureResponse> availableUnits;

    public UnitObject(ArrayList<APIUnitMeasureResponse> availableUnits1) {
        this.availableUnits = availableUnits1;
        id = 101;
        unitType = "";
        unit_number = "";
        finalCost = "";
        actualCost = "";
        discount = "";
        isActive = false;
    }
    public UnitObject() {
    }

    public String getUnitUniqueID() {
        return unitUniqueID;
    }

    public void setUnitUniqueID(String unitUniqueID) {
        this.unitUniqueID = unitUniqueID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitStockStatusID() {
        return unitStockStatusID;
    }

    public void setUnitStockStatusID(String unitStockStatusID) {
        this.unitStockStatusID = unitStockStatusID;
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

    public String getUnit_number() {
        return unit_number;
    }

    public void setUnit_number(String unit_number) {
        this.unit_number = unit_number;
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
