package com.rmart.customer.views;

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
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.adapters.FavouritesShopsAdapter;
import com.rmart.customer.models.ShopWiseWishListResponseDetails;
import com.rmart.customer.models.ShopWiseWishListResponseModel;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
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

    private List<ShopWiseWishListResponseDetails> favoritesShopsList;
    private FavouritesShopsAdapter favouritesShopsAdapter;

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
        LoggerInfo.printLog("Fragment", "CustomerWishListFragment");
        return inflater.inflate(R.layout.fragment_customer_favourites_view, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.wish_list));
        getFavouritesListData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        RecyclerView favouritesShopsListField = view.findViewById(R.id.wish_list_field);
        favouritesShopsListField.setHasFixedSize(false);

        favoritesShopsList = new ArrayList<>();

        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.recycler_decoration_divider_transparent)));
        favouritesShopsListField.addItemDecoration(divider);

        favouritesShopsAdapter = new FavouritesShopsAdapter(requireActivity(), favoritesShopsList, callBackListener);
        favouritesShopsListField.setAdapter(favouritesShopsAdapter);
    }

    private final CallBackInterface callBackListener = pObject -> {
          if(pObject instanceof Integer) {

          }
    };

    private void getFavouritesListData() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            favoritesShopsList.clear();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<ShopWiseWishListResponseModel> call = customerProductsService.getShowShopWiseWishListData(clientID, MyProfile.getInstance().getUserID());
            call.enqueue(new Callback<ShopWiseWishListResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<ShopWiseWishListResponseModel> call, @NotNull Response<ShopWiseWishListResponseModel> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        ShopWiseWishListResponseModel body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                List<ShopWiseWishListResponseDetails> shopWiseCartList = body.getShopWiseWishListDataResponse().getShopWiseWishListResponseDetailsList();
                                if (shopWiseCartList != null && !shopWiseCartList.isEmpty()) {
                                    favoritesShopsList.addAll(shopWiseCartList);
                                    setAdapter(body.getMsg());
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
                        showCloseDialog(null, getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ShopWiseWishListResponseModel> call, @NotNull Throwable t) {
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

    private void setAdapter(String message) {
        if (!favoritesShopsList.isEmpty()) {
            favouritesShopsAdapter.updateItems(favoritesShopsList);
            favouritesShopsAdapter.notifyDataSetChanged();
        } else {
            showCloseDialog(null, message);
        }
    }
}
