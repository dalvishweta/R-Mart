package com.rmart.customer.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.adapters.ConfirmOrdersAdapter;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.models.CustomerProductsModel;
import com.rmart.customer.models.ProductInCartDetailsModel;
import com.rmart.customer.models.ProductInCartResponse;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.BaseResponse;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmOrderFragment#getInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmOrderFragment extends BaseFragment {

    private CustomerProductsModel vendorShopDetails;

    private RecyclerView productsListField;
    private TextView tvShopNameField;
    private TextView tvContactNoField;
    private List<ProductInCartDetailsModel> productInCartDetailsList;
    private ConfirmOrdersAdapter confirmOrdersAdapter;
    private ProductInCartDetailsModel selectedProductInCartDetails;


    public ConfirmOrderFragment() {
        // Required empty public constructor
    }

    public static ConfirmOrderFragment getInstance(CustomerProductsModel vendorShopDetails) {
        ConfirmOrderFragment fragment = new ConfirmOrderFragment();
        Bundle extras = new Bundle();
        extras.putSerializable("VendorShopDetails", vendorShopDetails);
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            vendorShopDetails = (CustomerProductsModel) extras.getSerializable("VendorShopDetails");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_order, container, false);
    }

    private void loadUIComponents(View view) {
        productsListField = view.findViewById(R.id.products_list_field);
        productsListField.setHasFixedSize(false);

        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.recycler_decoration_divider)));
        productsListField.addItemDecoration(divider);

        tvShopNameField = view.findViewById(R.id.tv_shop_name_field);
        tvContactNoField = view.findViewById(R.id.tv_contact_no_field);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);

        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<ProductInCartResponse> call = customerProductsService.getVendorShowCartList(clientID, vendorShopDetails.getVendorId(), MyProfile.getInstance().getUserID());
            call.enqueue(new Callback<ProductInCartResponse>() {
                @Override
                public void onResponse(@NotNull Call<ProductInCartResponse> call, @NotNull Response<ProductInCartResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        ProductInCartResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                ProductInCartResponse.ProductInCartDetailsDataModel productInCartDetailsDataModel = body.getProductInCartDetailsDataModel();
                                if (productInCartDetailsDataModel != null) {
                                    productInCartDetailsList = productInCartDetailsDataModel.getProductInCartDetailsList();
                                }
                                setAdapter();
                            } else {
                                showDialog(body.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ProductInCartResponse> call, @NotNull Throwable t) {

                }
            });
        } else {
            showCloseDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void setAdapter() {
        if (productInCartDetailsList != null && !productInCartDetailsList.isEmpty()) {
            confirmOrdersAdapter = new ConfirmOrdersAdapter(requireActivity(), productInCartDetailsList, callBackListener);
            productsListField.setAdapter(confirmOrdersAdapter);
        } else {
            showCloseDialog(getString(R.string.message), getString(R.string.no_information_available));
        }
    }

    private CallBackInterface callBackListener = pObject -> {
        if (pObject instanceof ContentModel) {
            ContentModel contentModel = (ContentModel) pObject;
            if (contentModel.getStatus().equalsIgnoreCase(Constants.TAG_DELETE)) {
                selectedProductInCartDetails = (ProductInCartDetailsModel) contentModel.getValue();
                deleteProductSelected();
            } else if (contentModel.getStatus().equalsIgnoreCase(Constants.TAG_MOVE_TO_WISH_LIST)) {
                selectedProductInCartDetails = (ProductInCartDetailsModel) contentModel.getValue();
                moveToWishSelected();
            } else if (contentModel.getStatus().equalsIgnoreCase(Constants.TAG_MINUS)) {
                selectedProductInCartDetails = (ProductInCartDetailsModel) contentModel.getValue();
            } else if (contentModel.getStatus().equalsIgnoreCase(Constants.TAG_PLUS)) {
                selectedProductInCartDetails = (ProductInCartDetailsModel) contentModel.getValue();
            }
        }
    };

    private void moveToWishSelected() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<BaseResponse> call = customerProductsService.moveToWishList(clientID, 1, selectedProductInCartDetails.getCartId(),
                    selectedProductInCartDetails.getProductId());
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        BaseResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                showSuccessDialog(body.getMsg());
                            } else {
                                showDialog(body.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<BaseResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    showDialog(t.getMessage());
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void deleteProductSelected() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<BaseResponse> call = customerProductsService.deleteProductDetails(clientID, selectedProductInCartDetails.getCartId());
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        BaseResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                showSuccessDialog(body.getMsg());
                            } else {
                                showDialog(body.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<BaseResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    showDialog(t.getMessage());
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void showSuccessDialog(String message) {
        showDialog(message, pObject -> {
            int index = productInCartDetailsList.indexOf(selectedProductInCartDetails);
            if (index > -1) {
                productInCartDetailsList.remove(index);
                confirmOrdersAdapter.notifyItemRemoved(index);
            }
        });
    }

    private void showCloseDialog(String title, String message) {
        showDialog(title, message, pObject -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }
}