package com.rmart.inventory.views;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.adapters.ProductAdapter;
import com.rmart.inventory.models.Product;
import com.rmart.utilits.pojos.ProductResponse;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class AddProductToInventory extends BaseInventoryFragment implements View.OnClickListener {

    public static final int REQUEST_FILTERED_DATA_ID = 102;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    Product product;
    private SearchView searchView;
    private ImageView sortButton;
    private RecyclerView productRecycleView;
    ProductAdapter productAdapter;
    private AppCompatTextView tvTotalCount;
    AppCompatButton addProduct;
    ArrayList<ProductResponse> products;
    public AddProductToInventory() {
        // Required empty public constructor
    }

    public static AddProductToInventory newInstance(String param1, String param2) {
        AddProductToInventory fragment = new AddProductToInventory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        searchView.clearFocus();
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
        products = (ArrayList<ProductResponse>) new ArrayList<>(Objects.requireNonNull(inventoryViewModel.getProductList().getValue()).values());
        return inflater.inflate(R.layout.fragment_add_product_to_inventory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productRecycleView = view.findViewById(R.id.product_list);
        addProduct = view.findViewById(R.id.request_new_product);
        tvTotalCount = view.findViewById(R.id.category_count);
        addProduct.setOnClickListener(this);

        productRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        updateList(products);
        setSearchView(view);
        view.findViewById(R.id.sort).setOnClickListener(param -> {
            mListener.applyFilter(this, REQUEST_FILTERED_DATA_ID);
        });
    }

    private void updateList(ArrayList<ProductResponse> products) {
        try {
            tvTotalCount.setText(String.format(getResources().getString(R.string.total_products), products.size()));
            productAdapter = new ProductAdapter(products, view1 -> {
                ProductResponse product = (ProductResponse) view1.getTag();
                mListener.updateProduct(product, false);
            }, 3);
            productRecycleView.setAdapter(productAdapter);


        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        mListener.requestToCreateProduct();
    }
    protected void setSearchView(@NonNull View view) {
        if(null != productAdapter) {
            searchView = view.findViewById(R.id.searchView);
            searchView.setFocusable(false);
            searchView.setIconified(false);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    productAdapter.getFilter().filter(query);
                    // Toast.makeText(getActivity(), "onQueryTextSubmit "+query, Toast.LENGTH_LONG).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    productAdapter.getFilter().filter(newText);
                    return false;
                }
            });
        }
    }
}