package com.rmart.customer.views;

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
import com.rmart.customer.adapters.ChangeAddressAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.views.MyProfileActivity;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.pojos.AddressResponse;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

/**
 * Created by Satya Seshu on 07/09/20.
 */
public class ChangeAddressFragment extends CustomerHomeFragment {

    private ChangeAddressAdapter changeAddressAdapter;
    private ArrayList<AddressResponse> addressList;

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
    }

    public void updateToolBar() {
        requireActivity().setTitle(getString(R.string.change_address));
        ((CustomerHomeActivity)(requireActivity())).showCartIcon();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        RecyclerView addressListField = view.findViewById(R.id.addresses_list_field);
        AppCompatButton btnAddNewAddressField = view.findViewById(R.id.btn_add_new_address);
        btnAddNewAddressField.setOnClickListener(v -> {
            addNewAddressSelected();
        });
        AppCompatButton btnSelectThisAddress = view.findViewById(R.id.btn_select_this_address);
        btnSelectThisAddress.setOnClickListener(v -> {
            selectThisAddressSelected();
        });
        addressListField.setHasFixedSize(false);
        addressListField.setItemAnimator(new SlideInDownAnimator());


        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            addressList = myProfile.getAddressResponses();
            if (addressList != null && !addressList.isEmpty()) {
                changeAddressAdapter = new ChangeAddressAdapter(requireActivity(), addressList, callBackListener);
                addressListField.setAdapter(changeAddressAdapter);
            }
        }
    }

    private CallBackInterface callBackListener = pObject -> {
        if (pObject instanceof AddressResponse) {
            AddressResponse addressResponse = (AddressResponse) pObject;
            resetAddressList();
            int index = addressList.indexOf(addressResponse);
            if (index > -1) {
                addressResponse.setIsActive(1);
                addressList.set(index, addressResponse);
                changeAddressAdapter.notifyItemChanged(index);
            }
        }
    };

    private void resetAddressList() {
        for (int i = 0; i < addressList.size(); i++) {
            AddressResponse addressResponse = addressList.get(i);
            addressResponse.setIsActive(0);
            addressList.set(i, addressResponse);
            changeAddressAdapter.notifyItemChanged(i);
        }

    }

    private void selectThisAddressSelected() {
        MyProfile.getInstance().setAddressResponses(addressList);
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void addNewAddressSelected() {
        Intent intent = new Intent(requireActivity(), MyProfileActivity.class);
        intent.putExtra("IsNewAddress", true);
        startActivity(intent);
    }
}
