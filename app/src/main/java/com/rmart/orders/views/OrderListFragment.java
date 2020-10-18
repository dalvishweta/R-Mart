package com.rmart.orders.views;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.orders.adapters.OrdersListAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.orders.Order;
import com.rmart.utilits.pojos.orders.OrdersByStatus;
import com.rmart.utilits.pojos.orders.StateOfOrders;
import com.rmart.utilits.services.OrderService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListFragment extends BaseOrderFragment implements View.OnClickListener {

    private static final String ARG_ORDER_OBJECT = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AppCompatTextView tvTotalOrder;
    private RecyclerView orderList;
    StateOfOrders stateOfOrders;
    private String startIndex = "0";
    private ArrayList<Order> orders = new ArrayList<>();
    private OrdersByStatus data;
    private String mobileNumber;

    public OrderListFragment() {
        // Required empty public constructor
    }

    public static OrderListFragment newInstance(StateOfOrders param2) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stateOfOrders = (StateOfOrders) getArguments().getSerializable(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // myOrdersViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MyOrdersViewModel.class);
        // mSelectedOrderGroup = myOrdersViewModel.getReturnedOrders().getValue();
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Objects.requireNonNull(getActivity()).setTitle(mSelectedOrderGroup.getOrderType());
        startIndex = "0";
        getOrdersOfStatesFromServer(mobileNumber, stateOfOrders.getStatus());
    }

    private void getOrdersOfStatesFromServer(String mobileNumber, String status) {
        progressDialog.show();
        OrderService orderService = RetrofitClientInstance.getRetrofitInstance().create(OrderService.class);
        orderService.getStateOfOrder(startIndex, mobileNumber, status).enqueue(new Callback<OrdersByStatus>() {
            @Override
            public void onResponse(@NotNull Call<OrdersByStatus> call, @NotNull Response<OrdersByStatus> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    data = response.body();
                    orders = data.getOrders();
                    if (data != null) {
                        if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                            startIndex = data.getEndIndex();
                            updateUI();
                        } else {
                            showDialog(data.getMsg());
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                } else {
                    showDialog(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<OrdersByStatus> call, @NotNull Throwable t) {
                showDialog(t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void updateUI() {
        // SelectedOrderGroup mSelectedOrderGroup;
        // private MyOrdersViewModel myOrdersViewModel;
        OrdersListAdapter ordersListAdapter = new OrdersListAdapter(orders, this);
        String count  = data.getOrdersCount();
        if (TextUtils.isEmpty(count)) {
            count = "0";
        }
        tvTotalOrder.setText(String.format(getResources().getString(R.string.total_orders), count,  stateOfOrders.getStatusName()));
        orderList.setAdapter(ordersListAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotalOrder = view.findViewById(R.id.total_order);
        orderList = view.findViewById(R.id.order_list);
        if (MyProfile.getInstance().getRoleID().equals(Utils.DELIVERY_ID)) {
            mobileNumber = "7416226233";// MyProfile.getInstance().getMobileNumber();
        } else {
            mobileNumber = MyProfile.getInstance().getMobileNumber();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // myOrdersViewModel = null;
        mListener =null;
    }

    @Override
    public void onClick(View view) {
        Order orderObject = (Order) view.getTag();
        orderObject.setOrderStatus(stateOfOrders.getStatusName());
        orderObject.setOrderStatusID(stateOfOrders.getStatus());
        mListener.goToViewFullOrder(orderObject);
    }
}