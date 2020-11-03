package com.rmart.profile.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.baseclass.views.ProgressBarCircular;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.HttpsTrustManager;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.Utils;
import com.rmart.utilits.custom_views.CustomNetworkImageView;
import com.rmart.utilits.pojos.AddressResponse;

import java.util.List;
import java.util.Objects;

public class ViewMyProfileFragment extends BaseFragment implements View.OnClickListener, OnMapReadyCallback {

    private AppCompatTextView tvFirstName, tvLastName, tvMobileNumber, tvEmail, tvGender, deliveryCharge, minimumCharge,
            tvOpeningTIme, tvClosingTIme, tvDeliveryDaysAfterTime, tvDeliveryDaysBeforeTime;
    private AppCompatTextView tvShopName, tvShopACT,tvPANNumber, tvGSTNumber, tvStreetAddress,tvCity, tvShopNO, tvDeliveryRadius, tvState, tvPINCode, tvAadharNoField;
    private RecyclerView recyclerView;
    // MyProfileViewModel myProfileViewModel;
    private AddressResponse addressResponse;
    private GoogleMap googleMap;
    private OnCustomerHomeInteractionListener mListener;
    private ImageLoader imageLoader;
    private ProgressBarCircular aadharFrontImageProgressBar;
    private ProgressBarCircular aadharBackImageProgressBar;
    private ProgressBarCircular pancardProgressBar;
    private ProgressBarCircular shopImageProgressBar;
    private CustomNetworkImageView ivAadharFrontImageField, ivAadharBackImageField, ivPanCardImageField, ivShopImageField;

    public ViewMyProfileFragment() {
        // Required empty public constructor
    }

    public static ViewMyProfileFragment newInstance() {
        return new ViewMyProfileFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnCustomerHomeInteractionListener) context;
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

