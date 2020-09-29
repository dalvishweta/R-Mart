package com.rmart.customer.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.adapters.ConfirmOrdersAdapter;
import com.rmart.customer.models.AddProductToWishListResponse;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.models.CustomerProductsShopDetailsModel;
import com.rmart.customer.models.ProductInCartDetailsModel;
import com.rmart.customer.models.ProductInCartResponse;
import com.rmart.customer.models.ShoppingCartResponseDetails;
import com.rmart.profile.model.MyProfile;
import com.rmart.services.UpdateProductQuantityServices;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.BaseResponse;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingCartDetailsFragment#getInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartDetailsFragment extends BaseFragment {

    private ShoppingCartResponseDetails vendorShoppingCartDetails;

    private RecyclerView productsListField;
    private TextView tvShopNameField;
    private TextView tvContactNoField;
    private List<ProductInCartDetailsModel> productInCartDetailsList;
    private ConfirmOrdersAdapter confirmOrdersAdapter;
    private ProductInCartDetailsModel selectedProductInCartDetails;
    private AppCompatButton btnProceedToBuyField;
    private OnCustomerHomeInteractionListener onCustomerHomeInteractionListener;
    private CustomerProductsShopDetailsModel vendorShopDetails;
    private int totalProductsInCart = -1;

    public ShoppingCartDetailsFragment() {
        // Required empty public constructor
    }

    public static ShoppingCartDetailsFragment getInstance(ShoppingCartResponseDetails vendorShopDetails) {
        ShoppingCartDetailsFragment fragment = new ShoppingCartDetailsFragment();
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
            vendorShoppingCartDetails = (ShoppingCartResponseDetails) extras.getSerializable("VendorShopDetails");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CustomerHomeActivity) {
            onCustomerHomeInteractionListener = (OnCustomerHomeInteractionListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoggerInfo.printLog("Fragment", "ShoppingCartDetailsFragment");
        return inflater.inflate(R.layout.fragment_shopping_cart_details, container, false);
    }

    private void loadUIComponents(View view) {
        productsListField = view.findViewById(R.id.products_list_field);
        productsListField.setHasFixedSize(false);

        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.recycler_decoration_divider)));
        productsListField.addItemDecoration(divider);

        tvShopNameField = view.findViewById(R.id.tv_shop_name_field);
        tvContactNoField = view.findViewById(R.id.tv_contact_no_field);

        productInCartDetailsList = new ArrayList<>();
        btnProceedToBuyField = view.findViewById(R.id.btn_proceed_to_buy_field);
        btnProceedToBuyField.setOnClickListener(v -> proceedToBuySelected());
    }

    private void proceedToBuySelected() {
        if (totalProductsInCart != 0) {
            onCustomerHomeInteractionListener.gotoCompleteOrderDetailsScreen(vendorShopDetails);
        } else {
            showDialog(getString(R.string.no_items_in_cart));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateToolBar();
    }

    public void updateToolBar() {
        Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.shopping_cart));
        ((CustomerHomeActivity) (requireActivity())).hideCartIcon();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);

        updateShopDetailsUI();

        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<ProductInCartResponse> call = customerProductsService.getVendorShowCartList(clientID, vendorShoppingCartDetails.getVendorId(), MyProfile.getInstance().getUserID());
            call.enqueue(new Callback<ProductInCartResponse>() {
                @Override
                public void onResponse(@NotNull Call<ProductInCartResponse> call, @NotNull Response<ProductInCartResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        ProductInCartResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                ProductInCartResponse.ProductInCartDetailsDataModel productInCartDetailsDataModel = body.getProductInCartDetailsDataModel();
                                vendorShopDetails = body.getProductInCartDetailsDataModel().getVendorShopDetails();
                                if (productInCartDetailsDataModel != null) {
                                    productInCartDetailsList.addAll(productInCartDetailsDataModel.getProductInCartDetailsList());
                                }
                                setAdapter();
                            } else {
                                showCloseDialog(body.getMsg());
                            }
                        } else {
                            showCloseDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showCloseDialog(getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ProductInCartResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    showCloseDialog(t.getMessage());
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text), pObject -> requireActivity().getSupportFragmentManager().popBackStack());
        }
    }


    private void updateShopDetailsUI() {
        tvShopNameField.setText(vendorShoppingCartDetails.getShopName());
        tvContactNoField.setText(vendorShoppingCartDetails.getMobileNumber());
    }

    private void setAdapter() {
        if (!productInCartDetailsList.isEmpty()) {
            btnProceedToBuyField.setVisibility(View.VISIBLE);
            confirmOrdersAdapter = new ConfirmOrdersAdapter(requireActivity(), productInCartDetailsList, callBackListener);
            productsListField.setAdapter(confirmOrdersAdapter);
        } else {
            showCloseDialog(getString(R.string.no_information_available));
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
                deleteQuantityCountSelected();
            } else if (contentModel.getStatus().equalsIgnoreCase(Constants.TAG_PLUS)) {
                selectedProductInCartDetails = (ProductInCartDetailsModel) contentModel.getValue();
                addMoreQuantityCountSelected();
            }
        }
    };

    private void moveToWishSelected() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<AddProductToWishListResponse> call = customerProductsService.moveToWishList(clientID, selectedProductInCartDetails.getVendorId(), MyProfile.getInstance().getUserID(),
                    selectedProductInCartDetails.getProductId());
            call.enqueue(new Callback<AddProductToWishListResponse>() {
                @Override
                public void onResponse(@NotNull Call<AddProductToWishListResponse> call, @NotNull Response<AddProductToWishListResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        BaseResponse body = response.body();
                        if (body != null) {
                            showDialog(body.getMsg());
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddProductToWishListResponse> call, @NotNull Throwable t) {
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
            Call<ProductInCartResponse> call = customerProductsService.deleteProductDetails(clientID, selectedProductInCartDetails.getCartId());
            call.enqueue(new Callback<ProductInCartResponse>() {
                @Override
                public void onResponse(@NotNull Call<ProductInCartResponse> call, @NotNull Response<ProductInCartResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        ProductInCartResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                int totalCartInCount = body.getProductInCartDetailsDataModel().getTotalCartCount();
                                showSuccessDialog(totalCartInCount, body.getMsg());
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
                    progressDialog.dismiss();
                    showDialog(t.getMessage());
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void showSuccessDialog(int totalCartInCount, String message) {
        showDialog(message, pObject -> {
            MyProfile.getInstance().setCartCount(totalCartInCount);
            int index = productInCartDetailsList.indexOf(selectedProductInCartDetails);
            if (index > -1) {
                productInCartDetailsList.remove(index);
                confirmOrdersAdapter.notifyItemRemoved(index);
                if (productInCartDetailsList.size() == 0) {
                    popBackFromStack();
                }
            }
        });
    }

    private void showCloseDialog(String message) {
        showDialog(message, pObject -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void popBackFromStack() {
        requireActivity().onBackPressed();
    }

    private void deleteQuantityCountSelected() {
        int count = selectedProductInCartDetails.getTotalProductCartQty();
        if (count > 1) {
            count--;
            totalProductsInCart = count;
            selectedProductInCartDetails.setTotalProductCartQty(count);
            int index = productInCartDetailsList.indexOf(selectedProductInCartDetails);
            if (index > -1) {
                productInCartDetailsList.set(index, selectedProductInCartDetails);
                confirmOrdersAdapter.notifyItemChanged(index);
                UpdateProductQuantityServices.enqueueWork(requireActivity(), selectedProductInCartDetails);
            }
        }
    }

    private void addMoreQuantityCountSelected() {
        int count = selectedProductInCartDetails.getTotalProductCartQty();
        if (count <= 10) {
            count++;
            totalProductsInCart = count;
            selectedProductInCartDetails.setTotalProductCartQty(count);
            int index = productInCartDetailsList.indexOf(selectedProductInCartDetails);
            if (index > -1) {
                productInCartDetailsList.set(index, selectedProductInCartDetails);
                confirmOrdersAdapter.notifyItemChanged(index);
                UpdateProductQuantityServices.enqueueWork(requireActivity(), selectedProductInCartDetails);
            }
        }
    }
}