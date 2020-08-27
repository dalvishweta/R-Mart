package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductPojo {
    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("product_name")
    @Expose
    String product_name;
    @SerializedName("product_desc")
    @Expose
    String product_desc;
    @SerializedName("category_id")
    @Expose
    String category_id;
    @SerializedName("created_date")
    @Expose
    String created_date;
    @SerializedName("created_by")
    @Expose
    String created_by;
}
