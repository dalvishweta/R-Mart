package com.rmart.authentication;

public interface OnAuthenticationClickedListener {
    void goToHomeActivity();
    void goToForgotPassword();
    void goToRegistration();
    void validateOTP();
    void changePassword();
    void goToProfileActivity();

    void validateMailOTP();
}
