package com.rmart.profile.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.profile.model.MyAddress;
import com.rmart.profile.model.MyProfile;

public class MyProfileViewModel extends ViewModel {
    MutableLiveData<MyProfile> myProfileMutableLiveData;
    MutableLiveData<MyAddress> myAddress;
    public MyProfileViewModel() {
        myProfileMutableLiveData = new MutableLiveData<>();
        // MyProfile myProfile = new MyProfile();
        myProfileMutableLiveData.setValue(MyProfile.getInstance());
    }

    public MutableLiveData<MyProfile> getMyProfileMutableLiveData() {
        return myProfileMutableLiveData;
    }

    public void setMyProfileMutableLiveData(MutableLiveData<MyProfile> myProfileMutableLiveData) {
        this.myProfileMutableLiveData = myProfileMutableLiveData;
    }

    public MutableLiveData<MyAddress> getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(MutableLiveData<MyAddress> myAddress) {
        this.myAddress = myAddress;
    }
}
