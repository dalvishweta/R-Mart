
package com.rmart.customerservice.mobile.mplan.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topup {

    @SerializedName("rs")
    @Expose
    private int rs;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("validity")
    @Expose
    private String validity;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
