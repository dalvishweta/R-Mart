package com.rmart.inventory.views.viewholders;

import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.utilits.custom_views.CustomNetworkImageView;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView tvItemTitle, tvUnitValue, tvFinalCost, tvActual, tvOffer, availableUnits;
    public CustomNetworkImageView itemImg;
    public LinearLayout unitView;
    public LinearLayout progressLayoutField;
    public ProductViewHolder(View listItem) {
        super(listItem);
        tvItemTitle = listItem.findViewById(R.id.title);
        tvUnitValue = listItem.findViewById(R.id.sub_title_1);
        tvFinalCost = listItem.findViewById(R.id.sub_title_2);
        tvActual = listItem.findViewById(R.id.sub_title_3);
        tvOffer = listItem.findViewById(R.id.offer);
        unitView = listItem.findViewById(R.id.row2);
        availableUnits = listItem.findViewById(R.id.available_units);
        itemImg = listItem.findViewById(R.id.item_img);
        progressLayoutField = listItem.findViewById(R.id.progress_layout_field);
    }
}
