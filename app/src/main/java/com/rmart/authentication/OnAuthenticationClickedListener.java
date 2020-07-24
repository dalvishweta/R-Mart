package com.rmart.authentication;

public interface OnAuthenticationClickedListener {
    void goToHomeActivity();
    void goToForgotPassword();
    void validateOTP();
    void changePassword();
    void goToProfileActivity();
    boolean showPermission();
}
