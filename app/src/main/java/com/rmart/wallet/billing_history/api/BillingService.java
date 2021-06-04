package com.rmart.wallet.billing_history.api;

import com.rmart.BuildConfig;
import com.rmart.wallet.billing_history.models.BillingResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BillingService {
    @GET(BuildConfig.Billing_History)
    Call<BillingResponse> getBillingHistoryInfo(@Query("from_date") String from_date, @Query("to_date") String to_date, @Query("user_id") String user_id);
}
