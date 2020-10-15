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
    private String quantityText;
    private String unitText;
    private String costText;

    public ProductListAdapter(Context context, List<Object> orderList) {
        this.productList = orderList;
        layoutInflater = LayoutInflater.from(context);
        imageLoader = RMartApplication.getInstance().getImageLoader();
        quantityText = context.getString(R.string.quantity);
        unitText = context.getString(R.string.unit);
        costText = context.getString(R.string.cost);
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

            holder.productName.setText(productObject.getProductName());

            String unitsDetails = String.format("%s %s", productObject.getUnit(), productObject.getUnitMeasure());
            String imageUrl = productObject.getDisplayImage();
            if (!TextUtils.isEmpty(imageUrl)) {
                HttpsTrustManager.allowAllSSL();
                imageLoader.get(imageUrl, ImageLoader.getImageListener(holder.imageView,
                        R.mipmap.ic_launcher, android.R.drawable
                                .ic_dialog_alert));
                holder.imageView.setImageUrl(imageUrl, RMartApplication.getInstance().getImageLoader());
            }

            /*holder.price.setText(Utils.roundOffDoubleValue(productObject.getPrice()));
            holder.quantity.setText(String.valueOf(productObject.getQuantity()));
            holder.units.setText(unitsDetails);*/

            String productQuantityPriceDetails = String.format("%s%s  %s%s  \n%s%s", quantityText, productObject.getQuantity(), unitText, unitsDetails,
                    costText, Utils.roundOffDoubleValue(productObject.getPrice()));
            holder.tvProductPriceQuantityDetailsField.setText(productQuantityPriceDetails);

        } else if (dataObject instanceof CustomerOrderProductOrderedDetails) {
            CustomerOrderProductOrderedDetails productObject = (CustomerOrderProductOrderedDetails) dataObject;
            holder.productName.setText(productObject.getProductName());
            String unitsDetails = String.format("%s %s", productObject.getUnitNumber(), productObject.getUnitMeasure());
            String imageUrl = productObject.getProductImage();
            if (!TextUtils.isEmpty(imageUrl)) {
                HttpsTrustManager.allowAllSSL();
                imageLoader.get(imageUrl, ImageLoader.getImageListener(holder.imageView,
                        R.mipmap.ic_launcher, android.R.drawable
                                .ic_dialog_alert));
                holder.imageView.setImageUrl(imageUrl, RMartApplication.getInstance().getImageLoader());
            }

            /*holder.price.setText();
            holder.quantity.setText(productObject.getTotalProductCartQty());
            holder.units.setText(unitsDetails);*/

            String productQuantityPriceDetails = String.format("%s%s  %s%s  %s%s", quantityText, productObject.getTotalProductCartQty(), unitText, unitsDetails,
                    costText, Utils.roundOffDoubleValue(productObject.getTotalSellingPrice()));
            holder.tvProductPriceQuantityDetailsField.setText(productQuantityPriceDetails);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
