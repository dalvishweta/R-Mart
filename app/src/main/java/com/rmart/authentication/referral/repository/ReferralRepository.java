package com.rmart.authentication.referral.repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.rmart.BuildConfig;
import com.rmart.authentication.referral.api.ReferralService;
import com.rmart.authentication.referral.models.ReferralResponse;
import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralRepository {
    public static MutableLiveData<ReferralResponse> getReferral(Context applicationContext, ProgressDialog progressDialog, String short_link, String long_link,String user_id){

        ReferralService referralService = RetrofitClientInstance.getRetrofitInstanceForAdd().create(ReferralService.class);
        final MutableLiveData<ReferralResponse> referralResponseMutableLiveData = new MutableLiveData<>();
        Call<ReferralResponse> responseCall = referralService.getRefferealInfo(BuildConfig.REFER_NO,short_link,long_link, BuildConfig.ROLE_ID,BuildConfig.CLIENT_ID,user_id);
        final ReferralResponse referralResponse = new ReferralResponse();
        responseCall.enqueue(new Callback<ReferralResponse>() {
            @Override
            public void onResponse(Call<ReferralResponse> call, Response<ReferralResponse> response) {
                if (response.isSuccessful()){
                    ReferralResponse referralResponse1 = response.body();
                    referralResponseMutableLiveData.setValue(referralResponse1);
                    progressDialog.dismiss();
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.putExtra(Intent.EXTRA_TEXT,referralResponse1.getMsg()+ " "+short_link);
                    share.setType("text/plain");
                    share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    applicationContext.startActivity(share);
                }
                else {
                    referralResponse.setMsg(referralResponse.getMsg());
                    referralResponse.setCode(referralResponse.getCode());
                    referralResponseMutableLiveData.setValue(referralResponse);
                }
            }

            @Override
            public void onFailure(Call<ReferralResponse> call, Throwable t) {
                referralResponse.setMsg("Please Check Internet Connection");
                referralResponse.setCode(400);
                referralResponseMutableLiveData.setValue(referralResponse);
            }
        });

        return referralResponseMutableLiveData;

    }
}
