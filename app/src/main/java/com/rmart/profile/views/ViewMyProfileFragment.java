package com.rmart.profile.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.profile.model.MyLocation;
import com.rmart.profile.model.MyProfile;

import java.util.ArrayList;
import java.util.Objects;

public class ViewMyProfileFragment extends BaseMyProfileFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppCompatTextView tvFirstName, tvLastName, tvMobileNumber, tvEmail, tvShopName, tvPANNumber, tvGSTNumber, tvStreetAddress, tvShopNO, tvLandMark, tvDistrict, tvState;
    private String mParam1;
    private String mParam2;
    // MyProfileViewModel myProfileViewModel;
    public ViewMyProfileFragment() {
        // Required empty public constructor
    }

    public static ViewMyProfileFragment newInstance(String param1, String param2) {
        ViewMyProfileFragment fragment = new ViewMyProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.view_my_profile));
        updateUI(Objects.requireNonNull(MyProfile.getInstance()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // myProfileViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MyProfileViewModel.class);
        return inflater.inflate(R.layout.fragment_view_my_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvFirstName = view.findViewById(R.id.first_name);
        tvLastName = view.findViewById(R.id.last_name);
        tvMobileNumber = view.findViewById(R.id.mobile_number);
        tvEmail = view.findViewById(R.id.email);
        tvShopName = view.findViewById(R.id.shop_name);
        tvPANNumber = view.findViewById(R.id.pan_number);
        tvGSTNumber = view.findViewById(R.id.gst_number);

        tvStreetAddress = view.findViewById(R.id.street_address);
        tvShopNO = view.findViewById(R.id.shop_no);
        tvLandMark = view.findViewById(R.id.land_mark);
        tvDistrict = view.findViewById(R.id.district);

        tvState = view.findViewById(R.id.state);
        tvDistrict = view.findViewById(R.id.district);

        view.findViewById(R.id.submit).setOnClickListener(this);

        updateUI(Objects.requireNonNull(MyProfile.getInstance()));
    }

    private void updateUI(MyProfile myProfile) {
        tvFirstName.setText(myProfile.getFirstName());
        tvLastName.setText(myProfile.getLastName());
        tvMobileNumber.setText(myProfile.getMobileNumber());
        tvEmail.setText(myProfile.getEmail());
        tvShopName.setText(myProfile.getShopName());
        tvPANNumber.setText(myProfile.getPanNumber());
        tvGSTNumber.setText(myProfile.getGstNumber());
        ArrayList<MyLocation> locations = myProfile.getMyLocations();
        tvStreetAddress.setText(locations.get(0).getStreetAddress());
        tvShopNO.setText(locations.get(0).getShopNo());
        tvLandMark.setText(locations.get(0).getLandMark());
        tvDistrict.setText(locations.get(0).getDistrict());
        tvState.setText(locations.get(0).getState());

        setMapView(false, "profile");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                mListener.gotoEditProfile();
                break;
        }
    }
}