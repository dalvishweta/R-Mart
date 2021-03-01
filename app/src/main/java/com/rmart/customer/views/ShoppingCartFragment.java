package com.rmart.customer.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.adapters.ShoppingCartAdapter;
import com.rmart.customer.models.ShoppingCartResponse;
import com.rmart.customer.models.ShoppingCartResponseDetails;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RecyclerTouchListener;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
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

    private List<ShoppingCartResponseDetails> shopWiseCartList;
    private ShoppingCartResponseDetails selectedShoppingCartDetails;
    private OnCustomerHomeInteractionListener onCustomerHomeInteractionListener;
    private ShoppingCartAdapter shoppingCartAdapter;

    public static ShoppingCartFragment getInstance() {
        ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
        Bundle extras = new Bundle();
        shoppingCartFragment.setArguments(extras);
        return shoppingCartFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LoggerInfo.printLog("Fragment", "ShoppingCartFragment");
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
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
        updateToolBar();
    }

    public void updateToolBar() {
        Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.shopping_cart));
        if (requireActivity() instanceof CustomerHomeActivity) {
            ((CustomerHomeActivity) (requireActivity())).hideCartIcon();
        }
        getShopWiseCartList();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        RecyclerView shoppingCartListField = view.findViewById(R.id.shopping_cart_list_field);
        shoppingCartListField.setHasFixedSize(false);

        shopWiseCartList = new ArrayList<>();

        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.recycler_decoration_divider_transparent)));
        shoppingCartListField.addItemDecoration(divider);

        view.findViewById(R.id.btn_proceed_to_buy_field).setOnClickListener(v -> proceedToBuySelected());

        shoppingCartListField.addOnItemTouchListener(new RecyclerTouchListener(requireActivity(), "", shoppingCartListField, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                selectedShoppingCartDetails = shopWiseCartList.get(position);
                shopDetailsSelected();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        shoppingCartAdapter = new ShoppingCartAdapter(requireActivity(), shopWiseCartList);
        shoppingCartListField.setAdapter(shoppingCartAdapter);
    }

    private void resetCart() {
        shopWiseCartList.clear();
        shoppingCartAdapter.updateItems(shopWiseCartList);
        shoppingCartAdapter.notifyDataSetChanged();
    }

    private void getShopWiseCartList() {
        resetCart();
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<ShoppingCartResponse> call = customerProductsService.getShoppingCartList(clientID, MyProfile.getInstance().getUserID(),MyProfile.getInstance().getRoleID());
            call.enqueue(new Callback<ShoppingCartResponse>() {
                @Override
                public void onResponse(@NotNull Call<ShoppingCartResponse> call, @NotNull Response<ShoppingCartResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        ShoppingCartResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                List<ShoppingCartResponseDetails> lShopWiseCartList = body.getShoppingCartResponse().getShopWiseCartDataList();
                                MyProfile.getInstance().getCartCount().setValue(lShopWiseCartList.size());
                                if (!lShopWiseCartList.isEmpty()) {
                                    shopWiseCartList.addAll(lShopWiseCartList);
                                    setAdapter();
                                } else {
                                    showCloseDialog(body.getMsg());
                                }
                            } else {
                                showCloseDialog(body.getMsg());
                            }
                        } else {
                            showCloseDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showCloseDialog(response.message());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ShoppingCartResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        } else {
            showCloseDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void showCloseDialog(String title, String message) {
        showDialog(title, message, pObject -> checkStack());
    }

    private void showCloseDialog(String message) {
        showDialog(message, pObject -> checkStack());
    }

    private void checkStack() {
        if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
            Intent intent = new Intent(requireActivity(), CustomerHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            requireActivity().finish();
        } else {
            requireActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }

    private void shopDetailsSelected() {
        onCustomerHomeInteractionListener.gotoShoppingCartDetails(selectedShoppingCartDetails);
    }

    private void proceedToBuySelected() {

    }

    private void setAdapter() {
        if (!shopWiseCartList.isEmpty()) {
            shoppingCartAdapter.updateItems(shopWiseCartList);
            shoppingCartAdapter.notifyDataSetChanged();
        } else {
            showCloseDialog(getString(R.string.cart_is_empty));
        }
    }
}
