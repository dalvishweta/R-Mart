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
                if(response.isSuccessful()) {
                    ResponseGetPlans data = response.body();
                    resultMutableLiveData.setValue(data);
                } else {
                    final ResponseGetPlans result = new ResponseGetPlans();
                    result.setMsg(response.message());
                    result.setStatus(response.code());
                    resultMutableLiveData.setValue(result);
                    //Response{protocol=http/1.1, code=400, message=Bad Request, url=https://rokad.in/rest_server/mplan/getDataPlans_new?operator=Airtel&cricle=Andhra%20Pradesh%20Telangana&service_type=p&mobileapp=Y&mobileversionid=1.0&mobile_no=7453363818}
                }
            }

            @Override
            public void onFailure(Call<ResponseGetPlans> call, Throwable t) {
                final ResponseGetPlans result = new ResponseGetPlans();
                result.setMsg("Please make sure your phone is connected to internet");
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
