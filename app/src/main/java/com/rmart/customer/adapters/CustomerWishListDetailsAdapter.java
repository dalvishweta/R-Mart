package com.rmart.customer.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
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
import com.rmart.utilits.HttpsTrustManager;

import java.util.List;

/**
 * Created by Satya Seshu on 17/09/20.
 */
public class CustomerWishListDetailsAdapter extends RecyclerView.Adapter<CustomerWishListDetailsAdapter.ViewHolder> {

    private List<ShopWiseWishListResponseDetails> listData;
    private ImageLoader imageLoader;
    private LayoutInflater layoutInflater;
    private CallBackInterface callBackListener;

    public CustomerWishListDetailsAdapter(Context context, List<ShopWiseWishListResponseDetails> listData, CallBackInterface callBackListener) {
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

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ShopWiseWishListResponseDetails dataObject = listData.get(position);
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
            holder.tvQuantityField.setText(quantityDetails);
            String sellingPrice = String.format("Rs.%s", unitModelDetails.getSellingPrice());
            holder.tvSellingPriceField.setText(sellingPrice);

            holder.tvTotalPriceField.setText(unitModelDetails.getUnitPrice());
            holder.tvTotalPriceField.setPaintFlags(holder.tvTotalPriceField.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.tvProductDiscountField.setText("");
        holder.btnRemoveField.setTag(position);
        holder.btnAddToCartField.setTag(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        NetworkImageView ivProductImageField;
        TextView tvProductNameField;
        TextView tvProductDiscountField;
        TextView tvQuantityField;
        TextView tvSellingPriceField;
        TextView tvTotalPriceField;
        AppCompatButton btnAddToCartField;
        AppCompatButton btnRemoveField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImageField = itemView.findViewById(R.id.iv_product_image_field);
            tvProductNameField = itemView.findViewById(R.id.tv_product_name_field);
            tvProductDiscountField = itemView.findViewById(R.id.tv_product_discount_field);
            tvQuantityField = itemView.findViewById(R.id.tv_quantity_field);
            tvSellingPriceField = itemView.findViewById(R.id.tv_selling_price_field);
            tvTotalPriceField = itemView.findViewById(R.id.tv_total_price_field);
            btnAddToCartField = itemView.findViewById(R.id.btn_add_to_cart_field);
            btnRemoveField = itemView.findViewById(R.id.btn_remove_product_field);

            btnAddToCartField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                ShopWiseWishListResponseDetails selectedDetails = listData.get(tag);
                ContentModel contentModel = new ContentModel();
                contentModel.setStatus(Constants.TAG_ADD_TO_CART);
                contentModel.setValue(selectedDetails);
                callBackListener.callBackReceived(contentModel);
            });

            btnRemoveField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                ShopWiseWishListResponseDetails selectedDetails = listData.get(tag);
                ContentModel contentModel = new ContentModel();
                contentModel.setStatus(Constants.TAG_REMOVE);
                contentModel.setValue(selectedDetails);
                callBackListener.callBackReceived(contentModel);
            });
        }
    }
}
