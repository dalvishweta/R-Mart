package com.rmart.inventory.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.rmart.R;
import com.rmart.baseclass.InputFilterMinMax;
import com.rmart.baseclass.ItemData;
import com.rmart.baseclass.OptionSelectionFragment;

import java.util.ArrayList;
import java.util.Objects;


public class AddUnitDialog extends DialogFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AddUnitDialog() {
        // Required empty public constructor
    }

    public static AddUnitDialog newInstance(String param1, String param2) {
        AddUnitDialog fragment = new AddUnitDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        Objects.requireNonNull(getDialog().getWindow()).setAttributes(params);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_unit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatEditText discount = view.findViewById(R.id.discount);
        view.findViewById(R.id.save).setOnClickListener(this);
        view.findViewById(R.id.unit).setOnClickListener(this);
        discount.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 99)});
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.save) {
            dismiss();
        } else if (view.getId() == R.id.unit) {
            ArrayList<ItemData> itemData = new ArrayList<>();
            itemData.add(new ItemData("1 KGS", 0));
            itemData.add(new ItemData("2 KGS", 0));
            itemData.add(new ItemData("3 KGS", 0));
            itemData.add(new ItemData("4 KGS", 0));
            FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            OptionSelectionFragment optionSelectionFragment = OptionSelectionFragment.newInstance("", itemData);
            optionSelectionFragment.show(fragmentManager, "OptionSelectionFragment");
            optionSelectionFragment.setCancelable(false);
        }
    }
}