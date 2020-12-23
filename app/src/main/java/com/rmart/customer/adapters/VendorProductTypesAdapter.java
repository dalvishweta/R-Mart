package com.rmart.customer.adapters;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.utilits.Utils;
import com.rmart.utilits.custom_views.CustomNetworkImageView;

import java.util.ArrayList;
import java.util.List;

public class VendorProductTypesAdapter extends RecyclerView.Adapter<VendorProductTypesAdapter.ViewHolder> implements Filterable {

    private final LayoutInflater layoutInflater;
    private List<CustomerProductDetailsModel> productsList;
    private final ImageLoader imageLoader;
    private MyFilter myFilter;
    private List<CustomerProductDetailsModel> filteredListData;
    private final CallBackInterface callBackListener;

    public VendorProductTypesAdapter(Context context, List<CustomerProductDetailsModel> productsList, CallBackInterface callBackListener) {
        layoutInflater = LayoutInflater.from(context);
        this.productsList = productsList;
        imageLoader = RMartApplication.getInstance().getImageLoader();
        filteredListData = new ArrayList<>();
        this.filteredListData.addAll(productsList);
        this.callBackListener = callBackListener;
    }

    public void updateItems(List<CustomerProductDetailsModel> productsList) {
        this.productsList = productsList;
        this.filteredListData.clear();
        this.filteredListData.addAll(productsList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.vendor_product_types_list_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        CustomerProductDetailsModel dataObject = filteredListData.get(position);
        String productImageUrl = dataObject.getProductImage();
        if (!TextUtils.isEmpty(productImageUrl)) {
            imageLoader.get(productImageUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    Bitmap bitmap = response.getBitmap();
                    if (bitmap != null) {
                        holder.ivProductImageField.setLocalImageBitmap(bitmap);
                    }
                    holder.progressBarLayout.setVisibility(View.GONE);
                    holder.ivProductImageField.setVisibility(View.VISIBLE);
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    holder.progressBarLayout.setVisibility(View.GONE);
                    holder.ivProductImageField.setVisibility(View.VISIBLE);
                    holder.ivProductImageField.setBackgroundResource(android.R.drawable.ic_dialog_alert);
                }
            });
        } else {
            holder.progressBarLayout.setVisibility(View.GONE);
            holder.ivProductImageField.setVisibility(View.VISIBLE);
            holder.ivProductImageField.setBackgroundResource(android.R.drawable.ic_dialog_alert);
        }
        holder.tvProductNameField.setText(dataObject.getProductName());

        List<CustomerProductsDetailsUnitModel>  unitsList = dataObject.getUnits();
        if(unitsList != null && !unitsList.isEmpty()) {

            for (int i = unitsList.size()-1;i>=0;i-- ) {

                CustomerProductsDetailsUnitModel unitModelDetails = unitsList.get(i);
                String quantityDetails = String.format("%s %s", unitModelDetails.getUnitNumber(), unitModelDetails.getShortUnitMeasure());
                String sellingPrice = String.format("Rs.%s", Utils.roundOffDoubleValue(unitModelDetails.getSellingPrice(), "0.00"));
                String unitPriceDetails = Utils.roundOffDoubleValue(unitModelDetails.getUnitPrice(), "0.00");

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
                if(productDiscount>0){
                    break;
                }
            }
        }
    }
    @Override
    public int getItemCount() {
        return filteredListData.size();
    }
    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
        return myFilter;
    }
    private class MyFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                filteredListData.clear();
                for (CustomerProductDetailsModel productDetails : productsList) {
                    if (productDetails.getProductName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredListData.add(productDetails);
                    }
                }
            }
            results.count = filteredListData.size();
            results.values = filteredListData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredListData = (List<CustomerProductDetailsModel>) results.values;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CustomNetworkImageView ivProductImageField;
        TextView tvProductNameField;
        TextView tvProductDiscountField;
        TextView tvQuantityPriceDetailsField;
        LinearLayout progressBarLayout;
        View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivProductImageField = itemView.findViewById(R.id.iv_product_image_field);
            tvProductNameField = itemView.findViewById(R.id.tv_product_name_field);
            tvProductDiscountField = itemView.findViewById(R.id.tv_product_discount_field);
            tvQuantityPriceDetailsField = itemView.findViewById(R.id.tv_quantity_price_details_field);
            progressBarLayout = itemView.findViewById(R.id.progress_layout_field);
            itemView.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                CustomerProductDetailsModel selectedProductDetails = filteredListData.get(tag);
                callBackListener.callBackReceived(selectedProductDetails);
            });
        }
    }
}