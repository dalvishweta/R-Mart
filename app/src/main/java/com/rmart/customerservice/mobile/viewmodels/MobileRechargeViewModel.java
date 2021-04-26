package com.rmart.customerservice.mobile.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.customerservice.mobile.models.MRechargeBaseClass;
import com.rmart.customerservice.mobile.models.ResponseGetHistory;
import com.rmart.customerservice.mobile.repositories.MobileRechargeRepository;
import com.rmart.electricity.rsakeyResponse;

public class MobileRechargeViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<ResponseGetHistory> responseGetHistoryMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<MRechargeBaseClass> responseVRechargeMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<rsakeyResponse> responseRsakeyMutableLiveData = new MutableLiveData<>();


    public void getHistory(){
        isLoading.setValue(true);
        MobileRechargeRepository.getHistory().observeForever(responseGetHistory -> {
            responseGetHistoryMutableLiveData.setValue(responseGetHistory);
            isLoading.setValue(false);
        });

    }
    public void doVRecharge(int service_type,String preOperator_dth,String customer_number,String recharge_type,String preOperator,String PostOperator,
                            String Location,String Mobile_number,String rechargeType,String Recharge_amount, String user_id,String ccavneuData){
        isLoading.setValue(true);
        MobileRechargeRepository.getVRecharge(service_type, preOperator_dth, customer_number, recharge_type, preOperator, PostOperator, Location, Mobile_number, rechargeType, Recharge_amount, user_id, ccavneuData).observeForever(getRechargeResponse -> {
            responseVRechargeMutableLiveData.setValue(getRechargeResponse);
            isLoading.setValue(false);
        });

    }
    public void getRsaKey(String user_id,String txt_amount,String service_id,String service_name){
        isLoading.setValue(true);
        MobileRechargeRepository.getRSAKey(user_id,txt_amount,service_id,service_name).observeForever(responseKeyResponse -> {
            responseRsakeyMutableLiveData.setValue(responseKeyResponse);
            isLoading.setValue(false);
        });

    }
}
