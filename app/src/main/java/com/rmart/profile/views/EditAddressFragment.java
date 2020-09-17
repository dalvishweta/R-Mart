package com.rmart.profile.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.AddressListResponse;
import com.rmart.utilits.pojos.AddressResponse;
import com.rmart.utilits.services.ProfileService;
import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class EditAddressFragment extends BaseMyProfileFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppCompatEditText tvShopName, tvPANNumber, tvGSTNumber, tvStreetAddress, tvCity, tvShopNO, tvDeliveryRadius, tvPinCode, tvState;
    LinearLayout mRetailerView;
    AddressViewModel addressViewModel;
    AddressResponse myAddress;
    private AppCompatEditText tvAadharNoField, tvKycNoField;
    private ImageView ivAadharFrontImageField, ivAadharBackImageField, ivKycImageField;
    private boolean aadharFrontSelected = false;
    private boolean aadharBackSelected = false;
    private boolean kycSelected = false;
    private Bitmap aadharFrontImageBitmap, aadharBackImageBitmap, kycImageBitmap;
    private boolean isAddNewAddress;

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
        addressViewModel = new ViewModelProvider(Objects.requireNonNull(requireActivity())).get(AddressViewModel.class);
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
        ivKycImageField = view.findViewById(R.id.iv_kyc_no_image_field);
        tvAadharNoField = view.findViewById(R.id.tv_aadhar_number_no_field);
        tvKycNoField = view.findViewById(R.id.tv_kyc_no_field);

        view.findViewById(R.id.add_address).setOnClickListener(this);
        ivAadharFrontImageField.setOnClickListener(this);
        ivAadharBackImageField.setOnClickListener(this);
        ivKycImageField.setOnClickListener(this);
        updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.update_address));
        updateUI();
    }

    private void updateUI() {
        if(null != myAddress) {
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
        } else {
            Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.update_address));
        }
        if (BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.RETAILER_ID)) {
            mRetailerView.setVisibility(View.VISIBLE);
            if (null != myAddress) {
                tvStreetAddress.setText(myAddress.getAddress());
            }
        } else if (BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.CUSTOMER_ID)) {
            mRetailerView.setVisibility(View.GONE);
        } else {
            mRetailerView.setVisibility(View.GONE);
        }
        setMapView(false, "profile");
    }

    private void resetImageFields() {
        aadharFrontSelected = false;
        aadharBackSelected = false;
        kycSelected = false;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add_address:
                addAddressSelected();
                break;
            case R.id.iv_aadhar_front_image_field:
                resetImageFields();
                aadharFrontSelected = true;
                capturePhotoSelected();
                break;
            case R.id.iv_aadhar_back_image_field:
                resetImageFields();
                aadharBackSelected = true;
                capturePhotoSelected();
                break;
            case R.id.iv_kyc_no_image_field:
                resetImageFields();
                kycSelected = false;
                capturePhotoSelected();
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
                            InputStream imageStream = requireActivity().getContentResolver().openInputStream(profileImageUri);
                            if (aadharFrontSelected) {
                                aadharFrontImageBitmap = BitmapFactory.decodeStream(imageStream);
                                ivAadharFrontImageField.setImageBitmap(aadharFrontImageBitmap);
                            } else if (aadharBackSelected) {
                                aadharBackImageBitmap = BitmapFactory.decodeStream(imageStream);
                                ivAadharBackImageField.setImageBitmap(aadharBackImageBitmap);
                            } else if (kycSelected) {
                                kycImageBitmap = BitmapFactory.decodeStream(imageStream);
                                ivKycImageField.setImageBitmap(kycImageBitmap);
                            }
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

    private String getEncodedImage(Bitmap bitmap) {
        try {
            if (bitmap != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] b = byteArrayOutputStream.toByteArray();
                return Base64.encodeToString(b, Base64.DEFAULT);
            }
        } catch (Exception ex) {
            LoggerInfo.errorLog("encode image exception", ex.getMessage());
        }
        return "";
    }

    private void addAddressSelected() {
        if (mRetailerView.getVisibility() == View.VISIBLE) {
            String aadharNo = Objects.requireNonNull(tvAadharNoField.getText()).toString().trim();
            if (TextUtils.isEmpty(aadharNo)) {
                showDialog(getString(R.string.enter_your_aadhar_number));
                return;
            }
            if (aadharNo.length() != 12) {
                showDialog(getString(R.string.aadhar_number_error));
                return;
            }
            if (aadharFrontImageBitmap == null) {
                showDialog(getString(R.string.aadhar_front_image_required));
                return;
            }
            if (aadharBackImageBitmap == null) {
                showDialog(getString(R.string.aadhar_back_image_required));
                return;
            }
            String kycNo = Objects.requireNonNull(tvKycNoField.getText()).toString().trim();
            if (TextUtils.isEmpty(kycNo)) {
                showDialog(getString(R.string.enter_your_kyc_number));
                return;
            }
            if (kycImageBitmap == null) {
                showDialog(getString(R.string.kyc_image_required));
                return;
            }
        }

        progressDialog.show();
        if (myAddress == null) {
            getAddressData();
            ProfileService profileService = RetrofitClientInstance.getRetrofitInstance().create(ProfileService.class);
            profileService.addAddress(myAddress.getShopName(), myAddress.getPan_no(), myAddress.getGstInNo(), myAddress.getStore_number(),
                    myAddress.getAddress(), myAddress.getCity(), myAddress.getState(), myAddress.getPinCode(), myAddress.getLatitude(),
                    myAddress.getLongitude(), MyProfile.getInstance().getUserID(), MyProfile.getInstance().getRoleID(),
                    myAddress.getDeliveryRadius(), Utils.CLIENT_ID).enqueue(new Callback<AddressListResponse>() {
                @Override
                public void onResponse(@NotNull Call<AddressListResponse> call, @NotNull Response<AddressListResponse> response) {
                    if (response.isSuccessful()) {
                        AddressListResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                MyProfile.getInstance().setAddressResponses(data.getResponse());
                                Objects.requireNonNull(requireActivity()).onBackPressed();
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
                    myAddress.getDeliveryRadius(), Utils.CLIENT_ID, myAddress.getId()).enqueue(new Callback<AddressListResponse>() {
                @Override
                public void onResponse(@NotNull Call<AddressListResponse> call, @NotNull Response<AddressListResponse> response) {
                    if (response.isSuccessful()) {
                        AddressListResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                MyProfile.getInstance().setAddressResponses(data.getResponse());
                                Objects.requireNonNull(requireActivity()).onBackPressed();
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