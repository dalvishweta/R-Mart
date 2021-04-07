
package com.rmart.customerservice.prepaidopertor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("post_operators")
    @Expose
    private PrePaidOperators postOperators;
    @SerializedName("service_id")
    @Expose
    private int serviceId;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("service_detail")
    @Expose
    private String serviceDetail;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    public PrePaidOperators getPostOperators() {
        return postOperators;
    }

    public void setPostOperators(PrePaidOperators postOperators) {
        this.postOperators = postOperators;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDetail() {
        return serviceDetail;
    }

    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}
