package com.rmart.customer.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.adapters.FavouritesShopsAdapter;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.models.CustomerProductsShopDetailsModel;
import com.rmart.customer.models.ShopFavouritesListResponseModel;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
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
 * Created by Satya Seshu on 14/09/20.
 */
public class CustomerFavouritesFragment extends BaseFragment {

    private List<CustomerProductsShopDetailsModel> favoritesShopsList;
    private FavouritesShopsAdapter favouritesShopsAdapter;
    private OnCustomerHomeInteractionListener onCustomerHomeInteractionListener;

    public static CustomerFavouritesFragment getInstance() {
        CustomerFavouritesFragment customerWishListFragment = new CustomerFavouritesFragment();
        Bundle extras = new Bundle();
        customerWishListFragment.setArguments(extras);
        return customerWishListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LoggerInfo.printLog("Fragment", "CustomerFavouritesFragment");
        return inflater.inflate(R.layout.fragment_customer_favourites_view, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCustomerHomeInteractionListener) {
            onCustomerHomeInteractionListener = (OnCustomerHomeInteractionListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.my_favourites));
        getFavouritesListData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        RecyclerView favouritesShopsListField = view.findViewById(R.id.favourites_list_field);
        favouritesShopsListField.setHasFixedSize(false);

        favoritesShopsList = new ArrayList<>();

        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.recycler_decoration_divider_transparent)));
        favouritesShopsListField.addItemDecoration(divider);

        favouritesShopsAdapter = new FavouritesShopsAdapter(requireActivity(), favoritesShopsList, callBackListener);
        favouritesShopsListField.setAdapter(favouritesShopsAdapter);
    }

    private final CallBackInterface callBackListener = pObject -> {
        if(pObject instanceof ContentModel) {
            ContentModel contentModel = (ContentModel) pObject;
            String status = contentModel.getStatus();
            CustomerProductsShopDetailsModel selectedShopDetails = (CustomerProductsShopDetailsModel) contentModel.getValue();
            if(status.equalsIgnoreCase(Constants.TAG_REMOVE)) {
                showConfirmationDialog(getString(R.string.favourite_shop_details_delete_alert), pObject1 -> {
                    if (pObject1 == Constants.TAG_SUCCESS) {
                        deleteShopFromWishList(selectedShopDetails);
                    }
                });
            } else if(status.equalsIgnoreCase(Constants.TAG_DETAILS)) {
                onCustomerHomeInteractionListener.gotoVendorProductDetails(selectedShopDetails);
            }
        }
    };

    private void deleteShopFromWishList(CustomerProductsShopDetailsModel selectedShopDetails) {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<BaseResponse> call = customerProductsService.deleteShopFromWishList(clientID, selectedShopDetails.getVendorId(), selectedShopDetails.getShopId(),
                    MyProfile.getInstance().getUserID());
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        BaseResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                showDialog(body.getMsg(), pObject -> {
                                    updatedDeletedAdapter(selectedShopDetails);
                                });
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
                public void onFailure(@NotNull Call<BaseResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    showDialog(t.getMessage());
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void updatedDeletedAdapter(CustomerProductsShopDetailsModel selectedShopDetails) {
        int index = favoritesShopsList.indexOf(selectedShopDetails);
        if (index > -1) {
            favoritesShopsList.remove(selectedShopDetails);
            favouritesShopsAdapter.updateItems(favoritesShopsList);
            favouritesShopsAdapter.notifyDataSetChanged();
            if (favoritesShopsList.isEmpty()) {
                requireActivity().onBackPressed();
            }
        }
    }

    private void getFavouritesListData() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            favoritesShopsList.clear();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<ShopFavouritesListResponseModel> call = customerProductsService.getShowShopFavouritesList(clientID, MyProfile.getInstance().getUserID(), 0);
            call.enqueue(new Callback<ShopFavouritesListResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<ShopFavouritesListResponseModel> call, @NotNull Response<ShopFavouritesListResponseModel> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        ShopFavouritesListResponseModel body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                List<CustomerProductsShopDetailsModel> shopWiseCartList = body.getShopFavouritesListDataResponse().getCustomerProductsShopDetails();
                                if (shopWiseCartList != null && !shopWiseCartList.isEmpty()) {
                                    favoritesShopsList.addAll(shopWiseCartList);
                                    updateAdapter(body.getMsg());
                                } else {
                                    showCloseDialog(null, body.getMsg());
                                }
                            } else {
                                showCloseDialog(null, body.getMsg());
                            }
                        } else {
                            showCloseDialog(null, getString(R.string.no_information_available));
                        }
                    } else {
                        showCloseDialog(null, response.message());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ShopFavouritesListResponseModel> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    showCloseDialog(null, t.getMessage());
                }
            });
        } else {
            showCloseDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void showCloseDialog(String title, String message) {
        showDialog(title, message, pObject -> requireActivity().onBackPressed());
    }

    private void updateAdapter(String message) {
        if (!favoritesShopsList.isEmpty()) {
            favouritesShopsAdapter.updateItems(favoritesShopsList);
            favouritesShopsAdapter.notifyDataSetChanged();
        } else {
            showCloseDialog(null, message);
        }
    }
}
