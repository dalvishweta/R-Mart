package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProfileResponse {


    @SerializedName("id")
    @Expose
    String UserID;

    @SerializedName("first_name")
    @Expose
    String firstName;

    @SerializedName("last_name")
    @Expose
    String lastName;

    @SerializedName("gender")
    @Expose
    String gender;

    @SerializedName("DOB")
    @Expose
    String dob;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("mobile_number")
    @Expose
    String mobileNumber;

    @SerializedName("is_authenticated")
    @Expose
    String isAuthenticated;

    @SerializedName("profile image")
    @Expose
    String profileImage;

    @SerializedName("role_id")
    @Expose
    String roleID;

    @SerializedName("primary_add_id")
    @Expose
    String primaryAddressId;

    @SerializedName("address_data")
    @Expose
    ArrayList<AddressResponse> addressResponses;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getIsAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(String isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getPrimaryAddressId() {
        return primaryAddressId;
    }

    public void setPrimaryAddressId(String primaryAddressId) {
        this.primaryAddressId = primaryAddressId;
    }

    public ArrayList<AddressResponse> getAddressResponses() {
        return addressResponses;
    }

    public void setAddressResponses(ArrayList<AddressResponse> addressResponses) {
        this.addressResponses = addressResponses;
    }
}
