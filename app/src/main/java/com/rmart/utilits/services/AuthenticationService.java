package com.rmart.utilits.services;

import com.rmart.BuildConfig;
import com.rmart.utilits.pojos.BaseResponse;
import com.rmart.utilits.pojos.ChangePasswordResponse;
import com.rmart.utilits.pojos.ForgotPasswordResponse;
import com.rmart.utilits.pojos.LoginResponse;
import com.rmart.utilits.pojos.RegistrationResponse;
import com.rmart.utilits.pojos.ResendOTPResponse;
import com.rmart.utilits.pojos.ResponseUpdateProfile;
import com.rmart.utilits.pojos.ValidateOTP;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthenticationService {
    @POST(BuildConfig.LOGIN)
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("device_id") String deviceKey,
                              @Field("username") String username,
                              @Field("password") String password,
                              @Field("role_id") String role_id);


    @GET(BuildConfig.FEGISTRATION_FEE)
    Call<RegPayAmtResponse> regPayAmt();

/*    @GET(BuildConfig.REGISTRATION)
    Call<RegistrationResponse> registration();*/

    @FormUrlEncoded
    @POST(BuildConfig.REGISTRATION)
    Call<RegistrationResponse> registration(@Field("full_name") String fName,
                                            @Field("last_name") String lName,
                                            @Field("mobile_number") String mobile,
                                            @Field("email") String email,
                                            @Field("gender") String gender,
                                            @Field("password") String password,
                                            @Field("roll") String roll_no,
                                            @Field("client_id") String client_id);

    @FormUrlEncoded
    @POST(BuildConfig.VALIDATE_OTP)
    Call<ValidateOTP> validateOTP(@Field("mobile") String mobile,
                                  @Field("otp") String otp);

    @POST(BuildConfig.FORGOT_PASSWORD)
    @FormUrlEncoded
    Call<ForgotPasswordResponse> forgotPassword(@Field("mobile_number") String mobile,
                                                @Field("role_id") String role_id);

    @POST(BuildConfig.CHANGE_PASSWORD)
    @FormUrlEncoded
    Call<ChangePasswordResponse> changePassword(
            @Field("mobile") String mobileno,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password,
            @Field("role_id") String role_id);

    @POST(BuildConfig.CHANGE_PASSWORD_OTP)
    @FormUrlEncoded
    Call<ChangePasswordResponse> changePasswordOTP(
            @Field("mobile") String mobileno,
            @Field("otp") String otp,
            @Field("cpassword") String new_password,
            @Field("role_id") String role_id);

    @POST(BuildConfig.RESEND_OTP)
    @FormUrlEncoded
    Call<ResendOTPResponse> resendOTP(@Field("mobile") String mobile,
                                      @Field("client_id") String client_id);

    @POST(BuildConfig.PROFILE_UPDATE)
    @FormUrlEncoded
    Call<ResponseUpdateProfile> updateProfile(@Field("user_id") String userID,
                                              @Field("first_name") String firstName,
                                              @Field("last_name") String lastName,
                                              @Field("gender") String gender,
                                              @Field("email") String email,
                                              @Field("address") String address,
                                              @Field("address2") String address2,
                                              @Field("pincode") String pincode,
                                              @Field("dob") String dob,
                                              @Field("image") String image,
                                              @Field("state") String state);

    @POST(BuildConfig.RESEND_OTP)
    @FormUrlEncoded
    Call<ResendOTPResponse> resendMailOTP(@Field("email") String email,
                                          @Field("client_id") String client_id);

    @POST(BuildConfig.VALIDATE_OTP)
    @FormUrlEncoded
    Call<ValidateOTP> validateMailOTP(@Field("email") String email,
                                      @Field("otp") String otp);

//    @GET(BuildConfig.PRODUCTS)
//    Call<List<ProductPojo>> getProducts();
/*
    @GET(BuildConfig.REGISTRATION)
    Call<List<BaseResponse>> getREGISTRATION();*/

}