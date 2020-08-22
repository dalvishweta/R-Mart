package com.rmart.inventory.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.adapters.ProductAdapter;
import com.rmart.inventory.models.Product;

import java.util.ArrayList;
import java.util.Objects;

public class RequestNewProductFragment extends BaseInventoryFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    Product product;

    private RecyclerView productRecycleView;
    ProductAdapter productAdapter;
    private AppCompatTextView tvTotalCount;
    AppCompatButton addProduct;
    ArrayList<Product> products;
    public RequestNewProductFragment() {
        // Required empty public constructor
    }

    public static RequestNewProductFragment newInstance(String param1, String param2) {
        RequestNewProductFragment fragment = new RequestNewProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.add_new_product));
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        products = inventoryViewModel.getProducts();
        return inflater.inflate(R.layout.fragment_inventory_product_library_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productRecycleView = view.findViewById(R.id.product_list);
        addProduct = view.findViewById(R.id.add_product);
        tvTotalCount = view.findViewById(R.id.category_count);
        addProduct.setOnClickListener(this);
        view.findViewById(R.id.sort).setOnClickListener(param -> {

        });

        productRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        updateList(products);
    }

    private void updateList(ArrayList<Product> products) {
        try {
            tvTotalCount.setText(String.format(getResources().getString(R.string.total_products), products.size()));
            productAdapter = new ProductAdapter(products, view1 -> {
                Product product = (Product)view1.getTag();
                mListener.updateProduct(product, false);
            }, 3);
            productRecycleView.setAdapter(productAdapter);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.add_product) {
            mListener.addNewProduct();
        }
    }
}