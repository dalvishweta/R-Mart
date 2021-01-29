package com.rmart.profile.model;

import com.google.gson.annotations.SerializedName;

public class CreditDetails {
    public boolean creditoption;
    public boolean SellingConsumer;

    public boolean getCreditoption() {
        return creditoption;
    }

    public void setCreditoption(boolean creditoption) {
        this.creditoption = creditoption;
    }

    public boolean getSellingConsumer() {
        return SellingConsumer;
    }

    public void setSellingConsumer(boolean sellingConsumer) {
        SellingConsumer = sellingConsumer;
    }
}

