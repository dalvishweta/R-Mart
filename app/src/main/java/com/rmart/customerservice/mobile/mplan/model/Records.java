
package com.rmart.customerservice.mobile.mplan.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Records {

    @SerializedName("TOPUP")
    @Expose
    private List<Topup> topup = null;
    @SerializedName("3G/4G")
    @Expose
    private List<_3g4g> _3g4g = null;
    @SerializedName("RATE CUTTER")
    @Expose
    private List<RateCutter> rateCutter = null;
    @SerializedName("2G")
    @Expose
    private List<_2g> _2g = null;
    @SerializedName("SMS")
    @Expose
    private List<Sm> sms = null;
    @SerializedName("COMBO")
    @Expose
    private List<Combo> combo = null;

    public List<Topup> getTopup() {
        return topup;
    }

    public void setTopup(List<Topup> topup) {
        this.topup = topup;
    }

    public List<_3g4g> get3g4g() {
        return _3g4g;
    }

    public void set3g4g(List<_3g4g> _3g4g) {
        this._3g4g = _3g4g;
    }

    public List<RateCutter> getRateCutter() {
        return rateCutter;
    }

    public void setRateCutter(List<RateCutter> rateCutter) {
        this.rateCutter = rateCutter;
    }

    public List<_2g> get2g() {
        return _2g;
    }

    public void set2g(List<_2g> _2g) {
        this._2g = _2g;
    }

    public List<Sm> getSms() {
        return sms;
    }

    public void setSms(List<Sm> sms) {
        this.sms = sms;
    }

    public List<Combo> getCombo() {
        return combo;
    }

    public void setCombo(List<Combo> combo) {
        this.combo = combo;
    }

}
