package com.rmart.inventory.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.APIProductListResponse;
import com.rmart.utilits.pojos.APIStockListResponse;
import com.rmart.utilits.pojos.ProductResponse;
import com.rmart.utilits.services.APIService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectProductFromInventory extends BaseInventoryFragment implements View.OnClickListener {

    public static final int REQUEST_FILTERED_DATA_ID = 102;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private APIStockListResponse apiStockListResponse;
    private String mParam2;
    Product product;
    private SearchView searchView;
    private ImageView sortButton;
    private RecyclerView productRecycleView;
    ProductAdapter productAdapter;
    private AppCompatTextView tvTotalCount;
    AppCompatButton addProduct;
    ArrayList<ProductResponse> products = new ArrayList<>();
    public SelectProductFromInventory() {
        // Required empty public constructor
    }

    public static SelectProductFromInventory newInstance(APIStockListResponse apiStockListResponse, String param2) {
        SelectProductFromInventory fragment = new SelectProductFromInventory();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, apiStockListResponse);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        /*InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        searchView.clearFocus();*/
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.add_new_product));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            apiStockListResponse = (APIStockListResponse) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // products = new ArrayList<>(Objects.requireNonNull(inventoryViewModel.getProductList().getValue()).values());
        LoggerInfo.printLog("Fragment", "SelectProductFromInventory");
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
        searchView = view.findViewById(R.id.searchView);
        view.findViewById(R.id.sort).setOnClickListener(param -> {
            mListener.applyFilter(this, REQUEST_FILTERED_DATA_ID);
        });
        updateProductList();
    }

    private void updateList() {
        try {
            tvTotalCount.setText(String.format(getResources().getString(R.string.total_products), products.size()));
            productAdapter = new ProductAdapter(products, view1 -> {
                ProductResponse product = (ProductResponse) view1.getTag();
                mListener.updateProduct(product, false);
            }, 3);
            productRecycleView.setAdapter(productAdapter);
            setSearchView();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        mListener.requestToCreateProduct();
    }
    protected void setSearchView() {
        if(productAdapter.getItemCount()>10) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    productAdapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    productAdapter.getFilter().filter(newText);
                    return false;
                }
            });
        } else {
            searchView.setVisibility(View.GONE);
        }
    }


    public void updateProductList() {
        progressDialog.show();
        APIService apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        apiService.getAPIProducts("0", "100").enqueue(new Callback<APIProductListResponse>() {
            @Override
            public void onResponse(Call<APIProductListResponse> call, Response<APIProductListResponse> response) {
                if(response.isSuccessful()) {
                    APIProductListResponse data = response.body();
                    if(data.getStatus().equals(Utils.SUCCESS)) {
                        products = data.getProductList();
                        updateList();
                    } else {
                        showDialog("", data.getMsg());
                    }
                } else {
                    showDialog("", response.message());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<APIProductListResponse> call, Throwable t) {
                progressDialog.dismiss();
                showDialog("", t.getMessage());
            }
        });
       /* apiService.getAPIProducts("0", "100").enqueue(new Callback<APIProductListResponse>() {
            @Override
            public void onResponse(Call<APIProductListResponse> call, Response<APIProductListResponse> response) {
                if(response.isSuccessful()) {
                    APIProductListResponse data = response.body();
                    if(data.getStatus().equals(Utils.SUCCESS)) {
                        products = data.getProductList();
                        updateList();
                    } else {
                        showDialog("", data.getMsg());
                    }
                }
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<APIProductListResponse> call, Throwable t) {
                progressDialog.dismiss();
                showDialog("", t.getMessage());
            }
        });*/
    }

}