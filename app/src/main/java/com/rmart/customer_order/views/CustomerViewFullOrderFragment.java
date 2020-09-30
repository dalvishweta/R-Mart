package com.rmart.customer_order.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rmart.R;
import com.rmart.customer_order.adapters.ProductListAdapter;
import com.rmart.customer_order.viewmodel.MyOrdersViewModel;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.UpdatedOrderStatus;
import com.rmart.utilits.pojos.customer_orders.CustomerOrderProductList;
import com.rmart.utilits.pojos.customer_orders.CustomerOrderProductResponse;
import com.rmart.utilits.pojos.orders.Order;
import com.rmart.utilits.pojos.orders.Product;
import com.rmart.utilits.services.CustomerOrderService;
import com.rmart.utilits.services.OrderService;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerViewFullOrderFragment extends BaseOrderFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Order mOrderObject;
    private String mParam2;
    MyOrdersViewModel viewModel;
    AppCompatButton mLeftButton, mRightButton;
    private AppCompatTextView tvStatus, dateValue, vendorName, vendorNumber, vendorAddress, orderIdValue, tvAmount,
            tvDeliveryCharges, tvTotalCharges, tvPaymentType, customerName, customerNumber, customerAddress;
    private ProductListAdapter productAdapter;
    private CustomerOrderProductList orderProductList;
    private RecyclerView recyclerView;
    private LinearLayout deliveryBoyInfo, footer;

    public CustomerViewFullOrderFragment() {
        // Required empty public constructor
    }


    public static CustomerViewFullOrderFragment newInstance(Order param1, String param2) {
        CustomerViewFullOrderFragment fragment = new CustomerViewFullOrderFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrderObject = (Order) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.order_details));
        getServerData();
    }

    private void getServerData() {
        progressDialog.show();
        CustomerOrderService customerOrderService = RetrofitClientInstance.getRetrofitInstance().create(CustomerOrderService.class);
        customerOrderService.viewOrderById(mOrderObject.getOrderID(), MyProfile.getInstance().getMobileNumber()).enqueue(new Callback<CustomerOrderProductResponse>() {
            @Override
            public void onResponse(Call<CustomerOrderProductResponse> call, Response<CustomerOrderProductResponse> response) {
                if (response.isSuccessful()) {
                    CustomerOrderProductResponse data = response.body();
                    assert data != null;
                    if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                        orderProductList = data.getProductList();
                        updateUI();
                    } else {
                        showDialog(data.getMsg());
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CustomerOrderProductResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(MyOrdersViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_full_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.product_list);
        mLeftButton = view.findViewById(R.id.cancel_order);
        mLeftButton.setOnClickListener(this);
        mRightButton = view.findViewById(R.id.accept_order);
        mRightButton.setOnClickListener(this);

        view.findViewById(R.id.accept_order).setVisibility(View.GONE);

        footer = view.findViewById(R.id.bottom);
        //Vendor Info
        vendorName = view.findViewById(R.id.vendor_name);
        vendorNumber = view.findViewById(R.id.vendor_number);
        vendorAddress = view.findViewById(R.id.vendor_address);

        // Customer Info
        customerName = view.findViewById(R.id.customer_name);
        customerNumber = view.findViewById(R.id.customer_number);
        customerAddress = view.findViewById(R.id.customer_address);

        //order Info
        tvStatus = view.findViewById(R.id.status);
        dateValue = view.findViewById(R.id.date_value);
        orderIdValue = view.findViewById(R.id.order_id_value);

        // delivery boy info
        deliveryBoyInfo = view.findViewById(R.id.delivery_boy_info);
        deliveryBoyInfo.setVisibility(View.GONE);

        //Payment info
        tvAmount = view.findViewById(R.id.amount);
        tvDeliveryCharges = view.findViewById(R.id.delivery_charges);
        tvTotalCharges = view.findViewById(R.id.total_charges);
        tvPaymentType = view.findViewById(R.id.payment_type);
        view.findViewById(R.id.custom_details_root).setVisibility(View.VISIBLE);
        view.findViewById(R.id.vendor_details_root).setVisibility(View.VISIBLE);
    }

    void updateUI() {
        Resources res = getResources();
        String text = String.format(res.getString(R.string.status_order), orderProductList.getOrderInfo().getStatusName());
        tvStatus.setText(text);
        orderIdValue.setText(orderProductList.getOrderInfo().getOrderID());
        dateValue.setText(mOrderObject.getOrderDate().split(" ")[0]);
        if (orderProductList.getOrderInfo().getStatus().equalsIgnoreCase(Utils.OPEN_ORDER_STATUS) ||
                orderProductList.getOrderInfo().getStatus().equalsIgnoreCase(Utils.ACCEPTED_ORDER_STATUS) ||
                orderProductList.getOrderInfo().getStatus().equalsIgnoreCase(Utils.PACKED_ORDER_STATUS)) {
            // footer.setVisibility(View.GONE);
            footer.setVisibility(View.VISIBLE);
            mLeftButton.setVisibility(View.VISIBLE);
            mLeftButton.setText(R.string.canceled_orders);
            mLeftButton.setBackgroundResource(R.color.gray);
        } else if ((orderProductList.getOrderInfo().getStatus().equalsIgnoreCase(Utils.CANCEL_BY_RETAILER) ||
                orderProductList.getOrderInfo().getStatus().equalsIgnoreCase(Utils.CANCEL_BY_CUSTOMER) ||
                orderProductList.getOrderInfo().getStatus().equalsIgnoreCase(Utils.DELIVERED_ORDER_STATUS))) {
            footer.setVisibility(View.VISIBLE);
            mLeftButton.setText(R.string.re_order);
            mLeftButton.setVisibility(View.GONE);
            mLeftButton.setBackgroundResource(R.color.colorPrimary);
        } else {
            footer.setVisibility(View.GONE);
        }
        // setFooter();
        // vendor
        text = orderProductList.getVendorInfo().getFirstName() + " " + orderProductList.getVendorInfo().getLastName();
        vendorName.setText(text);
        vendorNumber.setText(orderProductList.getVendorInfo().getMobileNumber());
        vendorAddress.setText(orderProductList.getVendorInfo().getCompleteAddress());

        String customerNameDetails = orderProductList.getCustomerInfo().getFirstName() + " " + orderProductList.getCustomerInfo().getLastName();
        customerName.setText(customerNameDetails);
        customerAddress.setText(orderProductList.getCustomerInfo().getCompleteAddress());
        customerNumber.setText(orderProductList.getCustomerInfo().getCustomerNumber());

        // payment info
        tvAmount.setText(orderProductList.getOrderInfo().getOrderAmount());
        text = orderProductList.getOrderInfo().getOrderCharges();
        tvDeliveryCharges.setText(text);
        tvTotalCharges.setText(orderProductList.getOrderInfo().getTotalAmt());
        tvPaymentType.setText(orderProductList.getOrderInfo().getModeOfPayment());

        // payment info
        tvAmount.setText(orderProductList.getOrderInfo().getOrderAmount());
        text = orderProductList.getOrderInfo().getOrderCharges();
        tvDeliveryCharges.setText(text);
        tvTotalCharges.setText(orderProductList.getOrderInfo().getTotalAmt());
        tvPaymentType.setText(orderProductList.getOrderInfo().getModeOfPayment());

        List<Product> orderedProductsList = orderProductList.getProduct();
        if (orderedProductsList != null && !orderedProductsList.isEmpty()) {
            List<Object> lUpdatedProductsList = new ArrayList<>(orderedProductsList);
            productAdapter = new ProductListAdapter(requireActivity(), lUpdatedProductsList);
            recyclerView.setAdapter(productAdapter);
        }
    }

    @Override
    public void onClick(View view) {
        /*
            if(text.contains(getResources().getString(R.string.accept))) {
                updateOrderStatus(Utils.ACCEPTED_ORDER_STATUS);
            } else if(text.contains(getResources().getString(R.string.packed))) {
                updateOrderStatus(Utils.PACKED_ORDER_STATUS);
            } else if(text.contains(getResources().getString(R.string.shipped))) {
                updateOrderStatus(Utils.SHIPPED_ORDER_STATUS);
            } else if(text.contains(getResources().getString(R.string.delivered))) {
                updateOrderStatus(Utils.DELIVERED_ORDER_STATUS);
            } else if(text.contains(getResources().getString(R.string.returned))) {

            } else if(text.contains(getResources().getString(R.string.cancel))) {
                updateOrderStatus(Utils.CANCEL_ORDER_STATUS);
            }
        */
        String text = ((AppCompatButton) view).getText().toString();
        if (text.equalsIgnoreCase(getString(R.string.re_order))) {
            mListener.goToReOrder(orderProductList);
        } else {
            updateOrderStatus();
            updateUI();
        }
    }

    private void updateOrderStatus() {
        progressDialog.show();
        OrderService orderService = RetrofitClientInstance.getRetrofitInstance().create(OrderService.class);
        orderService.updateOrderStatus(mOrderObject.getOrderID(), MyProfile.getInstance().getUserID(), Utils.CANCEL_BY_CUSTOMER, "").enqueue(new Callback<UpdatedOrderStatus>() {
            @Override
            public void onResponse(Call<UpdatedOrderStatus> call, Response<UpdatedOrderStatus> response) {
                if (response.isSuccessful()) {
                    UpdatedOrderStatus data = response.body();
                    assert data != null;
                    if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                        showDialog(data.getStatus(), data.getMsg(), ((dialogInterface, i) -> {
                            requireActivity().onBackPressed();
                        }));
                    } else {
                        showDialog(data.getMsg());
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UpdatedOrderStatus> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    /*void updateToCancel() {
        viewModel.setCanceledOrders(mOrderObject);
    }
    void updateToAccepted() {
        viewModel.setAcceptedOrders(mOrderObject);
    }
    void updateToPacked() {
        viewModel.setPackedOrders(mOrderObject);
        // Objects.requireNonNull(viewModel.getPackedOrders().getValue()).getOrderObjects().add(mOrderObject);
    }
    void updateToShipped() {
        viewModel.setShippedOrders(mOrderObject);
        // Objects.requireNonNull(viewModel.getShippedOrders().getValue()).getOrderObjects().add(mOrderObject);
    }
    void updateToDelivered() {

        viewModel.setDeliveredOrders(mOrderObject);
        //Objects.requireNonNull(viewModel.getDeliveredOrders().getValue()).getOrderObjects().add(mOrderObject);
    }
    void updateToReturned() {
        viewModel.setReturnedOrders(mOrderObject);
        //Objects.requireNonNull(viewModel.getReturnedOrders().getValue()).getOrderObjects().add(mOrderObject);
    }*/

    void isOpenOrder() {
        mLeftButton.setBackgroundResource(R.drawable.btn_bg_canceled);
        mLeftButton.setText(R.string.cancel);

        mRightButton.setBackgroundResource(R.drawable.btn_bg_accepted);
        mRightButton.setText(R.string.accept);
    }

    void isCanceledOrder() {
        mLeftButton.setVisibility(View.GONE);
        mRightButton.setVisibility(View.GONE);
    }

    void isAcceptedOrder() {
        mRightButton.setBackgroundResource(R.drawable.btn_bg_shipped);
        mRightButton.setText(R.string.shipped);
        mLeftButton.setBackgroundResource(R.drawable.btn_bg_packed);
        mLeftButton.setText(R.string.packed);
    }

    void isPackedOrder() {
        mLeftButton.setBackgroundResource(R.drawable.btn_bg_shipped);
        mLeftButton.setText(R.string.shipped);
        mRightButton.setVisibility(View.GONE);
    }

    void isShippedOrder() {
        mLeftButton.setBackgroundResource(R.drawable.btn_bg_delivered);
        mLeftButton.setText(R.string.delivered);
        mRightButton.setVisibility(View.GONE);
    }

    void isDeliveredOrder() {
        mLeftButton.setVisibility(View.GONE);
        mRightButton.setVisibility(View.GONE);
        deliveryBoyInfo.setVisibility(View.VISIBLE);
        /*deliveryBoyNumber.setText(mOrderObject.getDeliveryBoyNumber());
        deliveryBoyName.setText(mOrderObject.getDeliveryBoyName());*/
    }

    void isReturnedOrder() {
        mLeftButton.setVisibility(View.GONE);
        mRightButton.setVisibility(View.GONE);
    }
}