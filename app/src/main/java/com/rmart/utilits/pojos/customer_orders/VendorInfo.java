package com.rmart.utilits.pojos.customer_orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.AddressResponse;

import java.util.ArrayList;

public class VendorInfo {

    @SerializedName("first_name")
    @Expose
    String firstName;

    @SerializedName("last_name")
    @Expose
    String lastName;

    @SerializedName("middle_name")
    @Expose
    String middleName;

    @SerializedName("mobile_number")
    @Expose
    String mobileNumber;

    @SerializedName("address")
    @Expose
    ArrayList<AddressResponse> responses;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public ArrayList<AddressResponse> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<AddressResponse> responses) {
        this.responses = responses;
    }
}
