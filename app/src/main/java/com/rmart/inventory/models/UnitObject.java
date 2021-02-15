package com.rmart.inventory.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Calendar;

public class UnitObject implements Serializable {
    @SerializedName("unit_name")
    @Expose
    String displayUnitValue;

    @SerializedName("product_unit_id")
    @Expose
    String productUnitID;

    @SerializedName("unit_id")
    @Expose
    String unitID;

    @SerializedName("stock_name")
    @Expose
    String stockName;

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

    @SerializedName("stock_id")
    @Expose
    String stockID;

    @SerializedName("unit_number")
    @Expose
    String unit_number;


    public void setBuisness_type(String buisness_type) {
        this.buisness_type = buisness_type;
    }

    @SerializedName("buisness_type")
    @Expose
    String buisness_type;
    /*@SerializedName("short_name")
    @Expose
    String shortName;*/

    private int position;
    boolean isActive = true;
    int minDiscount;
    int maxDiscount;
    String unitType;

    public String getBuisness_type() {
        return buisness_type;
    }

    String unitMeasure;
    long timeStamp = -1;
    boolean isProductUpdated = false;

    public UnitObject() {
    }

    public UnitObject( UnitObject unitObject) {
        this.displayUnitValue = unitObject.displayUnitValue;
        this.productUnitID = unitObject.productUnitID;
        this.unitID = unitObject.unitID;
        this.stockName = unitObject.stockName;
        this.finalCost  = unitObject.finalCost;
        this.quantity  = unitObject.quantity;
        this.actualCost  = unitObject.actualCost;
        this.discount  = unitObject.discount;
        this.stockID  = unitObject.stockID;
        this.unit_number  = unitObject.unit_number;
        this.timeStamp  = unitObject.timeStamp;
        this.isActive  = unitObject.isActive;
        this.minDiscount  = unitObject.minDiscount;
        this.maxDiscount  = unitObject.maxDiscount;
        this.unitType  = unitObject.unitType;
        this.unitMeasure  = unitObject.unitMeasure;
        this.unitMeasure  = unitObject.unitMeasure;
        this.buisness_type  = unitObject.buisness_type;
    }
    /*public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }*/

/*
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
*/

    public String getProductUnitID() {
        return productUnitID;
    }

    public void setProductUnitID(String productUnitID) {
        this.productUnitID = productUnitID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp() {
        this.timeStamp = Calendar.getInstance().getTimeInMillis();
    }
/*
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
    }*/

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitNumber() {
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

    /*
        public ArrayList<APIUnitMeasureResponse> getAvailableUnits() {
            return availableUnits;
        }

        public void setAvailableUnits(ArrayList<APIUnitMeasureResponse> availableUnits) {
            this.availableUnits = availableUnits;
        }
    */

    /*
        public String getStockName() {
            return stockName;
        }
    */

    public String getStockName() {
        return this.stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    @NotNull
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("unitID", productUnitID).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(productUnitID).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UnitObject)) {
            return false;
        }
        UnitObject rhs = ((UnitObject) other);
        return new EqualsBuilder().append(productUnitID, rhs.productUnitID).isEquals();
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public void setDefaultValues() {
        setDiscount("0");
        setStockID("5");
        setStockName("Available");
    }

    public boolean isProductUpdated() {
        return isProductUpdated;
    }

    public void setProductUpdated(boolean productUpdated) {
        isProductUpdated = productUpdated;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
