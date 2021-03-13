package com.rmart.customer.shops_new.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopHomePageResponce  extends BaseResponse {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("viewmore")
    @Expose
    private Object viewmore;
    @SerializedName("sliders")
    @Expose
    private List<Slider> sliders = null;
    @SerializedName("service")
    @Expose
    private List<ServiceOffer> service = null;
    @SerializedName("bigShopType")
    @Expose
    private List<BigShopType> bigShopType = null;
    @SerializedName("ShopTypes")
    @Expose
    private List<ShopType> shopTypes = null;
    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getViewmore() {
        return viewmore;
    }

    public void setViewmore(Object viewmore) {
        this.viewmore = viewmore;
    }

    public List<Slider> getSliders() {
        return sliders;
    }

    public void setSliders(List<Slider> sliders) {
        this.sliders = sliders;
    }

    public List<ServiceOffer> getService() {
        return service;
    }

    public void setService(List<ServiceOffer> serviceOffer) {
        this.service = serviceOffer;
    }

    public List<BigShopType> getBigShopType() {
        return bigShopType;
    }

    public void setBigShopType(List<BigShopType> bigShopType) {
        this.bigShopType = bigShopType;
    }

    public List<ShopType> getShopTypes() {
        return shopTypes;
    }

    public void setShopTypes(List<ShopType> shopTypes) {
        this.shopTypes = shopTypes;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

}

