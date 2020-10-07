package com.rmart.utilits.ccavenue;

import java.io.Serializable;

public class CCAvenueResponse implements Serializable {
    String  order_id;
    String  tracking_id;
    String  bank_ref_no;
    String  payment_mode;
    String  order_status;
    String  order_message;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTracking_id() {
        return tracking_id;
    }

    public void setTracking_id(String tracking_id) {
        this.tracking_id = tracking_id;
    }

    public String getBank_ref_no() {
        return bank_ref_no;
    }

    public void setBank_ref_no(String bank_ref_no) {
        this.bank_ref_no = bank_ref_no;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_message() {
        return order_message;
    }

    public void setOrder_message(String order_message) {
        this.order_message = order_message;
    }
}
