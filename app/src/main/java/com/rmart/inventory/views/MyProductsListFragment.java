package com.rmart.inventory.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.adapters.ProductAdapter;
import com.rmart.inventory.models.Product;
import com.rmart.inventory.viewmodel.InventoryViewModel;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.APIProductListResponse;
import com.rmart.utilits.pojos.APIStockListResponse;
import com.rmart.utilits.pojos.APIStockResponse;
import com.rmart.utilits.pojos.ProductResponse;
import com.rmart.utilits.services.APIService;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProductsListFragment extends BaseInventoryFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PRODUCT_LIST_TYPE = 1;

    private String mParam1;
    private String mParam2;
    private RecyclerView productRecycleView;
    ProductAdapter productAdapter;
    private AppCompatTextView tvTotalCount;
    LinearLayout addProduct;
    PopupMenu popup;
    SearchView searchView;
    public MyProductsListFragment() {
        // Required empty public constructor
    }

    public static MyProductsListFragment newInstance(String param1, String param2) {
        MyProductsListFragment fragment = new MyProductsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.my_product_list));
        /*InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        searchView.clearFocus();*/
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
        /*inventoryViewModel.getProductList().observe(getActivity(), products -> {
            if(null != products) {
                updateList(products);
            }
        });*/
        inventoryViewModel.getProductList().observe(Objects.requireNonNull(getActivity()), data-> {
            updateList(new ArrayList<>(Objects.requireNonNull(inventoryViewModel.getProductList().getValue()).values()));
        });
        return inflater.inflate(R.layout.fragment_inventory_product_list, container, false);
    }

    public void getStockList() {
        progressDialog.show();
        APIService apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        apiService.getAPIStockList().enqueue(new Callback<APIStockListResponse>() {
            @Override
            public void onResponse(Call<APIStockListResponse> call, Response<APIStockListResponse> response) {
                if(response.isSuccessful()) {
                    APIStockListResponse data = response.body();
                    assert data != null;
                    inventoryViewModel.setApiStocks(data.getArrayList());
                    ArrayList<APIStockResponse> apiStocks = inventoryViewModel.getApiStocks().getValue();
                    assert apiStocks != null;
                    for (APIStockResponse apiStockResponse : apiStocks) {
                        popup.getMenu().add(apiStockResponse.getStockName());
                    }

                } else {
                    showDialog("", response.message());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<APIStockListResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productRecycleView = view.findViewById(R.id.product_list);
        addProduct = view.findViewById(R.id.add_product);
        tvTotalCount = view.findViewById(R.id.category_count);
        addProduct.setOnClickListener(this);

        popup = new PopupMenu(Objects.requireNonNull(getActivity()), view.findViewById(R.id.sort));
        getStockList();
        // Inflating the Popup using xml file
        // popup.getMenuInflater().inflate(R.menu.inventory_view_products, popup.getMenu());

        view.findViewById(R.id.sort).setOnClickListener(param -> {

            // registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(item -> {
               /* if(item.getItemId() == R.id.sort_category) {
                    inventoryViewModel.setIsProductView(InventoryViewModel.CATEGORY);
                    mListener.goToHome();
                } else if(item.getItemId() == R.id.sort_sub_category) {
                    inventoryViewModel.setIsProductView(InventoryViewModel.SUB_CATEGORY);
                    mListener.goToHome();
                } else if(item.getItemId() == R.id.sort_product) {
                    inventoryViewModel.setIsProductView(InventoryViewModel.PRODUCT);
                    mListener.goToHome();
                }*/
                return true;
            });

            popup.show(); // showing popup menu
        });
        productRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        updateList(new ArrayList<>(Objects.requireNonNull(inventoryViewModel.getProductList().getValue()).values()));

        setSearchView(view);
    }
    protected void setSearchView(@NonNull View view) {
        if(null != productAdapter) {
            searchView = view.findViewById(R.id.searchView);
            searchView.setFocusable(false);
            searchView.setIconified(false);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if(query.length()<=0) {
                        searchView.clearFocus();
                        return true;

                    }
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
    private void updateList(ArrayList<ProductResponse> products) {
        try {
            tvTotalCount.setText(String.format(getResources().getString(R.string.total_products), products.size()));
            productAdapter = new ProductAdapter(products, view1 -> {
                Product product = (Product)view1.getTag();
                int index = products.indexOf(product);
                inventoryViewModel.setSelectedProduct(index);
                mListener.showProductPreview(product, true);
            }, 2);
            productRecycleView.setAdapter(productAdapter);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.add_product) {
            mListener.addProductToInventory();
        }
    }
}