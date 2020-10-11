package com.rmart.inventory.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.rmart.R;
import com.rmart.baseclass.InputFilterMinMax;
import com.rmart.baseclass.views.CustomLoadingDialog;
import com.rmart.inventory.adapters.CustomStringAdapter;
import com.rmart.inventory.models.APIUnitMeasures;
import com.rmart.inventory.models.UnitObject;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.APIStockListResponse;
import com.rmart.utilits.pojos.APIStockResponse;
import com.rmart.utilits.pojos.APIUnitMeasureResponse;
import com.rmart.utilits.pojos.ShowProductResponse;
import com.rmart.utilits.services.VendorInventoryService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rmart.inventory.views.AddProductToInventory.UNIT_VALUE;


public class AddUnitDialog extends DialogFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private UnitObject unitObject;
    private AppCompatEditText discount, actualPrice, valueOfUnit, quantity;
    private AppCompatTextView finalPrice, displayUnit, quantityValue;
    // private InventoryViewModel inventoryViewModel;
    private ArrayList<String> availableUnitsMeasurements = new ArrayList<>();
    private ArrayList<String> productStatus = new ArrayList<>();
    private ArrayList<APIStockResponse> apiStockResponses = new ArrayList<>();
    private APIUnitMeasures unitMeasurements;

    //SwitchCompat isActive;
    public AddUnitDialog() {
        // Required empty public constructor
    }

    public static AddUnitDialog newInstance(UnitObject unitObject, boolean isEdit, APIStockListResponse stockListResponse, APIUnitMeasures unitMeasurements) {
        // APIStockListResponse apiStockListResponse = stockListResponse;
        AddUnitDialog fragment = new AddUnitDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, unitObject);
        args.putSerializable(ARG_PARAM3, stockListResponse);
        args.putSerializable(ARG_PARAM4, unitMeasurements);
        args.putBoolean(ARG_PARAM2, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            unitObject = (UnitObject) getArguments().getSerializable(ARG_PARAM1);
            APIStockListResponse apiStockListResponse = (APIStockListResponse) getArguments().getSerializable(ARG_PARAM3);
            unitMeasurements = (APIUnitMeasures) getArguments().getSerializable(ARG_PARAM4);
            if (apiStockListResponse != null) {
                apiStockResponses = apiStockListResponse.getArrayList();
            }
            //mIsEdit = getArguments().getBoolean(ARG_PARAM2);
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
        view.findViewById(R.id.close).setOnClickListener(this);
        actualPrice = view.findViewById(R.id.price);
        finalPrice = view.findViewById(R.id.final_price);
        displayUnit = view.findViewById(R.id.display_unit);
        view.findViewById(R.id.save).setOnClickListener(this);
        view.findViewById(R.id.cancel).setOnClickListener(this);

        /*discount.setText("0");
        actualPrice.setText("0");
        finalPrice.setText("0");*/

        discount.setFilters(new InputFilter[]{new InputFilterMinMax(0, 99)});
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
        Spinner unitsSpinner = view.findViewById(R.id.unit_spinner);
        Spinner productStatusSpinner = view.findViewById(R.id.product_status);
        quantity = view.findViewById(R.id.quantity);
        quantityValue = view.findViewById(R.id.quantity_value);
        productStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String text = productStatus.get(pos);
                unitObject.setStockName(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        unitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                APIUnitMeasureResponse measureResponse = unitMeasurements.getUnitMeasurements().get(pos);
                String text = measureResponse.getAttributesName();
                unitObject.setUnitMeasure(text);
                unitObject.setUnitID(unitMeasurements.getUnitMeasurements().get(pos).getId());
                unitObject.setDisplayUnitValue(text);
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
        for (APIUnitMeasureResponse unitMeasureResponse : unitMeasurements.getUnitMeasurements()) {
            availableUnitsMeasurements.add(unitMeasureResponse.getAttributesName());
        }
        CustomStringAdapter customStringAdapter = new CustomStringAdapter(availableUnitsMeasurements, this.getContext());
        for (APIStockResponse apiStockResponse : apiStockResponses) {
            productStatus.add(apiStockResponse.getStockName());
        }
        CustomStringAdapter productStatusAdapter = new CustomStringAdapter(productStatus, this.getContext());
        productStatusSpinner.setAdapter(productStatusAdapter);
        unitsSpinner.setAdapter(customStringAdapter);

        actualPrice.setText(unitObject.getActualCost());
        discount.setText(unitObject.getDiscount());
        quantity.setText(unitObject.getQuantity());
        valueOfUnit.setText(unitObject.getUnitNumber());
        calculateFinalCost(unitObject.getActualCost(), unitObject.getDiscount());

        if ((null != unitObject.getProductUnitID() && unitObject.getProductUnitID().length() > 0 )|| unitObject.getTimeStamp() > 0) {
            ((Button)view.findViewById(R.id.cancel)).setText(R.string.delete);
            ((Button)view.findViewById(R.id.save)).setText(R.string.update);
            unitsSpinner.setSelection(availableUnitsMeasurements.indexOf(unitObject.getDisplayUnitValue()));
            productStatusSpinner.setSelection(productStatus.indexOf(unitObject.getStockName()));
        } else {
            unitObject.setTimeStamp();
            view.findViewById(R.id.cancel).setVisibility(View.GONE);
        }
    }

    private void updateDisplayValue() {
        unitObject.setUnit_number(Objects.requireNonNull(valueOfUnit.getText()).toString());
        unitObject.setDisplayUnitValue(unitObject.getDisplayUnitValue()); // unitObject.getUnitNumber() + " " +
        displayUnit.setText(unitObject.getUnitNumber() + " "+unitObject.getDisplayUnitValue());
    }

    private void updateQuantityDetails() {
        int valueOfUnitValue = Utils.getIntegerValueFromString(valueOfUnit.getText().toString().trim());
        int quantityValueInt = Utils.getIntegerValueFromString(quantity.getText().toString().trim());
        int totalQuantityDetails = quantityValueInt * valueOfUnitValue;
        if(quantityValueInt != 0) {
            String temp = String.format(Locale.getDefault(), "%d %s", totalQuantityDetails, unitObject.getDisplayUnitValue());
            quantityValue.setText(temp);
        } else {
            quantityValue.setText("");
        }
    }

    private void calculateFinalCost(String _price, String _discount) {
        try {
            int price = Utils.getIntegerValueFromString(_price);
            int discount = Utils.getIntegerValueFromString(_discount);
            unitObject.setActualCost(price+"");
            unitObject.setActualCost(discount+"");
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
                unitObject.setFinalCost(price+"");
                String __finalPrice = String.format(getString(R.string.after_discount), price+"");
                finalPrice.setText(__finalPrice);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save) {

            for (APIStockResponse apiStockResponse : apiStockResponses) {
                if (unitObject.getStockName().equalsIgnoreCase(apiStockResponse.getStockName())) {
                    unitObject.setStockID(apiStockResponse.getStockID());
                }
            }
            String _actualPrice = Objects.requireNonNull(actualPrice.getText()).toString();
            String _quantity = Objects.requireNonNull(quantity.getText()).toString();
            String _discount = Objects.requireNonNull(discount.getText()).toString();
            int valueOfUnitValue = Utils.getIntegerValueFromString(valueOfUnit.getText().toString().trim());
             if ( valueOfUnitValue<=0 ) {
                Toast.makeText(getContext(), R.string.error_unit_value, Toast.LENGTH_SHORT).show();
             } else if (_actualPrice.length() <= 0) {
                Toast.makeText(getContext(), R.string.error_unit_amount, Toast.LENGTH_SHORT).show();
             } else if (_discount.length() <= 0) {
                 Toast.makeText(getContext(), R.string.error_discount, Toast.LENGTH_SHORT).show();
             }else if (_quantity.length() <= 0) {
                Toast.makeText(getContext(), R.string.error_valid_quantity, Toast.LENGTH_SHORT).show();
             }  else {
                unitObject.setQuantity(_quantity);
                 unitObject.setActualCost(_actualPrice);
                 unitObject.setDiscount(_discount);
                Intent i = new Intent().putExtra(UNIT_VALUE, unitObject);
                Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                dismiss();
             }
        } else if (view.getId() == R.id.cancel) {
            showDialog("Are you sure you want to delete this unit from your Inventory? ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    deleteUnits();
                    /*Intent intent = new Intent();
                    intent.putExtra(UNIT_VALUE, unitObject);
                    intent.putExtra("IS_DELETED", true);
                    Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    dismiss();*/
                }
            }, true);

        } else if (view.getId() == R.id.close){
            this.dismiss();
        }
    }
    private void deleteUnits() {
        Dialog progressDialog = CustomLoadingDialog.getInstance(getActivity());
        progressDialog.show();
        VendorInventoryService inventoryService = RetrofitClientInstance.getRetrofitInstance().create(VendorInventoryService.class);
        inventoryService.deleteProductUnit(MyProfile.getInstance().getUserID(), unitObject.getProductUnitID()).enqueue(new Callback<ShowProductResponse>() {
            @Override
            public void onResponse(@NotNull Call<ShowProductResponse> call, @NotNull Response<ShowProductResponse> response) {
                if (response.isSuccessful()) {
                    ShowProductResponse data = response.body();
                    if (data != null) {
                        if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                            showDialog(data.getMsg(), (dialogInterface, i) -> {
                                Intent intent = new Intent();
                                intent.putExtra(UNIT_VALUE, unitObject);
                                intent.putExtra("IS_DELETED", true);
                                Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                                dismiss();
                            }, false);
                            AddUnitDialog.this.dismiss();
                        } else {
                            showDialog(data.getMsg(), null, false);
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available), null, false);
                    }
                } else {
                    showDialog(response.message(), null, false);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<ShowProductResponse> call, @NotNull Throwable t) {
                showDialog(t.getMessage(), null, false);
                progressDialog.dismiss();
            }
        });
    }
    protected void showDialog(String msg, DialogInterface.OnClickListener onPositiveClick, boolean cancelable) {
        try {
            AlertDialog.Builder builder = new
                    AlertDialog.Builder(
                    requireActivity(),
                    R.style.AlertDialog
            );
            builder.setTitle(requireActivity().getString(R.string.message));
            builder.setMessage(msg);
            builder.setCancelable(false);
            builder.setPositiveButton(requireActivity().getString(R.string.dialog_ok), onPositiveClick);
            if(cancelable) {
                builder.setNegativeButton(requireActivity().getString(R.string.cancel), null);
            }
            AlertDialog alertDialog = builder.create();
            alertDialog.setOnShowListener(arg0 -> alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireActivity(), R.color.button_bg)));
            if (!requireActivity().isFinishing()) {
                alertDialog.show();
            }
        } catch (Exception e) {
            // LoggerInfo.errorLog("show dialog exception", e.getMessage());
        }
    }
}