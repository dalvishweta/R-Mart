package com.rmart.authentication.referral.api;

import com.rmart.BuildConfig;
import com.rmart.authentication.referral.models.ReferralResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ReferralService {

    @POST(BuildConfig.Referral_CodeInfo)
    @FormUrlEncoded
    Call<ReferralResponse> getRefferealInfo(@Field("refer_no") String refer_no,@Field("short_link") String short_link,@Field("long_link")String long_link,@Field("role_id") String role_id,@Field("client_id") String client_id,@Field("share_by_ref") String share_by_ref);
}
