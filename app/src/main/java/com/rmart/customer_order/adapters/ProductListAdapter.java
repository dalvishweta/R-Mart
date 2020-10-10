package com.rmart.customer_order.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.customer.models.CustomerOrderProductOrderedDetails;
import com.rmart.inventory.views.viewholders.ProductItemViewHolder;
import com.rmart.utilits.HttpsTrustManager;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.orders.Product;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductItemViewHolder> {

    private List<Object> productList;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader;

    public ProductListAdapter(Context context, List<Object> orderList) {
        this.productList = orderList;
        layoutInflater = LayoutInflater.from(context);
        imageLoader = RMartApplication.getInstance().getImageLoader();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        //this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = layoutInflater.inflate(R.layout.item_order_product_item, parent, false);
        return new ProductItemViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemViewHolder holder, int position) {
        Object dataObject = productList.get(position);
        if (dataObject instanceof Product) {
            Product productObject = (Product) dataObject;
            holder.price.setText(productObject.getPrice());
            holder.productName.setText(productObject.getProductName());
            holder.quantity.setText(String.valueOf(productObject.getQuantity()));
            String unitsDetails = String.format("%s %s", productObject.getUnit(), productObject.getUnitMeasure());
            holder.units.setText(unitsDetails);
            String imageUrl = productObject.getDisplayImage();
            if (!TextUtils.isEmpty(imageUrl)) {
                HttpsTrustManager.allowAllSSL();
                imageLoader.get(imageUrl, ImageLoader.getImageListener(holder.imageView,
                        R.mipmap.ic_launcher, android.R.drawable
                                .ic_dialog_alert));
                holder.imageView.setImageUrl(imageUrl, RMartApplication.getInstance().getImageLoader());
            }
        } else if (dataObject instanceof CustomerOrderProductOrderedDetails) {
            CustomerOrderProductOrderedDetails productObject = (CustomerOrderProductOrderedDetails) dataObject;
            holder.price.setText(productObject.getTotalSellingPrice());
            holder.productName.setText(productObject.getProductName());
            holder.units.setText(productObject.getUnitNumber());
            holder.quantity.setText(productObject.getTotalProductCartQty());
            String unitsDetails = String.format("%s %s", productObject.getUnitNumber(), productObject.getUnitMeasure());
            holder.units.setText(unitsDetails);
            String imageUrl = productObject.getProductImage();
            if (!TextUtils.isEmpty(imageUrl)) {
                HttpsTrustManager.allowAllSSL();
                imageLoader.get(imageUrl, ImageLoader.getImageListener(holder.imageView,
                        R.mipmap.ic_launcher, android.R.drawable
                                .ic_dialog_alert));
                holder.imageView.setImageUrl(imageUrl, RMartApplication.getInstance().getImageLoader());
            }
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
