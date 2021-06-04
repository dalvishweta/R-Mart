package com.rmart.electricity.api;


import com.rmart.BuildConfig;
import com.rmart.electricity.billdetails.model.BillDataBaseClass;
import com.rmart.electricity.fetchbill.model.ElecProcessPOJO;
import com.rmart.electricity.paybill;
import com.rmart.electricity.RSAKeyResponse;

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
    @POST(BuildConfig.Electricity_recharge_Rokadmart)
    @FormUrlEncoded
    public Call<paybill> electicitybillProcess(@Field("user_id") String user_id,
                                               @Field("operator") String operator,
                                               @Field("ConsumerID") String bankIINNumber,
                                               @Field("bill_unit") String bill_unit,
                                               @Field("mobile_number") String mobileNumber,
                                               @Field("amount") String amount,
                                               @Field("ConsumerName") String ConsumerName,
                                               @Field("Orderid") String Orderid,
                                               @Field("ccavenuedata") String ccavenuedata,
                                               @Field("ccavenue") int ccavenue, //TODO: pass 0 for top-up and 1 for any chosen plan
                                               @Field("order_id") int order_id,
                                               @Field("wallet") boolean wallet

    );
    @POST(BuildConfig.electricity_pay_bill)
    @FormUrlEncoded
    public Call<BillDataBaseClass> electicitybillPayment(@Field("user_id") String user_id,
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
    public Call<RSAKeyResponse> electicityProcessRsaKey(@Field("user_id") String user_id,
                                                        @Field("txn_amount") String txn_amount,
                                                        @Field("service_id") String service_id,
                                                        @Field("service_name") String service_name,
                                                        @Field("order_id") String order_id);
    @POST(BuildConfig.electricity_rsa_key)
    @FormUrlEncoded
    public Call<RSAKeyResponse> electicityProcessRsaKeyVRecharge(@Field("user_id") String user_id,
                                                                 @Field("txn_amount") String txn_amount,
                                                                 @Field("service_id") String service_id,
                                                                 @Field("service_name") String service_name
                                                       );
}
