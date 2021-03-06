package com.rmart.inventory.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.glied.GlideApp;
import com.rmart.inventory.views.viewholders.ProductViewHolder;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.ProductResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> implements Filterable {

    private final View.OnClickListener onClickListener;
    private ArrayList<ProductResponse> productList;
    private List<ProductResponse> filteredListData;
    private MyFilter myFilter;
    private final int columnCount;
    private final ImageLoader imageLoader;
    private final LayoutInflater layoutInflater;

    public ProductAdapter(Context context, ArrayList<ProductResponse> productList, View.OnClickListener onClickListener, int columnCount) {
        this.onClickListener = onClickListener;
        this.productList = productList;
        this.columnCount = columnCount;
        this.filteredListData = new ArrayList<>();
        this.filteredListData.addAll(productList);
        imageLoader = RMartApplication.getInstance().getImageLoader();
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateItems(ArrayList<ProductResponse> productList) {
        this.productList = productList;
        filteredListData.clear();
        filteredListData.addAll(productList);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem= layoutInflater.inflate(R.layout.grid_product_layout, parent, false);
        listItem.setOnClickListener(onClickListener);
        ProductViewHolder productViewHolder = new ProductViewHolder(listItem);
        if(columnCount == 3) {
            productViewHolder.tvItemTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            productViewHolder.availableUnits.setVisibility(View.GONE);
            productViewHolder.availableUnits.setVisibility(View.GONE);
            listItem.findViewById(R.id.row2).setVisibility(View.GONE);
        } else {
            productViewHolder.tvItemTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            productViewHolder.availableUnits.setVisibility(View.VISIBLE);
            productViewHolder.availableUnits.setVisibility(View.VISIBLE);
            listItem.findViewById(R.id.row2).setVisibility(View.VISIBLE);
        }
        return  productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductResponse product = filteredListData.get(position);
        holder.itemView.setTag(product);
        if (null != product.getUnitObjects() && product.getUnitObjects().size() > 1) {
            holder.availableUnits.setVisibility(View.VISIBLE);
            holder.unitView.setVisibility(View.VISIBLE);
            holder.tvOffer.setVisibility(View.GONE);
            try {
                int discount = Integer.parseInt(product.getUnitObjects().get(0).getDiscount());
                if (discount > 0) {
                    holder.tvOffer.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.availableUnits.setText(String.format(holder.itemView.getContext().getString(R.string.available_other_sizes), product.getUnitObjects().size() + ""));
            //holder.tvActual.setText(Html.fromHtml("<strike> " + product.getUnitObjects().get(0).getActualCost()+" </strike>"));
            holder.tvActual.setText(HtmlCompat.fromHtml("<strike> " + product.getUnitObjects().get(0).getActualCost() + " </strike>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            holder.tvFinalCost.setText(product.getUnitObjects().get(0).getFinalCost());
            holder.tvUnitValue.setText(product.getUnitObjects().get(0).getUnitNumber());
            holder.tvOffer.setText(String.format(holder.itemView.getContext().getString(R.string.offer), product.getUnitObjects().get(0).getDiscount() + "%"));
        } else {
            holder.availableUnits.setVisibility(View.GONE);
            holder.unitView.setVisibility(View.GONE);
            holder.tvOffer.setVisibility(View.GONE);
        }
        String imageUrl;
        if (product.getType().equalsIgnoreCase(Utils.PRODUCT)) {
            imageUrl = product.getDisplayImage();
            holder.tvItemTitle.setText(product.getProductName());
        } else {
            imageUrl = product.getImage();
            holder.tvItemTitle.setText(product.getName());
        }

        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = product.getProductImage();
        }

        /*holder.itemImg.setVisibility(View.GONE);
        holder.progressLayoutField.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(imageUrl)) {
            imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    Bitmap bitmap = response.getBitmap();
                    if (bitmap != null) {
                        holder.itemImg.setLocalImageBitmap(bitmap);
                    }
                    holder.itemImg.setVisibility(View.VISIBLE);
                    holder.progressLayoutField.setVisibility(View.GONE);
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    holder.progressLayoutField.setVisibility(View.GONE);
                    holder.itemImg.setVisibility(View.VISIBLE);
                    holder.itemImg.setBackgroundResource(android.R.drawable.ic_dialog_alert);
                }
            });
        } else {
            holder.progressLayoutField.setVisibility(View.GONE);
            holder.itemImg.setVisibility(View.VISIBLE);
            holder.itemImg.setBackgroundResource(android.R.drawable.ic_dialog_alert);
        }*/


        holder.selectedgreeting.setVisibility(View.VISIBLE);
        GlideApp.with(holder.imageView.getContext()).load(imageUrl) .listener(new RequestListener<Drawable>() {

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.selectedgreeting.setVisibility(View.GONE);

                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.selectedgreeting.setVisibility(View.GONE);
                return false;
            }
        }).dontAnimate().
                diskCacheStrategy(DiskCacheStrategy.ALL).
                signature(new ObjectKey(imageUrl!=null?imageUrl:"")).
                error(R.mipmap.default_product_image).thumbnail(0.5f).into(holder.imageView);

//        holder.itemImg.setVisibility(View.GONE);
//        holder.progressLayoutField.setVisibility(View.VISIBLE);
//
//        if (!TextUtils.isEmpty(imageUrl)) {
//            imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
//                @Override
//                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                    Bitmap bitmap = response.getBitmap();
//                    if (bitmap != null) {
//                        holder.itemImg.setLocalImageBitmap(bitmap);
//                    }
//                    holder.itemImg.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    holder.progressLayoutField.setVisibility(View.GONE);
//                    holder.itemImg.setVisibility(View.VISIBLE);
 //              holder.itemImg.setBackgroundResource(android.R.drawable.ic_dialog_alert);
//                }
//            });
//        } else {
//            holder.progressLayoutField.setVisibility(View.GONE);
//            holder.itemImg.setVisibility(View.VISIBLE);
//            holder.itemImg.setBackgroundResource(android.R.drawable.ic_dialog_alert);
//        }
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
                for (ProductResponse product : productList) {
                    String name;
                    if (product.getType().equalsIgnoreCase(Utils.PRODUCT)) {
                        name = product.getProductName();
                    } else {
                        name = product.getName();
                    }
                    if (name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredListData.add(product);
                    }
                }
            }
            results.count = filteredListData.size();
            results.values = filteredListData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredListData = (List<ProductResponse>) results.values;
            notifyDataSetChanged();
        }
    }
}
