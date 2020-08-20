package com.rmart.inventory.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.rmart.R;
import com.rmart.inventory.adapters.ImageAdapter;
import com.rmart.inventory.adapters.ProductUnitAdapter;
import com.rmart.inventory.models.Product;

import java.util.Objects;

public class ShowProductPreviewFragment extends BaseInventoryFragment {
    private static final String ARG_PRODUCT = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    private  Product product;
    private String mParam2;
    ImageAdapter imageAdapter;
    ViewPager viewPager;

    AppCompatTextView tvProductName, tvProductDescription, tvProductRegionalName, tvProductExpiry;
    public ShowProductPreviewFragment() {
        // Required empty public constructor
    }

    public static ShowProductPreviewFragment newInstance(Product product, String param2) {
        ShowProductPreviewFragment fragment = new ShowProductPreviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (Product) getArguments().getSerializable(ARG_PRODUCT);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory_product_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvProductName = view.findViewById(R.id.product_name);
        recyclerView = view.findViewById(R.id.unit_base);
        tvProductDescription = view.findViewById(R.id.product_description);
        tvProductRegionalName = view.findViewById(R.id.product_regional_name);
        tvProductExpiry = view.findViewById(R.id.product_expiry);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewPager =  view.findViewById(R.id.view_pager);
        view.findViewById(R.id.edit).setOnClickListener(view1 -> {
            mListener.updateProduct(product);
        });
        view.findViewById(R.id.delete).setOnClickListener(view1 -> {
            Objects.requireNonNull(getActivity()).onBackPressed();
        });
        updateUi();

    }

    private void updateUi() {
        ImageAdapter imageAdapter = new ImageAdapter(Objects.requireNonNull(getContext()));
        viewPager.setAdapter(imageAdapter);
        tvProductName.setText(product.getName());

        ProductUnitAdapter unitBaseAdapter = new ProductUnitAdapter(product.getUnitObjects(), view -> {

        }, false);
        recyclerView.setAdapter(unitBaseAdapter);

        tvProductDescription.setText(product.getDescription());
        tvProductRegionalName.setText(product.getRegionalName());
        tvProductExpiry.setText(product.getExpiryDate());
    }
}