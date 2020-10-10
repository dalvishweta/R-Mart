package com.rmart.inventory.models;

import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.APIUnitMeasureResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class APIUnitMeasures implements Serializable {
    ArrayList<APIUnitMeasureResponse> unitMeasurements;

    public APIUnitMeasures(ArrayList<APIUnitMeasureResponse> unitMeasurements) {
        this.unitMeasurements = unitMeasurements;
    }

    public ArrayList<APIUnitMeasureResponse> getUnitMeasurements() {
        return unitMeasurements;
    }

    public void setUnitMeasurements(ArrayList<APIUnitMeasureResponse> unitMeasurements) {
        this.unitMeasurements = unitMeasurements;
    }
}
