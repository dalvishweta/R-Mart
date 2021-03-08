package com.rmart.electricity;

import com.rmart.customer.models.RSAKeyResponseDetails;

public interface OnAuthenticationClickedListener {
    void goToHomeActivity();
    void goToForgotPassword();
    void goToRegistration();
    void validateOTP(String mobileNumber, boolean closePreviousScreen);
    void changePassword(String otp, String mobileNumber);

    void goToProfileActivity(boolean isAddressAdded);

    void validateMailOTP();

    void goToHomePage();

    void goToCustomerHomeActivity();

    void proceedToPayment(RSAKeyResponseDetails mobileNumber);
}

