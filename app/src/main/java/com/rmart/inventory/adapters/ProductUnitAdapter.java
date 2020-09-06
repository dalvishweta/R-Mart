package com.rmart.inventory.adapters;

import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.models.UnitObject;
import com.rmart.inventory.views.viewholders.ProductUnitViewHolder;

import java.util.ArrayList;

public class ProductUnitAdapter extends RecyclerView.Adapter<ProductUnitViewHolder> {

    View.OnClickListener onClickListener;
    ArrayList<UnitObject> productUnitList;
    boolean canDelete = false;
    public ProductUnitAdapter(ArrayList<UnitObject> unitObjects, View.OnClickListener onClickListener, boolean canDelete) {
        this.onClickListener =onClickListener;
        this.productUnitList = unitObjects;
        this.canDelete = canDelete;
    }

    @NonNull
    @Override
    public ProductUnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        listItem= layoutInflater.inflate(R.layout.product_unit_base, parent, false);
        ProductUnitViewHolder productUnitViewHolder = new ProductUnitViewHolder(listItem);
        if(canDelete) {
            productUnitViewHolder.delete.setVisibility(View.VISIBLE);
            productUnitViewHolder.delete.setOnClickListener(onClickListener);
        } else {
            productUnitViewHolder.delete.setVisibility(View.GONE);
        }
        return productUnitViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ProductUnitViewHolder holder, int position) {
        UnitObject unit = productUnitList.get(position);
        holder.delete.setTag(unit);
        String html =  "<strike>" + unit.getActualCost()+"</strike>";
        holder.tvActual.setText(Html.fromHtml(html));
        holder.tvFinalCost.setText(unit.getFinalCost());
        holder.tvUnitValue.setText(unit.getDisplayUnitValue());
        holder.tvOffer.setText(String.format(holder.itemView.getContext().getString(R.string.offer_single_line), unit.getDiscount()+"%"));
        holder.tvIUnitState.setText(unit.getProductStatus());
        if(unit.getProductStatus().equalsIgnoreCase("Available")) {

        }
        /*if(unit.isActive()) {
            holder.tvIUnitState.setTextColor(holder.itemView.getContext().getColor(R.color.colorPrimary));
            holder.tvIUnitState.setText(holder.itemView.getContext().getString(R.string.active));
        } else {
            holder.tvIUnitState.setTextColor(holder.itemView.getContext().getColor(R.color.gray));
            holder.tvIUnitState.setText(holder.itemView.getContext().getString(R.string.in_active));
        }*/
    }

    @Override
    public int getItemCount() {
        return productUnitList.size();
    }
}
