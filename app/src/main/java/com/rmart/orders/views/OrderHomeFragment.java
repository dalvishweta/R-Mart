package com.rmart.orders.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.orders.adapters.OrdersHomeAdapter;
import com.rmart.orders.viewmodel.MyOrdersViewModel;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.orders.OrderStateListResponse;
import com.rmart.utilits.pojos.orders.StateOfOrders;
import com.rmart.utilits.services.OrderService;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rmart.utilits.Utils.OPEN_ORDER_STATUS;

public class OrderHomeFragment extends BaseOrderFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ArrayList<StateOfOrders> orderStatus;
    private HashMap<String, Integer> mapOrderStatus = new HashMap<>();
    private RecyclerView recyclerView;
    MyOrdersViewModel myOrdersViewModel;
    private AppCompatTextView openOrderCount;

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
        requireActivity().setTitle(getString(R.string.all_orders));
        getOrderStatusFromServer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // myOrdersViewModel = new ViewModelProvider(requireActivity()).get(MyOrdersViewModel.class);
        return inflater.inflate(R.layout.fragment_order_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            ((AppCompatTextView) view.findViewById(R.id.shop_name)).setText(String.format(getString(R.string.shop_name), MyProfile.getInstance().getAddressResponses().get(0).getShopName()));
            view.findViewById(R.id.accepted_orders).setOnClickListener(this);
            openOrderCount = view.findViewById(R.id.open_order_count);
            recyclerView = view.findViewById(R.id.other_order_names);
            /*myOrdersViewModel.getOrderGroupList().observe(requireActivity(), orderGroups -> {
                if(orderGroups != null) {

                }
            });*/

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    void getOrderStatusFromServer() {
        progressDialog.show();
        OrderService orderService = RetrofitClientInstance.getRetrofitInstance().create(OrderService.class);
        orderService.getOrderHome(MyProfile.getInstance().getUserID()).enqueue(new Callback<OrderStateListResponse>() {
            @Override
            public void onResponse(Call<OrderStateListResponse> call, Response<OrderStateListResponse> response) {
                if (response.isSuccessful()) {
                    OrderStateListResponse data = response.body();
                    assert data != null;
                    if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                        orderStatus = data.getOrderStates();
                        for (int i = 0; i < orderStatus.size(); i++) {
                            orderStatus.get(i).updateBackgroundColor();
                            mapOrderStatus.put(orderStatus.get(i).getStatus(), i);
                        }
                        updateUI();
                    } else {
                        showDialog(data.getMsg());
                    }
                } else {
                    showDialog(response.message());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<OrderStateListResponse> call, Throwable t) {
                showDialog(t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void updateUI() {

        int position = mapOrderStatus.get(OPEN_ORDER_STATUS);
        StateOfOrders data = orderStatus.get(position);
        openOrderCount.setText(data.getCount());
        ArrayList<StateOfOrders> list = (ArrayList<StateOfOrders>) orderStatus.clone();
        int index = mapOrderStatus.get(OPEN_ORDER_STATUS);
        list.remove(index);
        recyclerView.setAdapter(new OrdersHomeAdapter(list, this));

        /*ordersListAdapter = new OrdersListAdapter(orderStatus, this);
        orderList.setAdapter(ordersListAdapter);*/

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.accepted_orders) {
            int position = mapOrderStatus.get(OPEN_ORDER_STATUS);
            mListener.showOrderList(orderStatus.get(position));
            /*myOrdersViewModel.getSelectedOrderGroup().setValue(myOrdersViewModel.getOpenOrders().getValue());
            mListener.showOrderList();*/
        } else {
            StateOfOrders stateOfOrders = (StateOfOrders) view.getTag();
            mListener.showOrderList(stateOfOrders);
        }
    }
}