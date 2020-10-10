package com.rmart.profile.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.maps.model.LatLng;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.views.ProgressBarCircular;
import com.rmart.customer.views.CustomerHomeActivity;
import com.rmart.mapview.MapsFragment;
import com.rmart.orders.views.OrdersActivity;
import com.rmart.profile.model.MyAddress;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.viewmodels.AddressViewModel;
import com.rmart.services.UpdateProfileImageServices;
import com.rmart.utilits.HttpsTrustManager;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.custom_views.CustomNetworkImageView;
import com.rmart.utilits.custom_views.CustomTimePicker;
import com.rmart.utilits.pojos.AddressListResponse;
import com.rmart.utilits.pojos.AddressResponse;
import com.rmart.utilits.services.ProfileService;
import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class EditAddressFragment extends BaseMyProfileFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String AADHAR_FRONT_IMAGE = "aadhar_front_image";
    private static final String AADHAR_BACK_IMAGE = "aadhar_back_image";
    private static final String PANCARD_IMAGE = "pancard_image";
    private static final String SHOP_IMAGE = "shop_image";
    private AppCompatEditText tvShopName, tvPANNumber, tvGSTNumber, tvStreetAddress, tvCity, tvShopNO, tvDeliveryRadius, tvPinCode, tvState;
    private LinearLayout mRetailerView;
    private AddressResponse myAddress;
    private AppCompatEditText tvAadharNoField, etvDeliveryCharges, tvDeliveryDaysAfterTime, tvDeliveryDaysBeforeTime;
    private AppCompatTextView tvClosingTIme, tvOpeningTIme;
    private CustomNetworkImageView ivAadharFrontImageField, ivAadharBackImageField, ivPanCardImageField, ivShopImageField;
    private String aadharFrontImageUrl;
    private String aadharBackImageUrl;
    private String panCardImageUrl;
    private String shopImageUrl;
    private boolean isAddNewAddress;
    private ProgressBarCircular aadharFrontImageProgressBar;
    private ProgressBarCircular aadharBackImageProgressBar;
    private ProgressBarCircular pancardProgressBar;
    private ProgressBarCircular shopImageProgressBar;
    private int selectedPhotoType = -1;
    private double latitude;
    private double longitude;
    private MapsFragment mapsFragment;

    public EditAddressFragment() {
        // Required empty public constructor
    }

    public static EditAddressFragment newInstance(boolean isAddNewAddress, AddressResponse myAddress) {
        EditAddressFragment fragment = new EditAddressFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isAddNewAddress);
        args.putSerializable(ARG_PARAM2, myAddress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myAddress = (AddressResponse) getArguments().getSerializable(ARG_PARAM2);
            isAddNewAddress = getArguments().getBoolean(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoggerInfo.printLog("Fragment", "EditAddressFragment");
        AddressViewModel addressViewModel = new ViewModelProvider(Objects.requireNonNull(requireActivity())).get(AddressViewModel.class);
        addressViewModel.setMyAddressMutableLiveData(new MyAddress());
        addressViewModel.getMyAddressMutableLiveData().observe(requireActivity(), myAddress -> {

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
        ivAadharFrontImageField = view.findViewById(R.id.iv_aadhar_front_image_field);
        ivAadharBackImageField = view.findViewById(R.id.iv_aadhar_back_image_field);
        ivPanCardImageField = view.findViewById(R.id.iv_pan_card_no_image_field);
        tvAadharNoField = view.findViewById(R.id.tv_aadhar_number_no_field);
        ivShopImageField = view.findViewById(R.id.iv_shop_image_field);
        aadharFrontImageProgressBar = view.findViewById(R.id.aadhar_front_progess_bar);
        aadharBackImageProgressBar = view.findViewById(R.id.aadhar_back_progess_bar);
        pancardProgressBar = view.findViewById(R.id.pan_progess_bar);
        shopImageProgressBar = view.findViewById(R.id.shop_image_progess_bar);

        etvDeliveryCharges = view.findViewById(R.id.delivery_charges);

        tvOpeningTIme = view.findViewById(R.id.open_time);
        tvClosingTIme = view.findViewById(R.id.close_time);

        tvDeliveryDaysAfterTime = view.findViewById(R.id.delivery_days_after_time);
        tvDeliveryDaysBeforeTime = view.findViewById(R.id.delivery_days_before_time);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mapsFragment = MapsFragment.newInstance(false, "");
        mapsFragment.setCallBackListener(pObject -> {
            if(pObject instanceof Location) {
                Location selectedLocation = (Location) pObject;
                latitude = selectedLocation.getLatitude();
                longitude = selectedLocation.getLongitude();
            }
        });
        fragmentTransaction.add(R.id.map_layout_field, mapsFragment, MapsFragment.class.getName());
        fragmentTransaction.commit();

        view.findViewById(R.id.add_address).setOnClickListener(this);
        ivAadharFrontImageField.setOnClickListener(this);
        ivAadharBackImageField.setOnClickListener(this);
        ivPanCardImageField.setOnClickListener(this);
        ivShopImageField.setOnClickListener(this);

        tvOpeningTIme.setOnClickListener(this);
        tvClosingTIme.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.update_address));
        updateUI();
    }

    private void updateUI() {
        if (myAddress.getId() != -1) {
            Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.add_address));
            tvShopName.setText(myAddress.getShopName());
            tvPANNumber.setText(myAddress.getPan_no());
            tvGSTNumber.setText(myAddress.getGstInNo());
            tvStreetAddress.setText(myAddress.getAddress());
            tvShopNO.setText(myAddress.getStore_number());
            tvState.setText(myAddress.getState());
            tvPinCode.setText(myAddress.getPinCode());
            tvDeliveryRadius.setText(myAddress.getDeliveryRadius());
            tvCity.setText(myAddress.getCity());
            tvAadharNoField.setText(myAddress.getAadhaarCardNo());

            etvDeliveryCharges.setText(myAddress.getDeliveryCharges());
            tvOpeningTIme.setText(myAddress.getOpeningTime());
            tvClosingTIme.setText(myAddress.getClosingTime());
            tvDeliveryDaysAfterTime.setText(myAddress.getDeliveryDaysAfterTime());
            tvDeliveryDaysBeforeTime.setText(myAddress.getDeliveryDaysBeforeTime());

            ImageLoader imageLoader = RMartApplication.getInstance().getImageLoader();

            Location location = new Location("");
            location.setLatitude(myAddress.getLatitude());
            location.setLongitude(myAddress.getLongitude());
            mapsFragment.setLocation(location);

            String lAadharFrontImageUrl = myAddress.getAadharFrontImage();
            if (!TextUtils.isEmpty(lAadharFrontImageUrl)) {
                HttpsTrustManager.allowAllSSL();
                aadharFrontImageProgressBar.setVisibility(View.VISIBLE);
                imageLoader.get(lAadharFrontImageUrl, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        aadharFrontImageUrl = lAadharFrontImageUrl;
                        aadharFrontImageProgressBar.setVisibility(View.GONE);
                        Bitmap bitmap = response.getBitmap();
                        if (bitmap != null) {
                            ivAadharFrontImageField.setLocalImageBitmap(bitmap);
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        aadharFrontImageProgressBar.setVisibility(View.GONE);
                        ivAadharFrontImageField.setBackgroundResource(R.drawable.ic_aadhar_front);
                    }
                });
                ivAadharFrontImageField.setImageUrl(lAadharFrontImageUrl, RMartApplication.getInstance().getImageLoader());
            }
            String lAadharBackImageUrl = myAddress.getAadharBackImage();
            if (!TextUtils.isEmpty(lAadharBackImageUrl)) {
                HttpsTrustManager.allowAllSSL();
                aadharBackImageProgressBar.setVisibility(View.VISIBLE);
                imageLoader.get(lAadharBackImageUrl, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        aadharBackImageUrl = lAadharBackImageUrl;
                        aadharBackImageProgressBar.setVisibility(View.GONE);
                        Bitmap bitmap = response.getBitmap();
                        if (bitmap != null) {
                            ivAadharBackImageField.setLocalImageBitmap(bitmap);
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        aadharBackImageProgressBar.setVisibility(View.GONE);
                        ivAadharBackImageField.setBackgroundResource(R.drawable.ic_aadhar_back);
                    }
                });
                ivAadharBackImageField.setImageUrl(lAadharBackImageUrl, RMartApplication.getInstance().getImageLoader());
            }
            String lPancardImageUrl = myAddress.getPanCardImage();
            if (!TextUtils.isEmpty(lPancardImageUrl)) {
                HttpsTrustManager.allowAllSSL();
                pancardProgressBar.setVisibility(View.VISIBLE);
                ivPanCardImageField.setImageUrl(lPancardImageUrl, RMartApplication.getInstance().getImageLoader());
                imageLoader.get(lPancardImageUrl, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        panCardImageUrl = lPancardImageUrl;
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
                /*imageLoader.get(lPancardImageUrl, ImageLoader.getImageListener(ivPanCardImageField,
                        R.mipmap.ic_launcher, android.R.drawable
                                .ic_dialog_alert));*/
            }
            String lShopImageUrl = myAddress.getShopImage();
            if (!TextUtils.isEmpty(lShopImageUrl)) {
                HttpsTrustManager.allowAllSSL();
                shopImageProgressBar.setVisibility(View.VISIBLE);
                imageLoader.get(lShopImageUrl, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        shopImageUrl = lShopImageUrl;
                        shopImageProgressBar.setVisibility(View.GONE);
                        Bitmap bitmap = response.getBitmap();
                        if (bitmap != null) {
                            ivShopImageField.setLocalImageBitmap(bitmap);
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        shopImageProgressBar.setVisibility(View.GONE);
                        ivShopImageField.setBackgroundResource(R.drawable.ic_shop);
                    }
                });
                ivShopImageField.setImageUrl(lShopImageUrl, RMartApplication.getInstance().getImageLoader());
            }
        } else {
            Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.update_address));
        }
        if (BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.RETAILER_ID)) {
            mRetailerView.setVisibility(View.VISIBLE);
            if (myAddress.getId() != -1) {
                tvStreetAddress.setText(myAddress.getAddress());
            }
        } else if (BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.CUSTOMER_ID)) {
            mRetailerView.setVisibility(View.GONE);
        } else {
            mRetailerView.setVisibility(View.GONE);
        }
        setMapView(false, "profile");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add_address:
                addAddressSelected();
                break;
            case R.id.iv_aadhar_front_image_field:
                selectedPhotoType = 0;
                capturePhotoSelected();
                break;
            case R.id.iv_aadhar_back_image_field:
                selectedPhotoType = 1;
                capturePhotoSelected();
                break;
            case R.id.iv_pan_card_no_image_field:
                selectedPhotoType = 2;
                capturePhotoSelected();
                break;
            case R.id.iv_shop_image_field:
                selectedPhotoType = 3;
                capturePhotoSelected();
                break;
            case R.id.open_time:
            case R.id.close_time:
                new CustomTimePicker((AppCompatTextView) view, getActivity());
                break;
            default:
                break;
        }
    }

    private void capturePhotoSelected() {
        CropImage.activity()
                .start(requireActivity(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null) {
                if (resultCode == RESULT_OK) {
                    try {
                        Uri profileImageUri = result.getUri();
                        if (profileImageUri != null) {
                            showConfirmDialog(profileImageUri);
                        }
                    } catch (Exception ex) {
                        showDialog("", ex.getMessage());
                    }

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    LoggerInfo.errorLog("cropping error", error.getMessage());
                }
            }
        }
    }

    private void showConfirmDialog(Uri profileImageUri) {
        showConfirmationDialog(getString(R.string.image_saving_confirmation_alert), pObject -> {
            if (selectedPhotoType == 0) {
                aadharFrontImageUrl = profileImageUri.getPath();
                UpdateProfileImageServices.enqueueWork(requireActivity(), AADHAR_FRONT_IMAGE, aadharFrontImageUrl);
                ivAadharFrontImageField.setLocalImageUri(profileImageUri);
            } else if (selectedPhotoType == 1) {
                aadharBackImageUrl = profileImageUri.getPath();
                UpdateProfileImageServices.enqueueWork(requireActivity(), AADHAR_BACK_IMAGE, aadharBackImageUrl);
                ivAadharBackImageField.setLocalImageUri(profileImageUri);
            } else if (selectedPhotoType == 2) {
                panCardImageUrl = profileImageUri.getPath();
                UpdateProfileImageServices.enqueueWork(requireActivity(), PANCARD_IMAGE, panCardImageUrl);
                ivPanCardImageField.setLocalImageUri(profileImageUri);
            } else if (selectedPhotoType == 3) {
                shopImageUrl = profileImageUri.getPath();
                UpdateProfileImageServices.enqueueWork(requireActivity(), SHOP_IMAGE, shopImageUrl);
                ivShopImageField.setLocalImageUri(profileImageUri);
            }
        });
    }

    private void addAddressSelected() {
        String aadharNo = "";
        if (mRetailerView.getVisibility() == View.VISIBLE) {

            String shopName = Objects.requireNonNull(tvShopName.getText()).toString().trim();
            if (TextUtils.isEmpty(shopName)) {
                showDialog(getString(R.string.shop_name_required));
                return;
            }

            if (TextUtils.isEmpty(shopImageUrl)) {
                showDialog(getString(R.string.shop_image_required));
                return;
            }

            String shopNO = Objects.requireNonNull(tvShopNO.getText()).toString().trim();
            if (TextUtils.isEmpty(shopNO)) {
                showDialog(getString(R.string.shop_no_required));
                return;
            }


            String gstNumber = Objects.requireNonNull(tvGSTNumber.getText()).toString().trim();
            if (TextUtils.isEmpty(gstNumber)) {
                showDialog(getString(R.string.gst_number_required));
                return;
            }

            String deliveryRadius = Objects.requireNonNull(tvDeliveryRadius.getText()).toString().trim();
            if (TextUtils.isEmpty(deliveryRadius)) {
                showDialog(getString(R.string.delivery_radius_required));
                return;
            }

            String deliveryCharges = Objects.requireNonNull(etvDeliveryCharges.getText()).toString().trim();
            if (TextUtils.isEmpty(deliveryCharges)) {
                showDialog(getString(R.string.delivery_charges_required));
                return;
            }

            String openingTime = Objects.requireNonNull(tvOpeningTIme.getText()).toString().trim();
            if (TextUtils.isEmpty(openingTime)) {
                showDialog(getString(R.string.opening_time_required));
                return;
            }

            String closingTime = Objects.requireNonNull(tvClosingTIme.getText()).toString().trim();
            if (TextUtils.isEmpty(closingTime)) {
                showDialog(getString(R.string.closing_time_required));
                return;
            }

            String deliveryDaysBeforeTime = Objects.requireNonNull(tvDeliveryDaysBeforeTime.getText()).toString().trim();
            if (TextUtils.isEmpty(deliveryDaysBeforeTime)) {
                showDialog(getString(R.string.delivery_days_before_time));
                return;
            }


            String deliveryDaysAfterTime = Objects.requireNonNull(tvDeliveryDaysAfterTime.getText()).toString().trim();
            if (TextUtils.isEmpty(deliveryDaysAfterTime)) {
                showDialog(getString(R.string.delivery_days_after_time));
                return;
            }

            aadharNo = Objects.requireNonNull(tvAadharNoField.getText()).toString().trim();
            if (TextUtils.isEmpty(aadharNo)) {
                showDialog(getString(R.string.enter_your_aadhar_number));
                return;
            }
            if (aadharNo.length() != 12) {
                showDialog(getString(R.string.aadhar_number_error));
                return;
            }

            if (TextUtils.isEmpty(aadharFrontImageUrl)) {
                showDialog(getString(R.string.aadhar_front_image_required));
                return;
            }
            if (TextUtils.isEmpty(aadharBackImageUrl)) {
                showDialog(getString(R.string.aadhar_back_image_required));
                return;
            }
            String panCardNo = Objects.requireNonNull(tvPANNumber.getText()).toString().trim();
            if (TextUtils.isEmpty(panCardNo)) {
                showDialog(getString(R.string.enter_your_pan_number));
                return;
            }
            if (TextUtils.isEmpty(panCardImageUrl)) {
                showDialog(getString(R.string.pancard_image_required));
                return;
            }

            String streetAddress = Objects.requireNonNull(tvStreetAddress.getText()).toString().trim();
            if (TextUtils.isEmpty(streetAddress)) {
                showDialog(getString(R.string.street_address_required));
                return;
            }

            String city = Objects.requireNonNull(tvCity.getText()).toString().trim();
            if (TextUtils.isEmpty(city)) {
                showDialog(getString(R.string.city_required));
                return;
            }

        }

        progressDialog.show();
        if (myAddress.getId() == -1) {
            getAddressData();
            ProfileService profileService = RetrofitClientInstance.getRetrofitInstance().create(ProfileService.class);
            profileService.addAddress(myAddress.getShopName(), myAddress.getPan_no(), myAddress.getGstInNo(), myAddress.getStore_number(),
                    myAddress.getAddress(), myAddress.getCity(), myAddress.getState(), myAddress.getPinCode(), myAddress.getLatitude(),
                    myAddress.getLongitude(), MyProfile.getInstance().getUserID(), MyProfile.getInstance().getRoleID(),
                    myAddress.getDeliveryRadius(), Utils.CLIENT_ID, aadharNo, myAddress.getDeliveryCharges(),
                    myAddress.getOpeningTime(), myAddress.getClosingTime(), myAddress.getDeliveryDaysAfterTime(), myAddress.getDeliveryDaysBeforeTime()).enqueue(new Callback<AddressListResponse>() {
                @Override
                public void onResponse(@NotNull Call<AddressListResponse> call, @NotNull Response<AddressListResponse> response) {
                    if (response.isSuccessful()) {
                        AddressListResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                showDialog(getString(R.string.address_is_added), pObject -> {
                                    MyProfile.getInstance().setPrimaryAddressId(data.getResponse().get(0).getId().toString());
                                    MyProfile.getInstance().setAddressResponses(data.getResponse());
                                    if (isAddNewAddress) {
                                        gotoCustomerHomeScreen();
                                    }
                                    Objects.requireNonNull(requireActivity()).onBackPressed();
                                });
                            } else {
                                myAddress = null;
                                showDialog("", data.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<AddressListResponse> call, @NotNull Throwable t) {
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
                    myAddress.getDeliveryRadius(), Utils.CLIENT_ID, myAddress.getId(), aadharNo, myAddress.getDeliveryCharges(),
                    myAddress.getOpeningTime(), myAddress.getClosingTime(), myAddress.getDeliveryDaysAfterTime(), myAddress.getDeliveryDaysBeforeTime()).enqueue(new Callback<AddressListResponse>() {
                @Override
                public void onResponse(@NotNull Call<AddressListResponse> call, @NotNull Response<AddressListResponse> response) {
                    if (response.isSuccessful()) {
                        AddressListResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                showDialog(data.getMsg(), pObject -> {
                                    MyProfile.getInstance().setAddressResponses(data.getResponse());
                                    Objects.requireNonNull(requireActivity()).onBackPressed();
                                });
                            } else {
                                showDialog("", data.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<AddressListResponse> call, @NotNull Throwable t) {
                    showDialog("", t.getMessage());
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void gotoCustomerHomeScreen() {
        MyProfile myProfile = MyProfile.getInstance();
        Intent intent;
        if (myProfile != null) {
            String roleId = myProfile.getRoleID();
            if (roleId.equals(Utils.CUSTOMER_ID)) {
                intent = new Intent(requireActivity(), CustomerHomeActivity.class);
            } else {
                intent = new Intent(requireActivity(), OrdersActivity.class);
            }
            startActivity(intent);
        }
    }

    private void getAddressData() {
        myAddress.setLongitude(longitude);
        myAddress.setLatitude(latitude);
        myAddress.setShopName(Objects.requireNonNull(tvShopName.getText()).toString());
        myAddress.setPan_no(Objects.requireNonNull(tvPANNumber.getText()).toString());
        myAddress.setGstInNo(Objects.requireNonNull(tvGSTNumber.getText()).toString());
        myAddress.setStore_number(Objects.requireNonNull(tvShopNO.getText()).toString());
        myAddress.setAddress(Objects.requireNonNull(tvStreetAddress.getText()).toString());
        myAddress.setCity(Objects.requireNonNull(tvCity.getText()).toString());
        myAddress.setState(Objects.requireNonNull(tvState.getText()).toString());
        myAddress.setDeliveryRadius(Objects.requireNonNull(tvDeliveryRadius.getText()).toString());
        myAddress.setPinCode(Objects.requireNonNull(tvPinCode.getText()).toString());

        myAddress.setDeliveryCharges(Objects.requireNonNull(etvDeliveryCharges.getText()).toString());

        myAddress.setOpeningTime(Objects.requireNonNull(tvOpeningTIme.getText()).toString());
        myAddress.setClosingTime(Objects.requireNonNull(tvClosingTIme.getText()).toString());

        myAddress.setDeliveryDaysAfterTime(Objects.requireNonNull(tvDeliveryDaysAfterTime.getText()).toString());
        myAddress.setDeliveryDaysBeforeTime(Objects.requireNonNull(tvDeliveryDaysBeforeTime.getText()).toString());

    }

    public void updateLocationDetails(LatLng latLng) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;
    }
}