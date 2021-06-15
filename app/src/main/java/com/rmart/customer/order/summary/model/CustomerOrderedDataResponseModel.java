package com.rmart.customer.order.summary.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.customer.models.CustomerOrderPaymentInfoDetails;
import com.rmart.customer.models.CustomerOrderPersonalDetails;
import com.rmart.customer.models.CustomerOrderProductOrderedDetails;

import java.util.List;

public class CustomerOrderedDataResponseModel {

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

    @SerializedName("total_cart_count")
    @Expose
    public int  total_cart_count;


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

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public int getTotal_cart_count() {
        return total_cart_count;
    }

    public void setTotal_cart_count(int total_cart_count) {
        this.total_cart_count = total_cart_count;
    }
}
