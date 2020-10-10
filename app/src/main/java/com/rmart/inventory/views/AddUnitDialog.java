package com.rmart.inventory.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;

import com.rmart.R;
import com.rmart.baseclass.InputFilterMinMax;
import com.rmart.inventory.adapters.CustomStringAdapter;
import com.rmart.inventory.models.UnitObject;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.APIStockListResponse;
import com.rmart.utilits.pojos.APIStockResponse;
import com.rmart.utilits.pojos.APIUnitMeasureResponse;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import static com.rmart.inventory.views.AddProductToInventory.UNIT_VALUE;


public class AddUnitDialog extends DialogFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    ArrayList<String> availableUnits;
    UnitObject unitObject;
    private boolean mIsEdit;
    CustomStringAdapter customStringAdapter, productStatusAdapter;
    AppCompatEditText discount, actualPrice, valueOfUnit, quantity;
    AppCompatTextView finalPrice, displayUnit, quantityValue;
    Spinner unitsSpinner, productStatusSpinner;
    // private InventoryViewModel inventoryViewModel;
    ArrayList<String> availableUnitsMeasurements = new ArrayList<>();
    ArrayList<String> productStatus = new ArrayList<>();
    ArrayList<APIStockResponse> apiStockResponses = new ArrayList<>();
    APIStockListResponse apiStockListResponse;

    //SwitchCompat isActive;
    public AddUnitDialog() {
        // Required empty public constructor
    }

    public static AddUnitDialog newInstance(UnitObject unitObject, boolean isEdit, APIStockListResponse stockListResponse) {
        // APIStockListResponse apiStockListResponse = stockListResponse;
        AddUnitDialog fragment = new AddUnitDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, unitObject);
        args.putSerializable(ARG_PARAM3, stockListResponse);
        args.putBoolean(ARG_PARAM2, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            unitObject = (UnitObject) getArguments().getSerializable(ARG_PARAM1);
            apiStockListResponse = (APIStockListResponse) getArguments().getSerializable(ARG_PARAM3);
            assert apiStockListResponse != null;
            apiStockResponses = apiStockListResponse.getArrayList();
            mIsEdit = getArguments().getBoolean(ARG_PARAM2);
        }
        // inventoryViewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);
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
        actualPrice = view.findViewById(R.id.price);
        finalPrice = view.findViewById(R.id.final_price);
        displayUnit = view.findViewById(R.id.display_unit);
        view.findViewById(R.id.save).setOnClickListener(this);
        view.findViewById(R.id.cancel).setOnClickListener(this);
        discount.setFilters(new InputFilter[]{new InputFilterMinMax(1, 99)});
        actualPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                unitObject.setActualCost(charSequence.toString());
                String _discount = Objects.requireNonNull(discount.getText()).toString();
                calculateFinalCost(charSequence.toString(), _discount);
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
                calculateFinalCost(price, charSequence.toString());
                /*if (charSequence.length() > 0) {
                    unitObject.setDiscount(charSequence.toString());
                    double _price = Utils.getDoubleValueFromString(price);
                    int _discount = Integer.parseInt(charSequence.toString());
                    calculateFinalCost(_price, _discount);
                } else {
                    calculateFinalCost(_price, 0);
                }*/
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
                updateQuantityDetails();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        unitsSpinner = view.findViewById(R.id.unit_spinner);
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
        unitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                APIUnitMeasureResponse measureResponse = unitObject.getAvailableUnits().get(pos);
                String text = measureResponse.getAttributesName();
                unitObject.setUnitMeasure(text);
                unitObject.setUnitID(unitObject.getAvailableUnits().get(pos).getId());
                unitObject.setShortName(text);
                updateDisplayValue();
                updateQuantityDetails();
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
               updateQuantityDetails();
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
        for (APIStockResponse apiStockResponse : apiStockResponses) {
            productStatus.add(apiStockResponse.getStockName());
        }
        productStatusAdapter = new CustomStringAdapter(productStatus, this.getContext());
        productStatusSpinner.setAdapter(productStatusAdapter);

        unitsSpinner.setAdapter(customStringAdapter);
        actualPrice.setText(unitObject.getActualCost());
        discount.setText(unitObject.getDiscount());
        quantity.setText(unitObject.getQuantity());

        calculateFinalCost(unitObject.getActualCost(), unitObject.getDiscount());
        /*if (unitObject.getFinalCost().length() <= 0) {
            calculateFinalCost(0, 0);
        } else {
            finalPrice.setText(unitObject.getFinalCost());
        }*/
    }

    private void updateDisplayValue() {
        unitObject.setUnit_number(Objects.requireNonNull(valueOfUnit.getText()).toString());
        unitObject.setDisplayUnitValue(unitObject.getUnitNumber() + " " + unitObject.getShortName());
        displayUnit.setText(unitObject.getDisplayUnitValue());
    }

    private void updateQuantityDetails() {
        // int valueOfUnitValue = Utils.getIntegerValueFromString(valueOfUnit.getText().toString().trim());
        int quantityValueInt = Utils.getIntegerValueFromString(quantity.getText().toString().trim());
        // int totalQuantityDetails = quantityValueInt * valueOfUnitValue;
        if(quantityValueInt != 0) {
            String temp = String.format(Locale.getDefault(), "%d %s", quantityValueInt, unitObject.getShortName());
            quantityValue.setText(temp);
        } else {
            quantityValue.setText("");
        }
    }

    private void calculateFinalCost(String _price, String _discount) {
        try {

            int price = Utils.getIntegerValueFromString(_price);
            int discount = Utils.getIntegerValueFromString(_discount);

            if (discount != 0) {
                float data = ((float) (100-discount) / (float) 100);
                double discountedPrice = price * data;
                if (discountedPrice > 0) {
                    unitObject.setFinalCost(String.valueOf(Utils.roundOffDoubleValue(discountedPrice)));
                    String __finalPrice = String.format(getString(R.string.after_discount), unitObject.getFinalCost());
                    finalPrice.setText(__finalPrice);
                } else {
                    String __finalPrice = String.format(getString(R.string.after_discount), "");
                    finalPrice.setText(__finalPrice);
                }
            } else {
                String __finalPrice = String.format(getString(R.string.after_discount), price+"");
                finalPrice.setText(__finalPrice);
            }
        } catch (Exception e) {

        }

    }

   /* private void calculateFinalCost(double _price, int _discount) {
        try {
            if (_discount != 0 &&  _price != 0) {
                float data = ((float) _discount / 100);
                double discountedPrice = _price - (_price * data);
                if (discountedPrice > 0) {
                    unitObject.setFinalCost(String.valueOf(Utils.roundOffDoubleValue(discountedPrice)));
                    String __finalPrice = String.format(getString(R.string.after_discount), unitObject.getFinalCost());
                    finalPrice.setText(__finalPrice);
                } else {
                    String __finalPrice = String.format(getString(R.string.after_discount), "");
                    finalPrice.setText(__finalPrice);
                }
            } else {
                String __finalPrice = String.format(getString(R.string.after_discount), Double.toString(_price));
                finalPrice.setText(__finalPrice);
            }
        } catch (Exception e) {

        }

    }*/

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save) {

            for (APIStockResponse apiStockResponse : apiStockResponses) {
                if (unitObject.getProductStatus().equalsIgnoreCase(apiStockResponse.getStockName())) {
                    unitObject.setStockID(apiStockResponse.getStockID());
                    unitObject.setStockID(apiStockResponse.getStockID());
                }
            }
            String _actualPrice = Objects.requireNonNull(actualPrice.getText()).toString();
            String _quantity = Objects.requireNonNull(quantity.getText()).toString();
            int valueOfUnitValue = Utils.getIntegerValueFromString(valueOfUnit.getText().toString().trim());
             if ( valueOfUnitValue<=0 ) {
                Toast.makeText(getContext(), R.string.error_quantity_value, Toast.LENGTH_SHORT).show();
            } else if (_actualPrice.length() <= 0) {
                Toast.makeText(getContext(), R.string.error_unit_amount, Toast.LENGTH_SHORT).show();
            } else if (_quantity.length() <= 0) {
                Toast.makeText(getContext(), R.string.error_valid_quantity, Toast.LENGTH_SHORT).show();
            }  else {
                unitObject.setQuantity(_quantity);
                Intent i = new Intent().putExtra(UNIT_VALUE, unitObject);
                Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                dismiss();
            }
        } else if (view.getId() == R.id.cancel) {
            this.dismiss();
        }
    }
}