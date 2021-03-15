package com.rmart.customer.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class HomePageData {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("viewmore")
    @Expose
    private String viewmore;
    @SerializedName("sliders")
    @Expose
    private ArrayList<SliderImages> sliders = null;
    @SerializedName("service")
    @Expose
    private ArrayList<ServiceOffer> service = null;
    @SerializedName("bigShopType")
    @Expose
    private ArrayList<BigShopType> bigShopType = null;
    @SerializedName("ShopTypes")
    @Expose
    private ArrayList<ShopType> shopTypes = null;
    @SerializedName("offers")
    @Expose
    private ArrayList<Offer> offers = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getViewmore() {
        return viewmore;
    }

    public void setViewmore(String viewmore) {
        this.viewmore = viewmore;
    }

    public ArrayList<SliderImages> getSliders() {
        return sliders;
    }

    public void setSliders(ArrayList<SliderImages> sliders) {
        this.sliders = sliders;
    }

    public ArrayList<ServiceOffer> getService() {
        return service;
    }

    public void setService(ArrayList<ServiceOffer> serviceOffer) {
        this.service = serviceOffer;
    }

    public ArrayList<BigShopType> getBigShopType() {
        return bigShopType;
    }

    public void setBigShopType(ArrayList<BigShopType> bigShopType) {
        this.bigShopType = bigShopType;
    }

    public ArrayList<ShopType> getShopTypes() {
        return shopTypes;
    }

    public void setShopTypes(ArrayList<ShopType> shopTypes) {
        this.shopTypes = shopTypes;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }

}

