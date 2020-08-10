package com.rmart.orders.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.views.viewholders.CategoryViewHolder;
import com.rmart.orders.models.OrderObject;
import com.rmart.orders.views.viewholders.OrdersHomeViewHolder;

import java.util.ArrayList;

public class OrdersHomeAdapter extends RecyclerView.Adapter<OrdersHomeViewHolder> {

    View.OnClickListener onClickListener;
    ArrayList<OrderObject> orderObjects;
    public OrdersHomeAdapter(ArrayList<OrderObject> orderObjects, View.OnClickListener onClickListener) {
        this.orderObjects = orderObjects;
        this.onClickListener =onClickListener;
    }
    @NonNull
    @Override
    public OrdersHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_grid_order_home_layout, parent, false);
        listItem.setOnClickListener(onClickListener);
        return new OrdersHomeViewHolder(listItem) ;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersHomeViewHolder holder, int position) {
        OrderObject orderObject = orderObjects.get(position);
        holder.orderCount.setText(orderObject.getCount());
        holder.orderTitle.setText(orderObject.getName());
        holder.orderCount.setBackgroundResource(orderObject.getBgTop());
        holder.orderTitle.setBackgroundResource(orderObject.getBgBottom());
    }

    @Override
    public int getItemCount() {
        return orderObjects.size();
    }
}