        SupportMapFragment mapsFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapsFragment != null) {
            mapsFragment.getMapAsync(this);
        }

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
        AddressAdapter addressAdapter = new AddressAdapter(view1 -> {
            int position = (int) view1.getTag();
            mListener.gotoEditAddress(MyProfile.getInstance().getAddressResponses().get(position));
        });
        recyclerView.setAdapter(addressAdapter);
    }

    private void setRetailerView(View view) {
        imageLoader = RMartApplication.getInstance().getImageLoader();
        view.findViewById(R.id.location_list_view).setVisibility(View.GONE);
        view.findViewById(R.id.retailer_view).setVisibility(View.VISIBLE);
        view.findViewById(R.id.edit_retailer).setOnClickListener(this);
        tvShopName = view.findViewById(R.id.shop_name);
        tvShopACT = view.findViewById(R.id.shop_act);
        tvPANNumber = view.findViewById(R.id.pan_number);
        tvGSTNumber = view.findViewById(R.id.gst_number);
        tvStreetAddress = view.findViewById(R.id.street_address);
        tvShopNO = view.findViewById(R.id.shop_no);
        tvDeliveryRadius = view.findViewById(R.id.delivery_radius);
        tvState = view.findViewById(R.id.state);
        tvCity = view.findViewById(R.id.city);
        tvPINCode = view.findViewById(R.id.pin_code);
        deliveryCharge = view.findViewById(R.id.delivery_charges);
        minimumCharge  = view.findViewById(R.id.minimum_order);
        tvOpeningTIme = view.findViewById(R.id.open_time);
        tvClosingTIme = view.findViewById(R.id.close_time);
        tvDeliveryDaysAfterTime = view.findViewById(R.id.delivery_days_after_time);
        tvDeliveryDaysBeforeTime = view.findViewById(R.id.delivery_days_before_time);

        ivAadharFrontImageField = view.findViewById(R.id.iv_aadhar_front_image_field);
        ivAadharBackImageField = view.findViewById(R.id.iv_aadhar_back_image_field);
        ivPanCardImageField = view.findViewById(R.id.iv_pan_card_no_image_field);
        ivShopImageField = view.findViewById(R.id.iv_shop_image_field);
        aadharFrontImageProgressBar = view.findViewById(R.id.aadhar_front_progress_bar);
        aadharBackImageProgressBar = view.findViewById(R.id.aadhar_back_progress_bar);
        pancardProgressBar = view.findViewById(R.id.pan_progress_bar);
        shopImageProgressBar = view.findViewById(R.id.shop_image_progress_bar);
        tvAadharNoField = view.findViewById(R.id.tv_aadhar_number_no_field);

        setRetailerAddressData();
    }

    private void setRetailerAddressData() {
        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            List<AddressResponse> addressResponseList = myProfile.getAddressResponses();
            if (addressResponseList != null && !addressResponseList.isEmpty()) {
                addressResponse = addressResponseList.get(0);
                tvShopName.setText(addressResponse.getShopName());
                tvShopACT.setText(addressResponse.getShopACT());
                tvPANNumber.setText(addressResponse.getPan_no());
                tvGSTNumber.setText(addressResponse.getGstInNo());
                tvStreetAddress.setText(addressResponse.getAddress());
                tvShopNO.setText(addressResponse.getStore_number());
                String deliveryRadius = String.format("%s km", addressResponse.getDeliveryRadius());
                tvDeliveryRadius.setText(deliveryRadius);
                tvCity.setText(addressResponse.getCity());
                tvState.setText(addressResponse.getState());
                tvPINCode.setText(addressResponse.getPinCode());
                tvAadharNoField.setText(addressResponse.getAadhaarCardNo());

                deliveryCharge.setText(addressResponse.getDeliveryCharges());
                minimumCharge.setText(addressResponse.getMinimumOrder());

                tvOpeningTIme.setText(addressResponse.getOpeningTime());
                tvClosingTIme.setText(addressResponse.getClosingTime());
                tvDeliveryDaysAfterTime.setText(addressResponse.getDeliveryDaysAfterTime());
                tvDeliveryDaysBeforeTime.setText(addressResponse.getDeliveryDaysBeforeTime());

                String lAadharFrontImageUrl = addressResponse.getAadharFrontImage();
                if (!TextUtils.isEmpty(lAadharFrontImageUrl)) {
                    aadharFrontImageProgressBar.setVisibility(View.VISIBLE);
                    HttpsTrustManager.allowAllSSL();
                    imageLoader.get(lAadharFrontImageUrl, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            Bitmap bitmap = response.getBitmap();
                            if (bitmap != null) {
                                ivAadharFrontImageField.setLocalImageBitmap(bitmap);
                            }
                            aadharFrontImageProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            aadharFrontImageProgressBar.setVisibility(View.GONE);
                            ivAadharFrontImageField.setBackgroundResource(R.drawable.ic_aadhar_front);
                        }
                    });
                    ivAadharFrontImageField.setImageUrl(lAadharFrontImageUrl, RMartApplication.getInstance().getImageLoader());
                }  else {
                    ivAadharFrontImageField.setVisibility(View.GONE);
                }
                String lAadharBackImageUrl = addressResponse.getAadharBackImage();
                if (!TextUtils.isEmpty(lAadharBackImageUrl)) {
                    HttpsTrustManager.allowAllSSL();
                    imageLoader.get(lAadharBackImageUrl, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            aadharBackImageProgressBar.setVisibility(View.GONE);
                            Bitmap bitmap = response.getBitmap();
                            if (bitmap != null) {
                                ivAadharBackImageField.setLocalImageBitmap(bitmap);
                            }
                            aadharBackImageProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            aadharBackImageProgressBar.setVisibility(View.GONE);
                            ivAadharBackImageField.setBackgroundResource(R.drawable.ic_aadhar_back);
                        }
                    });
                    ivAadharBackImageField.setImageUrl(lAadharBackImageUrl, RMartApplication.getInstance().getImageLoader());
                }  else {
                    aadharBackImageProgressBar.setVisibility(View.GONE);
                }
                String lPanCardImageUrl = addressResponse.getPanCardImage();
                if (!TextUtils.isEmpty(lPanCardImageUrl)) {
                    HttpsTrustManager.allowAllSSL();
                    ivPanCardImageField.setImageUrl(lPanCardImageUrl, RMartApplication.getInstance().getImageLoader());
                    imageLoader.get(lPanCardImageUrl, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            Bitmap bitmap = response.getBitmap();
                            if (bitmap != null) {
                                ivPanCardImageField.setLocalImageBitmap(bitmap);
                            }
                            pancardProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pancardProgressBar.setVisibility(View.GONE);
                            ivPanCardImageField.setBackgroundResource(R.drawable.ic_pan);
                        }
                    });
                } else {
                    pancardProgressBar.setVisibility(View.GONE);
                }
                String lShopImageUrl = addressResponse.getShopImage();
                if (!TextUtils.isEmpty(lShopImageUrl)) {
                    HttpsTrustManager.allowAllSSL();
                    imageLoader.get(lShopImageUrl, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            Bitmap bitmap = response.getBitmap();
                            if (bitmap != null) {
                                ivShopImageField.setLocalImageBitmap(bitmap);
                            }
                            ivShopImageField.setBackgroundResource(R.drawable.ic_shop);
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            shopImageProgressBar.setVisibility(View.GONE);
                            ivShopImageField.setBackgroundResource(R.drawable.ic_shop);
                        }
                    });
                    ivShopImageField.setImageUrl(lShopImageUrl, RMartApplication.getInstance().getImageLoader());
                } else {
                    shopImageProgressBar.setVisibility(View.GONE);
                }
                updateMapLocation();
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

            List<AddressResponse> addressResponseList = myProfile.getAddressResponses();
            if (addressResponseList != null && !addressResponseList.isEmpty()) {
                addressResponse = myProfile.getAddressResponses().get(0);
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_address:
                addressResponse = new AddressResponse();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.googleMap = googleMap;
        updateMapLocation();
    }

    private void updateMapLocation() {
        if(googleMap != null && addressResponse != null) {
            googleMap.clear();
            LatLng latLng = new LatLng(addressResponse.getLatitude(), addressResponse.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(addressResponse.getAddress());
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            googleMap.addMarker(markerOptions);
        }
    }
}