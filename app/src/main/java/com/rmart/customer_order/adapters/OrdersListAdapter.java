package com.rmart.customer_order.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.customer_order.views.viewholders.OrdersListItemViewHolder;
import com.rmart.utilits.pojos.orders.Order;

import java.util.ArrayList;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListItemViewHolder> {

    View.OnClickListener onClickListener;
    ArrayList<Order> orderList;
    public OrdersListAdapter(ArrayList<Order> orderList, View.OnClickListener onClickListener) {
        this.orderList = orderList;
        this.onClickListener =onClickListener;
    }
    @NonNull
    @Override
    public OrdersListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_order_list, parent, false);
        listItem.setOnClickListener(onClickListener);
        return new OrdersListItemViewHolder(listItem) ;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersListItemViewHolder holder, int position) {
        Order orderObject = orderList.get(position);
        if (orderObject.getStatusDisplay().length() <= 0) {
            // holder.status.setVisibility(View.GONE);
            holder.statusDisplayName.setVisibility(View.GONE);
        } else {
            // holder.status.setVisibility(View.VISIBLE);
            holder.statusDisplayName.setVisibility(View.VISIBLE);
            holder.statusDisplayName.setText(orderObject.getStatusDisplay());
            // holder.statusDisplayName.setText(orderObject.getStatus());
        }
        holder.orderCount.setText(orderObject.getProductCount());
        holder.orderID.setText(orderObject.getOrderID());
        String date = "";
        try {
            date = orderObject.getOrderDate().split(" ")[0];
        } catch (Exception e) {

        }
        holder.date.setText(date);
        holder.itemView.setTag(orderObject);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
