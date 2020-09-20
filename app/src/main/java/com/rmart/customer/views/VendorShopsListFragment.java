package com.rmart.customer.views;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.adapters.VendorShopsListAdapter;
import com.rmart.customer.models.CustomerProductsResponse;
import com.rmart.customer.models.CustomerProductsShopDetailsModel;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.AddressResponse;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Satya Seshu on 07/09/20.
 */
public class VendorShopsListFragment extends CustomerHomeFragment {

    private AppCompatEditText etProductsSearchField;
    private int currentPage = 0;
    private List<CustomerProductsShopDetailsModel> productsList;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int PAGE_SIZE = 20;
    private int totalShopsCount = 0;
    private String searchShopName = "";
    private VendorShopsListAdapter vendorShopsListAdapter;
    private OnCustomerHomeInteractionListener onCustomerHomeInteractionListener;

    public static VendorShopsListFragment getInstance() {
        VendorShopsListFragment vendorListViewFragment = new VendorShopsListFragment();
        Bundle extras = new Bundle();
        vendorListViewFragment.setArguments(extras);
        return vendorListViewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LoggerInfo.printLog("Fragment", "VendorShopsListFragment");
        return inflater.inflate(R.layout.fragment_vendor_list_view, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnCustomerHomeInteractionListener) {
            onCustomerHomeInteractionListener = (OnCustomerHomeInteractionListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {

        RecyclerView productsListField = view.findViewById(R.id.products_list_field);
        AppCompatTextView tvAddressField = view.findViewById(R.id.tv_address_field);
        etProductsSearchField = view.findViewById(R.id.edt_product_search_field);
        ImageView ivSearchField = view.findViewById(R.id.iv_search_field);
        etProductsSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() != 0) {
                    performSearch();
                }
                ivSearchField.setImageResource(R.drawable.search);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        productsListField.setLayoutManager(layoutManager);
        productsListField.setHasFixedSize(false);
        productsListField.setItemAnimator(new SlideInDownAnimator());
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

        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.recycler_decoration_divider)));
        productsListField.addItemDecoration(divider);

        vendorShopsListAdapter = new VendorShopsListAdapter(requireActivity(), productsList, callBackListener);
        productsListField.setAdapter(vendorShopsListAdapter);

        view.findViewById(R.id.btn_change_address_field).setOnClickListener(v -> {
            changeAddressSelected();
        });

        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            ArrayList<AddressResponse> addressList = myProfile.getAddressResponses();
            if (addressList != null && !addressList.isEmpty()) {
                for (AddressResponse addressResponse : addressList) {
                    if (addressResponse.getIsActive() == 1) {
                        tvAddressField.setText(addressResponse.getAddress());
                        break;
                    }
                }
            }
        }

        getShopsList();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateToolBar();
    }

    public void updateToolBar() {
        requireActivity().setTitle(getString(R.string.shops_list));
        ((CustomerHomeActivity)(requireActivity())).showCartIcon();
    }

    private CallBackInterface callBackListener = pObject -> {
        if(pObject instanceof CustomerProductsShopDetailsModel) {
            onCustomerHomeInteractionListener.gotoVendorProductDetails((CustomerProductsShopDetailsModel) pObject);
        } else if(pObject instanceof Integer) {

        }
    };

    private void changeAddressSelected() {
        onCustomerHomeInteractionListener.gotoChangeAddress();
    }

    private void performSearch() {
        String newText = Objects.requireNonNull(etProductsSearchField.getText()).toString().trim();
        if (newText.length() < 1) {
            vendorShopsListAdapter.updateItems(new ArrayList<>());
            vendorShopsListAdapter.notifyDataSetChanged();
        } else if (newText.length() == 3) {
            searchShopName = newText;
            currentPage = 0;
            getShopsList();
        } else {
            vendorShopsListAdapter.getFilter().filter(newText);
        }
    }

    private void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        getShopsList();
    }

    private void getShopsList() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            customerProductsService.getCustomerShopsList(clientID, currentPage, searchShopName, MyProfile.getInstance().getUserID()).enqueue(new Callback<CustomerProductsResponse>() {
                @Override
                public void onResponse(@NotNull Call<CustomerProductsResponse> call, @NotNull Response<CustomerProductsResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        CustomerProductsResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase("success")) {
                                totalShopsCount = data.getCustomerShopsList().getShopTotalCount();
                                List<CustomerProductsShopDetailsModel> customerProductsList = data.getCustomerShopsList().getCustomerShopsList();
                                updateAdapter(customerProductsList);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CustomerProductsResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void updateAdapter(List<CustomerProductsShopDetailsModel> customerProductsList) {
        productsList.addAll(customerProductsList);
        vendorShopsListAdapter.updateItems(customerProductsList);
        vendorShopsListAdapter.notifyDataSetChanged();
        if (productsList.size() >= totalShopsCount) {
            isLastPage = true;
        } else {
            isLoading = false;
        }
    }
}
