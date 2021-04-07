
package com.rmart.customerservice.postpaidopertor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostOperators {

    @SerializedName("ATM")
    @Expose
    private String atm;
    @SerializedName("ARL")
    @Expose
    private String arl;
    @SerializedName("BSL")
    @Expose
    private String bsl;
    @SerializedName("BSM")
    @Expose
    private String bsm;
    @SerializedName("IDL")
    @Expose
    private String idl;
    @SerializedName("IDM")
    @Expose
    private String idm;
    @SerializedName("VFM")
    @Expose
    private String vfm;
    @SerializedName("TIL")
    @Expose
    private String til;
    @SerializedName("TIM")
    @Expose
    private String tim;

    public String getAtm() {
        return atm;
    }

    public void setAtm(String atm) {
        this.atm = atm;
    }

    public String getArl() {
        return arl;
    }

    public void setArl(String arl) {
        this.arl = arl;
    }

    public String getBsl() {
        return bsl;
    }

    public void setBsl(String bsl) {
        this.bsl = bsl;
    }

    public String getBsm() {
        return bsm;
    }

    public void setBsm(String bsm) {
        this.bsm = bsm;
    }

    public String getIdl() {
        return idl;
    }

    public void setIdl(String idl) {
        this.idl = idl;
    }

    public String getIdm() {
        return idm;
    }

    public void setIdm(String idm) {
        this.idm = idm;
    }

    public String getVfm() {
        return vfm;
    }

    public void setVfm(String vfm) {
        this.vfm = vfm;
    }

    public String getTil() {
        return til;
    }

    public void setTil(String til) {
        this.til = til;
    }

    public String getTim() {
        return tim;
    }

    public void setTim(String tim) {
        this.tim = tim;
    }

}
