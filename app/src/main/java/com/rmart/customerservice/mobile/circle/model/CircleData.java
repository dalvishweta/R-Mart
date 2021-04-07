package com.rmart.customerservice.mobile.circle.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CircleData{
    @SerializedName("locations")
    public ArrayList<Circle> locations;
    @SerializedName("service_id")
    public String serviceID;
    @SerializedName("service_name")
    public String serviceName;
    @SerializedName("service_detail")
    public String serviceDetail;
}
