package com.rmart.profile.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.profile.model.MyProfile;

public class MyProfileViewModel extends ViewModel {
    MutableLiveData<MyProfile> myProfileMutableLiveData;

    public MyProfileViewModel() {
        myProfileMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<MyProfile> getMyProfileMutableLiveData() {
        return myProfileMutableLiveData;
    }

    public void setMyProfileMutableLiveData(MutableLiveData<MyProfile> myProfileMutableLiveData) {
        this.myProfileMutableLiveData = myProfileMutableLiveData;
    }
}
