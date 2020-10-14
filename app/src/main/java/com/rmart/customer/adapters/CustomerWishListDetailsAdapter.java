package com.rmart.customer.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.customer.models.ShopWiseWishListResponseDetails;
import com.rmart.customer.models.WishListResponseDetails;
import com.rmart.customer.models.WishListResponseModel;
import com.rmart.utilits.HttpsTrustManager;
import com.rmart.utilits.Utils;

import java.util.List;

/**
 * Created by Satya Seshu on 17/09/20.
 */
public class CustomerWishListDetailsAdapter extends RecyclerView.Adapter<CustomerWishListDetailsAdapter.ViewHolder> {

    private List<WishListResponseDetails> listData;
    private ImageLoader imageLoader;
    private LayoutInflater layoutInflater;
    private CallBackInterface callBackListener;

    public CustomerWishListDetailsAdapter(Context context, List<WishListResponseDetails> listData, CallBackInterface callBackListener) {
        layoutInflater = LayoutInflater.from(context);
        this.listData = listData;
        imageLoader = RMartApplication.getInstance().getImageLoader();
        this.callBackListener = callBackListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.customer_wish_list_details_items_list, parent, false);
        return new ViewHolder(itemView);
    }

    public void updateItems(List<WishListResponseDetails> listData) {
        this.listData = listData;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WishListResponseDetails dataObject = listData.get(position);
        String productImageUrl = dataObject.getProductImage();
        if (!TextUtils.isEmpty(productImageUrl)) {
            HttpsTrustManager.allowAllSSL();
            imageLoader.get(productImageUrl, ImageLoader.getImageListener(holder.ivProductImageField,
                    R.mipmap.ic_launcher, android.R.drawable
                            .ic_dialog_alert));
            holder.ivProductImageField.setImageUrl(productImageUrl, imageLoader);
        }
        holder.tvProductNameField.setText(dataObject.getProductName());
        List<CustomerProductsDetailsUnitModel> unitsList = dataObject.getUnits();
        if (unitsList != null && !unitsList.isEmpty()) {
            CustomerProductsDetailsUnitModel unitModelDetails = unitsList.get(0);
            String quantityDetails = String.format("%s %s", unitModelDetails.getUnitNumber(), unitModelDetails.getShortUnitMeasure());
            String sellingPrice = String.format("Rs.%s", Utils.roundOffDoubleValue(unitModelDetails.getSellingPrice()));
            String unitPriceDetails = Utils.roundOffDoubleValue(unitModelDetails.getUnitPrice());

            String quantityPriceDetails = String.format("%s  %s  %s", quantityDetails, sellingPrice, unitPriceDetails);
            SpannableString quantityPriceDetailsSpannable = new SpannableString(quantityPriceDetails);
            quantityPriceDetailsSpannable.setSpan(new StyleSpan(Typeface.BOLD), quantityPriceDetails.indexOf(sellingPrice),
                    quantityPriceDetails.indexOf(sellingPrice) + sellingPrice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            quantityPriceDetailsSpannable.setSpan(new StrikethroughSpan(), quantityPriceDetails.indexOf(unitPriceDetails),
                    quantityPriceDetails.indexOf(unitPriceDetails) + unitPriceDetails.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvQuantityPriceDetailsField.setText(quantityPriceDetailsSpannable);
        }
        holder.tvProductDiscountField.setText("");
        holder.btnRemoveField.setTag(position);
        holder.btnAddToCartField.setTag(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        NetworkImageView ivProductImageField;
        TextView tvProductNameField;
        TextView tvProductDiscountField;
        AppCompatButton btnAddToCartField;
        AppCompatButton btnRemoveField;
        TextView tvQuantityPriceDetailsField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImageField = itemView.findViewById(R.id.iv_product_image_field);
            tvProductNameField = itemView.findViewById(R.id.tv_product_name_field);
            tvProductDiscountField = itemView.findViewById(R.id.tv_product_discount_field);
            btnAddToCartField = itemView.findViewById(R.id.btn_add_to_cart_field);
            btnRemoveField = itemView.findViewById(R.id.btn_remove_product_field);
            tvQuantityPriceDetailsField = itemView.findViewById(R.id.tv_quantity_price_details_field);

            btnAddToCartField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                WishListResponseDetails selectedDetails = listData.get(tag);
                ContentModel contentModel = new ContentModel();
                contentModel.setStatus(Constants.TAG_ADD_TO_CART);
                contentModel.setValue(selectedDetails);
                callBackListener.callBackReceived(contentModel);
            });

            btnRemoveField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                WishListResponseDetails selectedDetails = listData.get(tag);
                ContentModel contentModel = new ContentModel();
                contentModel.setStatus(Constants.TAG_REMOVE);
                contentModel.setValue(selectedDetails);
                callBackListener.callBackReceived(contentModel);
            });
        }
    }
}
