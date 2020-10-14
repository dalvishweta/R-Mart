package com.rmart.profile.views;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.mapview.MapsFragment;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.AddressResponse;

import java.util.List;
import java.util.Objects;

public class ViewMyProfileFragment extends BaseMyProfileFragment implements View.OnClickListener {

    private AppCompatTextView tvFirstName, tvLastName, tvMobileNumber, tvEmail, tvGender, deliveryCharge,
            tvOpeningTIme, tvClosingTIme, tvDeliveryDaysAfterTime, tvDeliveryDaysBeforeTime;
    private AppCompatTextView tvShopName, tvPANNumber, tvGSTNumber, tvStreetAddress,tvCity, tvShopNO, tvDeliveryRadius, tvState, tvPINCode;
    RecyclerView recyclerView;
    AddressAdapter addressAdapter;
    private MapsFragment mapsFragment;
    // MyProfileViewModel myProfileViewModel;
    public ViewMyProfileFragment() {
        // Required empty public constructor
    }

    public static ViewMyProfileFragment newInstance() {
        return new ViewMyProfileFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.view_my_profile));
        updateUI(Objects.requireNonNull(MyProfile.getInstance()));
        if(BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.RETAILER_ID)) {
            setRetailerAddressData();
        } else if(BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.CUSTOMER_ID)) {
            setCustomerAddressData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // myProfileViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MyProfileViewModel.class);
        LoggerInfo.printLog("Fragment", "ViewMyProfileFragment");
        return inflater.inflate(R.layout.fragment_view_my_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvFirstName = view.findViewById(R.id.first_name);
        tvLastName = view.findViewById(R.id.last_name);
        tvMobileNumber = view.findViewById(R.id.mobile_number);
        tvEmail = view.findViewById(R.id.email);
        tvGender = view.findViewById(R.id.gender);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mapsFragment = MapsFragment.newInstance(false, false,0.0, 0.0);
        fragmentTransaction.replace(R.id.map_layout_field, mapsFragment, MapsFragment.class.getName());
        fragmentTransaction.commit();

        view.findViewById(R.id.edit_profile).setOnClickListener(this);
        // view.findViewById(R.id.submit).setOnClickListener(this);
        if (BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.RETAILER_ID)) {
            setRetailerView(view);
        } else if (BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.CUSTOMER_ID)) {
            setCustomerView(view);
        }

        updateUI(Objects.requireNonNull(MyProfile.getInstance()));
    }

    private void setCustomerView(View view) {
        view.findViewById(R.id.location_list_view).setVisibility(View.VISIBLE);
        view.findViewById(R.id.retailer_view).setVisibility(View.GONE);
        view.findViewById(R.id.add_new_address).setOnClickListener(this);
        recyclerView = view.findViewById(R.id.address_list);
        setCustomerAddressData();
    }

    private void setCustomerAddressData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addressAdapter = new AddressAdapter(view1 -> {
            int position = (int) view1.getTag();
            mListener.gotoEditAddress(MyProfile.getInstance().getAddressResponses().get(position));
        });
        recyclerView.setAdapter(addressAdapter);
    }

    private void setRetailerView(View view) {
        view.findViewById(R.id.location_list_view).setVisibility(View.GONE);
        view.findViewById(R.id.retailer_view).setVisibility(View.VISIBLE);
        view.findViewById(R.id.edit_retailer).setOnClickListener(this);
        tvShopName = view.findViewById(R.id.shop_name);
        tvPANNumber = view.findViewById(R.id.pan_number);
        tvGSTNumber = view.findViewById(R.id.gst_number);
        tvStreetAddress = view.findViewById(R.id.street_address);
        tvShopNO = view.findViewById(R.id.shop_no);
        tvDeliveryRadius = view.findViewById(R.id.delivery_radius);
        tvState = view.findViewById(R.id.state);
        tvCity = view.findViewById(R.id.city);
        tvPINCode = view.findViewById(R.id.pin_code);
        deliveryCharge = view.findViewById(R.id.delivery_charges);
        tvOpeningTIme = view.findViewById(R.id.open_time);
        tvClosingTIme = view.findViewById(R.id.close_time);
        tvDeliveryDaysAfterTime = view.findViewById(R.id.delivery_days_after_time);
        tvDeliveryDaysBeforeTime = view.findViewById(R.id.delivery_days_before_time);

        setRetailerAddressData();
    }

    private void setRetailerAddressData() {
        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            List<AddressResponse> addressResponseList = myProfile.getAddressResponses();
            if (addressResponseList != null && !addressResponseList.isEmpty()) {
                AddressResponse address = addressResponseList.get(0);
                tvShopName.setText(address.getShopName());
                tvPANNumber.setText(address.getPan_no());
                tvGSTNumber.setText(address.getGstInNo());
                tvStreetAddress.setText(address.getAddress());
                tvShopNO.setText(address.getStore_number());
                tvDeliveryRadius.setText(address.getDeliveryRadius());
                tvCity.setText(address.getCity());
                tvState.setText(address.getState());
                tvPINCode.setText(address.getPinCode());

                deliveryCharge.setText(address.getDeliveryCharges());
                tvOpeningTIme.setText(address.getOpeningTime());
                tvClosingTIme.setText(address.getClosingTime());
                tvDeliveryDaysAfterTime.setText(address.getDeliveryDaysAfterTime());
                tvDeliveryDaysBeforeTime.setText(address.getDeliveryDaysBeforeTime());

                Location location = new Location("");
                location.setLatitude(address.getLatitude());
                location.setLongitude(address.getLongitude());
                mapsFragment.setLocation(location);
            }
        }
    }

    private void updateUI(MyProfile myProfile) {
        if (myProfile != null) {
            tvFirstName.setText(myProfile.getFirstName());
            tvLastName.setText(myProfile.getLastName());
            tvMobileNumber.setText(myProfile.getMobileNumber());
            tvEmail.setText(myProfile.getEmail());
            tvGender.setText(myProfile.getGender());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_address:
                AddressResponse addressResponse = new AddressResponse();
                addressResponse.setId(-1);
                mListener.gotoEditAddress(addressResponse);
                break;
            case R.id.edit_retailer:
                mListener.gotoEditAddress(MyProfile.getInstance().getAddressResponses().get(0));
                break;
            case R.id.edit_profile:
                mListener.gotoEditProfile();
                break;
            default:
                break;
        }
    }
}