package com.rmart.services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.rmart.baseclass.Constants;
import com.rmart.customer.models.AddToCartResponseDetails;
import com.rmart.customer.models.ProductInCartDetailsModel;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Satya Seshu on 19/09/20.
 */
public class UpdateProductQuantityServices extends JobIntentService {

    private ProductInCartDetailsModel productInCartDetails;

    public static void enqueueWork(Context context, ProductInCartDetailsModel productInCartDetails) {
        Intent intent = new Intent(context, UpdateProductQuantityServices.class);
        intent.putExtra("ProductCartDetails", productInCartDetails);
        enqueueWork(context, UpdateProductQuantityServices.class, Constants.JOB_INSTANT_MESSENGER, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            productInCartDetails = (ProductInCartDetailsModel) extras.getSerializable("ProductCartDetails");
        }
        updateProductQuantityDetails();
    }

    private void updateProductQuantityDetails() {
        CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
        String clientID = "2";
        Call<AddToCartResponseDetails> call = customerProductsService.addToCart(clientID, productInCartDetails.getVendorId(), MyProfile.getInstance().getUserID(),
                productInCartDetails.getProductUnitId(), productInCartDetails.getTotalProductCartQty());
        call.enqueue(new Callback<AddToCartResponseDetails>() {
            @Override
            public void onResponse(@NotNull Call<AddToCartResponseDetails> call, @NotNull Response<AddToCartResponseDetails> response) {
            
            }

            @Override
            public void onFailure(@NotNull Call<AddToCartResponseDetails> call, @NotNull Throwable t) {

            }
        });
    }
}
