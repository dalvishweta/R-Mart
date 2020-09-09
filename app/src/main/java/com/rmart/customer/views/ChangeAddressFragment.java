package com.rmart.customer.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.customer.adapters.ChangeAddressAdapter;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

/**
 * Created by Satya Seshu on 07/09/20.
 */
public class ChangeAddressFragment extends CustomerHomeFragment {

    private ChangeAddressAdapter changeAddressAdapter;
    private RecyclerView addressListField;
    private List<Object> addressList;

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
        return inflater.inflate(R.layout.fragment_change_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        addressListField = view.findViewById(R.id.addresses_list_field);
        Button btnAddNewAddressField = view.findViewById(R.id.btn_add_new_address);
        btnAddNewAddressField.setOnClickListener(v-> {
            addNewAddressSelected();
        });
        Button btnSelectThisAddress = view.findViewById(R.id.btn_select_this_address);
        btnSelectThisAddress.setOnClickListener(v-> {
            addSelectAddressSelected();
        });
        addressListField.setHasFixedSize(false);
        addressListField.setItemAnimator(new SlideInDownAnimator());
        addressList = new ArrayList<>();

        changeAddressAdapter = new ChangeAddressAdapter(requireActivity(), addressList);
        addressListField.setAdapter(changeAddressAdapter);
    }

    private void addSelectAddressSelected() {

    }

    private void addNewAddressSelected() {
    }
}
