package com.rmart.profile;

import com.rmart.utilits.pojos.AddressResponse;

public interface OnMyProfileClickedListener {
    void gotoEditProfile();
    void gotoEditAddress(AddressResponse address);
    void gotoViewProfile();
    void gotoMapView();
}
