
package com.rmart.customerservice.postpaidopertor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("img_name")
    @Expose
    private String imgName;
    @SerializedName("img_path")
    @Expose
    private String imgPath;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

}
