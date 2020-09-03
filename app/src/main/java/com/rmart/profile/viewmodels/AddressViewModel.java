package com.rmart.profile.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.profile.model.MyAddress;

public class AddressViewModel extends ViewModel {
    MutableLiveData<MyAddress> myAddressMutableLiveData;
    public AddressViewModel() {
        myAddressMutableLiveData = new MutableLiveData<>(new MyAddress());
    }

    public MutableLiveData<MyAddress> getMyAddressMutableLiveData() {
        return myAddressMutableLiveData;
    }

    public void setMyAddressMutableLiveData(MyAddress myAddressMutableLiveData) {
        this.myAddressMutableLiveData.setValue(myAddressMutableLiveData); ;
    }
}
