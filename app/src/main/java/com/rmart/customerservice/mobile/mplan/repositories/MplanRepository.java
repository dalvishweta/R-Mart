package com.rmart.customerservice.mobile.mplan.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.customerservice.mobile.models.mPlans.PostPaidResponseGetPlans;
import com.rmart.customerservice.mobile.models.mPlans.ResponseGetPlans;
import com.rmart.customerservice.mobile.mplan.api.MplanListApi;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MplanRepository {
    public static final String PREPAID= "P";
    public static final String POSTPAID= "PO";
    public static MutableLiveData<ResponseGetPlans> getPrepaidPlanList(String operator, String cricle, String service_type, String mobileapp,String mobileversionid,String mobile_no){


        MplanListApi planListApi = RetrofitClientInstance.getRetrofitInstanceRokad().create(MplanListApi.class);
        final MutableLiveData<ResponseGetPlans> resultMutableLiveData = new MutableLiveData<>();

        Call<ResponseGetPlans> call = planListApi.getPlan(operator,cricle,service_type,mobileapp,mobileversionid,mobile_no);

        call.enqueue(new Callback<ResponseGetPlans>() {
            @Override
            public void onResponse(Call<ResponseGetPlans> call, Response<ResponseGetPlans> response) {
                ResponseGetPlans data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<ResponseGetPlans> call, Throwable t) {
                final ResponseGetPlans result = new ResponseGetPlans();
                if(t.getLocalizedMessage().contains("hostname"))
                {
                    result.setMsg("Please Check Internet Connection");

                } else {
                    result.setMsg(t.getMessage());
                }
                result.setStatus(500);
                resultMutableLiveData.setValue(result);
            }
        });


        return resultMutableLiveData;

    }

    public static MutableLiveData<PostPaidResponseGetPlans> getPostpaidPlanList(String operator, String cricle, String service_type, String mobileapp,String mobileversionid,String mobile_no){


        MplanListApi planListApi = RetrofitClientInstance.getRetrofitInstanceRokad().create(MplanListApi.class);
        final MutableLiveData<PostPaidResponseGetPlans> resultMutableLiveData = new MutableLiveData<>();

        Call<PostPaidResponseGetPlans> call = planListApi.getPostPaidPlan(operator,cricle,service_type,mobileapp,mobileversionid,mobile_no);

        call.enqueue(new Callback<PostPaidResponseGetPlans>() {
            @Override
            public void onResponse(Call<PostPaidResponseGetPlans> call, Response<PostPaidResponseGetPlans> response) {
                PostPaidResponseGetPlans data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<PostPaidResponseGetPlans> call, Throwable t) {
                final PostPaidResponseGetPlans result = new PostPaidResponseGetPlans();
                if(t.getLocalizedMessage().contains("hostname"))
                {
                    result.setMsg("Please Check Internet Connection");

                } else {
                    result.setMsg(t.getMessage());
                }
                result.setStatus(500);
                resultMutableLiveData.setValue(result);
            }
        });


        return resultMutableLiveData;

    }


}
