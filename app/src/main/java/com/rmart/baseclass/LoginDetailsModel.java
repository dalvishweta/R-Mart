package com.rmart.baseclass;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 08/10/20.
 */
public class LoginDetailsModel implements Serializable {

    private String mobileNumber;
    private String password;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
