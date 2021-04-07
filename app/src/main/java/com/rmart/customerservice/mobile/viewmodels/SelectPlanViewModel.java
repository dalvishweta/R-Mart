package com.rmart.customerservice.mobile.viewmodels;

import com.rmart.customerservice.mobile.circle.model.Circle;
import com.rmart.customerservice.mobile.models.ResponseGetHistory;
import com.rmart.customerservice.mobile.operators.model.Operator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectPlanViewModel  extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> mobile = new MutableLiveData<>();
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<Operator> selectedOperatorMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Circle> circleMutableLiveData = new MutableLiveData<>();

}