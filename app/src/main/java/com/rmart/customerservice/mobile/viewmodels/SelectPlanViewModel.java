package com.rmart.customerservice.mobile.viewmodels;

import com.rmart.customerservice.mobile.circle.model.Circle;
import com.rmart.customerservice.mobile.fragments.FragmentMobileRecharge;
import com.rmart.customerservice.mobile.models.mPlans.PostPaidResponseGetPlans;
import com.rmart.customerservice.mobile.models.mPlans.RechargePlans;
import com.rmart.customerservice.mobile.models.mPlans.ResponseGetPlans;
import com.rmart.customerservice.mobile.mplan.repositories.MplanRepository;
import com.rmart.customerservice.mobile.operators.model.Operator;

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
}