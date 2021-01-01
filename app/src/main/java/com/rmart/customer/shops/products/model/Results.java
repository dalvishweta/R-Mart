package com.rmart.customer.shops.products.model;

import com.google.gson.annotations.SerializedName;
import com.rmart.customer.shops.home.model.Category;
import com.rmart.customer.shops.home.model.ProductData;

import java.util.ArrayList;

public class Results {


    @SerializedName("category")
    public ArrayList<Category> category;
    @SerializedName("total_product_count")
    public int total_product_count;
    @SerializedName("product_data")
    public ArrayList<ProductData> productData;



}
