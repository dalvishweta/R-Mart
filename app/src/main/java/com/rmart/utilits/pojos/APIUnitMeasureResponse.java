package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIUnitMeasureResponse extends BaseResponse {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("attributes_name")
    @Expose
    String attributesName;

    @SerializedName("attributes_desc")
    @Expose
    String attributesDesc;

    /*@SerializedName("short_name")
    @Expose
    String shortName;*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttributesName() {
        return attributesName;
    }

    public void setAttributesName(String attributesName) {
        this.attributesName = attributesName;
    }

    public String getAttributesDesc() {
        return attributesDesc;
    }

    public void setAttributesDesc(String attributesDesc) {
        this.attributesDesc = attributesDesc;
    }

   /* public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }*/
}
