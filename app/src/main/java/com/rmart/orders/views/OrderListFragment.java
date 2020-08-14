package com.rmart.orders.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.orders.adapters.OrdersListAdapter;
import com.rmart.orders.models.OrderObject;
import com.rmart.orders.models.OrdersByType;

import java.util.Objects;

public class OrderListFragment extends BaseOrderFragment implements View.OnClickListener {

    private static final String ARG_ORDER_OBJECT = "param1";
    private static final String ARG_PARAM2 = "param2";

    // private String mParam1;
    private String mParam2;
    OrdersByType allOrders;
    public OrderListFragment() {
        // Required empty public constructor
    }

    public static OrderListFragment newInstance(OrdersByType ordersByType, String param2) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ORDER_OBJECT, ordersByType);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            allOrders = (OrdersByType) getArguments().getSerializable(ARG_ORDER_OBJECT);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(allOrders.getOrderType());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.total_orders), allOrders.getOrderObjects().size());
        ((AppCompatTextView)view.findViewById(R.id.total_order)).setText(text);
        RecyclerView orderList = view.findViewById(R.id.order_list);
        OrdersListAdapter ordersListAdapter = new OrdersListAdapter(allOrders.getOrderObjects(), this);
        orderList.setAdapter(ordersListAdapter);
    }

    @Override
    public void onClick(View view) {
        OrderObject orderObject = (OrderObject) view.getTag();
        mListener.goToViewFullOrder(orderObject);
    }
}