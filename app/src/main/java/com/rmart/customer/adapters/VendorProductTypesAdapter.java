package com.rmart.customer.adapters;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.utilits.HttpsTrustManager;
import com.rmart.utilits.Utils;

import java.util.List;

/**
 * Created by Satya Seshu on 10/09/20.
 */
public class VendorProductTypesAdapter extends RecyclerView.Adapter<VendorProductTypesAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<CustomerProductDetailsModel> productTypesList;
    private ImageLoader imageLoader;

    public VendorProductTypesAdapter(Context context, List<CustomerProductDetailsModel> productTypesList) {
        layoutInflater = LayoutInflater.from(context);
        this.productTypesList = productTypesList;
        imageLoader = RMartApplication.getInstance().getImageLoader();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.vendor_product_types_list_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomerProductDetailsModel dataObject = productTypesList.get(position);
        String productImageUrl = dataObject.getProductImage();
        if (!TextUtils.isEmpty(productImageUrl)) {
            HttpsTrustManager.allowAllSSL();
            imageLoader.get(productImageUrl, ImageLoader.getImageListener(holder.ivProductImageField,
                    R.mipmap.ic_launcher, android.R.drawable
                            .ic_dialog_alert));
            holder.ivProductImageField.setImageUrl(productImageUrl, imageLoader);
        }
        holder.tvProductNameField.setText(dataObject.getProductName());

        List<CustomerProductsDetailsUnitModel>  unitsList = dataObject.getUnits();
        if(unitsList != null && !unitsList.isEmpty()) {
            CustomerProductsDetailsUnitModel unitModelDetails = unitsList.get(0);
            String quantityDetails = String.format("%s %s", unitModelDetails.getUnitNumber(), unitModelDetails.getShortUnitMeasure());
            String sellingPrice = String.format("Rs.%s", Utils.roundOffDoubleValue(unitModelDetails.getSellingPrice()));
            String unitPriceDetails = Utils.roundOffDoubleValue(unitModelDetails.getUnitPrice());
            int productDiscount = unitModelDetails.getProductDiscount();
            String productDiscountDetails = productDiscount + "% \n Off";
            holder.tvProductDiscountField.setText(productDiscountDetails);

            String quantityPriceDetails = String.format("%s  %s  %s", quantityDetails, sellingPrice, unitPriceDetails);
            SpannableString quantityPriceDetailsSpannable = new SpannableString(quantityPriceDetails);
            quantityPriceDetailsSpannable.setSpan(new StyleSpan(Typeface.BOLD), quantityPriceDetails.indexOf(sellingPrice),
                    quantityPriceDetails.indexOf(sellingPrice) + sellingPrice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            quantityPriceDetailsSpannable.setSpan(new StrikethroughSpan(), quantityPriceDetails.indexOf(unitPriceDetails),
                    quantityPriceDetails.indexOf(unitPriceDetails) + unitPriceDetails.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvQuantityPriceDetailsField.setText(quantityPriceDetailsSpannable);
        }
    }

    @Override
    public int getItemCount() {
        return productTypesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        NetworkImageView ivProductImageField;
        TextView tvProductNameField;
        TextView tvProductDiscountField;
        TextView tvQuantityPriceDetailsField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImageField = itemView.findViewById(R.id.iv_product_image_field);
            tvProductNameField = itemView.findViewById(R.id.tv_product_name_field);
            tvProductDiscountField = itemView.findViewById(R.id.tv_product_discount_field);
            tvQuantityPriceDetailsField = itemView.findViewById(R.id.tv_quantity_price_details_field);
        }
    }
}
