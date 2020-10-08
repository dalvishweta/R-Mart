package com.rmart.customer.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
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
            holder.tvQuantityField.setText(quantityDetails);
            String sellingPrice = String.format("Rs.%s", unitModelDetails.getSellingPrice());
            holder.tvSellingPriceField.setText(sellingPrice);

            holder.tvTotalPriceField.setText(Utils.roundOffDoubleValue(unitModelDetails.getUnitPrice()));
            holder.tvTotalPriceField.setPaintFlags(holder.tvTotalPriceField.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            int productDiscount = unitModelDetails.getProductDiscount();
            String productDiscountDetails = productDiscount + "% \n Off";
            holder.tvProductDiscountField.setText(productDiscountDetails);
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
        TextView tvQuantityField;
        TextView tvSellingPriceField;
        TextView tvTotalPriceField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImageField = itemView.findViewById(R.id.iv_product_image_field);
            tvProductNameField = itemView.findViewById(R.id.tv_product_name_field);
            tvProductDiscountField = itemView.findViewById(R.id.tv_product_discount_field);
            tvQuantityField = itemView.findViewById(R.id.tv_quantity_field);
            tvSellingPriceField = itemView.findViewById(R.id.tv_selling_price_field);
            tvTotalPriceField = itemView.findViewById(R.id.tv_total_price_field);
        }
    }
}
