package com.rmart.wallet.api;

import com.rmart.wallet.model.WalletResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WalletTransction {

    @GET("Wallet/userWalletAmount/{user_id}/{wallet_id}")
    Call<WalletResponse> getWalletInfo(@Path(value="user_id") String user_id,@Path(value="wallet_id") String wallet_id );
}
