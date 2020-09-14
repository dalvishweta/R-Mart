package com.rmart.customer.models;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 13/09/20.
 */
public class ContentModel implements Serializable {

    private String status;
    private Object value;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
