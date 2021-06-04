package com.rmart.profile.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.rmart.utilits.pojos.AddressResponse;
import com.rmart.utilits.pojos.ProfileResponse;

import java.util.ArrayList;

public class MyProfile {

    static MyProfile myProfile = null;
    public static final String DELIVERY = "Delivery";
    public static final String RETAILER = "Retailer";
    public static final String CUSTOMER = "Customer";
    public static final String PREF_NAME = "Rokad Client";
    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private String userID;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String email;
    private String mobileNumber;
    private String vendorMobileNumber;
    private ProfileResponse vendorInfo = new ProfileResponse();
    private String isAuthenticated;
    private String profileImage;
    private String roleID;
    private String deliveryInDays;
    private Boolean wholeselar;
    private Boolean credit_option;
    private String primaryAddressId;
    private String Wallet_id;
    public Boolean getWholeselar() {
        return wholeselar;
    }

    public Boolean getCredit_option() {
        return credit_option;
    }


    private ArrayList<AddressResponse> addressResponses;
    private MutableLiveData<Bitmap> userProfileImage = new MutableLiveData<>();
    private MutableLiveData<Integer> cartCount = new MutableLiveData<>(0);

    public static MyProfile getInstance(Context context) {
           Gson gson = new Gson();

            SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            String json =  sharedPref.getString(CUSTOMER,null);
            myProfile =gson.fromJson(json,MyProfile.class);



        return myProfile;
    }

    public void setWholeselar(Boolean wholeselar) {
        this.wholeselar = wholeselar;
    }

    public void setCredit_option(Boolean credit_option) {
        this.credit_option = credit_option;
    }

    public static void setInstance(Context context,ProfileResponse profileResponse) {
        myProfile = new MyProfile();
        myProfile.userID = profileResponse.getUserID();
        myProfile.credit_option = profileResponse.getCredit_option();
        myProfile.wholeselar = profileResponse.getWholeselar();
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
        myProfile.Wallet_id=profileResponse.getWallet_id();
        ProfileResponse vendor = profileResponse.getVendorData();
        if (null != vendor) {
            myProfile.vendorInfo = vendor;
        }
        if(profileResponse.getAddressResponses().size() > 0) {
            myProfile.addressResponses = profileResponse.getAddressResponses();
        }
        Gson gson = new Gson();
        String str = gson.toJson(myProfile);
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CUSTOMER, str);
        editor.apply();
        editor.commit();


    }

    public String getVendorMobileNumber() {
        return vendorMobileNumber;
    }

    public void setVendorMobileNumber(String vendorMobileNumber) {
        this.vendorMobileNumber = vendorMobileNumber;
    }

    public String getWallet_id() {
        return Wallet_id;
    }

    public void setWallet_id(String wallet_id) {
        Wallet_id = wallet_id;
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

    public void setAddressResponses(ArrayList<AddressResponse> addressResponses,Context c) {


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

    public ProfileResponse getVendorInfo() {
        return vendorInfo;
    }

    public void setVendorInfo(ProfileResponse vendorInfo) {
        this.vendorInfo = vendorInfo;
    }

    public MutableLiveData<Integer> getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount.setValue(cartCount);
    }
}
