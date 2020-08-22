package com.rmart.inventory.adapters;

import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.models.Product;
import com.rmart.inventory.views.viewholders.ProductViewHolder;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    View.OnClickListener onClickListener;
    ArrayList<Product> productList;
    int columnCount;
    public ProductAdapter(ArrayList<Product> productList, View.OnClickListener onClickListener, int columnCount) {
        this.onClickListener =onClickListener;
        this.productList = productList;
        this.columnCount = columnCount;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        listItem= layoutInflater.inflate(R.layout.grid_product_layout, parent, false);
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
        Product product = productList.get(position);
        holder.tvItemTitle.setText(product.getName());
        holder.itemView.setTag(product);
        if (product.getUnitObjects().size() >0) {
            holder.tvActual.setText(Html.fromHtml("<strike> " + product.getUnitObjects().get(0).getActualCost()+" </strike>"));
            holder.tvFinalCost.setText(product.getUnitObjects().get(0).getFinalCost());
            holder.tvUnitValue.setText(product.getUnitObjects().get(0).getUnitValue());
            holder.tvOffer.setText(String.format(holder.itemView.getContext().getString(R.string.offer), product.getUnitObjects().get(0).getDiscount()+"%"));
        }
        if(product.getUnitObjects().size()>1) {
            holder.availableUnits. setVisibility(View.VISIBLE);
            holder.availableUnits. setText(String.format(holder.itemView.getContext().getString(R.string.available_other_sizes), product.getUnitObjects().size()+""));
        } else {
            holder.availableUnits. setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
