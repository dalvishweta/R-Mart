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
import com.rmart.orders.adapters.OrdersHomeAdapter;
import com.rmart.orders.viewmodel.MyOrdersViewModel;
import com.rmart.orders.models.SelectedOrderGroup;
import com.rmart.profile.model.MyProfile;

import java.util.Objects;

public class OrderHomeFragment extends BaseOrderFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    MyOrdersViewModel myOrdersViewModel;

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
        myOrdersViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MyOrdersViewModel.class);
        return inflater.inflate(R.layout.fragment_order_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatTextView)view.findViewById(R.id.shop_name)).setText(String.format(getString(R.string.shop_name), MyProfile.getInstance().getAddressResponses().get(0).getShopName()));
        view.findViewById(R.id.accepted_orders).setOnClickListener(this);
        ((AppCompatTextView)view.findViewById(R.id.open_order_count)).setText(Objects.requireNonNull(myOrdersViewModel.getOpenOrders().getValue()).getOrderObjects().size()+"");
        RecyclerView recyclerView = view.findViewById(R.id.other_order_names);
        myOrdersViewModel.getOrderGroupList().observe(Objects.requireNonNull(getActivity()), orderGroups -> {
            if(orderGroups != null) {
                recyclerView.setAdapter(new OrdersHomeAdapter(orderGroups, this));
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.accepted_orders) {
            myOrdersViewModel.getSelectedOrderGroup().setValue(myOrdersViewModel.getOpenOrders().getValue());
            mListener.showOrderList();
        } else {
            SelectedOrderGroup selectedOrderGroup = (SelectedOrderGroup) view.getTag();
            myOrdersViewModel.getSelectedOrderGroup().setValue(selectedOrderGroup);
            mListener.showOrderList();
        }
    }
}