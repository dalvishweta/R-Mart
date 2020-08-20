package com.rmart.inventory.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.adapters.ProductUnitAdapter;
import com.rmart.inventory.models.Product;
import com.rmart.inventory.models.UnitObject;

import java.util.ArrayList;

public class EditProductFragment extends BaseInventoryFragment implements View.OnClickListener {

    private static final String ARG_PRODUCT = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int INT_ADD_UNIT = 101;
    public static final int INT_UPDATE_UNIT = 102;
    public static final String UNIT_VALUE = "unit_value";

    private Product mProduct;
    private String mParam2;

    ArrayList<String> availableUnits1 = new ArrayList<>();
    public AppCompatTextView chooseCategory, chooseSubCategory, chooseProduct, productBrand;
    AppCompatEditText expiry, productRegionalName, deliveryDays, productDescription;
    private RecyclerView recyclerView;

    public EditProductFragment() {
        // Required empty public constructor
    }

    public static EditProductFragment newInstance(Product param1, String param2) {
        EditProductFragment fragment = new EditProductFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(ARG_PRODUCT);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        view.findViewById(R.id.save).setOnClickListener(this);
        view.findViewById(R.id.add_unit).setOnClickListener(this);

        updateUI();
    }

    private void updateUI() {
        chooseCategory.setText(mProduct.getCategory());
        chooseSubCategory.setText(mProduct.getSubCategory());
        chooseProduct.setText(mProduct.getName());
        productBrand.setText(mProduct.getBrand());
        productRegionalName.setText(mProduct.getRegionalName());
        productDescription.setText(mProduct.getDescription());
        expiry.setText(mProduct.getExpiryDate());
        deliveryDays.setText(mProduct.getDeliveryInDays());
        updateList();


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_unit) {
            mListener.addUnit(new UnitObject(availableUnits1), this, INT_ADD_UNIT);
        } else if (view.getId() == R.id.save) {
            mListener.showProductPreview((Product) view.getTag());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INT_ADD_UNIT) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                assert bundle != null;
                UnitObject unitData = (UnitObject) bundle.getSerializable(UNIT_VALUE);
                mProduct.getUnitObjects().add(unitData);
                updateList();

            } else if(requestCode == INT_UPDATE_UNIT) {
                Bundle bundle = data.getExtras();
                assert bundle != null;
                UnitObject unitData = (UnitObject) bundle.getSerializable(UNIT_VALUE);
                mProduct.getUnitObjects().add(unitData);
            }
        }
    }

    private void updateList() {
        ProductUnitAdapter unitBaseAdapter = new ProductUnitAdapter(mProduct.getUnitObjects(), view -> {
            UnitObject unitObject = (UnitObject) view.getTag();
            mProduct.getUnitObjects().remove(unitObject);
            updateList();

        }, true);
        recyclerView.setAdapter(unitBaseAdapter);

//        unitBaseAdapter = new ProductUnitAdapter(mProduct.getUnitObjects(), );
//        recyclerView.setAdapter(unitBaseAdapter);
    }
}