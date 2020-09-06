package com.rmart.profile.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.rmart.R;
import com.rmart.inventory.adapters.CustomStringAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.LoginResponse;
import com.rmart.utilits.services.ProfileService;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditMyProfileFragment extends BaseMyProfileFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppCompatEditText tvFirstName, tvLastName;
    AppCompatTextView tvMobileNumber, tvEmail;
    private boolean mIsFromLogin;
    private String mParam2;
    private Spinner spinner;
    private String selectedGender;
    // private MyProfileViewModel myProfileViewModel;
    ArrayList<String> strings = new ArrayList<>();
    public EditMyProfileFragment() {
    }

    public static EditMyProfileFragment newInstance(boolean isEdit, String param2) {
        EditMyProfileFragment fragment = new EditMyProfileFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isEdit);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIsFromLogin = getArguments().getBoolean(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.edit_my_profile));
        updateUI(Objects.requireNonNull(MyProfile.getInstance()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // myProfileViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MyProfileViewModel.class);
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


        strings.add("Male");
        strings.add("Femail");
        strings.add("Other");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                selectedGender = strings.get(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CustomStringAdapter customStringAdapter = new CustomStringAdapter(strings, this.getContext());
        spinner.setAdapter(customStringAdapter);
        view.findViewById(R.id.submit).setOnClickListener(this);

        updateUI(Objects.requireNonNull(MyProfile.getInstance()));


        view.findViewById(R.id.submit).setOnClickListener(this);
        // view.findViewById(R.id.update_location).setOnClickListener(this);

    }
    private void updateUI(MyProfile myProfile) {
        tvFirstName.setText(myProfile.getFirstName());
        tvLastName.setText(myProfile.getLastName());
        tvMobileNumber.setText(myProfile.getMobileNumber());
        tvEmail.setText(myProfile.getEmail());
        spinner.setSelection(strings.indexOf(myProfile.getGender()));
        /*if(mIsFromLogin || myProfile.getMyLocations().size()<0) {
            MyProfile.getInstance().setMyLocations(new MyLocation());
        }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_location:
                mListener.gotoMapView();
                break;
            case R.id.submit:
                String email = Objects.requireNonNull(tvEmail.getText()).toString();
                if (!Utils.isValidEmail(email)) {
                    showDialog("", getString(R.string.required_mail));
                } else {
                    progressDialog.show();
                    ProfileService profileService = RetrofitClientInstance.getRetrofitInstance().create(ProfileService.class);
                    profileService.updateProfile(MyProfile.getInstance().getMobileNumber(),
                            tvFirstName.getText().toString(), tvLastName.getText().toString(), MyProfile.getInstance().getUserID(),
                            selectedGender, email, MyProfile.getInstance().getPrimaryAddressId()).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if(response.isSuccessful()) {
                                LoginResponse data = response.body();
                                if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                    data.getLoginData().setAddressResponses(MyProfile.getInstance().getAddressResponses());
                                    MyProfile.setInstance(data.getLoginData());
                                    Objects.requireNonNull(getActivity()).onBackPressed();
                                } else {
                                    showDialog("", data.getMsg());
                                }
                            } else {
                                showDialog("", response.message());
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            showDialog("", t.getMessage());
                        }
                    });
                }
                break;
        }
    }
}