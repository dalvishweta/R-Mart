package com.rmart.profile.model;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

import com.rmart.utilits.pojos.AddressResponse;
import com.rmart.utilits.pojos.ProfileResponse;

import java.util.ArrayList;

public class MyProfile {

    static MyProfile myProfile = null;

    public static final String DELIVERY = "Delivery";
    public static final String RETAILER = "Retailer";
    public static final String CUSTOMER = "Customer";

    private String userID;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String email;
    private String mobileNumber;
    private String isAuthenticated;
    private String profileImage;
    private String roleID;
    private String deliveryInDays = "5";
    private String primaryAddressId;
    private ArrayList<AddressResponse> addressResponses;
    private MutableLiveData<Bitmap> userProfileImage = new MutableLiveData<>();
    private MutableLiveData<Integer> cartCount = new MutableLiveData<>();

    public static MyProfile getInstance() {
        return myProfile;
    }

    public static void setInstance(ProfileResponse profileResponse) {
        myProfile = new MyProfile();
        myProfile.userID = profileResponse.getUserID();
        myProfile.firstName = profileResponse.getFirstName();
        myProfile.lastName = profileResponse.getLastName();
        myProfile.gender = profileResponse.getGender();
        myProfile.dob = profileResponse.getDob();
        myProfile.email = profileResponse.getEmail();
        myProfile.mobileNumber = profileResponse.getMobileNumber();
        myProfile.isAuthenticated = profileResponse.getIsAuthenticated();
        myProfile.profileImage = profileResponse.getProfileImage();
        myProfile.roleID = profileResponse.getRoleID();
        myProfile.primaryAddressId = profileResponse.getPrimaryAddressId();
        if(profileResponse.getAddressResponses().size() > 0) {
            myProfile.setAddressResponses(profileResponse.getAddressResponses());
        }

    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        userID = userID;
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
        if(null == addressResponses) {
            addressResponses = new ArrayList<>();
        }
        return addressResponses;
    }

    public void setAddressResponses(ArrayList<AddressResponse> addressResponses) {
        this.addressResponses = addressResponses;
    }

    public String getDeliveryInDays() {
        return deliveryInDays;
    }

    public void setDeliveryInDays(String deliveryInDays) {
        this.deliveryInDays = deliveryInDays;
    }

    public MutableLiveData<Bitmap> getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(Bitmap userImageBitmap) {
        userProfileImage.setValue(userImageBitmap);
    }

    public MutableLiveData<Integer> getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount.setValue(cartCount);
    }
}
