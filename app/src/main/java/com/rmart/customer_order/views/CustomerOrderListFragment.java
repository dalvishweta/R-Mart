package com.rmart.customer_order.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.customer_order.adapters.OrdersListAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.orders.Order;
import com.rmart.utilits.pojos.orders.OrdersByStatus;
import com.rmart.utilits.pojos.orders.StateOfOrders;
import com.rmart.utilits.services.CustomerOrderService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerOrderListFragment extends BaseOrderFragment implements View.OnClickListener {

    private static final String ARG_ORDER_OBJECT = "param1";
    private static final String ARG_PARAM2 = "param2";

    // SelectedOrderGroup mSelectedOrderGroup;
    // private MyOrdersViewModel myOrdersViewModel;
    private AppCompatTextView tvTotalOrder;
    private RecyclerView orderList;
    StateOfOrders stateOfOrders;
    private String startIndex = "0";
    private ArrayList<Order> orders = new ArrayList<>();
    private OrdersByStatus data;

    public CustomerOrderListFragment() {
        // Required empty public constructor
    }

    public static CustomerOrderListFragment newInstance() {
        return new CustomerOrderListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LoggerInfo.printLog("Fragment", "CustomerOrderListFragment");
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        startIndex = "0";
        getOrdersOfStatesFromServer();
        requireActivity().setTitle(R.string.my_orders);
    }

    private void getOrdersOfStatesFromServer() {
        if(!Utils.isNetworkConnected(requireActivity())) {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
            return;
        }
        progressDialog.show();
        CustomerOrderService customerOrderService = RetrofitClientInstance.getRetrofitInstance().create(CustomerOrderService.class);
        customerOrderService.getStateOfOrder(startIndex, MyProfile.getInstance().getMobileNumber()).enqueue(new Callback<OrdersByStatus>() {
            @Override
            public void onResponse(@NotNull Call<OrdersByStatus> call, @NotNull Response<OrdersByStatus> response) {
                if (response.isSuccessful()) {
                    data = response.body();
                    if (data != null) {
                        orders = data.getOrders();
                        if (orders.size() > 0) {
                            assert data != null;
                            if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                for (Order order: orders) {
                                    order.setOrderDate(Utils.getDateString(order.getOrderDate(), Utils.YYYY_MM_DD, Utils.DD_MM_YYYY));
                                   if(order.getStatus().equalsIgnoreCase(Utils.CANCEL_BY_CUSTOMER)) {
                                        order.setStatusDisplay(getString(R.string.canceled_by_me));
                                    }
                                }
                                startIndex = data.getEndIndex();
                                updateUI();
                            } else {
                                showCloseDialog(data.getMsg());
                            }
                        } else {
                            showCloseDialog(data.getMsg());
                        }
                    } else {
                        showCloseDialog(getString(R.string.no_information_available));
                    }
                } else {
                    showCloseDialog(response.message());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<OrdersByStatus> call, @NotNull Throwable t) {
                if(t instanceof SocketTimeoutException){
                    showDialog("", getString(R.string.network_slow));
                } else {
                    showDialog("", t.getMessage());
                }
                progressDialog.dismiss();
            }
        });
    }

    private void updateUI() {
        OrdersListAdapter ordersListAdapter = new OrdersListAdapter(orders, this);
        String count = data.getOrdersCount();
        if (null == count || count.length()<=0) {
            count = "0";
        }
        if (null != stateOfOrders) {
            tvTotalOrder.setText(String.format(getResources().getString(R.string.total_orders), count, stateOfOrders.getStatusName()));
        } else {
            tvTotalOrder.setVisibility(View.INVISIBLE);
        }
        orderList.setAdapter(ordersListAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotalOrder = view.findViewById(R.id.total_order);
        orderList = view.findViewById(R.id.order_list);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // myOrdersViewModel = null;
        mListener = null;
    }

    private void showCloseDialog(String message) {
        showDialog(message, pObject -> {
            requireActivity().onBackPressed();
        });
    }

    @Override
    public void onClick(View view) {
        Order orderObject = (Order) view.getTag();
        // orderObject.setOrderStatus(stateOfOrders.getStatusName());
        //orderObject.setOrderStatusID(stateOfOrders.getStatus());
        mListener.goToViewFullOrder(orderObject);
    }
}