package com.rmart.inventory.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rmart.R;
import com.rmart.inventory.adapters.ProductAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.ProductListResponse;
import com.rmart.utilits.pojos.ProductResponse;
import com.rmart.utilits.pojos.ShowProductResponse;
import com.rmart.utilits.services.VendorInventoryService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
    private ProductAdapter productAdapter;
    private AppCompatTextView tvTotalCount;
    LinearLayout addProduct;
    PopupMenu popup;
    SearchView searchView;
    VendorInventoryService vendorInventoryService;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    ProductListResponse productList;
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
        requireActivity().setTitle(getString(R.string.my_product_list));
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
        /*inventoryViewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);
        inventoryViewModel.getProductList().observe(requireActivity(), data-> {
            updateList(new ArrayList<>(Objects.requireNonNull(inventoryViewModel.getProductList().getValue()).values()));
        });*/
        LoggerInfo.printLog("Fragment", "MyProductsListFragment");
        vendorInventoryService = RetrofitClientInstance.getRetrofitInstance().create(VendorInventoryService.class);
        getProductList();
        return inflater.inflate(R.layout.fragment_inventory_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productRecycleView = view.findViewById(R.id.product_list);
        addProduct = view.findViewById(R.id.add_product);
        tvTotalCount = view.findViewById(R.id.category_count);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_items);
        // Your code to make your refresh action
        // CallYourRefreshingMethod();
        mSwipeRefreshLayout.setOnRefreshListener(this::getProductList);
        addProduct.setOnClickListener(this);
        popup = new PopupMenu(requireActivity(), view.findViewById(R.id.sort));
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
        //productRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false);
        productRecycleView.setLayoutManager(layoutManager);
        // updateList(productList.getProductResponses());
        setSearchView(view);
    }

    private void getProductList() {
        progressDialog.show();
        vendorInventoryService.getProductList("0", MyProfile.getInstance().getMobileNumber(), "1,2,3,4,5,6,7").enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProductListResponse> call, @NotNull Response<ProductListResponse> response) {
                if (response.isSuccessful()) {
                    productList = response.body();
                    assert productList != null;
                    if (productList.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                        if (productList.getProductResponses().size() <= 0) {
                            mListener.addProductToInventory();
                        } else {

                            /*for (ProductResponse productResponse : data.getProductResponses()) {
                                inventoryViewModel.setProductList(productResponse);
                            }*/
                            updateList(productList.getProductResponses());
                        }
                    } else {
                        showDialog("", productList.getMsg());
                    }
                } else {
                    showDialog("", response.message());
                }
                if(mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<ProductListResponse> call, @NotNull Throwable t) {
                showDialog("", t.getMessage());
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                progressDialog.dismiss();
            }
        });
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
            progressDialog.show();
            productAdapter = new ProductAdapter(products, view1 -> {
                ProductResponse product = (ProductResponse)view1.getTag();
                vendorInventoryService.getProduct(product.getProductID(), MyProfile.getInstance().getUserID()).enqueue(new Callback<ShowProductResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<ShowProductResponse> call, @NotNull Response<ShowProductResponse> response) {
                        if (response.isSuccessful()) {
                            ShowProductResponse data = response.body();
                            if (data != null) {
                                if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                    ProductResponse response1 = data.getProductResponse();
                                    // inventoryViewModel.setSelectedProduct(response1.getProductID());
                                    mListener.showProductPreview(response1, true);
                                }
                            } else {
                                showDialog(getString(R.string.no_information_available));
                            }
                        } else {
                            showDialog("", "");
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NotNull Call<ShowProductResponse> call, @NotNull Throwable t) {
                        showDialog("", t.getMessage());
                        progressDialog.dismiss();
                    }
                });
                // int index = products.indexOf(product);

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