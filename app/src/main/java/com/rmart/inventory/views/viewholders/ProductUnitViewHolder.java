package com.rmart.inventory.views.viewholders;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;

public class ProductUnitViewHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView tvIUnitState, tvUnitValue, tvFinalCost, tvActual, tvOffer;
    public AppCompatImageButton delete;

    public ProductUnitViewHolder(View listItem) {
        super(listItem);
        tvUnitValue = listItem.findViewById(R.id.sub_title_1);
        tvFinalCost = listItem.findViewById(R.id.sub_title_2);
        tvActual = listItem.findViewById(R.id.sub_title_3);
        tvOffer = listItem.findViewById(R.id.offer);
        tvIUnitState = listItem.findViewById(R.id.unit_status);
        delete = listItem.findViewById(R.id.delete);
    }
}
