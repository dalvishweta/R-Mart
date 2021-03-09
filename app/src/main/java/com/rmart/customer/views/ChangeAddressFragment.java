package com.rmart.customer.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.adapters.ChangeAddressAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.views.MyProfileActivity;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.AddressListResponse;
import com.rmart.utilits.pojos.AddressResponse;
import com.rmart.utilits.services.ProfileService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Satya Seshu on 07/09/20.
 */
public class ChangeAddressFragment extends CustomerHomeFragment {

    private ChangeAddressAdapter changeAddressAdapter;
    private ArrayList<AddressResponse> addressList = new ArrayList<>();
    private RecyclerView addressListField;
    private AddressResponse myAddress;
    private AppCompatButton btnSelectThisAddress;

    public static ChangeAddressFragment getInstance() {
        ChangeAddressFragment changeAddressFragment = new ChangeAddressFragment();
        Bundle extras = new Bundle();
        changeAddressFragment.setArguments(extras);
        return changeAddressFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LoggerInfo.printLog("Fragment", "ChangeAddressFragment");
        return inflater.inflate(R.layout.fragment_change_address, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateToolBar();
        resetAddressList();
        getAddressesList();
    }

    private void getAddressesList() {
        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            addressList = myProfile.getAddressResponses();
            if (addressList != null && !addressList.isEmpty()) {
                String primaryAddress = myProfile.getPrimaryAddressId();
                int primaryAddressValue = Utils.getIntegerValueFromString(primaryAddress);
                for (int i = 0; i < addressList.size(); i++) {
                    AddressResponse addressResponse = addressList.get(i);
                    if (addressResponse.getId() == primaryAddressValue) {
                        addressResponse.setPrimaryAddress(true);
                        addressList.set(i, addressResponse);
                        break;
                    }
                }
                if(!addressList.isEmpty()) {
                    btnSelectThisAddress.setVisibility(View.VISIBLE);
                    changeAddressAdapter = new ChangeAddressAdapter(requireActivity(), addressList, callBackListener);
                    addressListField.setAdapter(changeAddressAdapter);
                } else {
                    btnSelectThisAddress.setVisibility(View.GONE);
                }
            }
        }
    }

    public void updateToolBar() {
        requireActivity().setTitle(getString(R.string.change_address));
        ((CustomerHomeActivity) (requireActivity())).showCartIcon();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        addressListField = view.findViewById(R.id.addresses_list_field);
        AppCompatButton btnAddNewAddressField = view.findViewById(R.id.btn_add_new_address);
        btnAddNewAddressField.setOnClickListener(v -> addNewAddressSelected());
        btnSelectThisAddress = view.findViewById(R.id.btn_select_this_address);
        btnSelectThisAddress.setOnClickListener(v -> selectThisAddressSelected());
        addressListField.setHasFixedSize(false);
        addressListField.setItemAnimator(new SlideInDownAnimator());

    }

    private final CallBackInterface callBackListener = pObject -> {
        if (pObject instanceof AddressResponse) {
            resetAddressList();
            MyProfile.getInstance().setAddressResponses(addressList);
            myAddress = (AddressResponse) pObject;
            int index = addressList.indexOf(myAddress);
            if (index > -1) {
                MyProfile.getInstance().setPrimaryAddressId(myAddress.getId().toString());
                myAddress.setPrimaryAddress(true);
                addressList.set(index, myAddress);
                changeAddressAdapter.notifyItemChanged(index);
            }
        }
    };

    private void resetAddressList() {
        for (int i = 0; i < addressList.size(); i++) {
            AddressResponse addressResponse = addressList.get(i);
            addressResponse.setPrimaryAddress(false);
            addressList.set(i, addressResponse);
            changeAddressAdapter.notifyItemChanged(i);
        }
    }

    private void selectThisAddressSelected() {
        if(myAddress!=null) {
            progressDialog.show();
            ProfileService profileService = RetrofitClientInstance.getRetrofitInstance().create(ProfileService.class);
            profileService.updateAddress(myAddress.getShopACT(), myAddress.getMinimumOrder(), myAddress.getShopName(), myAddress.getPan_no(), myAddress.getGstInNo(), myAddress.getStore_number(),
                    myAddress.getAddress(), myAddress.getCity(), myAddress.getState(), myAddress.getPinCode(), myAddress.getLatitude(),
                    myAddress.getLongitude(), MyProfile.getInstance().getUserID(), MyProfile.getInstance().getRoleID(),
                    myAddress.getDeliveryRadius(), Utils.CLIENT_ID, myAddress.getId(), "", myAddress.getDeliveryCharges(),
                    myAddress.getOpeningTime(), myAddress.getClosingTime(), myAddress.getDeliveryDaysAfterTime(), myAddress.getDeliveryDaysBeforeTime(), myAddress.getId().toString(),myAddress.getBusinessType(),myAddress.getShopTypeId()+"",myAddress.getBankName(),myAddress.getIfscCode(),myAddress.getBranchName(),myAddress.getBankAccNo(),false,false).enqueue(new Callback<AddressListResponse>() {
                @Override
                public void onResponse(@NotNull Call<AddressListResponse> call, @NotNull Response<AddressListResponse> response) {
                    if (response.isSuccessful()) {
                        AddressListResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                showDialog(data.getMsg(), pObject -> {
                                    MyProfile.getInstance().setPrimaryAddressId(myAddress.getId().toString());
                                    MyProfile.getInstance().setAddressResponses(addressList);
                                    Objects.requireNonNull(requireActivity()).onBackPressed();
                                });
                            } else {
                                showDialog(data.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<AddressListResponse> call, @NotNull Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        } else {
            showDialog("", "Please Select Address");
        }
    }

    private void addNewAddressSelected() {
        Intent intent = new Intent(requireActivity(), MyProfileActivity.class);
        intent.putExtra("IsNewAddress", true);
        startActivityForResult(intent, Constants.KEY_CHANGE_ADDRESS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK) return;
        if(requestCode == Constants.KEY_CHANGE_ADDRESS) {
            requireActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }
}
