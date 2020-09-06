package com.rmart.inventory.adapters;

import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.models.Product;
import com.rmart.inventory.views.viewholders.ProductViewHolder;
import com.rmart.utilits.pojos.ProductResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>  implements Filterable {

    View.OnClickListener onClickListener;
    ArrayList<ProductResponse> productList;
    private List<ProductResponse> filteredListData;
    private MyFilter myFilter;
    int columnCount;

    public ProductAdapter(ArrayList<ProductResponse> productList, View.OnClickListener onClickListener, int columnCount) {
        this.onClickListener =onClickListener;
        this.productList = productList;
        this.columnCount = columnCount;
        this.filteredListData = new ArrayList<>();
        this.filteredListData.addAll(productList);
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
        ProductResponse product = filteredListData.get(position);
        holder.tvItemTitle.setText(product.getName());
        holder.itemView.setTag(product);
        if(null != product.getUnitObjects() && product.getUnitObjects().size()>1) {
            holder.availableUnits. setVisibility(View.VISIBLE);
            holder.availableUnits. setText(String.format(holder.itemView.getContext().getString(R.string.available_other_sizes), product.getUnitObjects().size()+""));

            holder.tvActual.setText(Html.fromHtml("<strike> " + product.getUnitObjects().get(0).getActualCost()+" </strike>"));
            holder.tvFinalCost.setText(product.getUnitObjects().get(0).getFinalCost());
            holder.tvUnitValue.setText(product.getUnitObjects().get(0).getUnitValue());
            holder.tvOffer.setText(String.format(holder.itemView.getContext().getString(R.string.offer), product.getUnitObjects().get(0).getDiscount()+"%"));

        } else {
            holder.availableUnits. setVisibility(View.GONE);
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
                for (ProductResponse product : productList) {
                    if (product.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
