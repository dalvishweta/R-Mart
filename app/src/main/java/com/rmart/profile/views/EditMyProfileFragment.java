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
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.baseclass.views.CircularNetworkImageView;
import com.rmart.baseclass.views.ProgressBarCircular;
import com.rmart.customer.adapters.CustomSpinnerAdapter;
import com.rmart.inventory.adapters.CustomStringAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.CommonUtils;
import com.rmart.utilits.HttpsTrustManager;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.LoginResponse;
import com.rmart.utilits.services.ProfileService;
import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class EditMyProfileFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppCompatEditText tvFirstName, tvLastName;
    AppCompatTextView tvMobileNumber, tvEmail;
    private boolean mIsFromLogin;
    private Spinner spinner;
    private String selectedGender;
    // private MyProfileViewModel myProfileViewModel;
    private List<Object> gendersList = new ArrayList<>();

    private CircularNetworkImageView ivProfileImageField;
    private ProgressBarCircular profileCircularBar;
    private Bitmap profileImageBitmap;

    public EditMyProfileFragment() {
    }

    public static EditMyProfileFragment newInstance(boolean isEdit) {
        EditMyProfileFragment fragment = new EditMyProfileFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIsFromLogin = getArguments().getBoolean(ARG_PARAM1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.edit_my_profile));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_my_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvFirstName = view.findViewById(R.id.first_name);
        tvLastName = view.findViewById(R.id.last_name);
        tvMobileNumber = view.findViewById(R.id.mobile_number);
        tvEmail = view.findViewById(R.id.email);
        spinner = view.findViewById(R.id.gender);
        ivProfileImageField = view.findViewById(R.id.iv_profile_image_field);
        ivProfileImageField.setOnClickListener(this);
        profileCircularBar = view.findViewById(R.id.profile_circular_field);
        gendersList.add(Utils.SELECT_YOUR_GENDER);
        gendersList.add("Male");
        gendersList.add("Female");
        gendersList.add("Other");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                selectedGender = (String) gendersList.get(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CustomSpinnerAdapter customStringAdapter = new CustomSpinnerAdapter(requireActivity(), gendersList);
        spinner.setAdapter(customStringAdapter);
        view.findViewById(R.id.submit).setOnClickListener(this);

        updateUI(Objects.requireNonNull(MyProfile.getInstance()));

        view.findViewById(R.id.submit).setOnClickListener(this);
    }
    private void updateUI(MyProfile myProfile) {
        if(myProfile != null) {
            tvFirstName.setText(myProfile.getFirstName());
            tvLastName.setText(myProfile.getLastName());
            tvMobileNumber.setText(myProfile.getMobileNumber());
            tvEmail.setText(myProfile.getEmail());
            spinner.setSelection(gendersList.indexOf(myProfile.getGender()));
            updateUserProfileImage();
        }
    }

    private void updateUserProfileImage() {
        Bitmap bitmap = MyProfile.getInstance().getUserProfileImage().getValue();
        if (bitmap != null) {
            profileCircularBar.setVisibility(View.GONE);
            ivProfileImageField.setImageBitmap(bitmap);
        } else {
            String imageUrl = MyProfile.getInstance().getProfileImage();
            if (!TextUtils.isEmpty(imageUrl)) {
                HttpsTrustManager.allowAllSSL();
                ImageLoader imageLoader = RMartApplication.getInstance().getImageLoader();
                imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        profileCircularBar.setVisibility(View.GONE);
                        profileImageBitmap = response.getBitmap();
                        ivProfileImageField.setImageBitmap(profileImageBitmap);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        profileCircularBar.setVisibility(View.GONE);
                        ivProfileImageField.setImageResource(R.drawable.avatar);
                    }
                });
                ivProfileImageField.setImageUrl(imageUrl, imageLoader);
            } else {
                Bitmap newBitmap = MyProfile.getInstance().getUserProfileImage().getValue();
                if (newBitmap != null) {
                    profileImageBitmap = newBitmap;
                    ivProfileImageField.setImageBitmap(profileImageBitmap);
                }
                profileCircularBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
           /* case R.id.update_location:
                mListener.gotoMapView();
                break;*/
            case R.id.submit:
                submitSelected();
                break;
            case R.id.iv_profile_image_field:
                profileSelected();
                break;
            default:
                break;
        }
    }

    private void profileSelected() {
        CropImage.activity().start(Objects.requireNonNull(requireActivity()), this);
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
                            profileImageBitmap = BitmapFactory.decodeStream(imageStream);
                            if (profileImageBitmap != null) {
                                ivProfileImageField.setImageBitmap(Utils.getCompressBitmapImage(profileImageBitmap));
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

    private void submitSelected() {
        String firstName = Objects.requireNonNull(tvFirstName.getText()).toString().trim();
        if (TextUtils.isEmpty(firstName)) {
            showDialog(getString(R.string.please_enter_your_first_name));
            return;
        }
        String lastName = Objects.requireNonNull(tvLastName.getText()).toString().trim();
        if (TextUtils.isEmpty(lastName)) {
            showDialog(getString(R.string.please_enter_your_last_name));
            return;
        }
        String mobileNumber = Objects.requireNonNull(tvLastName.getText()).toString().trim();
        if (TextUtils.isEmpty(mobileNumber)) {
            showDialog(getString(R.string.required_mobile_number));
            return;
        }
        String email = Objects.requireNonNull(tvEmail.getText()).toString();
        if (TextUtils.isEmpty(email) || !Utils.isValidEmail(email)) {
            showDialog("", getString(R.string.required_mail));
            return;
        }

        if (TextUtils.isEmpty(selectedGender) || selectedGender.equalsIgnoreCase(Utils.SELECT_YOUR_GENDER)) {
            showDialog("", getString(R.string.required_gender));
            return;
        }
        if(!Utils.isNetworkConnected(requireActivity())) {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
            return;
        }

        progressDialog.show();
        String encodedImage = getEncodedImage();
        ProfileService profileService = RetrofitClientInstance.getRetrofitInstance().create(ProfileService.class);
        profileService.updateProfile(MyProfile.getInstance().getMobileNumber(),
                Objects.requireNonNull(tvFirstName.getText()).toString(), Objects.requireNonNull(tvLastName.getText()).toString(), MyProfile.getInstance().getUserID(),
                selectedGender, email, MyProfile.getInstance().getPrimaryAddressId(), encodedImage).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        LoginResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                data.getLoginData().setAddressResponses(MyProfile.getInstance().getAddressResponses());
                                MyProfile.setInstance(data.getLoginData());

                                showDialog("", data.getMsg(), pObject -> {
                                    if (profileImageBitmap != null) {
                                        MyProfile.getInstance().setUserProfileImage(profileImageBitmap);
                                    }
                                    Objects.requireNonNull(requireActivity()).onBackPressed();
                                });
                            } else {
                                showDialog(data.getMsg());
                            }
                        }
                    } else {
                        showDialog(response.message());
                    }
                } catch (Exception ex) {
                    showDialog(ex.getMessage());
                } finally {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                showDialog("", t.getMessage());
            }
        });
    }

    private String getEncodedImage() {
        try {
            if (profileImageBitmap != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                profileImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] b = byteArrayOutputStream.toByteArray();
                return Base64.encodeToString(b, Base64.DEFAULT);
            }
        } catch (Exception ex) {
            LoggerInfo.errorLog("encode image exception", ex.getMessage());
        }
        return "";
    }
}