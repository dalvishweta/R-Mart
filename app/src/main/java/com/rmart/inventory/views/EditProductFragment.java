package com.rmart.inventory.views;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.adapters.ProductUnitAdapter;
import com.rmart.inventory.models.Product;
import com.rmart.inventory.models.UnitObject;

import java.util.ArrayList;
import java.util.Objects;

public class EditProductFragment extends BaseInventoryFragment implements View.OnClickListener {

    private static final String ARG_PRODUCT = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int INT_ADD_UNIT = 101;
    public static final int INT_UPDATE_UNIT = 102;
    public static final String UNIT_VALUE = "unit_value";

    private Product mClonedProduct;
    private Product mProduct;
    private boolean isEdit;

    ArrayList<String> availableUnits1 = new ArrayList<>();
    public AppCompatTextView chooseCategory, chooseSubCategory, chooseProduct, productBrand;
    AppCompatEditText expiry, productRegionalName, deliveryDays, productDescription;
    private RecyclerView recyclerView;

    public EditProductFragment() {
        // Required empty public constructor
    }

    public static EditProductFragment newInstance(Product product, boolean isEdit) {
        EditProductFragment fragment = new EditProductFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        args.putBoolean(ARG_PARAM2, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(ARG_PRODUCT);
            if(null == mProduct) {
                mClonedProduct = new Product();
                mProduct = new Product();
            } else {
                try {
                    mClonedProduct = new Product(mProduct);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            isEdit = getArguments().getBoolean(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        availableUnits1.add("1 KG");
        availableUnits1.add("2 KG");
        availableUnits1.add("5 KG");
        availableUnits1.add("10 KG");
        availableUnits1.add("25 KG");
        return inflater.inflate(R.layout.fragment_edit_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chooseCategory = view.findViewById(R.id.choose_category);
        chooseSubCategory = view.findViewById(R.id.choose_sub_category);
        chooseProduct = view.findViewById(R.id.choose_product);
        productBrand = view.findViewById(R.id.product_brand);
        productRegionalName = view.findViewById(R.id.product_regional_name);
        productDescription = view.findViewById(R.id.product_description);
        expiry = view.findViewById(R.id.expiry);
        deliveryDays = view.findViewById(R.id.delivery_days);
        recyclerView = view.findViewById(R.id.unit_base);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // addUnit = view.findViewById(R.id.add_unit);
        AppCompatButton save = (AppCompatButton)view.findViewById(R.id.save);
        if(isEdit) {
            save.setText(getString(R.string.update));
            save.setOnClickListener(this);
        } else {
            save.setText(getString(R.string.save));
            save.setOnClickListener(this);
        }
        view.findViewById(R.id.add_unit).setOnClickListener(this);

        updateUI();
    }

    private void updateUI() {
        chooseCategory.setText(mClonedProduct.getCategory());
        chooseSubCategory.setText(mClonedProduct.getSubCategory());
        chooseProduct.setText(mClonedProduct.getName());
        productBrand.setText(mClonedProduct.getBrand());
        productRegionalName.setText(mClonedProduct.getRegionalName());
        productDescription.setText(mClonedProduct.getDescription());
        expiry.setText(mClonedProduct.getExpiryDate());
        deliveryDays.setText(mClonedProduct.getDeliveryInDays());
        updateList();


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_unit) {
            mListener.addUnit(new UnitObject(availableUnits1), this, INT_ADD_UNIT);
        } else if (view.getId() == R.id.save) {
            if( Objects.requireNonNull(expiry.getText()).toString().length()<=2) {
                showDialog("", "Please enter valid expiry date.");
                return;
            } else if( Objects.requireNonNull(productRegionalName.getText()).toString().length()<=1) {
                showDialog("", "Please enter valid Regional name.");
                return;
            } else if( Objects.requireNonNull(deliveryDays.getText()).toString().length()<=0) {
                showDialog("", "Please enter valid days to delivery.");
                return;
            }  else if( Objects.requireNonNull(productDescription.getText()).toString().length()<=1) {
                showDialog("", "Please enter valid product description");
                return;
            }

            mClonedProduct.setExpiryDate(expiry.getText().toString());
            mClonedProduct.setRegionalName(productRegionalName.getText().toString());
            mClonedProduct.setDeliveryInDays(deliveryDays.getText().toString());
            mClonedProduct.setDescription(productDescription.getText().toString());

            if (isEdit) {
                Objects.requireNonNull(inventoryViewModel.getProductList().getValue()).remove(mProduct);
                Objects.requireNonNull(inventoryViewModel.getProductList().getValue()).add(inventoryViewModel.getSelectedProduct().getValue(), mClonedProduct);
                Integer index = inventoryViewModel.getSelectedProduct().getValue();
                inventoryViewModel.getSelectedProduct().setValue(index);

            } else {
                Objects.requireNonNull(inventoryViewModel.getProductList().getValue()).add(mClonedProduct);
            }
            showToast();
            // mListener.showProductPreview((Product) view.getTag());
        }
    }

    private void showToast() {
        showDialog("", mClonedProduct.getName() + " is successfully added to your Inventory.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });
        // Toast.makeText(getContext(), getString(R.string.item_added), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INT_ADD_UNIT) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                assert bundle != null;
                UnitObject unitData = (UnitObject) bundle.getSerializable(UNIT_VALUE);
                mClonedProduct.getUnitObjects().add(unitData);
                updateList();
            } /*else if(requestCode == INT_UPDATE_UNIT) {
                Bundle bundle = data.getExtras();
                assert bundle != null;
                UnitObject unitData = (UnitObject) bundle.getSerializable(UNIT_VALUE);
                mClonedProduct.getUnitObjects().add(unitData);
            }*/
        }
    }

    private void updateList() {
        ProductUnitAdapter unitBaseAdapter = new ProductUnitAdapter(mClonedProduct.getUnitObjects(), view -> {
            UnitObject unitObject = (UnitObject) view.getTag();
            mClonedProduct.getUnitObjects().remove(unitObject);
            updateList();
        }, true);
        recyclerView.setAdapter(unitBaseAdapter);
    }
    
}