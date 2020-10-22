package com.rmart.customer.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.adapters.CustomerWishListDetailsAdapter;
import com.rmart.customer.models.AddToCartResponseDetails;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.customer.models.ShopWiseWishListResponseDetails;
import com.rmart.customer.models.WishListResponseDetails;
import com.rmart.customer.models.WishListResponseModel;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.GridSpacesItemDecoration;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.BaseResponse;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Satya Seshu on 17/09/20.
 */
public class CustomerWishListDetailsFragment extends BaseFragment {

    private TextView tvNoOfProductsField;
    private List<WishListResponseDetails> wishListCart;
    private ShopWiseWishListResponseDetails vendorShopDetails;
    private CustomerWishListDetailsAdapter customerWishListDetailsAdapter;
    private int totalWishListCount = 0;
    private int currentPage = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int PAGE_SIZE = 20;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static CustomerWishListDetailsFragment getInstance(ShopWiseWishListResponseDetails vendorShopDetails) {
        CustomerWishListDetailsFragment customerWishListDetailsFragment = new CustomerWishListDetailsFragment();
        Bundle extras = new Bundle();
        extras.putSerializable("VendorShopDetails", vendorShopDetails);
        customerWishListDetailsFragment.setArguments(extras);
        return customerWishListDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            vendorShopDetails = (ShopWiseWishListResponseDetails) extras.getSerializable("VendorShopDetails");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LoggerInfo.printLog("Fragment", "CustomerWishListDetailsFragment");
        return inflater.inflate(R.layout.fragment_customer_wish_list_details, container, false);
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
    }

    private void loadUIComponents(View view) {
        RecyclerView productsListField = view.findViewById(R.id.products_list_field);
        TextView tvShopNameField = view.findViewById(R.id.tv_shop_name_field);
        tvNoOfProductsField = view.findViewById(R.id.tv_no_of_products_field);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_field);
        swipeRefreshLayout.setRefreshing(false);

        tvShopNameField.setText(vendorShopDetails.getShopName());

        productsListField.setHasFixedSize(false);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            resetProductsList();
            getWishListDetails();
        });

        wishListCart = new ArrayList<>();

        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false);
        productsListField.setLayoutManager(layoutManager);
        productsListField.addItemDecoration(new GridSpacesItemDecoration(20));

        productsListField.setItemAnimator(new DefaultItemAnimator());

        productsListField.setHasFixedSize(false);
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

        customerWishListDetailsAdapter = new CustomerWishListDetailsAdapter(requireActivity(), wishListCart, callBackListener);
        productsListField.setAdapter(customerWishListDetailsAdapter);

        getWishListDetails();
    }

    private void resetProductsList() {
        wishListCart.clear();
        customerWishListDetailsAdapter.updateItems(wishListCart);
        customerWishListDetailsAdapter.notifyDataSetChanged();
        currentPage = 0;
    }

    private void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        getWishListDetails();
    }

    private void getWishListDetails() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<WishListResponseModel> call = customerProductsService.getShowWishListData(clientID, vendorShopDetails.getVendorId(), currentPage, MyProfile.getInstance().getUserID());
            call.enqueue(new Callback<WishListResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<WishListResponseModel> call, @NotNull Response<WishListResponseModel> response) {
                    progressDialog.dismiss();
                    resetProductsList();
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()) {
                        WishListResponseModel body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                totalWishListCount = body.getWishListResponseDataResponse().getTotalWishlistCount();
                                updateShopDetails();
                                List<WishListResponseDetails> lWishListListDetails = body.getWishListResponseDataResponse().getWishListResponseDetails();
                                if (lWishListListDetails != null && !lWishListListDetails.isEmpty()) {
                                    updateAdapter(lWishListListDetails);
                                } else {
                                    showDialog(body.getMsg());
                                }
                            } else {
                                showDialog(body.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog(response.message());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<WishListResponseModel> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    showDialog(t.getMessage());
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void updateShopDetails() {
        String productsCount = String.format(Locale.getDefault(), "%d %s", totalWishListCount, (totalWishListCount == 1) ? getString(R.string.product) : getString(R.string.products));
        tvNoOfProductsField.setText(productsCount);
    }

    private void updateAdapter(List<WishListResponseDetails> customerProductsList) {
        wishListCart.addAll(customerProductsList);
        customerWishListDetailsAdapter.updateItems(customerProductsList);
        customerWishListDetailsAdapter.notifyDataSetChanged();
        if (wishListCart.size() >= totalWishListCount) {
            isLastPage = true;
        } else {
            isLoading = false;
        }
    }

    private final CallBackInterface callBackListener = pObject -> {
        if (pObject instanceof ContentModel) {
            ContentModel contentModel = (ContentModel) pObject;
            String status = contentModel.getStatus();
            if (status.equalsIgnoreCase(Constants.TAG_ADD_TO_CART)) {
                WishListResponseDetails selectedItem = (WishListResponseDetails) contentModel.getValue();
                List<CustomerProductsDetailsUnitModel> unitsList = selectedItem.getUnits();
                if (unitsList != null && !unitsList.isEmpty()) {
                    addToCartSelected(unitsList.get(0));
                }
            } else if (status.equalsIgnoreCase(Constants.TAG_REMOVE)) {
                WishListResponseDetails selectedItem = (WishListResponseDetails) contentModel.getValue();
                removeFromCartSelected(selectedItem);
            }
        }
    };

    private void addToCartSelected(CustomerProductsDetailsUnitModel productUnitDetails) {
        if (Utils.isNetworkConnected(requireActivity())) {
            int totalProductCartQty = productUnitDetails.getTotalProductCartQty();
            int productUnitQuantity = productUnitDetails.getProductUnitQuantity();
            if (totalProductCartQty < productUnitQuantity) {
                progressDialog.show();
                CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
                String clientID = "2";
                Call<AddToCartResponseDetails> call = customerProductsService.addToCart(clientID, vendorShopDetails.getVendorId(), MyProfile.getInstance().getUserID(),
                        productUnitDetails.getProductUnitId(), 1, "add");
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
                showDialog(getString(R.string.out_of_stock_error));
            }
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void removeFromCartSelected(WishListResponseDetails shopWiseWishListResponseDetails) {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<BaseResponse> call = customerProductsService.deleteProductFromWishList(clientID, shopWiseWishListResponseDetails.getWishListId());
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        BaseResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                showDialog(body.getMsg(), pObject -> removeFromCart(shopWiseWishListResponseDetails));
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

    private void removeFromCart(WishListResponseDetails wishListResponseDetails) {
        int index = wishListCart.indexOf(wishListResponseDetails);
        if (index > -1) {
            wishListCart.remove(index);
            customerWishListDetailsAdapter.notifyItemRemoved(index);
        }
        if (wishListCart.size() == 0) {
            requireActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }
}
