package com.rmart.customer.shops.list.models;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.R;
import com.rmart.glied.GlideApp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

/**
 * Created by Satya Seshu on 08/09/20.
 */
public class CustomerProductsShopDetailsModel implements Serializable {

    @SerializedName("shop_mobile_no")
    @Expose
    private String shopMobileNo;

    public String getBusiness_type() {
        return business_type;
    }

    @SerializedName("business_type")
    @Expose
    private String business_type;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("shop_image")
    @Expose
    private Object shopImage;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shop_del_chrg")
    @Expose
    private String shopDeliveryCharges;
    @SerializedName("min_order_amt")
    @Expose
    private Integer minOrderAmt;
    @SerializedName("delivery_days")
    @Expose
    private String deliveryDays;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("shop_wishlist_status")
    @Expose
    private Integer shopWishListStatus = 0;
    @SerializedName("shop_wishlist_id")
    @Expose
    private Integer shopWishListId = -1;
    @SerializedName("latitude")
    @Expose
    private Double latitude;

    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("email_id")
    @Expose
    private String emailId;

    @SerializedName("shop_latitude")
    @Expose
    private Double shopLatitude;

    @SerializedName("shop_longitude")
    @Expose
    private Double shopLongitude;

    @SerializedName("delivery_days_after_time")
    @Expose
    private String deliveryDaysAfterTime;

    @SerializedName("delivery_days_before_time")
    @Expose
    private String deliveryDaysBeforeTime;
    @SerializedName("closing_time")
    @Expose
    private String closingTime;
    @SerializedName("opening_time")
    @Expose
    private String openingTime;

    @SerializedName("delivery_charges")
    @Expose
    private Integer deliveryCharges;
    @SerializedName("delivery_method")
    @Expose
    public String deliveryMethod;

    public Integer getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(Integer deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getShopMobileNo() {
        return shopMobileNo;
    }

    public void setShopMobileNo(String shopMobileNo) {
        this.shopMobileNo = shopMobileNo;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Object getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDeliveryCharges() {
        return shopDeliveryCharges;
    }

    public void setShopDeliveryCharges(String shopDeliveryCharges) {
        this.shopDeliveryCharges = shopDeliveryCharges;
    }

    public Integer getMinOrderAmt() {
        return minOrderAmt;
    }

    public void setMinOrderAmt(Integer minOrderAmt) {
        this.minOrderAmt = minOrderAmt;
    }

    public String getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(String deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getShopWishListStatus() {
        return shopWishListStatus;
    }

    public void setShopWishListStatus(Integer shopWishListStatus) {
        this.shopWishListStatus = shopWishListStatus;
    }

    public Integer getShopWishListId() {
        return shopWishListId;
    }

    public void setShopWishListId(Integer shopWishListId) {
        this.shopWishListId = shopWishListId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Double getShopLatitude() {
        return shopLatitude;
    }

    public void setShopLatitude(Double shopLatitude) {
        this.shopLatitude = shopLatitude;
    }

    public Double getShopLongitude() {
        return shopLongitude;
    }

    public void setShopLongitude(Double shopLongitude) {
        this.shopLongitude = shopLongitude;
    }

    public String getDeliveryDaysAfterTime() {
        return deliveryDaysAfterTime;
    }

    public void setDeliveryDaysAfterTime(String deliveryDaysAfterTime) {
        this.deliveryDaysAfterTime = deliveryDaysAfterTime;
    }

    public String getDeliveryDaysBeforeTime() {
        return deliveryDaysBeforeTime;
    }

    public void setDeliveryDaysBeforeTime(String deliveryDaysBeforeTime) {
        this.deliveryDaysBeforeTime = deliveryDaysBeforeTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    @NotNull
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("shopId", shopId).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(shopId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CustomerProductsShopDetailsModel)) {
            return false;
        }
        CustomerProductsShopDetailsModel rhs = ((CustomerProductsShopDetailsModel) other);
        return new EqualsBuilder().append(shopId, rhs.shopId).isEquals();
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(View view, CustomerProductsShopDetailsModel data) {

        ImageView imageview = view.findViewById(R.id.imageview);
        ImageView selectedgreeting = view.findViewById(R.id.selectedgreeting);
        selectedgreeting.setVisibility(View.VISIBLE);
        GlideApp.with(view.getContext()).load(data.getShopImage()) .listener(new RequestListener<Drawable>() {

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                selectedgreeting.setVisibility(View.GONE);

                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                selectedgreeting.setVisibility(View.GONE);
                return false;
            }
        }).dontAnimate().
                diskCacheStrategy(DiskCacheStrategy.ALL).
                signature(new ObjectKey(data.getShopImage())).
                error(R.mipmap.applogo).thumbnail(0.5f).into(imageview);
    }
}
