package com.rmart.utilits.pojos.customer_orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerInfo {
    @SerializedName("first_name")
    @Expose
    String firstName;

    @SerializedName("last_name")
    @Expose
    String lastName;

    @SerializedName("created_date")
    @Expose
    String createdDate;

    @SerializedName("address")
    @Expose
    String address;

    @SerializedName("country")
    @Expose
    String country;

    @SerializedName("city")
    @Expose
    String city;

    @SerializedName("state")
    @Expose
    String state;

    @SerializedName("customer_number")
    @Expose
    String customerNumber;

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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }
    public String getCompleteAddress() {
        String data = "";
        if(address != null) {
            data += address;
        }
        if(city != null) {
            data += ", "+city;
        }
        if(state != null) {
            data += state;
        }
        if(country != null) {
            data += country;
        }
        return data;
    }
}
