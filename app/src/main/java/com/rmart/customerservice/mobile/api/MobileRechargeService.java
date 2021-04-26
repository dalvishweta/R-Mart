package com.rmart.customerservice.mobile.api;


import com.rmart.BuildConfig;
import com.rmart.customerservice.mobile.circle.model.CircleResponse;
import com.rmart.customerservice.mobile.models.MRechargeBaseClass;
import com.rmart.customerservice.mobile.models.ResponseGetHistory;
import com.rmart.customerservice.mobile.models.ResponseMobileRecharge;
import com.rmart.customerservice.mobile.models.mPlans.ResponseGetPlans;
import com.rmart.customerservice.mobile.models.mPlans.ResponseGetPostpaidPlans;
import com.rmart.customerservice.mobile.operators.model.OperatorResponse;
import com.rmart.electricity.RSAKeyResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MobileRechargeService {

    @POST(BuildConfig.RECHARGE)
    @FormUrlEncoded
    Call<ResponseMobileRecharge> prePaidRecharge(
            @Field("recharge_from") String rechargeFrom,
            @Field("plan_type") String planType,
            @Field("service") String service,
            @Field("pre_operator") String preOperator,
            @Field("mobile_number") String mobileNumber,
            @Field("recharge_amount") String rechargeAmount,
            @Field("user_id") String userID,
            @Field("mobile_operator") String mobileOperator,
            @Field("rec_type") String rechargeType,
            @Field("recharge_type") String recTypeParam, //TODO: pass 0 for top-up and 1 for any chosen plan
            @Field("mobileapp") String mobileApp,
            @Field("mobileversionid") String mobileVersionID,
            @Field("payment_type") String paymentType);

    @POST(BuildConfig.RECHARGE)
    @FormUrlEncoded
    Call<ResponseMobileRecharge> postPaidRecharge(
            @Field("recharge_from") String rechargeFrom,
            @Field("plan_type") String planType,
            @Field("service") String service,
            @Field("post_operator") String preOperator,
            @Field("mobile_number") String mobileNumber,
            @Field("recharge_amount") String rechargeAmount,
            @Field("user_id") String userID,
            @Field("mobile_operator") String mobileOperator,
            @Field("rec_type") String rechargeType,
            @Field("recharge_type") String recTypeParam, //TODO: pass 0 for top-up and 1 for any chosen plan
            @Field("mobileapp") String mobileApp,
            @Field("mobileversionid") String mobileVersionID,
            @Field("payment_type") String paymentType);

    @POST(BuildConfig.GET_PLANS)
    @FormUrlEncoded
    Call<ResponseGetPlans> getPrepaidPlans(
            @Field("operator") String operator,
            @Field("cricle") String cricle,
            @Field("service_type") String serviceType,
            @Field("mobileapp") String mobileApp,
            @Field("mobileversionid") String mobileVersionID
    );

    @POST(BuildConfig.GET_PLANS)
    @FormUrlEncoded
    Call<ResponseGetPlans> getPrepaidPlansPOST(
            @Field("operator") String operator,
            @Field("cricle") String cricle,
            @Field("service_type") String serviceType,
            @Field("mobileapp") String mobileApp,
            @Field("mobileversionid") String mobileVersionID
    );

    @POST(BuildConfig.GET_PLANS)
    @FormUrlEncoded
    Call<ResponseGetPostpaidPlans> getPostpaidPlans(
            @Field("operator") String operator,
            @Field("cricle") String cricle,
            @Field("service_type") String serviceType,
            @Field("mobile_no") String mobileNumber,
            @Field("mobileapp") String mobileApp,
            @Field("mobileversionid") String mobileVersionID
    );

    @POST(BuildConfig.HISTORY)
    @FormUrlEncoded
    Call<ResponseGetHistory> getHistory(@Field("user_id") String userID,@Field("service_id") String serviceID );

    @POST(BuildConfig.operator)
    @FormUrlEncoded
    Call<OperatorResponse> operators(@Field("type") String type);
    @GET(BuildConfig.location)
    Call<CircleResponse> circles();


    @POST(BuildConfig.Mobile_recharge)
    @FormUrlEncoded
    Call<MRechargeBaseClass> VRecharge(
            @Field("servicetype") int servicetype,
            @Field("pre_operator_dth") String pre_operator_dth,
            @Field("customer_number") String customer_number,
            @Field("rechargetype") String rechargetype,
            @Field("pre_operator") String pre_operator,
            @Field("post_operator") String post_operator,
            @Field("location") String location,
            @Field("mobile_number") String mobile_number,
            @Field("recharge_type") String recharge_type,
            @Field("recharge_amount") String recharge_amount, //TODO: pass 0 for top-up and 1 for any chosen plan
            @Field("user_id") String user_id,
            @Field("ccavenuedata") String ccavenue_data);

    @POST(BuildConfig.electricity_rsa_key)
    @FormUrlEncoded
    public Call<RSAKeyResponse> RsaKeyVRecharge(@Field("user_id") String user_id,
                                                @Field("txn_amount") String txn_amount,
                                                @Field("service_id") String service_id,
                                                @Field("service_name") String service_name
    );

}
