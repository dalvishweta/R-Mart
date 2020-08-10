package com.rmart.orders.views.viewholders;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;

public class OrdersHomeViewHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView orderCount, orderTitle;
    public OrdersHomeViewHolder(View listItem) {
        super(listItem);
        orderCount = listItem.findViewById(R.id.order_count);
        orderTitle = listItem.findViewById(R.id.order_title);
    }

}
