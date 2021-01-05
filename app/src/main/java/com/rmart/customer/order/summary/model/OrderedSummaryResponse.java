package com.rmart.customer.order.summary.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;


public class OrderedSummaryResponse extends BaseResponse {


    @SerializedName("data")
    @Expose
    private CustomerOrderedDataResponseModel customerOrderedDataResponseModel;

    public CustomerOrderedDataResponseModel getCustomerOrderedDataResponseModel() {
        return customerOrderedDataResponseModel;
    }

    public void setCustomerOrderedDataResponseModel(CustomerOrderedDataResponseModel customerOrderedDataResponseModel) {
        this.customerOrderedDataResponseModel = customerOrderedDataResponseModel;
    }



}


