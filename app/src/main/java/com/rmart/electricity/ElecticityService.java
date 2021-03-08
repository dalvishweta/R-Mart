package com.rmart.electricity;


import com.rmart.BuildConfig;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ElecticityService {
    @POST(BuildConfig.electricity_fetch_bill)
    @FormUrlEncoded
    public Call<ElecProcessPOJO> electicityProcess(@Field("user_id") String user_id,
                                                   @Field("operator") String operator,
                                                   @Field("ConsumerID") String bankIINNumber,
                                                   @Field("bill_unit") String bill_unit,
                                                   @Field("mobile_number") String mobileNumber);
    @POST(BuildConfig.electricity_pay_bill)
    @FormUrlEncoded
    public Call<paybill> electicitybillProcess(@Field("user_id") String user_id,
                                                       @Field("operator") String operator,
                                                   @Field("ConsumerID") String bankIINNumber,
                                                   @Field("bill_unit") String bill_unit,
                                                   @Field("mobile_number") String mobileNumber,
                                                       @Field("amount") String amount,
                                                       @Field("ConsumerName") String ConsumerName,
                                                        @Field("Orderid") String Orderid,
                                                       @Field("ccavenuedata") String ccavenuedata

    );

    @POST(BuildConfig.electricity_rsa_key)
    @FormUrlEncoded
    public Call<rsakeyResponse> electicityProcessRsaKey(@Field("user_id") String user_id,
                                                   @Field("txn_amount") String txn_amount,
                                                   @Field("service_id") String service_id,
                                                        @Field("service_name") String service_name,
                                                   @Field("order_id") String order_id);
    @POST(BuildConfig.electricity_rsa_key)
    @FormUrlEncoded
    public Call<rsakeyResponse> electicityProcessRsaKeyVRecharge(@Field("user_id") String user_id,
                                                        @Field("txn_amount") String txn_amount,
                                                        @Field("service_id") String service_id,
                                                        @Field("service_name") String service_name
                                                       );
}
