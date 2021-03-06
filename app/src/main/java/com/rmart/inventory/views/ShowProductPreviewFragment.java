package com.rmart.inventory.views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.views.AutoScrollViewPager;
import com.rmart.inventory.adapters.ImageAdapter;
import com.rmart.inventory.adapters.ProductUnitAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.DateUtilities;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.APIStockListResponse;
import com.rmart.utilits.pojos.APIStockResponse;
import com.rmart.utilits.pojos.AddressResponse;
import com.rmart.utilits.BaseResponse;
import com.rmart.utilits.pojos.ImageURLResponse;
import com.rmart.utilits.pojos.ProductResponse;
import com.rmart.utilits.pojos.ShowProductResponse;
import com.rmart.utilits.services.VendorInventoryService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowProductPreviewFragment extends BaseInventoryFragment {
    private static final String ARG_PRODUCT = "product";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM1 = "param1";
    private RecyclerView recyclerView;
    private ProductResponse product;
    private boolean isEdit;
    private AutoScrollViewPager autoScrollViewPager;
    //private APIStockListResponse apiStockListResponse;
    private AppCompatTextView tvProductName, tvProductDescription, tvProductRegionalName, tvProductExpiry, tvDeliveryDaysBeforeTime,
            tvOpeningTime, tvClosingTime, tvDeliveryDaysAfterTime;
    private TabLayout dotIndicatorLayoutField;

    public ShowProductPreviewFragment() {
        // Required empty public constructor
    }

    public static ShowProductPreviewFragment newInstance(ProductResponse product, boolean isEdit, APIStockListResponse apiStockListResponse) {
        ShowProductPreviewFragment fragment = new ShowProductPreviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        args.putSerializable(ARG_PARAM1, apiStockListResponse);
        args.putBoolean(ARG_PARAM2, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (ProductResponse) getArguments().getSerializable(ARG_PRODUCT);
            APIStockListResponse apiStockListResponse = (APIStockListResponse) getArguments().getSerializable(ARG_PARAM1);
            HashMap<String, APIStockResponse> stockList =  new HashMap<>();
            for (APIStockResponse apiStockResponse: apiStockListResponse.getArrayList()) {
                stockList.put(apiStockResponse.getStockID(), apiStockResponse);
            }
            isEdit = getArguments().getBoolean(ARG_PARAM2);
        }
        /*product = Objects.requireNonNull(inventoryViewModel.getProductList().getValue()).get(inventoryViewModel.getSelectedProduct().getValue());
            inventoryViewModel.getSelectedProduct().observe(Objects.requireNonNull(getActivity()), index -> {
                product = Objects.requireNonNull(inventoryViewModel.getProductList().getValue()).get(index);
            } );*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoggerInfo.printLog("Fragment", "ShowProductPreviewFragment");
        return inflater.inflate(R.layout.fragment_inventory_product_preview, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.product_details));
        if(!Utils.isNetworkConnected(requireActivity())) {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
            return;
        }
        progressDialog.show();
        VendorInventoryService vendorInventoryService = RetrofitClientInstance.getRetrofitInstance().create(VendorInventoryService.class);
        vendorInventoryService.getProduct(product.getProductID(), MyProfile.getInstance(getActivity()).getUserID()).enqueue(new Callback<ShowProductResponse>() {
            @Override
            public void onResponse(@NotNull Call<ShowProductResponse> call, @NotNull Response<ShowProductResponse> response) {
                if (response.isSuccessful()) {
                    ShowProductResponse data = response.body();
                    if (data != null) {
                        if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                            product = data.getProductResponse();
                            updateUI();
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
                if(t instanceof SocketTimeoutException){
                    showDialog("", getString(R.string.network_slow));
                } else {
                    showDialog("", t.getMessage());
                }
                progressDialog.dismiss();
            }
        });

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvProductName = view.findViewById(R.id.product_name);
        recyclerView = view.findViewById(R.id.unit_base);
        tvProductDescription = view.findViewById(R.id.tv_product_description_field);
        tvProductRegionalName = view.findViewById(R.id.tv_product_regional_name);
        tvDeliveryDaysBeforeTime = view.findViewById(R.id.tv_delivery_before_time);
        tvClosingTime = view.findViewById(R.id.tv_closing_time);
        tvOpeningTime = view.findViewById(R.id.tv_opening_time);
        tvDeliveryDaysAfterTime = view.findViewById(R.id.tv_delivery_after_time);
        tvProductExpiry = view.findViewById(R.id.tv_product_expiry);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        autoScrollViewPager = view.findViewById(R.id.view_pager);
        AppCompatButton delete = view.findViewById(R.id.unit_delete);
        AppCompatButton edit = view.findViewById(R.id.edit);
        dotIndicatorLayoutField = view.findViewById(R.id.product_images_dot_indicator_field);

        autoScrollViewPager.startAutoScroll();
        autoScrollViewPager.setInterval(1000);
        autoScrollViewPager.setCycle(true);
        autoScrollViewPager.setStopScrollWhenTouch(true);

        if (isEdit) {
            edit.setOnClickListener(v -> {
                if(!Utils.isNetworkConnected(requireActivity())) {
                    showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
                    return;
                }
                mListener.updateProduct(product, true);
            });
            delete.setOnClickListener(v -> {
                if(!Utils.isNetworkConnected(requireActivity())) {
                    showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
                    return;
                }
                showConfirmationDialog(getString(R.string.delete_product_conformation), deleteView -> {
                    if(!Utils.isNetworkConnected(requireActivity())) {
                        showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
                        return;
                    }
                    progressDialog.show();
                    VendorInventoryService vendorInventoryService = RetrofitClientInstance.getRetrofitInstance().create(VendorInventoryService.class);
                    vendorInventoryService.deleteProduct(product.getProductID(), MyProfile.getInstance(getActivity()).getUserID()).enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                            BaseResponse data = response.body();
                            if(data != null) {
                                if(data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                    showDialog("", getString(R.string.success_product_delete), (dialogInterface, i) -> requireActivity().onBackPressed());
                                } else {
                                    showDialog(getString(R.string.no_information_available));
                                }
                            } else {
                                showDialog(getString(R.string.no_information_available));
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(@NotNull Call<BaseResponse> call, @NotNull Throwable t) {
                            if(t instanceof SocketTimeoutException){
                                showDialog("", getString(R.string.network_slow));
                            } else {
                                showDialog("", t.getMessage());
                            }
                            progressDialog.dismiss();
                        }
                    });
                });
            });
        } else {
            delete.setVisibility(View.GONE);
            edit.setText(getString(R.string.save));
        }

        // updateUI();
    }

    private void showProductPreviewSelected(String productVideoLink) {
        if (!TextUtils.isEmpty(productVideoLink)) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(productVideoLink));
            try {
                this.startActivity(webIntent);
            } catch (ActivityNotFoundException ex) {
                showDialog(ex.getMessage());
            }
        } else {
            showDialog(getString(R.string.no_video_link_found));
        }
    }

    private final CallBackInterface callBackInterface = pObject -> {

    };

    private void updateUI() {
        List<ImageURLResponse> lImagesList = new ArrayList<>(product.getImageDataObject());
        String videoLink = product.getVideoLInk();
        if (!TextUtils.isEmpty(videoLink)) {
            if(Utils.isValidYoutubeUrl(videoLink)) {
                String productVideoUrl = Utils.getYoutubeThumbnailUrlFromVideoUrl(videoLink);
                if (!TextUtils.isEmpty(productVideoUrl)) {
                    ImageURLResponse imageURLResponse = new ImageURLResponse();
                    imageURLResponse.setDisplayImage(productVideoUrl);
                    imageURLResponse.setImageURL(videoLink);
                    imageURLResponse.setProductVideoSelected(true);
                    lImagesList.add(imageURLResponse);
                }
            }
        }
        ImageAdapter imageAdapter = new ImageAdapter(requireContext(), lImagesList);
        imageAdapter.setCallBackListener(pObject -> {
            if (pObject instanceof ImageURLResponse) {
                ImageURLResponse imageURLResponse = (ImageURLResponse) pObject;
                showProductPreviewSelected(imageURLResponse.getImageURL());
            }
        });
        autoScrollViewPager.setAdapter(imageAdapter);
        dotIndicatorLayoutField.setVisibility(lImagesList.size() == 1 ? View.GONE : View.VISIBLE);
        dotIndicatorLayoutField.setupWithViewPager(autoScrollViewPager);
        tvProductName.setText(product.getProductName());

        MyProfile myProfile = MyProfile.getInstance(getActivity());
        if (myProfile != null) {
            ArrayList<AddressResponse> addressResponsesList = myProfile.getAddressResponses();
            if(addressResponsesList != null && !addressResponsesList.isEmpty()) {
                AddressResponse addressResponse = addressResponsesList.get(0);
                String deliveryDaysBeforeTime = addressResponse.getDeliveryDaysBeforeTime();
                if(!TextUtils.isEmpty(deliveryDaysBeforeTime)) {
                    if (deliveryDaysBeforeTime.equalsIgnoreCase("0")) {
                        tvDeliveryDaysBeforeTime.setText(getString(R.string.delivery_in_same_day));
                    } else if (deliveryDaysBeforeTime.equalsIgnoreCase("1")) {
                        tvDeliveryDaysBeforeTime.setText(getString(R.string.delivery_in_1_day));
                    } else {
                        tvDeliveryDaysBeforeTime.setText(String.format(getString(R.string.delivery_in_days), deliveryDaysBeforeTime));
                    }
                }

                String deliveryDaysAfterTime = addressResponse.getDeliveryDaysAfterTime();
                if(!TextUtils.isEmpty(deliveryDaysAfterTime)) {
                    if (deliveryDaysAfterTime.equalsIgnoreCase("0")) {
                        tvDeliveryDaysAfterTime.setText(getString(R.string.delivery_in_same_day));
                    } else if (deliveryDaysAfterTime.equalsIgnoreCase("1")) {
                        tvDeliveryDaysAfterTime.setText(getString(R.string.delivery_in_1_day));
                    } else {
                        tvDeliveryDaysAfterTime.setText(String.format(getString(R.string.delivery_in_days), deliveryDaysAfterTime));
                    }
                }
                tvClosingTime.setText(addressResponse.getClosingTime());
                tvOpeningTime.setText(addressResponse.getOpeningTime());
            }
        }

        ProductUnitAdapter unitBaseAdapter = new ProductUnitAdapter(product.getUnitObjects(), callBackInterface, false);
        recyclerView.setAdapter(unitBaseAdapter);

        tvProductDescription.setText(product.getDescription());
        tvProductRegionalName.setText(product.getRegionalName());
        String expiryDate = product.getExpiry_date();
        if(!TextUtils.isEmpty(expiryDate)) {
            if(expiryDate.equalsIgnoreCase("1970-01-01") || expiryDate.equalsIgnoreCase("01-01-1970")) {
                tvProductExpiry.setText("");
            } else {
                Calendar expiryDateCalendar = DateUtilities.getCalendarFromString(expiryDate);
                tvProductExpiry.setText(DateUtilities.getDateStringFromCalendar(expiryDateCalendar));
            }
        }
    }
}