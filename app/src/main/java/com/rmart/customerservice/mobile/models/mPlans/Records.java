
package com.rmart.customerservice.mobile.models.mPlans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Records implements Serializable {

    @SerializedName("TOPUP")
    @Expose
    private ArrayList<RechargePlans> tOPUP = new ArrayList<>();
    @SerializedName("RATE CUTTER")
    @Expose
    private ArrayList<RechargePlans> rATECUTTER = new ArrayList<>();
    @SerializedName("SMS")
    @Expose
    private ArrayList<RechargePlans> sMS = new ArrayList<>();
    @SerializedName("Romaing")
    @Expose
    private ArrayList<RechargePlans> romaing = new ArrayList<>();
    @SerializedName("COMBO")
    @Expose
    private ArrayList<RechargePlans> cOMBO = new ArrayList<>();

    @SerializedName("3G/4G")
    @Expose
    private ArrayList<RechargePlans> G4G3 = new ArrayList<>();

    @SerializedName("FULLTT")
    @Expose
    private ArrayList<RechargePlans> fullTT = new ArrayList<>();

    @SerializedName("FRC")
    @Expose
    private ArrayList<RechargePlans> FRC = new ArrayList<>();

    @SerializedName("2G")
    @Expose
    private ArrayList<RechargePlans> G2 = new ArrayList<>();

    private List<RechargePlans> specialPlan = new ArrayList<>();

    public List<RechargePlans> getG4G3() {
        return G4G3;
    }

    public void setG4G3(ArrayList<RechargePlans> g4G3) {
        G4G3 = g4G3;
    }

    public List<RechargePlans> getG2() {
        return G2;
    }

    public void setG2(ArrayList<RechargePlans> g2) {
        G2 = g2;
    }

    public List<RechargePlans> getTOPUP() {
        return tOPUP;
    }

    public void setTOPUP(ArrayList<RechargePlans> tOPUP) {
        this.tOPUP = tOPUP;
    }

    public List<RechargePlans> getRATECUTTER() {
        return rATECUTTER;
    }

    public void setRATECUTTER(ArrayList<RechargePlans> rATECUTTER) {
        this.rATECUTTER = rATECUTTER;
    }

    public List<RechargePlans> getSMS() {
        return sMS;
    }

    public void setSMS(ArrayList<RechargePlans> sMS) {
        this.sMS = sMS;
    }

    public List<RechargePlans> getRomaing() {
        return romaing;
    }

    public void setRomaing(ArrayList<RechargePlans> romaing) {
        this.romaing = romaing;
    }

    public List<RechargePlans> getCOMBO() {
        return cOMBO;
    }

    public void setCOMBO(ArrayList<RechargePlans> cOMBO) {
        this.cOMBO = cOMBO;
    }

    public void setSpecialPlans(List<RechargePlans> _specialPlan) {
        specialPlan = _specialPlan;
    }

    public List<RechargePlans> getSpecialPlan() {
        return specialPlan;
    }

    public ArrayList<RechargePlans> getFullTT() {
        return fullTT;
    }

    public void setFullTT(ArrayList<RechargePlans> fullTT) {
        this.fullTT = fullTT;
    }

    public ArrayList<RechargePlans> getFRC() {
        return FRC;
    }

    public void setFRC(ArrayList<RechargePlans> FRC) {
        this.FRC = FRC;
    }
}
