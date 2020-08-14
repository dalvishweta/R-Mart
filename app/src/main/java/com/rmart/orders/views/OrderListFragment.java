package com.rmart.orders.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.orders.adapters.OrdersListAdapter;
import com.rmart.orders.models.OrderObject;
import com.rmart.orders.models.SelectedOrderGroup;
import com.rmart.orders.viewmodel.MyOrdersViewModel;

import java.util.Objects;

public class OrderListFragment extends BaseOrderFragment implements View.OnClickListener {

    private static final String ARG_ORDER_OBJECT = "param1";
    private static final String ARG_PARAM2 = "param2";

    SelectedOrderGroup mSelectedOrderGroup;
    private MyOrdersViewModel myOrdersViewModel;
    private OrdersListAdapter ordersListAdapter;
    private AppCompatTextView tvTotalOrder;
    private RecyclerView orderList;

    public OrderListFragment() {
        // Required empty public constructor
    }

    public static OrderListFragment newInstance(String param2) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myOrdersViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MyOrdersViewModel.class);
        mSelectedOrderGroup = myOrdersViewModel.getReturnedOrders().getValue();
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(mSelectedOrderGroup.getOrderType());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotalOrder = view.findViewById(R.id.total_order);
        orderList = view.findViewById(R.id.order_list);

        myOrdersViewModel.getSelectedOrderGroup().observe(Objects.requireNonNull(getActivity()), selectedOrderGroup -> {
            try {
                mSelectedOrderGroup = selectedOrderGroup;
                ordersListAdapter = new OrdersListAdapter(mSelectedOrderGroup.getOrderObjects(), this);
                tvTotalOrder.setText(String.format(getResources().getString(R.string.total_orders), mSelectedOrderGroup.getOrderObjects().size(),  mSelectedOrderGroup.getOrderType()));
                orderList.setAdapter(ordersListAdapter);
            } catch (Exception e) {

            }

        });
        // tvTotalOrder.setText(String.format(getResources().getString(R.string.total_orders), mSelectedOrderGroup.getOrderObjects().size(),  mSelectedOrderGroup.getOrderType()));

        /*ordersListAdapter = new OrdersListAdapter(mSelectedOrderGroup.getOrderObjects(), this);
        orderList.setAdapter(ordersListAdapter);*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        myOrdersViewModel = null;
        mListener =null;
    }

    @Override
    public void onClick(View view) {
        OrderObject orderObject = (OrderObject) view.getTag();
        mListener.goToViewFullOrder(orderObject);
    }
}