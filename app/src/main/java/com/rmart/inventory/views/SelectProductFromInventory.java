package com.rmart.inventory.views;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.adapters.ProductAdapter;
import com.rmart.retiler.product.view.AddNewProductActivity;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.APIProductListResponse;
import com.rmart.utilits.pojos.ProductResponse;
import com.rmart.utilits.services.APIService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectProductFromInventory extends BaseInventoryFragment implements View.OnClickListener {

    public static final int REQUEST_FILTERED_DATA_ID = 102;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SearchView searchView;
    PopupMenu popup;
    ProductAdapter productAdapter;
    private AppCompatTextView tvTotalCount;
    AppCompatButton addProduct;
//    LinearLayout llAddCustomProduct;
    ArrayList<ProductResponse> products = new ArrayList<>();
    private String listType;
    private String id;
    private String type = Utils.PRODUCT;

    public SelectProductFromInventory() {
        // Required empty public constructor
    }

    public static SelectProductFromInventory newInstance(String param2, String id) {
        SelectProductFromInventory fragment = new SelectProductFromInventory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listType = getArguments().getString(ARG_PARAM2);
            id = getArguments().getString(ARG_PARAM1);
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LoggerInfo.printLog("Fragment", "SelectProductFromInventory");
        return inflater.inflate(R.layout.fragment_add_product_to_inventory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView productRecycleView = view.findViewById(R.id.product_list);
        addProduct = view.findViewById(R.id.request_new_product);
        tvTotalCount = view.findViewById(R.id.category_count);
        addProduct.setOnClickListener(this);
        /*TextView tvCustomProduct = view.findViewById(R.id.tv_product_type);
        tvCustomProduct.setText("Custom Product");
        llAddCustomProduct = view.findViewById(R.id.add_product);
        llAddCustomProduct.setOnClickListener(this);*/
        productRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        productAdapter = new ProductAdapter(requireActivity(), products, productView -> {
            searchView.setQuery("", false);
            searchView.clearFocus();
            ProductResponse product = (ProductResponse) productView.getTag();
            if (product.getType().equalsIgnoreCase(Utils.PRODUCT)) {
                mListener.updateProduct(product, false);
            } else if (product.getType().equalsIgnoreCase(Utils.CATEGORY)) {
                mListener.addProductToInventory(Utils.SUB_CATEGORY, product.getId());
            } else if (product.getType().equalsIgnoreCase(Utils.SUB_CATEGORY)) {
                mListener.addProductToInventory(Utils.SUB_CATEGORY_PRODUCT, product.getId());
            } else if (product.getType().equalsIgnoreCase(Utils.BRAND)) {
                mListener.addProductToInventory(Utils.BRAND_PRODUCTS, product.getId());
            }
        }, 3);
        productRecycleView.setAdapter(productAdapter);

        searchView = view.findViewById(R.id.searchView);
        view.findViewById(R.id.sort).setOnClickListener(param -> {
            mListener.applyFilter(this, REQUEST_FILTERED_DATA_ID);
        });
        addProduct.setVisibility(View.GONE);
        // updateProductList();
        popup = new PopupMenu(requireActivity(), view.findViewById(R.id.sort));

        APIService apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        resetProductsList();
        if (listType.equalsIgnoreCase(Utils.PRODUCT)) {
            popup.getMenu().add(Menu.NONE, 4, 4, Utils.CATEGORY);
            popup.getMenu().add(Menu.NONE, 3, 3, Utils.BRAND);
            if(!Utils.isNetworkConnected(requireActivity())) {
                showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
                return;
            }
            progressDialog.show();
            apiService.getProductList("0", "100").enqueue(new Callback<APIProductListResponse>() {
                @Override
                public void onResponse(@NotNull Call<APIProductListResponse> call, @NotNull Response<APIProductListResponse> response) {
                    if (response.isSuccessful()) {
                        APIProductListResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equals(Utils.SUCCESS)) {
                                products = data.getProductList();
                                for (ProductResponse productResponse: products) {
                                    productResponse.setType(Utils.PRODUCT);
                                }
                                if(products.size() == 1) {
                                    type = "Product";
                                } else {
                                    type = "Products";
                                }
                                updateList();
                            } else {
                                showDialog("", data.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }

                    } else {
                        showDialog("", response.message());
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<APIProductListResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        } else if (listType.equalsIgnoreCase(Utils.CATEGORY)) {
            popup.getMenu().add(Menu.NONE, 1, 1, Utils.PRODUCT);
            popup.getMenu().add(Menu.NONE, 3, 3, Utils.BRAND);
            if(!Utils.isNetworkConnected(requireActivity())) {
                showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
                return;
            }
            progressDialog.show();
            apiService.getCategoryList("0", "100").enqueue(new Callback<APIProductListResponse>() {
                @Override
                public void onResponse(@NotNull Call<APIProductListResponse> call, @NotNull Response<APIProductListResponse> response) {
                    if (response.isSuccessful()) {
                        APIProductListResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equals(Utils.SUCCESS)) {
                                products = data.getProductList();
                                type = "Category";
                                for (ProductResponse productResponse: products) {
                                    productResponse.setType(Utils.CATEGORY);
                                }
                                updateList();
                            } else {
                                showDialog("", data.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }

                    } else {
                        showDialog("", response.message());
                    }
                    progressDialog.dismiss();
                }
                @Override
                public void onFailure(@NotNull Call<APIProductListResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        } else if (listType.equalsIgnoreCase(Utils.SUB_CATEGORY)) {
            view.findViewById(R.id.sort).setVisibility(View.GONE);
            if(!Utils.isNetworkConnected(requireActivity())) {
                showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
                return;
            }
            progressDialog.show();
            apiService.getSubCategoryList("0", "100", id).enqueue(new Callback<APIProductListResponse>() {
                @Override
                public void onResponse(@NotNull Call<APIProductListResponse> call, @NotNull Response<APIProductListResponse> response) {
                    if (response.isSuccessful()) {
                        APIProductListResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equals(Utils.SUCCESS)) {
                                products = data.getProductList();
                                type = "Sub Category";
                                for (ProductResponse productResponse: products) {
                                    productResponse.setType(Utils.SUB_CATEGORY);
                                }
                                updateList();
                            } else {
                                showDialog("", data.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }

                    } else {
                        showDialog("", response.message());
                    }
                    progressDialog.dismiss();
                }
                @Override
                public void onFailure(@NotNull Call<APIProductListResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        }  else if (listType.equalsIgnoreCase(Utils.SUB_CATEGORY_PRODUCT)) {
            view.findViewById(R.id.sort).setVisibility(View.GONE);
            if(!Utils.isNetworkConnected(requireActivity())) {
                showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
                return;
            }
            progressDialog.show();
            apiService.getSubCategoryProductsList("0", "100", id).enqueue(new Callback<APIProductListResponse>() {
                @Override
                public void onResponse(@NotNull Call<APIProductListResponse> call, @NotNull Response<APIProductListResponse> response) {
                    if (response.isSuccessful()) {
                        APIProductListResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equals(Utils.SUCCESS)) {
                                products = data.getProductList();
                                for (ProductResponse productResponse: products) {
                                    productResponse.setType(Utils.PRODUCT);
                                }
                                type = "Products";
                                updateList();
                            } else {
                                showDialog("", data.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }

                    } else {
                        showDialog("", response.message());
                    }
                    progressDialog.dismiss();
                }
                @Override
                public void onFailure(@NotNull Call<APIProductListResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        } else if (listType.equalsIgnoreCase(Utils.BRAND_PRODUCTS)) {
            view.findViewById(R.id.sort).setVisibility(View.GONE);
            if(!Utils.isNetworkConnected(requireActivity())) {
                showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
                return;
            }
            progressDialog.show();
            apiService.getBrandProductsList("0", "100", id).enqueue(new Callback<APIProductListResponse>() {
                @Override
                public void onResponse(@NotNull Call<APIProductListResponse> call, @NotNull Response<APIProductListResponse> response) {
                    if (response.isSuccessful()) {
                        APIProductListResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equals(Utils.SUCCESS)) {
                                products = data.getProductList();
                                for (ProductResponse productResponse: products) {
                                    productResponse.setType(Utils.PRODUCT);
                                }
                                type = "Products";
                                updateList();
                            } else {
                                showDialog("", data.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }

                    } else {
                        showDialog("", response.message());
                    }
                    progressDialog.dismiss();
                }
                @Override
                public void onFailure(@NotNull Call<APIProductListResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        } else {
            popup.getMenu().add(Menu.NONE, 1, 1, Utils.PRODUCT);
            popup.getMenu().add(Menu.NONE, 4, 4, Utils.CATEGORY);
            if(!Utils.isNetworkConnected(requireActivity())) {
                showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
                return;
            }
            progressDialog.show();
            apiService.getBrandList("0", "100").enqueue(new Callback<APIProductListResponse>() {
                @Override
                public void onResponse(@NotNull Call<APIProductListResponse> call, @NotNull Response<APIProductListResponse> response) {
                    if (response.isSuccessful()) {
                        APIProductListResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equals(Utils.SUCCESS)) {
                                products = data.getProductList();
                                for (ProductResponse productResponse: products) {
                                    productResponse.setType(Utils.BRAND);
                                }
                                type = "Brands";
                                updateList();
                            } else {
                                showDialog("", data.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }

                    } else {
                        showDialog("", response.message());
                    }
                    progressDialog.dismiss();
                }
                @Override
                public void onFailure(@NotNull Call<APIProductListResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        }
        popup.setOnMenuItemClickListener(item -> {
            mListener.addProductToInventory(item.getTitle().toString(), "");
            return true;
        });
        view.findViewById(R.id.sort).setOnClickListener(param -> {
            popup.show();
        });
        setSearchView();
    }

    private void resetProductsList() {
        products.clear();
        productAdapter.updateItems(products);
        productAdapter.notifyDataSetChanged();
    }

    private void updateList() {
        try {
            //tvTotalCount.setText("Showing "+ products.size()+" "+type);
            String totalCount = String.format(Locale.getDefault(), "Showing %d %s", products.size(), type);
            tvTotalCount.setText(totalCount);
            productAdapter.updateItems(products);
            productAdapter.notifyDataSetChanged();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == addProduct) {
            mListener.requestToCreateProduct();
        }/*else if (view == llAddCustomProduct){
            Intent intent = new Intent(getContext(), AddNewProductActivity.class);
            startActivity(intent);
        }*/
    }

    private final SearchView.OnQueryTextListener searchQueryListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            if (query.length() == 0) {
                searchView.clearFocus();
                return true;
            }
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (!TextUtils.isEmpty(newText)) {
                productAdapter.getFilter().filter(newText, count -> {
                    String totalCount = String.format(Locale.getDefault(), "Showing %d %s", count, type);
                    tvTotalCount.setText(totalCount);
                });
            } else {
                updateList();
            }
            return false;
        }
    };

    protected void setSearchView() {
        if (null != productAdapter) {
            searchView.setOnQueryTextListener(searchQueryListener);
        }
    }
}