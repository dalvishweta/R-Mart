package com.rmart.inventory.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.rmart.R;
import com.rmart.baseclass.InputFilterMinMax;
import com.rmart.inventory.adapters.UnitAdapter;
import com.rmart.inventory.models.UnitObject;

import java.util.ArrayList;
import java.util.Objects;

import static com.rmart.inventory.views.EditProductFragment.UNIT_VALUE;


public class AddUnitDialog extends DialogFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<String> availableUnits;
    UnitObject unitObject;
    private boolean mIsEdit;
    UnitAdapter unitAdapter;
    AppCompatEditText discount, actualPrice;
    AppCompatTextView finalPrice;
    Spinner spinner;
    SwitchCompat isActive;
    public AddUnitDialog() {
        // Required empty public constructor
    }

    public static AddUnitDialog newInstance(UnitObject unitObject, boolean isEdit) {
        AddUnitDialog fragment = new AddUnitDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, unitObject);
        args.putBoolean(ARG_PARAM2, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            unitObject = (UnitObject) getArguments().getSerializable(ARG_PARAM1);
            mIsEdit = getArguments().getBoolean(ARG_PARAM2);
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
        discount = view.findViewById(R.id.discount);
        actualPrice  = view.findViewById(R.id.price);
        finalPrice  = view.findViewById(R.id.final_price);

        view.findViewById(R.id.save).setOnClickListener(this);
        discount.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 99)});
        actualPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                unitObject.setActualCost(charSequence.toString());
                String _discount = Objects.requireNonNull(discount.getText()).toString();
                if((charSequence.toString().length() > 0)) {
                    int _price = Integer.parseInt(charSequence.toString());
                    if (_discount.length()>0) {
                        int __discount = Integer.parseInt(_discount);
                        calculateFinalCost(_price, __discount);
                    } else {
                        calculateFinalCost(_price, 0);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String price = Objects.requireNonNull(actualPrice.getText()).toString();
                unitObject.setDiscount(charSequence.toString());
                if((charSequence.toString().length() > 0) && ( price.length() > 0)) {
                    int _price = Integer.parseInt(price);
                    int _discount = Integer.parseInt(charSequence.toString());
                    calculateFinalCost(_price, _discount);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        spinner = view.findViewById(R.id.unit);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String text = unitObject.getAvailableUnits().get(pos);
                unitObject.setUnitValue(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        isActive = view.findViewById(R.id.switchButton);
        unitAdapter = new UnitAdapter(unitObject.getAvailableUnits(), this.getContext());
        spinner.setAdapter(unitAdapter);
        actualPrice.setText(unitObject.getActualCost());
        discount.setText(unitObject.getDiscount());
        if(unitObject.getFinalCost().length()<=0) {
            calculateFinalCost(0, 0);
        } else {
            finalPrice.setText(unitObject.getFinalCost());
        }

        isActive.setChecked(unitObject.isActive());

    }

    private void calculateFinalCost(int _price, int _discount) {
        float data =  ((float) _discount/100);
        float _finalPrice  = _price - (_price *data);
        if(_finalPrice >0) {
            unitObject.setFinalCost(String.valueOf((int)_finalPrice));
            String __finalPrice = String.format(getString(R.string.after_discount), unitObject.getFinalCost());
            finalPrice.setText(__finalPrice);
        } else {
            String __finalPrice = String.format(getString(R.string.after_discount), "");
            finalPrice.setText(__finalPrice);
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.save) {

            String _actualPrice = Objects.requireNonNull(actualPrice.getText()).toString();
            if(_actualPrice.length()<=0) {
                Toast.makeText(getContext(), "Please enter valid amount", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent().putExtra(UNIT_VALUE, unitObject);
                Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
            }
            dismiss();
        } else if (view.getId() == R.id.unit) {
            /*ArrayList<ItemData> itemData = new ArrayList<>();
            itemData.add(new ItemData("1 KGS", 0));
            itemData.add(new ItemData("2 KGS", 0));
            itemData.add(new ItemData("3 KGS", 0));
            itemData.add(new ItemData("4 KGS", 0));
            FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            OptionSelectionFragment optionSelectionFragment = OptionSelectionFragment.newInstance("", itemData);
            optionSelectionFragment.show(fragmentManager, "OptionSelectionFragment");
            optionSelectionFragment.setCancelable(false);*/
        }
    }
}