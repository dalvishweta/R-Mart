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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.adapters.VendorProductDetailsAdapter;
import com.rmart.customer.models.CustomerProductsModel;
import com.rmart.customer.models.ProductBaseModel;
import com.rmart.customer.models.VendorProductDataResponse;
import com.rmart.customer.models.VendorProductDetailsResponse;
import com.rmart.customer.models.VendorProductShopDataResponse;
import com.rmart.utilits.HttpsTrustManager;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VendorProductDetailsFragment#getInstance} factory method to
 * create an instance of this fragment.
 */
public class VendorProductDetailsFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters

    public VendorProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment VendorDetailsFragment.
     */

    private RecyclerView productsListField;
    private AppCompatEditText etProductsSearchField;
    private int currentPage = 0;
    private String searchProductName = "";
    private CustomerProductsModel customerProductsDetails;
    private int productCategoryId = -1;
    private TextView tvShopNameField;
    private TextView tvPhoneNoField;
    private TextView tvViewAddressField;
    private NetworkImageView ivShopImageField;
    private VendorProductDetailsAdapter vendorProductDetailsAdapter;
    private List<ProductBaseModel> vendorProductsList;
    private OnCustomerHomeInteractionListener onCustomerHomeInteractionListener;

    public static VendorProductDetailsFragment getInstance(CustomerProductsModel customerProductsModel) {
        VendorProductDetailsFragment fragment = new VendorProductDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, customerProductsModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customerProductsDetails = (CustomerProductsModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoggerInfo.printLog("Fragment", "VendorProductDetailsFragment");
        return inflater.inflate(R.layout.fragment_vendor_product_details, container, false);
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

        getVendorDetails();
    }

    private void loadUIComponents(View view) {
        productsListField = view.findViewById(R.id.products_list_field);
        productsListField.setHasFixedSize(false);

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

        ivShopImageField = view.findViewById(R.id.iv_shop_image);
        tvShopNameField = view.findViewById(R.id.tv_shop_name_field);
        tvPhoneNoField = view.findViewById(R.id.tv_phone_no_field);
        tvViewAddressField = view.findViewById(R.id.tv_view_address_field);

        vendorProductsList = new ArrayList<>();
        vendorProductDetailsAdapter = new VendorProductDetailsAdapter(requireActivity(), vendorProductsList, callBackListener);
        productsListField.setAdapter(vendorProductDetailsAdapter);
    }

    private CallBackInterface callBackListener = pObject -> {
        if(pObject instanceof VendorProductDataResponse) {
            onCustomerHomeInteractionListener.gotoProductDescDetails((VendorProductDataResponse) pObject, customerProductsDetails);
        }
    };

    private void getVendorDetails() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<VendorProductDetailsResponse> call;
            if (TextUtils.isEmpty(searchProductName) && currentPage == 0 && productCategoryId == -1) {
                call = customerProductsService.getVendorShopDetails(clientID, customerProductsDetails.getVendorId(), customerProductsDetails.getShopId());
            } else if (TextUtils.isEmpty(searchProductName) && currentPage != 0 && productCategoryId != -1) {
                call = customerProductsService.getVendorShopDetails(clientID, customerProductsDetails.getVendorId(), customerProductsDetails.getShopId(), productCategoryId, productCategoryId);
            } else if (!TextUtils.isEmpty(searchProductName) && currentPage != 0 && productCategoryId == -1) {
                call = customerProductsService.getVendorShopDetails(clientID, customerProductsDetails.getVendorId(), customerProductsDetails.getShopId(),
                        currentPage, searchProductName);
            } else {
                call = customerProductsService.getVendorShopDetails(clientID, customerProductsDetails.getVendorId(), customerProductsDetails.getShopId(), productCategoryId,
                        currentPage, searchProductName);
            }
            call.enqueue(new Callback<VendorProductDetailsResponse>() {
                @Override
                public void onResponse(@NotNull Call<VendorProductDetailsResponse> call, @NotNull Response<VendorProductDetailsResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        VendorProductDetailsResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                List<VendorProductDataResponse> productDataList = body.getCustomerProductDetailsModel().getProductData();
                                VendorProductShopDataResponse shopDataResponse = body.getCustomerProductDetailsModel().getShopData();
                                updateShopDetailsUI(shopDataResponse);
                                updateAdapter(productDataList);
                            } else {
                                showDialog(body.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    }  else {
                        showDialog(getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<VendorProductDetailsResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void updateAdapter(List<VendorProductDataResponse> productDataList) {
        if(productDataList != null && !productDataList.isEmpty()) {
            LinkedHashMap<ProductBaseModel, List<VendorProductDataResponse>> linkedMapDetails = groupDataIntoHashMap(productDataList);
            for (Map.Entry<ProductBaseModel, List<VendorProductDataResponse>> entry : linkedMapDetails.entrySet()) {
                ProductBaseModel productBaseModel = entry.getKey();
                List<VendorProductDataResponse> listData = entry.getValue();
                if(listData != null && !listData.isEmpty()) {
                    productBaseModel.setProductsList(listData);
                    vendorProductsList.add(productBaseModel);
                }
            }
        }
        vendorProductDetailsAdapter.updateItems(vendorProductsList);
        vendorProductDetailsAdapter.notifyDataSetChanged();
    }

    private LinkedHashMap<ProductBaseModel, List<VendorProductDataResponse>> groupDataIntoHashMap(List<VendorProductDataResponse> productDataList) {
        LinkedHashMap<ProductBaseModel, List<VendorProductDataResponse>> groupedHashMap = new LinkedHashMap<>();
        for(VendorProductDataResponse element : productDataList) {
            ProductBaseModel productBaseModel = new ProductBaseModel();
            productBaseModel.setProductCategoryName(element.getParentCategoryName());
            productBaseModel.setProductCategoryId(element.getParentCategoryId());
            if(groupedHashMap.containsKey(productBaseModel)) {
                List<VendorProductDataResponse> listData = groupedHashMap.get(productBaseModel);
                if(listData != null) {
                    listData.add(element);
                }
            } else {
                List<VendorProductDataResponse> listData = new ArrayList<>();
                listData.add(element);
                groupedHashMap.put(productBaseModel, listData);
            }
        }
        return groupedHashMap;
    }

    private void updateShopDetailsUI(VendorProductShopDataResponse shopDataResponse) {
        requireActivity().setTitle(shopDataResponse.getShopName());
        List<String> shopImagesList = shopDataResponse.getShopImage();
        if(shopImagesList != null && !shopImagesList.isEmpty()) {
            String shopImageUrl = shopImagesList.get(0);
            if(!TextUtils.isEmpty(shopImageUrl)) {
                HttpsTrustManager.allowAllSSL();
                ImageLoader imageLoader = RMartApplication.getInstance().getImageLoader();
                imageLoader.get(shopImageUrl, ImageLoader.getImageListener(ivShopImageField,
                        R.mipmap.ic_launcher, android.R.drawable
                                .ic_dialog_alert));
                ivShopImageField.setImageUrl(shopImageUrl, imageLoader);
            }
        }
        tvShopNameField.setText(shopDataResponse.getShopName());
        tvViewAddressField.setText(shopDataResponse.getShopAddress());
        tvPhoneNoField.setText(shopDataResponse.getShopMobileNo());
    }

    private void performSearch() {
        searchProductName = Objects.requireNonNull(etProductsSearchField.getText()).toString().trim();
        /*if (newText.length() < 1) {
            customerProductsListAdapter.updateItems(new ArrayList<>());
            customerProductsListAdapter.notifyDataSetChanged();
        } else if (newText.length() == 3) {
            searchShopName = newText;
            currentPage = 0;
            getShopsList();
        } else {
            customerProductsListAdapter.getFilter().filter(newText);
        }*/
    }
}