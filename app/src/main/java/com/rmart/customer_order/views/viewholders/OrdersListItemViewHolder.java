package com.rmart.customer_order.views.viewholders;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;

public class OrdersListItemViewHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView orderCount, date, orderID, statusDisplayName;  /*status,*/
    public OrdersListItemViewHolder(View listItem) {
        super(listItem);
        date = listItem.findViewById(R.id.date_value);
        orderCount = listItem.findViewById(R.id.order_count);
        orderID = listItem.findViewById(R.id.order_id_value);
        // status = listItem.findViewById(R.id.status);
        statusDisplayName = listItem.findViewById(R.id.status_value);
    }
}
