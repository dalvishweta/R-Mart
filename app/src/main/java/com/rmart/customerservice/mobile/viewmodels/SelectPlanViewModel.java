package com.rmart.customerservice.mobile.viewmodels;

import com.rmart.BuildConfig;
import com.rmart.customerservice.mobile.circle.model.Circle;
import com.rmart.customerservice.mobile.models.MRechargeBaseClass;
import com.rmart.customerservice.mobile.models.mPlans.PostPaidResponseGetPlans;
import com.rmart.customerservice.mobile.models.mPlans.RechargePlans;
import com.rmart.customerservice.mobile.models.mPlans.ResponseGetPlans;
import com.rmart.customerservice.mobile.mplan.repositories.MplanRepository;
import com.rmart.customerservice.mobile.operators.model.Operator;
import com.rmart.customerservice.mobile.repositories.MobileRechargeRepository;
import com.rmart.electricity.RSAKeyResponse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectPlanViewModel  extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Boolean> isError = new MutableLiveData<>();
    public MutableLiveData<String> mobile = new MutableLiveData<>();
    public MutableLiveData<RechargePlans> rechargePlansMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> type = new MutableLiveData<>();
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<Operator> selectedOperatorMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Circle> circleMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoadingPlan = new MutableLiveData<>();
    public MutableLiveData<ResponseGetPlans> mplanListResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<PostPaidResponseGetPlans> postPaidResponseGetPlansMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<MRechargeBaseClass> responseVRechargeMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<RSAKeyResponse> responseRsakeyMutableLiveData = new MutableLiveData<>();

    public void getPrePaidPlanList()
    {

        Circle a= circleMutableLiveData.getValue();
        if(selectedOperatorMutableLiveData.getValue()!=null && a!=null) {
            isLoadingPlan.setValue(true);
            MutableLiveData<ResponseGetPlans> resultMutableLiveData = MplanRepository.getPrepaidPlanList(selectedOperatorMutableLiveData.getValue().mplanOperator, a.value, "p", "Y", "1.0", mobile.getValue());
            resultMutableLiveData.observeForever(mplanResponse -> {
                mplanListResponseMutableLiveData.setValue(mplanResponse);
                if(mplanResponse.getStatus()==200){
                    isError.setValue(false);
                } else {
                    isError.setValue(true);
                }
                errorMessage.setValue(mplanResponse.getMsg());

                isLoadingPlan.setValue(false);

            });
        }

    }
    public void getPostPaidPlanList()
    {

        Circle a= circleMutableLiveData.getValue();
        if(selectedOperatorMutableLiveData.getValue()!=null && a!=null) {
            isLoadingPlan.setValue(true);
            MutableLiveData<PostPaidResponseGetPlans> resultMutableLiveData = MplanRepository.getPostpaidPlanList(selectedOperatorMutableLiveData.getValue().mplanOperator, a.value, "PO", "Y", "1.0", mobile.getValue());
            resultMutableLiveData.observeForever(mplanResponse -> {
                if(mplanResponse!=null) {
                    postPaidResponseGetPlansMutableLiveData.setValue(mplanResponse);
                    if (mplanResponse.getStatus() == 200) {
                        isError.setValue(false);
                    } else {
                        isError.setValue(true);
                    }
                    errorMessage.setValue(mplanResponse.getMsg());
                } else {
                    errorMessage.setValue("No Data Found");
                    isError.setValue(true);


                }

                isLoadingPlan.setValue(false);

            });
        }

    }

    public void doVRecharge(int service_type,String preOperator_dth,String customer_number,String recharge_type,String preOperator,String PostOperator,
                            String Location,String Mobile_number,String rechargeType,String Recharge_amount, String user_id,String ccavneuData){
        isLoading.setValue(true);
        MobileRechargeRepository.getVRecharge(service_type, preOperator_dth, customer_number, recharge_type, preOperator, PostOperator, Location, Mobile_number, rechargeType, Recharge_amount, user_id, ccavneuData).observeForever(getRechargeResponse -> {
            responseVRechargeMutableLiveData.setValue(getRechargeResponse);
            isLoading.setValue(false);
        });

    }
    public void getRsaKey(String user_id){
        isLoading.setValue(true);
        MobileRechargeRepository.getRSAKey(user_id, String.valueOf(rechargePlansMutableLiveData.getValue().getRs()), BuildConfig.service_id,BuildConfig.service_name).observeForever(responseKeyResponse -> {

            responseRsakeyMutableLiveData.setValue(responseKeyResponse);
            isLoading.setValue(false);
        });

    }
}