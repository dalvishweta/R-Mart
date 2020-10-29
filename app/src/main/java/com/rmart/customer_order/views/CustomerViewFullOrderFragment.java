package com.rmart.customer_order.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.customer_order.adapters.ProductListAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.UpdatedOrderStatus;
import com.rmart.utilits.pojos.customer_orders.CustomerOrderProductList;
import com.rmart.utilits.pojos.customer_orders.CustomerOrderProductResponse;
import com.rmart.utilits.pojos.customer_orders.VendorInfo;
import com.rmart.utilits.pojos.orders.Order;
import com.rmart.utilits.pojos.orders.Product;
import com.rmart.utilits.services.CustomerOrderService;
import com.rmart.utilits.services.OrderService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerViewFullOrderFragment extends BaseOrderFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Order mOrderObject;
    private AppCompatButton mLeftButton, mRightButton;
    private AppCompatTextView tvStatus, dateValue, vendorName, vendorNumber, vendorAddress, orderIdValue, tvAmount,
            tvDeliveryCharges, tvTotalCharges, tvPaymentType, customerName, customerNumber, customerAddress;
    private CustomerOrderProductList orderProductList;
    private RecyclerView recyclerView;
    private LinearLayout deliveryBoyInfo, footer;
    private TextView tvStatusComments;
    private LinearLayout vendorDetailsLayout;

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
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.order_details));
        getServerData();
    }

    private void getServerData() {
        if(Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerOrderService customerOrderService = RetrofitClientInstance.getRetrofitInstance().create(CustomerOrderService.class);
            customerOrderService.viewOrderById(mOrderObject.getOrderID(), MyProfile.getInstance().getMobileNumber()).enqueue(new Callback<CustomerOrderProductResponse>() {
                @Override
                public void onResponse(@NotNull Call<CustomerOrderProductResponse> call, @NotNull Response<CustomerOrderProductResponse> response) {
                    if (response.isSuccessful()) {
                        CustomerOrderProductResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                orderProductList = data.getProductList();
                                if (orderProductList.getOrderInfo().getStatus().equalsIgnoreCase(Utils.CANCEL_BY_RETAILER)) {
                                    orderProductList.getOrderInfo().setStatusName("Order cancelled by Retailer");
                                } else if (orderProductList.getOrderInfo().getStatus().equalsIgnoreCase(Utils.CANCEL_BY_CUSTOMER)) {
                                    orderProductList.getOrderInfo().setStatusName("Order cancelled by Customer");
                                }
                                updateUI();
                            } else {
                                showCloseDialog(null, data.getMsg());
                            }
                        } else {
                            showCloseDialog(null, getString(R.string.no_information_available));
                        }
                    } else {
                        showCloseDialog(null, response.message());
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<CustomerOrderProductResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    showCloseDialog(null, t.getMessage());
                }
            });
        } else {
            showCloseDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //MyOrdersViewModel viewModel = new ViewModelProvider(requireActivity()).get(MyOrdersViewModel.class);
        // Inflate the layout for this fragment
        LoggerInfo.printLog("Fragment", "CustomerViewFullOrderFragment");
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

        tvStatusComments = view.findViewById(R.id.status_comments);

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
        vendorDetailsLayout = view.findViewById(R.id.vendor_details_root);
        vendorDetailsLayout.setVisibility(View.VISIBLE);
    }

    void updateUI() {
        Resources res = getResources();
        if (orderProductList.getOrderInfo().getStatus().equalsIgnoreCase(Utils.CANCEL_BY_RETAILER)|| orderProductList.getOrderInfo().getStatus().equalsIgnoreCase(Utils.CANCEL_BY_CUSTOMER)) {
            tvStatus.setText(getString(R.string.cancel_status) +orderProductList.getOrderInfo().getStatusName());
        } else {
            String text = String.format(res.getString(R.string.status_order), orderProductList.getOrderInfo().getStatusName());
            tvStatus.setText(text);
        }

        String statusCommentsReason = orderProductList.getOrderInfo().getStatusComments();
        if(TextUtils.isEmpty(statusCommentsReason)) {
            tvStatusComments.setVisibility(View.GONE);
        }
        String statusComments = String.format("%s : %s", getString(R.string.comments), statusCommentsReason);
        tvStatusComments.setText(statusComments);

        orderIdValue.setText(orderProductList.getOrderInfo().getReceiptNumber());
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
            mLeftButton.setBackgroundResource(R.color.colorPrimary);
        } else {
            footer.setVisibility(View.GONE);
        }
        // setFooter();
        // vendor
        VendorInfo vendorDetails = orderProductList.getVendorInfo();
        String text;
        if (vendorDetails != null) {
            text = vendorDetails.getFirstName() + " " + vendorDetails.getLastName();
            vendorName.setText(text);
            vendorNumber.setText(vendorDetails.getMobileNumber());
            vendorAddress.setText(vendorDetails.getCompleteAddress());
        } else {
            vendorDetailsLayout.setVisibility(View.GONE);
        }


        String customerNameDetails = orderProductList.getCustomerInfo().getFirstName() + " " + orderProductList.getCustomerInfo().getLastName();
        customerName.setText(customerNameDetails);
        customerAddress.setText(orderProductList.getCustomerInfo().getCompleteAddress());
        customerNumber.setText(orderProductList.getCustomerInfo().getCustomerNumber());

        // payment info
        tvAmount.setText(Utils.roundOffDoubleValue(orderProductList.getOrderInfo().getOrderAmount()));
        text = orderProductList.getOrderInfo().getOrderCharges();
        tvDeliveryCharges.setText(text);
        tvTotalCharges.setText(Utils.roundOffDoubleValue(orderProductList.getOrderInfo().getTotalAmt()));
        tvPaymentType.setText(orderProductList.getOrderInfo().getModeOfPayment());

        // payment info
        tvAmount.setText(Utils.roundOffDoubleValue(orderProductList.getOrderInfo().getOrderAmount()));
        text = orderProductList.getOrderInfo().getOrderCharges();
        tvDeliveryCharges.setText(text);
        tvTotalCharges.setText(Utils.roundOffDoubleValue(orderProductList.getOrderInfo().getTotalAmt()));
        tvPaymentType.setText(orderProductList.getOrderInfo().getModeOfPayment());

        List<Product> orderedProductsList = orderProductList.getProduct();
        if (orderedProductsList != null && !orderedProductsList.isEmpty()) {
            List<Object> lUpdatedProductsList = new ArrayList<>(orderedProductsList);
            ProductListAdapter productAdapter = new ProductListAdapter(requireActivity(), lUpdatedProductsList);
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
            SelectOrderStatusView selectOrderStatus = SelectOrderStatusView.getInstance();
            selectOrderStatus.setCallBackListener(pObject -> {
                if (pObject instanceof String) {
                    String reason = (String) pObject;
                    updateOrderStatus(reason);
                    updateUI();
                }
            });
            if (!requireActivity().isFinishing()) {
                selectOrderStatus.show(requireActivity().getSupportFragmentManager(), SelectOrderStatusView.class.getName());
            }
        }
    }

    private void updateOrderStatus(String reasonStatus) { // cancel
        progressDialog.show();
        OrderService orderService = RetrofitClientInstance.getRetrofitInstance().create(OrderService.class);
        orderService.updateOrderStatus(mOrderObject.getOrderID(), MyProfile.getInstance().getUserID(), Utils.CANCEL_BY_CUSTOMER, reasonStatus).enqueue(new Callback<UpdatedOrderStatus>() {
            @Override
            public void onResponse(@NotNull Call<UpdatedOrderStatus> call, @NotNull Response<UpdatedOrderStatus> response) {
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
            public void onFailure(@NotNull Call<UpdatedOrderStatus> call, @NotNull Throwable t) {
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

    private void showCloseDialog(String title, String message) {
        if (!requireActivity().isFinishing()) {
            showDialog(title, message, pObject -> {
                requireActivity().getSupportFragmentManager().popBackStack();
            });
        }
    }
}