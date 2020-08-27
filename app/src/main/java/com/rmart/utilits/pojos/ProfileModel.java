package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileModel {
    @SerializedName("User_id")
    @Expose
    String UserID;

    @SerializedName("First Name")
    @Expose
    String firstName;

    @SerializedName("Last Name")
    @Expose
    String lastName;

    @SerializedName("DOB")
    @Expose
    String dob;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("mobileno")
    @Expose
    String mobileNumber;

    @SerializedName("Pincode")
    @Expose
    String pinCode;

    @SerializedName("profile image")
    @Expose
    String profileImage;

    @SerializedName("address")
    @Expose
    String address;

    @SerializedName("address2")
    @Expose
    String district;

    @SerializedName("state")
    @Expose
    String state;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
