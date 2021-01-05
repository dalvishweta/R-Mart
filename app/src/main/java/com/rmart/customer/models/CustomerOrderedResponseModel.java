package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

import java.util.List;

/**
 * Created by Satya Seshu on 22/09/20.
 */
public class CustomerOrderedResponseModel extends BaseResponse {


    @SerializedName("data")
    @Expose
    private CustomerOrderedDataResponseModel customerOrderedDataResponseModel;

    public CustomerOrderedDataResponseModel getCustomerOrderedDataResponseModel() {
        return customerOrderedDataResponseModel;
    }

    public void setCustomerOrderedDataResponseModel(CustomerOrderedDataResponseModel customerOrderedDataResponseModel) {
        this.customerOrderedDataResponseModel = customerOrderedDataResponseModel;
    }

    public static class CustomerOrderedDataResponseModel {

        @SerializedName("delivery_method")
        @Expose
        public String deliveryMethod;
        @SerializedName("customer_detail")
        @Expose
        private CustomerOrderPersonalDetails customerOrderPersonalDetails;

        @SerializedName("payment_info")
        @Expose
        private CustomerOrderPaymentInfoDetails customerOrderPaymentInfoDetails;

        @SerializedName("products_ordered")
        @Expose
        private List<CustomerOrderProductOrderedDetails> customerOrderProductDetailsList;

        public CustomerOrderPersonalDetails getCustomerOrderPersonalDetails() {
            return customerOrderPersonalDetails;
        }

        public void setCustomerOrderPersonalDetails(CustomerOrderPersonalDetails customerOrderPersonalDetails) {
            this.customerOrderPersonalDetails = customerOrderPersonalDetails;
        }

        public CustomerOrderPaymentInfoDetails getCustomerOrderPaymentInfoDetails() {
            return customerOrderPaymentInfoDetails;
        }

        public void setCustomerOrderPaymentInfoDetails(CustomerOrderPaymentInfoDetails customerOrderPaymentInfoDetails) {
            this.customerOrderPaymentInfoDetails = customerOrderPaymentInfoDetails;
        }

        public List<CustomerOrderProductOrderedDetails> getCustomerOrderProductDetailsList() {
            return customerOrderProductDetailsList;
        }

        public void setCustomerOrderProductDetailsList(List<CustomerOrderProductOrderedDetails> customerOrderProductDetailsList) {
            this.customerOrderProductDetailsList = customerOrderProductDetailsList;
        }
    }

}
