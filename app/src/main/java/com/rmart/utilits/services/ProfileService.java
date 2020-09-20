package com.rmart.utilits.services;

import com.rmart.BuildConfig;
import com.rmart.utilits.pojos.AddressListResponse;
import com.rmart.utilits.pojos.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProfileService {
    @FormUrlEncoded
    @POST(BuildConfig.ADD_ADDRESS)
    Call<AddressListResponse> addAddress(@Field("shop_name") String shopName,
                                         @Field("pan_no") String panNO,
                                         @Field("gstin_no") String gstIN,
                                         @Field("store_number") String storeNumber,
                                         @Field("address") String address,
                                         @Field("city") String city,
                                         @Field("state") String state,
                                         @Field("pincode") String pinCode,
                                         @Field("latitude") String latitude,
                                         @Field("longitude") String longitude,
                                         @Field("user_id") String userID,
                                         @Field("role_id") String roleID,
                                         @Field("delivery_radius") String deliveryRadius,
                                         @Field("client_id") String client_id,
                                         @Field("aadhar_front_image") String aadharFrontImage,
                                         @Field("aadhar_back_image") String aadharBackImage,
                                         @Field("pancard_image") String panCardImage);

    @FormUrlEncoded
    @POST(BuildConfig.UPDATE_ADDRESS)
    Call<AddressListResponse> updateAddress(@Field("shop_name") String shopName,
                                            @Field("pan_no") String panNO,
                                            @Field("gstin_no") String gstIN,
                                            @Field("store_number") String storeNumber,
                                            @Field("address") String address,
                                            @Field("city") String city,
                                            @Field("state") String state,
                                            @Field("pincode") String pinCode,
                                            @Field("latitude") String latitude,
                                            @Field("longitude") String longitude,
                                            @Field("user_id") String userID,
                                            @Field("role_id") String roleID,
                                            @Field("delivery_radius") String deliveryRadius,
                                            @Field("client_id") String client_id,
                                            @Field("id") String id

    );

    @FormUrlEncoded
    @POST(BuildConfig.UPDATE_PROFILE)
    Call<LoginResponse> updateProfile (@Field("mobile") String mobile,
                                       @Field("full_name") String fullName,
                                       @Field("last_name") String lastName,
                                       @Field("user_id") String user_id,
                                       @Field("gender") String gender,
                                       @Field("email") String email,
                                       @Field("primary_add_id") String primary_add_id,
                                       @Field("image") String profileImage);
}
