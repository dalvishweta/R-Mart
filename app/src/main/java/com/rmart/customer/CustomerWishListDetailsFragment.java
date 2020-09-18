package com.rmart.customer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.adapters.CustomerWishListDetailsAdapter;
import com.rmart.customer.models.AddToCartResponseDetails;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.customer.models.ShopWiseWishListResponseDetails;
import com.rmart.customer.models.ShopWiseWishListResponseModel;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.BaseResponse;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Satya Seshu on 17/09/20.
 */
public class CustomerWishListDetailsFragment extends BaseFragment {

    private RecyclerView productsListField;
    private TextView tvShopNameField;
    private TextView tvNoOfProductsField;
    private List<ShopWiseWishListResponseDetails> wishListCart;
    private OnCustomerHomeInteractionListener onCustomerHomeInteractionListener;
    private int vendorId;
    private CustomerWishListDetailsAdapter customerWishListDetailsAdapter;

    public static CustomerWishListDetailsFragment getInstance(int vendorId) {
        CustomerWishListDetailsFragment customerWishListDetailsFragment = new CustomerWishListDetailsFragment();
        Bundle extras = new Bundle();
        extras.putInt("VendorId", vendorId);
        customerWishListDetailsFragment.setArguments(extras);
        return customerWishListDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            vendorId = extras.getInt("VendorId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_customer_wish_list_details, container, false);
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
        Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.wish_list));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);

        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<ShopWiseWishListResponseModel> call = customerProductsService.getShowShopWiseWishListData(clientID, vendorId, MyProfile.getInstance().getUserID());
            call.enqueue(new Callback<ShopWiseWishListResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<ShopWiseWishListResponseModel> call, @NotNull Response<ShopWiseWishListResponseModel> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        ShopWiseWishListResponseModel body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                List<ShopWiseWishListResponseDetails> shopWiseCartList = body.getWishListResponseDataResponse().getShopWiseWishListResponseDetailsList();
                                if (shopWiseCartList != null && !shopWiseCartList.isEmpty()) {
                                    wishListCart.addAll(shopWiseCartList);
                                    setAdapter();
                                } else {
                                    showDialog(body.getMsg());
                                }
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
                public void onFailure(@NotNull Call<ShopWiseWishListResponseModel> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    showDialog(t.getMessage());
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text), pObject -> {
                requireActivity().getSupportFragmentManager().popBackStack();
            });
        }
    }

    private void loadUIComponents(View view) {
        productsListField = view.findViewById(R.id.products_list_field);
        tvShopNameField = view.findViewById(R.id.tv_shop_name_field);
        tvNoOfProductsField = view.findViewById(R.id.tv_no_of_products_field);

        productsListField.setHasFixedSize(false);

        wishListCart = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false);
        productsListField.setLayoutManager(manager);
    }

    private void setAdapter() {
        if (!wishListCart.isEmpty()) {
            customerWishListDetailsAdapter = new CustomerWishListDetailsAdapter(requireActivity(), wishListCart, callBackListener);
            productsListField.setAdapter(customerWishListDetailsAdapter);
        }
    }

    private CallBackInterface callBackListener = pObject -> {
        if (pObject instanceof ContentModel) {
            ContentModel contentModel = (ContentModel) pObject;
            String status = contentModel.getStatus();
            if (status.equalsIgnoreCase(Constants.TAG_ADD_TO_CART)) {
                ShopWiseWishListResponseDetails selectedItem = (ShopWiseWishListResponseDetails) contentModel.getValue();
                List<CustomerProductsDetailsUnitModel> unitsList = selectedItem.getUnits();
                if (unitsList != null && !unitsList.isEmpty()) {
                    addToCartSelected(unitsList.get(0));
                }
            } else if (status.equalsIgnoreCase(Constants.TAG_REMOVE)) {
                ShopWiseWishListResponseDetails selectedItem = (ShopWiseWishListResponseDetails) contentModel.getValue();
                removeFromCartSelected(selectedItem);
            }
        }
    };

    private void addToCartSelected(CustomerProductsDetailsUnitModel productUnitDetails) {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<AddToCartResponseDetails> call = customerProductsService.addToCart(clientID, vendorId, MyProfile.getInstance().getUserID(),
                    productUnitDetails.getProductUnitId(), productUnitDetails.getProductUnitQuantity());
            call.enqueue(new Callback<AddToCartResponseDetails>() {
                @Override
                public void onResponse(@NotNull Call<AddToCartResponseDetails> call, @NotNull Response<AddToCartResponseDetails> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        AddToCartResponseDetails body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                AddToCartResponseDetails.AddToCartDataResponse addToCartDataResponse = body.getAddToCartDataResponse();
                                if (addToCartDataResponse != null) {
                                    Integer totalCartCount = addToCartDataResponse.getTotalCartCount();
                                    MyProfile.getInstance().setCartCount(totalCartCount);
                                    showDialog(body.getMsg());
                                } else {
                                    showDialog(getString(R.string.no_information_available));
                                }
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
                public void onFailure(@NotNull Call<AddToCartResponseDetails> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    showDialog(t.getMessage());
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void removeFromCartSelected(ShopWiseWishListResponseDetails shopWiseWishListResponseDetails) {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<BaseResponse> call = customerProductsService.removeFromCart(clientID, shopWiseWishListResponseDetails.getWishListId());
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        BaseResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                showDialog(body.getMsg(), pObject -> {
                                    removeFromCart(shopWiseWishListResponseDetails);
                                });
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

    private void removeFromCart(ShopWiseWishListResponseDetails shopWiseWishListResponseDetails) {
        int index = wishListCart.indexOf(shopWiseWishListResponseDetails);
        if (index > -1) {
            wishListCart.remove(index);
            customerWishListDetailsAdapter.notifyItemRemoved(index);
        }
    }
}
