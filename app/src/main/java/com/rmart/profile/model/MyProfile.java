package com.rmart.profile.model;

import com.rmart.R;

import java.util.ArrayList;

public class MyProfile {

    static MyProfile myProfile;
    public static final boolean DELIVERY = MyProfile.getInstance().getRoleType().equals("delivery");
    public static final boolean RETAILER = MyProfile.getInstance().getRoleType().equals("retailer");
    public static final boolean CUSTOMER = MyProfile.getInstance().getRoleType().equals("retailer");
    public static MyProfile getInstance() {
        if(null == myProfile) {
            myProfile = new MyProfile();
        }
        return myProfile;
    }
    private  MyProfile() {
        imageImages = new ArrayList<>();
        imageImages.add(R.drawable.supermarket1);
        imageImages.add(R.drawable.supermarket2);
        imageImages.add(R.drawable.supermarket3);
        myLocations.add(new MyLocation());
    }
    String roleType = "retailer";
    // RETAILER DETAILS
    String firstName = "Vamshee Krishna";
    String lastName = "Paleti";
    String email = "vamsheekrishna.paleti@gmail.com";
    String mobileNumber = "7416226233";

    // BUSINESS DETAILS
    String shopName = "Vamshee Food Enterprises";
    ArrayList<Integer> imageImages;
    String gstNumber = "gst-123456789";
    String panNumber = "BOMPP1130D1";

    ArrayList<MyLocation> myLocations = new ArrayList<>();

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public ArrayList<Integer> getImageImages() {
        return imageImages;
    }

    public void setImageImages(ArrayList<Integer> imageImages) {
        this.imageImages = imageImages;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public ArrayList<MyLocation> getMyLocations() {
        return myLocations;
    }

    public void setMyLocations(ArrayList<MyLocation> myLocations) {
        this.myLocations = myLocations;
    }
}
