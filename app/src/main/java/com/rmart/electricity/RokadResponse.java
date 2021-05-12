package com.rmart.electricity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RokadResponse implements Serializable {
    @SerializedName("ResponseStatus")
    @Expose
    private String responseStatus;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("MerTxnID")
    @Expose
    private String merTxnID;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMerTxnID() {
        return merTxnID;
    }

    public void setMerTxnID(String merTxnID) {
        this.merTxnID = merTxnID;
    }


}
