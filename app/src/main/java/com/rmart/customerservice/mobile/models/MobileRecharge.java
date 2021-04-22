package com.rmart.customerservice.mobile.models;


import android.content.Context;

import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.profile.model.MyProfile;

import java.util.ArrayList;
import java.util.HashMap;

public class MobileRecharge {

    private ArrayList<SubscriberModule> prepaidSubscriberList;
    HashMap<String,  SubscriberModule> prepaidSubscriberMap;
    private String rechargeFrom = "mobile";
    private String planType = "";
    private String service = "TSO";
    private String mobileNumber = "";
    private String rechargeAmount ="";

    private String preOperator = ""; // V
    private String mobileOperator =""; // Vodafone
    private String rechargeType = "0"; //TODO: pass 0 for topup and 1 for any chosen plan
    private String stateName;
    private String paymentType = "cash";
    private int selectedSubscriber = -1;
    private String vcNumber = "";
    public MobileRecharge(Context context) {
        this.prepaidSubscriberList = new ArrayList<>();
        prepaidSubscriberMap = new HashMap<>();
        this.prepaidSubscriberList.add(new SubscriberModule(0, R.drawable.airtel, "AIRTEL", "A","Airtel"));
        prepaidSubscriberMap.put("A", prepaidSubscriberList.get(0));
        this.prepaidSubscriberList.add(new SubscriberModule(1, R.drawable.airtel, "AIRTEL LANDLINE ", "A","Airtel"));
        prepaidSubscriberMap.put("A", prepaidSubscriberList.get(1));

        this.prepaidSubscriberList.add(new SubscriberModule(2, R.drawable.bsnl, "BSNL LANDLINE", "B", "BSNL"));
        prepaidSubscriberMap.put("B", prepaidSubscriberList.get(2));
        this.prepaidSubscriberList.add(new SubscriberModule(3, R.drawable.bsnl, "BSNL", "B", "BSNL"));
        prepaidSubscriberMap.put("B", prepaidSubscriberList.get(3));


        this.prepaidSubscriberList.add(new SubscriberModule(4, R.drawable.idea, "IDEA LANDLINE ", "I", "idea"));
        prepaidSubscriberMap.put("I", prepaidSubscriberList.get(4));
        this.prepaidSubscriberList.add(new SubscriberModule(5, R.drawable.idea, "IDEA", "I", "idea"));
        prepaidSubscriberMap.put("I", prepaidSubscriberList.get(5));

        this.prepaidSubscriberList.add(new SubscriberModule(6, R.drawable.vodafone, "Vodafone", "V", "Vodafone"));
        prepaidSubscriberMap.put("V", prepaidSubscriberList.get(6));

        this.prepaidSubscriberList.add(new SubscriberModule(7,R.drawable.indicom,"Tata Indicom", "TI","Tata Indicom"));
        prepaidSubscriberMap.put("TI", prepaidSubscriberList.get(7));
        this.prepaidSubscriberList.add(new SubscriberModule(8,R.drawable.indicom,"TATA INDI LANDLINE", "TI","Tata Indicom"));
        prepaidSubscriberMap.put("TI", prepaidSubscriberList.get(8));


    }

    public ArrayList<SubscriberModule> getPrepaidSubscriberList() {
        this.prepaidSubscriberList = new ArrayList<>();
        prepaidSubscriberMap = new HashMap<>();
        this.prepaidSubscriberList.add(new SubscriberModule(0, R.drawable.airtel, "Airtel", "A","Airtel"));
        prepaidSubscriberMap.put("A", prepaidSubscriberList.get(0));
        this.prepaidSubscriberList.add(new SubscriberModule(1, R.drawable.reliance, "Reliance GSM", "RG",""));
        prepaidSubscriberMap.put("RG", prepaidSubscriberList.get(1));
        this.prepaidSubscriberList.add(new SubscriberModule(2, R.drawable.bsnl, "BSNL", "B", "BSNL"));
        prepaidSubscriberMap.put("B", prepaidSubscriberList.get(2));
      /*  this.prepaidSubscriberList.add(new SubscriberModule(3, R.drawable.idea, "Idea", "I", "idea"));
        prepaidSubscriberMap.put("I", prepaidSubscriberList.get(3));*/
        this.prepaidSubscriberList.add(new SubscriberModule(3, R.drawable.vodafone, "Vodafone", "V", "Vodafone"));
        prepaidSubscriberMap.put("V", prepaidSubscriberList.get(3));
        this.prepaidSubscriberList.add(new SubscriberModule(4,R.drawable.jio,"JIO","JOE", "jio"));
        prepaidSubscriberMap.put("JOE", prepaidSubscriberList.get(4));
        this.prepaidSubscriberList.add(new SubscriberModule(5,R.drawable.docomo,"Tata Docomo","TD",""));
        prepaidSubscriberMap.put("TD", prepaidSubscriberList.get(5));
        this.prepaidSubscriberList.add(new SubscriberModule(6,R.drawable.indicom,"Tata Indicom", "TI","Tata Indicom"));
        prepaidSubscriberMap.put("TI", prepaidSubscriberList.get(6));
        this.prepaidSubscriberList.add(new SubscriberModule(7,R.drawable.aircel,"VI", "V","VI"));
        prepaidSubscriberMap.put("AI", prepaidSubscriberList.get(7));
       /* this.prepaidSubscriberList.add(new SubscriberModule(8,R.drawable.aircel,"Aircel", "AI",""));
        prepaidSubscriberMap.put("AI", prepaidSubscriberList.get(8));*/

        return prepaidSubscriberList;
    }



    public SubscriberModule getPrepaidSubscriber(String key) {
        if(prepaidSubscriberMap.containsKey(key)) {
            return prepaidSubscriberMap.get(key);
        } else {
            return prepaidSubscriberMap.get("AI");
        }
    }

    public int getSelectedSubscriber() {
        return selectedSubscriber;
    }

    public void setSelectedSubscriber(int selectedSubscriber) {
        this.selectedSubscriber = selectedSubscriber;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getMobileAppVersionId() {
        return mobileAppVersionId;
    }

    public void setMobileAppVersionId(String mobileAppVersionId) {
        this.mobileAppVersionId = mobileAppVersionId;
    }

    public String getMobileApp() {
        return mobileApp;
    }

    public void setMobileApp(String mobileApp) {
        this.mobileApp = mobileApp;
    }

    private String mobileAppVersionId =String.valueOf( BuildConfig.VERSION_CODE);
    private String mobileApp = BuildConfig.VERSION_NAME;

    public String getRecType() {
        return recType;
    }

    public void setRecType(String recType) {
        this.recType = recType;
    }

    private String recType = "Prepaid";
    private int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getRechargeFrom() {
        return rechargeFrom;
    }

    public void setRechargeFrom(String rechargeFrom) {
        this.rechargeFrom = rechargeFrom;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPreOperator() {
        return preOperator;
    }

    public String getVcNumber() {
        return vcNumber;
    }

    public void setVcNumber(String vcNumber) {
        this.vcNumber = vcNumber;
    }

    public void setPreOperator(String preOperator) {
        this.preOperator = preOperator;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }



    public String getMobileOperator() {
        return mobileOperator;
    }

    public void setMobileOperator(String mobileOperator) {
        this.mobileOperator = mobileOperator;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }

}
