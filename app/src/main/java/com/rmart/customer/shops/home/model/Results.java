package com.rmart.customer.shops.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Results {

    @SerializedName("type")
    public String type;
    @SerializedName("name")
    public String name;
    @SerializedName("category")
    public ArrayList<Category> category;
    @SerializedName("product_data")
    public ArrayList<ProductData> productData;


}
