package com.rmart.profile.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.profile.model.MyAddress;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.viewmodels.AddressViewModel;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.AddressResponse;
import com.rmart.utilits.services.ProfileService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAddressFragment extends BaseMyProfileFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Boolean isEdit;
    private String mParam2;
    private AppCompatEditText tvShopName, tvPANNumber, tvGSTNumber, tvStreetAddress,tvCity, tvShopNO, tvDeliveryRadius, tvPinCode, tvState;
    LinearLayout mRetailerView;
    AddressViewModel addressViewModel;
    AddressResponse myAddress;
    public EditAddressFragment() {
        // Required empty public constructor
    }

    public static EditAddressFragment newInstance(boolean isEdit, AddressResponse myAddress) {
        EditAddressFragment fragment = new EditAddressFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isEdit);
        args.putSerializable(ARG_PARAM2, myAddress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEdit = getArguments().getBoolean(ARG_PARAM1);
            myAddress = (AddressResponse) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addressViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(AddressViewModel.class);
        addressViewModel.setMyAddressMutableLiveData(new MyAddress());
        addressViewModel.getMyAddressMutableLiveData().observe(getActivity(), myAddress -> {

        });
        return inflater.inflate(R.layout.fragment_add_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRetailerView = view.findViewById(R.id.retailer_view);

        tvShopName = view.findViewById(R.id.shop_name);
        tvPANNumber = view.findViewById(R.id.pan_number);
        tvGSTNumber = view.findViewById(R.id.gst_number);
        tvStreetAddress = view.findViewById(R.id.street_address);
        tvShopNO = view.findViewById(R.id.shop_no);
        tvDeliveryRadius = view.findViewById(R.id.delivery_radius);
        tvPinCode = view.findViewById(R.id.pin_code);
        tvState = view.findViewById(R.id.state);
        tvCity = view.findViewById(R.id.city);

        view.findViewById(R.id.add_address).setOnClickListener(this);
        updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if(null != myAddress) {
            Objects.requireNonNull(getActivity()).setTitle(getString(R.string.add_address));
            tvShopName.setText(myAddress.getShopName());
            tvPANNumber.setText(myAddress.getPan_no());
            tvGSTNumber.setText(myAddress.getGstInNo());
            tvStreetAddress.setText(myAddress.getAddress());
            tvShopNO.setText(myAddress.getStore_number());
            tvState.setText(myAddress.getState());
            tvPinCode.setText(myAddress.getPinCode());
            tvDeliveryRadius.setText(myAddress.getDeliveryRadius());
            tvCity.setText(myAddress.getCity());
        } else {
            Objects.requireNonNull(getActivity()).setTitle(getString(R.string.update_address));
        }
        if(BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.RETAILER_ID)) {
            mRetailerView.setVisibility(View.VISIBLE);
        } else if(BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.CUSTOMER_ID)) {
            mRetailerView.setVisibility(View.GONE);
        } else {
            mRetailerView.setVisibility(View.GONE);
        }

        /*ArrayList<MyLocation> locations = myProfile.getMyLocations();


        if(MyProfile.getInstance().getRoleType().equals(MyProfile.RETAILER)) {
            if(locations!= null && locations.size()>0) {

            }
        }*/
        setMapView(false, "profile");
    }

    @Override
    public void onClick(View view) {

        progressDialog.show();
        if (myAddress == null) {
            getAddressData();
            ProfileService profileService = RetrofitClientInstance.getRetrofitInstance().create(ProfileService.class);
            profileService.addAddress(myAddress.getShopName(), myAddress.getPan_no(), myAddress.getGstInNo(), myAddress.getStore_number(),
                    myAddress.getAddress(), myAddress.getCity(), myAddress.getState(), myAddress.getPinCode(), myAddress.getLatitude(),
                    myAddress.getLongitude(), MyProfile.getInstance().getUserID(), MyProfile.getInstance().getRoleID(),
                    myAddress.getDeliveryRadius(), Utils.CLIENT_ID).enqueue(new Callback<AddressResponse>() {
                @Override
                public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                    if (response.isSuccessful()) {
                        AddressResponse data = response.body();
                        if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                            MyProfile.getInstance().getAddressResponses().add(data);
                            Objects.requireNonNull(getActivity()).onBackPressed();
                        } else {
                            myAddress = null;
                            showDialog("", data.getMsg());
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AddressResponse> call, Throwable t) {
                    showDialog("", t.getMessage());
                    progressDialog.dismiss();
                }
            });
        } else {
            getAddressData();
            ProfileService profileService = RetrofitClientInstance.getRetrofitInstance().create(ProfileService.class);
            profileService.updateAddress(myAddress.getShopName(), myAddress.getPan_no(), myAddress.getGstInNo(), myAddress.getStore_number(),
                    myAddress.getAddress(), myAddress.getCity(), myAddress.getState(), myAddress.getPinCode(), myAddress.getLatitude(),
                    myAddress.getLongitude(), MyProfile.getInstance().getUserID(), MyProfile.getInstance().getRoleID(),
                    myAddress.getDeliveryRadius(), Utils.CLIENT_ID, myAddress.getId()).enqueue(new Callback<AddressResponse>() {
                @Override
                public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                    if (response.isSuccessful()) {
                        AddressResponse data = response.body();
                        if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                            MyProfile.getInstance().getAddressResponses().remove(0);
                            MyProfile.getInstance().getAddressResponses().add(data);
                            Objects.requireNonNull(getActivity()).onBackPressed();
                        } else {
                            showDialog("", data.getMsg());
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AddressResponse> call, Throwable t) {
                    showDialog("", t.getMessage());
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void getAddressData() {
        if (myAddress == null) {
            myAddress = new AddressResponse();// addressViewModel.getMyAddressMutableLiveData().getValue();
        }
        myAddress.setLongitude("120203030");
        myAddress.setLatitude("120203030");
        myAddress.setShopName(Objects.requireNonNull(tvShopName.getText()).toString());
        myAddress.setPan_no(Objects.requireNonNull(tvPANNumber.getText()).toString());
        myAddress.setGstInNo(Objects.requireNonNull(tvGSTNumber.getText()).toString());
        myAddress.setStore_number(Objects.requireNonNull(tvShopNO.getText()).toString());
        myAddress.setAddress(Objects.requireNonNull(tvStreetAddress.getText()).toString());
        myAddress.setCity(Objects.requireNonNull(tvCity.getText()).toString());
        myAddress.setState(Objects.requireNonNull(tvState.getText()).toString());
        myAddress.setDeliveryRadius(Objects.requireNonNull(tvDeliveryRadius.getText()).toString());
        myAddress.setPinCode(Objects.requireNonNull(tvPinCode.getText()).toString());
    }
}