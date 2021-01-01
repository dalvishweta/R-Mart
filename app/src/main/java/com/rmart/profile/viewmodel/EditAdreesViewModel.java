package com.rmart.profile.viewmodel;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.textfield.TextInputLayout;
import com.rmart.utilits.pojos.AddressResponse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class EditAdreesViewModel extends ViewModel {
    public EditAdreesViewModel() {
    }
    public MutableLiveData<AddressResponse> addressResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String>  errorShopNameStringMutableLiveData= new MutableLiveData<>();
    public MutableLiveData<String>  errorBanckAccountStringMutableLiveData= new MutableLiveData<>();
    public MutableLiveData<String>  errorBanckIFSCStringMutableLiveData= new MutableLiveData<>();
    public MutableLiveData<String> errorShopNoStringMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorShopActStringMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorgstDeliveryRadiusMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorgstDeliveryChargesMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorgstMinimumOrderMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorgstBeforeCloseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorgstsfterCloseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorAadharNoMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorPanNumberMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorStreetAdressMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorCityMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorDistrictMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorPinCodeMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorStateMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorgstCloseTimeMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorgstOpenTimeMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorgstNoStringMutableLiveData = new MutableLiveData<>();
    public  MutableLiveData<String> shopImageUrl = new MutableLiveData<>();
    public  MutableLiveData<String> aadharFrontImageUrl = new MutableLiveData<>();
    public MutableLiveData<String> aadharBackImageUrl = new MutableLiveData<>();
    public  MutableLiveData<String> panCardImageUrl = new MutableLiveData<>();
    @BindingAdapter("app:errorText")
    public static void setErrorMessage(TextInputLayout view, String errorMessage) {
        view.setError(errorMessage);
    }

    public  void loadImage(View view, String data,int ImageID,int lodaserID,int shopId,OnLoad onLoad) {
        ImageView imageview = view.findViewById(ImageID);
        ImageView selectedgreeting = view.findViewById(lodaserID);
        selectedgreeting.setVisibility(View.VISIBLE);


        Glide.with(view.getContext())
                .asBitmap()
                .load(data==null?"":data)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageview.setImageBitmap(resource);
                        selectedgreeting.setVisibility(View.GONE);
                        onLoad.onLoadImage(data);


                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        selectedgreeting.setVisibility(View.GONE);
                        imageview.setImageDrawable(view.getResources().getDrawable(shopId));

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        selectedgreeting.setVisibility(View.GONE);
                        imageview.setImageDrawable(view.getResources().getDrawable(shopId));

                    }

                });

    }

}
