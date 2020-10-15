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
import com.rmart.utilits.Utils;

import java.util.List;
import java.util.Locale;

/**
 * Created by Satya Seshu on 12/09/20.
 */
public class ConfirmOrdersAdapter extends RecyclerView.Adapter<ConfirmOrdersAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<ProductInCartDetailsModel> listData;
    private final CallBackInterface callBackListener;
    private final ImageLoader imageLoader;

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
        String productImageUrl = dataObject.getProductImage();
        int totalProductCartQuantity = dataObject.getTotalProductCartQty();
        holder.tvNoOfQuantityField.setText(String.valueOf(totalProductCartQuantity));

        int totalUnitNumbers = totalProductCartQuantity * dataObject.getUnitNumber();
        String quantityDetails = String.format(Locale.getDefault(), "%d %s", totalUnitNumbers, dataObject.getShortUnitMeasure());
        double totalSellingPrice = totalProductCartQuantity * dataObject.getPerProductSellingPrice();
        String sellingPrice = String.format("Rs. %s", Utils.roundOffDoubleValue(totalSellingPrice));

        Double totalUnitPrice = totalProductCartQuantity * dataObject.getPerProductUnitPrice();
        String unitPriceDetails = String.valueOf(totalUnitPrice);
        String quantityPriceDetails = String.format("%s  %s  %s", quantityDetails, sellingPrice, unitPriceDetails);
        SpannableString quantityPriceDetailsSpannable = new SpannableString(quantityPriceDetails);
        quantityPriceDetailsSpannable.setSpan(new StyleSpan(Typeface.BOLD), quantityPriceDetails.indexOf(sellingPrice),
                quantityPriceDetails.indexOf(sellingPrice) + sellingPrice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        quantityPriceDetailsSpannable.setSpan(new StrikethroughSpan(), quantityPriceDetails.indexOf(unitPriceDetails),
                quantityPriceDetails.indexOf(unitPriceDetails) + unitPriceDetails.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvQuantityPriceDetailsField.setText(quantityPriceDetailsSpannable);

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
        holder.btnDeleteProductField.setTag(position);
        holder.btnDeleteProductField.setOnClickListener(v -> {
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
        Button btnDeleteProductField;
        NetworkImageView ivProductImageField;
        TextView tvProductNameField;
        Button btnMinusField;
        Button btnPlusField;
        TextView tvNoOfQuantityField;
        TextView tvQuantityPriceDetailsField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImageField = itemView.findViewById(R.id.iv_product_image_field);
            tvProductNameField = itemView.findViewById(R.id.tv_product_name_field);
            btnMinusField = itemView.findViewById(R.id.btn_minus_field);
            tvNoOfQuantityField = itemView.findViewById(R.id.tv_no_of_quantity_field);
            btnPlusField = itemView.findViewById(R.id.btn_add_field);
            btnMoveToWishListField = itemView.findViewById(R.id.btn_move_to_wish_list_field);
            btnDeleteProductField = itemView.findViewById(R.id.btn_delete_product_field);
            tvQuantityPriceDetailsField = itemView.findViewById(R.id.tv_quantity_price_details_field);
        }
    }
}
