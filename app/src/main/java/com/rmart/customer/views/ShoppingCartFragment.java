package com.rmart.customer.views;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.adapters.ShoppingCartAdapter;
import com.rmart.customer.models.VendorProductDataResponse;
import com.rmart.customer.models.VendorProductDetailsResponse;
import com.rmart.customer.models.VendorProductShopDataResponse;
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
public class ShoppingCartFragment extends BaseFragment {

    private RecyclerView shoppingCartListField;
    private List<Object> shoppingCartList;

    public ShoppingCartFragment getInstance() {
        ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
        Bundle extras = new Bundle();

        shoppingCartFragment.setArguments(extras);
        return shoppingCartFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);

        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<BaseResponse> call = customerProductsService.getShoppingCartList();
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        BaseResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {

                                setAdapter();
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
                public void onFailure(@NotNull Call<BaseResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                }
            });
        } else {
            showCloseDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void loadUIComponents(View view) {
        shoppingCartListField = view.findViewById(R.id.shopping_cart_list_field);
        shoppingCartListField.setHasFixedSize(false);

        shoppingCartList = new ArrayList<>();

        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.recycler_decoration_divider_transparent)));
        shoppingCartListField.addItemDecoration(divider);
    }

    private void showCloseDialog(String title, String message) {
        showDialog(title, message, pObject -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void setAdapter() {
        if(!shoppingCartList.isEmpty()) {
            ShoppingCartAdapter shoppingCartAdapter = new ShoppingCartAdapter(requireActivity(), shoppingCartList);
            shoppingCartListField.setAdapter(shoppingCartAdapter);
        }
    }
}
