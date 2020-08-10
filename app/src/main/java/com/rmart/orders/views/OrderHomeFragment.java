package com.rmart.orders.views;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.orders.adapters.OrdersHomeAdapter;
import com.rmart.orders.models.OrderObject;

import java.util.ArrayList;
import java.util.Objects;

public class OrderHomeFragment extends BaseOrderFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public OrderHomeFragment() {
        // Required empty public constructor
    }


    public static OrderHomeFragment newInstance(String param1, String param2) {
        OrderHomeFragment fragment = new OrderHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.all_orders));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.other_order_names);
        ArrayList<OrderObject> orderObjects = new ArrayList<>();
        orderObjects.add(new OrderObject(getString(R.string.accepted_orders),"120",R.drawable.item_accepted_top_bg,R.drawable.item_accepted_bottom_bg));
        orderObjects.add(new OrderObject(getString(R.string.packed_orders),"110",R.drawable.item_packed_top_bg,R.drawable.item_packed_bottom_bg));
        orderObjects.add(new OrderObject(getString(R.string.shipped_orders),"100",R.drawable.item_shipped_top_bg,R.drawable.item_shipped_bottom_bg));
        orderObjects.add(new OrderObject(getString(R.string.delivered_orders),"90",R.drawable.item_delivered_top_bg,R.drawable.item_delivered_bottom_bg));
        orderObjects.add(new OrderObject(getString(R.string.rejected_orders),"120",R.drawable.item_rejected_top_bg,R.drawable.item_rejected_bottom_bg));
        orderObjects.add(new OrderObject(getString(R.string.canceled_orders),"150",R.drawable.item_canceled_top_bg,R.drawable.item_canceled_bottom_bg));
        recyclerView.setAdapter(new OrdersHomeAdapter(orderObjects, this));
    }

    @Override
    public void onClick(View view) {
        OrderObject orderObject = (OrderObject) view.getTag();
    }
}