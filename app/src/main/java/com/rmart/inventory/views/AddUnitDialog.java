package com.rmart.inventory.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

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
import com.rmart.inventory.adapters.CustomStringAdapter;
import com.rmart.inventory.models.UnitObject;
import com.rmart.inventory.viewmodel.InventoryViewModel;
import com.rmart.utilits.pojos.APIStockResponse;
import com.rmart.utilits.pojos.APIUnitMeasureResponse;

import java.util.ArrayList;
import java.util.Objects;

import static com.rmart.inventory.views.AddProductToInventory.UNIT_VALUE;


public class AddUnitDialog extends DialogFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<String> availableUnits;
    UnitObject unitObject;
    private boolean mIsEdit;
    CustomStringAdapter customStringAdapter, productStatusAdapter;
    AppCompatEditText discount, actualPrice, valueOfUnit, quantity, quantityValue;
    AppCompatTextView finalPrice, displayUnit;
    Spinner spinner, productStatusSpinner;
    private InventoryViewModel inventoryViewModel;
    ArrayList<String> availableUnitsMeasurements = new ArrayList<>();
    ArrayList<String> productStatus = new ArrayList<>();

    //SwitchCompat isActive;
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
        inventoryViewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);
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
        displayUnit = view.findViewById(R.id.display_unit);
        view.findViewById(R.id.save).setOnClickListener(this);
        view.findViewById(R.id.cancel).setOnClickListener(this);
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
        valueOfUnit = view.findViewById(R.id.value_of_unit);
        valueOfUnit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateDisplayValue();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        spinner = view.findViewById(R.id.unit);
        productStatusSpinner = view.findViewById(R.id.product_status);
        quantity = view.findViewById(R.id.quantity);

        quantityValue = view.findViewById(R.id.quantity_value);


        productStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String text = productStatus.get(pos);
                unitObject.setProductStatus(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String text = unitObject.getAvailableUnits().get(pos).getAttributesName();
                unitObject.setUnitMeasure(text);
                updateDisplayValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String temp = charSequence.toString()+" "+unitObject.getUnitMeasure();
                quantityValue.setText(temp);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //isActive = view.findViewById(R.id.switchButton);
        for (APIUnitMeasureResponse unitMeasureResponse : unitObject.getAvailableUnits()) {
            availableUnitsMeasurements.add(unitMeasureResponse.getAttributesName());
        }
        customStringAdapter = new CustomStringAdapter(availableUnitsMeasurements, this.getContext());

        for (APIStockResponse apiStockResponse : Objects.requireNonNull(inventoryViewModel.getApiStocks().getValue())) {
            productStatus.add(apiStockResponse.getStockName());
        }
        productStatusAdapter = new CustomStringAdapter(productStatus, this.getContext());
        productStatusSpinner.setAdapter(productStatusAdapter);

        spinner.setAdapter(customStringAdapter);
        actualPrice.setText(unitObject.getActualCost());
        discount.setText(unitObject.getDiscount());
        quantity.setText(unitObject.getQuantity());
        if(unitObject.getFinalCost().length()<=0) {
            calculateFinalCost(0, 0);
        } else {
            finalPrice.setText(unitObject.getFinalCost());
        }
    }

    private void updateDisplayValue() {
        unitObject.setUnit_number(Objects.requireNonNull(valueOfUnit.getText()).toString());
        unitObject.setDisplayUnitValue(unitObject.getUnit_number()+ " "+unitObject.getUnitMeasure());
        displayUnit.setText(unitObject.getDisplayUnitValue());
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
            for (APIStockResponse apiStockResponse : Objects.requireNonNull(inventoryViewModel.getApiStocks().getValue())) {
                if (unitObject.getProductStatus().equalsIgnoreCase(apiStockResponse.getStockName())) {
                    unitObject.setStockID(apiStockResponse.getStockID());
                    unitObject.setStockID(apiStockResponse.getStockID());
                }
            }
            String _actualPrice = Objects.requireNonNull(actualPrice.getText()).toString();
            String _quantity = Objects.requireNonNull(quantity.getText()).toString();
            if(_actualPrice.length()<=0) {
                Toast.makeText(getContext(), R.string.error_unit_amount, Toast.LENGTH_SHORT).show();
            } else if (_quantity.length()<=0) {
                Toast.makeText(getContext(), R.string.error_valid_quantity, Toast.LENGTH_SHORT).show();
            } else {
                unitObject.setQuantity(_quantity);
                    Intent i = new Intent().putExtra(UNIT_VALUE, unitObject);
                    Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
            }
            dismiss();
        } else if (view.getId() == R.id.cancel) {
            this.dismiss();
        }
    }
}