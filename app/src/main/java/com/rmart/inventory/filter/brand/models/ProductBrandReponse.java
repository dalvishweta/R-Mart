package com.rmart.inventory.filter.brand.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.retiler.inventory.brand.model.Brand;
import com.rmart.utilits.pojos.BaseResponse;
import java.util.ArrayList;

public class ProductBrandReponse extends BaseResponse {
  @SerializedName("Code")
  @Expose
  private String Code;
  @SerializedName("start_index")
  @Expose
  private String startIndex;
  @SerializedName("end_index")
  @Expose
  private String endIndex;
  @SerializedName("count")
  @Expose
  private int count;
  @SerializedName("type")
  @Expose
  private String type;

  public String getCode() {
    return Code;
  }

  public String getStartIndex() {
    return startIndex;
  }

  public String getEndIndex() {
    return endIndex;
  }

  public int getCount() {
    return count;
  }

  public String getType() {
    return type;
  }

  public ArrayList<Brand> getBrand() {
    return brand;
  }

  @SerializedName("data")
  @Expose
  private ArrayList<Brand> brand;
}
