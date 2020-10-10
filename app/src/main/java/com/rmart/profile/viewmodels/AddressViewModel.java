package com.rmart.profile.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.rmart.profile.model.MyAddress;

public class AddressViewModel extends ViewModel {

    private MutableLiveData<LatLng> mapListenerPoints;
    MutableLiveData<MyAddress> myAddressMutableLiveData;
    public AddressViewModel() {
        myAddressMutableLiveData = new MutableLiveData<>(new MyAddress());
        mapListenerPoints = new MutableLiveData<>();
    }

    public MutableLiveData<MyAddress> getMyAddressMutableLiveData() {
        return myAddressMutableLiveData;
    }

    public void setMyAddressMutableLiveData(MyAddress myAddressMutableLiveData) {
        this.myAddressMutableLiveData.setValue(myAddressMutableLiveData); ;
    }

    public MutableLiveData<LatLng> getMapListenerPoints() {
        return mapListenerPoints;
    }

    public void setMapListenerPoints(LatLng latLng) {
        this.mapListenerPoints.setValue(latLng);
    }
}
