package com.rmart.wallet.api;

import com.rmart.BuildConfig;
import com.rmart.electricity.RSAKeyResponse;
import com.rmart.wallet.model.CheckWalletTopup;
import com.rmart.wallet.model.WalletResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WalletTransction {

    @GET("Wallet/userWalletAmount/{user_id}/{wallet_id}")
    Call<WalletResponse> getWalletInfo(@Path(value="user_id") String user_id,@Path(value="wallet_id") String wallet_id );

    @POST(BuildConfig.Wallet_Toup)
    @FormUrlEncoded
    public Call<RSAKeyResponse> RsaKeyVRecharge(@Field("user_id") String user_id,
                                                @Field("wallet_id") String wallet_id,
                                                @Field("credit_amount") String txn_amount,
                                                @Field("service_id") String service_id,
                                                @Field("service_name") String service_name

    );
    @POST(BuildConfig.Check_Wallet_Toup)
    @FormUrlEncoded
    public Call<CheckWalletTopup> CheckWalletTopup(@Field("user_id") String user_id,
                                                   @Field("wallet_id") String wallet_id,
                                                   @Field("data") String data


    );
}
