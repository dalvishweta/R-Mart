package com.rmart.customer.views;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.adapters.VendorProductTypesAdapter;
import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.customer.models.ProductBaseModel;
import com.rmart.customer.models.VendorProductDetailsResponse;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.CommonUtils;
import com.rmart.utilits.GridSpacesItemDecoration;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorSameProductsListScreen extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ProductBaseModel productCategoryDetails;
    private ShopDetailsModel vendorShopDetails;
    private int currentPage = 0;
    private String searchProductName = "";
    private List<CustomerProductDetailsModel> productsList;
    private VendorProductTypesAdapter vendorProductsListAdapter;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int PAGE_SIZE = 20;
    private OnCustomerHomeInteractionListener onCustomerHomeInteractionListener;
    private AppCompatEditText etProductsSearchField;

    public VendorSameProductsListScreen() {
        // Required empty public constructor
    }

    public static VendorSameProductsListScreen getInstance(ProductBaseModel productCategoryDetails, ShopDetailsModel vendorShopDetails) {
        VendorSameProductsListScreen fragment = new VendorSameProductsListScreen();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, productCategoryDetails);
        args.putSerializable(ARG_PARAM2, vendorShopDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productCategoryDetails = (ProductBaseModel) getArguments().getSerializable(ARG_PARAM1);
            vendorShopDetails = (ShopDetailsModel) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoggerInfo.printLog("Fragment", "VendorSameProductsListScreen");
        return inflater.inflate(R.layout.fragment_vendor_same_products_list_screen, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnCustomerHomeInteractionListener) {
            onCustomerHomeInteractionListener = (OnCustomerHomeInteractionListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateToolBar();
    }

    public void updateToolBar() {
        requireActivity().setTitle(vendorShopDetails.getShopName());
        ((CustomerHomeActivity)(requireActivity())).showCartIcon();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);

        getVendorDetails();
    }

    private void loadUIComponents(View view) {

        RecyclerView productsListField = view.findViewById(R.id.products_list_field);
        productsListField.setHasFixedSize(false);

        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false);
        productsListField.setLayoutManager(layoutManager);

        productsListField.addItemDecoration(new GridSpacesItemDecoration(20));

        productsListField.setItemAnimator(new DefaultItemAnimator());
        productsListField.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        loadMoreItems();
                    }
                }
            }
        });

        productsList = new ArrayList<>();
        vendorProductsListAdapter = new VendorProductTypesAdapter(requireActivity(), productsList, pObject -> {
            CustomerProductDetailsModel selectedProductDetails = (CustomerProductDetailsModel) pObject;
            onCustomerHomeInteractionListener.gotoProductDescDetails(selectedProductDetails, vendorShopDetails);
        });
        productsListField.setAdapter(vendorProductsListAdapter);

        etProductsSearchField = view.findViewById(R.id.edt_product_search_field);
        ImageView ivSearchField = view.findViewById(R.id.iv_search_field);

        ivSearchField.setOnClickListener(v -> {
            etProductsSearchField.setText("");
            searchProductName = "";
            currentPage = 0;
            CommonUtils.closeVirtualKeyboard(requireActivity(), ivSearchField);
        });
        etProductsSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    ivSearchField.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                    performSearch();
                } else {
                    ivSearchField.setImageResource(R.drawable.search);
                    searchProductName = "";
                    resetVendorProductDetails();
                    getVendorDetails();
                }
            }
        });
    }

    private void performSearch() {
        searchProductName = Objects.requireNonNull(etProductsSearchField.getText()).toString().trim();
        if (searchProductName.length() == 0) {
            vendorProductsListAdapter.updateItems(productsList);
            vendorProductsListAdapter.notifyDataSetChanged();
        } else if (searchProductName.length() == 3) {
            currentPage = 0;
            getVendorDetails();
        } else {
            vendorProductsListAdapter.getFilter().filter(searchProductName);
        }
    }

    private void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        getVendorDetails();
    }

    private void resetVendorProductDetails() {
        productsList.clear();
        vendorProductsListAdapter.updateItems(productsList);
        vendorProductsListAdapter.notifyDataSetChanged();
    }

    private void getVendorDetails() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<VendorProductDetailsResponse> call;
            String customerId = MyProfile.getInstance(getContext()).getUserID();
            if (TextUtils.isEmpty(searchProductName) && currentPage != 0) {
                call = customerProductsService.getVendorShopDetails(clientID, vendorShopDetails.getVendorId(), vendorShopDetails.getShopId(),
                        productCategoryDetails.getProductCategoryId(), currentPage, customerId);
            } else {
                call = customerProductsService.getVendorShopDetails(clientID, vendorShopDetails.getVendorId(), vendorShopDetails.getShopId(),
                        productCategoryDetails.getProductCategoryId(), currentPage, searchProductName, customerId);
            }
            call.enqueue(new Callback<VendorProductDetailsResponse>() {
                @Override
                public void onResponse(@NotNull Call<VendorProductDetailsResponse> call, @NotNull Response<VendorProductDetailsResponse> response) {
                    progressDialog.dismiss();
                    if(currentPage == 0){
                    resetVendorProductDetails();
                    }
                    if (response.isSuccessful()) {
                        VendorProductDetailsResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                List<CustomerProductDetailsModel> productDataList = body.getVendorProductDataResponse().getProductsListData();
                                if (productDataList != null && !productDataList.isEmpty()) {
                                    productsList.addAll(productDataList);

                                }
                                updateAdapter(body.getMsg());
                            } else {
                                showDialog(body.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog(response.message());
                    }
                    isLoading =false;
                }

                @Override
                public void onFailure(@NotNull Call<VendorProductDetailsResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                    isLoading =false;
                }
            });
        } else {
            isLoading =false;
            showCloseDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void showCloseDialog(String title, String message) {
        showDialog(title, message, pObject -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void updateAdapter(String message) {
        if(!productsList.isEmpty()) {
            vendorProductsListAdapter.updateItems(productsList);
            vendorProductsListAdapter.notifyDataSetChanged();
        } else {
            showDialog(message);
        }
    }
}