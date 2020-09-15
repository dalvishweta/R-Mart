package com.rmart.customer.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.models.ProductInCartDetailsModel;
import com.rmart.utilits.HttpsTrustManager;

import java.util.List;

/**
 * Created by Satya Seshu on 12/09/20.
 */
public class ConfirmOrdersAdapter extends RecyclerView.Adapter<ConfirmOrdersAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<ProductInCartDetailsModel> listData;
    private CallBackInterface callBackListener;
    private ImageLoader imageLoader;

    public ConfirmOrdersAdapter(Context context, List<ProductInCartDetailsModel> listData, CallBackInterface callBackListener) {
        layoutInflater = LayoutInflater.from(context);
        this.listData = listData;
        this.callBackListener = callBackListener;
        imageLoader = RMartApplication.getInstance().getImageLoader();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.confirm_order_list_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductInCartDetailsModel dataObject = listData.get(position);
        holder.tvProductNameField.setText(dataObject.getProductName());
        holder.tvNoOfQuantityField.setText(String.valueOf(dataObject.getTotalProductCartQty()));
        String productImageUrl = dataObject.getProductImage();
        if (!TextUtils.isEmpty(productImageUrl)) {
            HttpsTrustManager.allowAllSSL();
            imageLoader.get(productImageUrl, ImageLoader.getImageListener(holder.ivProductImageField,
                    R.mipmap.ic_launcher, android.R.drawable
                            .ic_dialog_alert));
            holder.ivProductImageField.setImageUrl(productImageUrl, imageLoader);
        }
        holder.btnMoveToWishListField.setTag(position);
        holder.btnMoveToWishListField.setOnClickListener(v -> {
            int tag = (int) v.getTag();
            ContentModel contentModel = new ContentModel();
            contentModel.setStatus(Constants.TAG_MOVE_TO_WISH_LIST);
            contentModel.setValue(listData.get(tag));
            callBackListener.callBackReceived(contentModel);
        });
        holder.deleteProductField.setTag(position);
        holder.deleteProductField.setOnClickListener(v -> {
            int tag = (int) v.getTag();
            ContentModel contentModel = new ContentModel();
            contentModel.setStatus(Constants.TAG_DELETE);
            contentModel.setValue(listData.get(tag));
            callBackListener.callBackReceived(contentModel);
        });
        holder.btnMinusField.setTag(position);
        holder.btnMinusField.setOnClickListener(v -> {
            int tag = (int) v.getTag();
            ContentModel contentModel = new ContentModel();
            contentModel.setStatus(Constants.TAG_MINUS);
            contentModel.setValue(listData.get(tag));
            callBackListener.callBackReceived(contentModel);
        });
        holder.btnPlusField.setTag(position);
        holder.btnPlusField.setOnClickListener(v -> {
            int tag = (int) v.getTag();
            ContentModel contentModel = new ContentModel();
            contentModel.setStatus(Constants.TAG_PLUS);
            contentModel.setValue(listData.get(tag));
            callBackListener.callBackReceived(contentModel);
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        Button btnMoveToWishListField;
        Button deleteProductField;
        NetworkImageView ivProductImageField;
        TextView tvProductNameField;
        Button btnMinusField;
        Button btnPlusField;
        TextView tvNoOfQuantityField;
        TextView tvCurrentPriceField;
        TextView tvTotalPriceField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurrentPriceField = itemView.findViewById(R.id.tv_current_price_field);
            tvTotalPriceField = itemView.findViewById(R.id.tv_total_price_field);
            btnMinusField = itemView.findViewById(R.id.btn_minus_field);
            tvNoOfQuantityField = itemView.findViewById(R.id.tv_no_of_quantity_field);
            btnPlusField = itemView.findViewById(R.id.btn_add_field);
        }
    }
}
