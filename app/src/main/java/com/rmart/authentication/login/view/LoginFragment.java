package com.rmart.authentication.login.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rmart.R;
import com.rmart.authentication.login.viewmodels.LoginServicemodule;
import com.rmart.authentication.registration.view.RegisterFragment;
import com.rmart.authentication.views.LoginBaseFragment;
import com.rmart.baseclass.Constants;
import com.rmart.baseclass.LoginDetailsModel;
import com.rmart.databinding.FragmentLoginBinding;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RokadMartCache;
import com.rmart.utilits.UpdateCartCountDetails;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.LoginResponse;
import com.rmart.utilits.pojos.ProfileResponse;

import java.util.Objects;


public class LoginFragment extends LoginBaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String deviceToken;
    public String mMobileNumber;
    ProfileResponse profileResponse;
    LoginServicemodule mViewModel;
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
     LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentLoginBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false);


        StringBuilder sb=new StringBuilder();

        binding.editTextOne.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(sb.length()==0&binding.editTextOne.length()==1)
                {
                    sb.append(s);
                    binding.editTextOne.clearFocus();
                    binding.editTextTwo.requestFocus();
                    binding.editTextTwo.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                if(sb.length()==1)
                {

                    sb.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if(sb.length()==0)
                {

                    binding.editTextOne.requestFocus();
                }

            }
        });
        binding.editTextTwo.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(sb.length()==0&binding.editTextOne.length()==1)
                {
                    sb.append(s);
                    binding.editTextTwo.clearFocus();
                    binding.editTextThree.requestFocus();
                    binding.editTextThree.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                if(sb.length()==1)
                {

                    sb.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if(sb.length()==0)
                {

                    binding.editTextTwo.requestFocus();
                }

            }
        });
        binding.editTextThree.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(sb.length()==0&binding.editTextOne.length()==1)
                {
                    sb.append(s);
                    binding.editTextThree.clearFocus();
                    binding.editTextFour.requestFocus();
                    binding.editTextFour.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                if(sb.length()==1)
                {

                    sb.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if(sb.length()==0)
                {

                    binding.editTextThree.requestFocus();
                }

            }
        });

        mViewModel = new ViewModelProvider(this).get(LoginServicemodule.class);
        mViewModel.isLoading.setValue(false);
        mViewModel.isVisibleLogin.setValue(true);
        mViewModel.isVisibleOTP.setValue(false);

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(requireActivity(), instanceIdResult -> {
            deviceToken = instanceIdResult.getToken();
            LoggerInfo.printLog("FCM Token", deviceToken);
            Log.d("DEVICE_TOKEN",deviceToken);
            mViewModel.device_id.setValue(deviceToken);
            System.out.println(mViewModel.device_id.getValue());
        });
        binding.setLoginOtpModule(mViewModel);
        binding.setLifecycleOwner(this);
        mViewModel.VerifyOTPPOJOMutableLiveData.observeForever(new Observer<LoginResponse>() {
            @Override
            public void onChanged(com.rmart.utilits.pojos.LoginResponse loginResponse) {
                Log.d("loginResponse",loginResponse.getMsg());
                  if (loginResponse.getMsg().equalsIgnoreCase("Mobile Number not exist")){
                      changefragment(mViewModel.mobile_numberr.getValue());
                    }
                    else if(loginResponse.getMsg().equalsIgnoreCase("Wrong OTP")){

                      showDialog(loginResponse.getMsg());
                    }else {

                      checkCredentials(loginResponse);
                  }
            }
        });
        return binding.getRoot();

    }

    private void changefragment(String mMobileNumber) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.base_container, RegisterFragment.newInstance(mMobileNumber)).addToBackStack(null)
                .commit();
    }


    private void checkCredentials(LoginResponse data) {

        if (data != null) {
            if (data.getStatus().equalsIgnoreCase("success")) {
                if (data.getLoginData().getRoleID().equalsIgnoreCase(getString(R.string.role_id))) {
                    try {
                        LoginDetailsModel loginDetailsModel = new LoginDetailsModel();
                      //  loginDetailsModel.setMobileNumber(mMobileNumber);
                        //loginDetailsModel.setPassword(mPassword);
                        profileResponse = data.getLoginData();
                        //checkRegistration(profileResponse,mMobileNumber,"1234");

                        MyProfile.setInstance(getActivity(),profileResponse);
                        //MyProfile.getInstance().setCartCount(profileResponse.getTotalCartCount());
                        UpdateCartCountDetails.updateCartCountDetails.onNext(profileResponse.getTotalCartCount());
                        if (MyProfile.getInstance(getActivity()).getPrimaryAddressId() == null) {
                            mListener.goToProfileActivity(true);
                        } else {
                            switch (data.getLoginData().getRoleID()) {
                                case Utils.CUSTOMER_ID:

                                    RokadMartCache.putData(Constants.CACHE_CUSTOMER_DETAILS, requireActivity(), loginDetailsModel);
                                    mListener.goToCustomerHomeActivity();
                                    SharedPreferences sharedPref = Objects.requireNonNull(requireActivity()).getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString(getString(R.string.uid), Utils.CUSTOMER_ID);
                                    editor.apply();
                                    break;
                                case Utils.RETAILER_ID:
                                    // RokadMartCache.putData(Constants.CACHE_RETAILER_DETAILS, requireActivity(), loginDetailsModel);
                                    mListener.goToHomeActivity();
                                    break;
                                case Utils.DELIVERY_ID:
                                    // RokadMartCache.putData(Constants.CACHE_DELIVERY_DETAILS, requireActivity(), loginDetailsModel);
                                    mListener.goToHomeActivity();
                                    break;
                            }
                        }

                        // mListener.goToProfileActivity();
                        Objects.requireNonNull(requireActivity()).onBackPressed();
                    } catch (Exception e) {
                        if (data.getMsg().contains("role id")) {
                            showDialog("", getString(R.string.error_role_login));
                        } else {
                            showDialog(e.getMessage());
                        }
                    }
                } else {
                    showDialog("", getString(R.string.error_role_login));
                }
            } else {
                if (data.getMsg().contains("role id")) {
                    showDialog("", getString(R.string.error_role_login));
                } else {
                    showDialog("", data.getMsg(), (dialogInterface, i) -> {
                        if (data.getMsg().contains("verify")) {
                            //resendOTP();
                        } else if (data.getMsg().contains("mail_verify")) {
                            mListener.validateMailOTP();
                        } else if (data.getMsg().contains("pay")) {
                            mListener.proceedToPayment(data.getPaymentData());
                        }
                    });
                }
            }
        } else {
            showDialog(getString(R.string.no_information_available));
        }
    }


}