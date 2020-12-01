package com.rmart.orders.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.customer.adapters.CustomSpinnerAdapter;
import com.rmart.customer_order.views.SelectOrderStatusView;
import com.rmart.orders.adapters.ProductListAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.DeliveryBoyList;
import com.rmart.utilits.pojos.ProfileResponse;
import com.rmart.utilits.pojos.UpdatedOrderStatus;
import com.rmart.utilits.pojos.customer_orders.CustomerOrderProductList;
import com.rmart.utilits.pojos.customer_orders.CustomerOrderProductResponse;
import com.rmart.utilits.pojos.orders.Order;
import com.rmart.utilits.services.OrderService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewFullOrderFragment extends BaseOrderFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Order mOrderObject;
    private AppCompatButton mCancelOrderBtn, mAcceptOrderBtn;
    private AppCompatTextView tvStatus, customerName, customerAddress, customerNumber, dateValue, orderIdValue, tvAmount, tvDeliveryCharges, tvTotalCharges, tvPaymentType,
            deliveryBoyName, deliveryBoyNumber;
    private CustomerOrderProductList orderProductList = new CustomerOrderProductList();
    private RecyclerView recyclerView;
    private LinearLayout deliveryBoyInfo, footerView;
    private ProfileResponse selectedDeliveryBoy;
    private CustomSpinnerAdapter deliveryBoyAdapter;
    private Spinner deliveryBoySpinner;
    ArrayList<Object> reasonsList = new ArrayList<>();
    ArrayList<Object> deliveryBoyList = new ArrayList<>();
    private TextView tvStatusComments;
    private String userID;

    public ViewFullOrderFragment() {
        // Required empty public constructor
    }


    public static ViewFullOrderFragment newInstance(Order param1, String param2) {
        ViewFullOrderFragment fragment = new ViewFullOrderFragment();
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
        requireActivity().setTitle("Order details");
        getServerData(userID);
    }

    private void getServerData(String userID) {
        if(!Utils.isNetworkConnected(requireActivity())) {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
            return;
        }
        progressDialog.show();
        OrderService orderService = RetrofitClientInstance.getRetrofitInstance().create(OrderService.class);
        orderService.getOrderProductList("0", userID, mOrderObject.getOrderID()).enqueue(new Callback<CustomerOrderProductResponse>() {
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
                            showDialog(data.getMsg());
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<CustomerOrderProductResponse> call, @NotNull Throwable t) {
                if(t instanceof SocketTimeoutException){
                    showDialog("", getString(R.string.network_slow));
                } else {
                    showDialog("", t.getMessage());
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //MyOrdersViewModel viewModel = new ViewModelProvider(requireActivity()).get(MyOrdersViewModel.class);
        // Inflate the layout for this fragment
        LoggerInfo.printLog("Fragment", "ViewFullOrderFragment");
        return inflater.inflate(R.layout.fragment_view_full_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userID = MyProfile.getInstance().getUserID();

        recyclerView = view.findViewById(R.id.product_list);
        mCancelOrderBtn = view.findViewById(R.id.cancel_order);
        mCancelOrderBtn.setOnClickListener(this);
        mAcceptOrderBtn = view.findViewById(R.id.accept_order);
        mAcceptOrderBtn.setOnClickListener(this);
        tvStatusComments = view.findViewById(R.id.status_comments);

        // Customer Info
        customerName = view.findViewById(R.id.customer_name);
        customerNumber = view.findViewById(R.id.customer_number);
        customerAddress = view.findViewById(R.id.customer_address);

        // Vendor Info
        view.findViewById(R.id.vendor_details_root).setVisibility(View.GONE);
       /* vendorName = view.findViewById(R.id.vendor_name);
        vendorNumber = view.findViewById(R.id.vendor_number);
        vendorAddress = view.findViewById(R.id.vendor_address);*/

        tvStatus = view.findViewById(R.id.status);
        dateValue = view.findViewById(R.id.date_value);
        orderIdValue = view.findViewById(R.id.order_id_value);

       // delivery boy info
        deliveryBoyInfo = view.findViewById(R.id.delivery_boy_info);
        deliveryBoyInfo.setVisibility(View.GONE);
        deliveryBoyName = view.findViewById(R.id.delivery_boy_name);
        deliveryBoyName.setVisibility(View.GONE);
        deliveryBoyNumber = view.findViewById(R.id.delivery_boy_number);
        deliveryBoySpinner = view.findViewById(R.id.delivery_boy_spinner);
        deliveryBoySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDeliveryBoy = (ProfileResponse) deliveryBoyList.get(i);
                deliveryBoyNumber.setText(selectedDeliveryBoy.getMobileNumber());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Payment info
        tvAmount = view.findViewById(R.id.amount);
        tvDeliveryCharges = view.findViewById(R.id.delivery_charges);
        tvTotalCharges = view.findViewById(R.id.total_charges);
        tvPaymentType = view.findViewById(R.id.payment_type);
        footerView = view.findViewById(R.id.bottom);

    }
    /*void setValuesToUI() {
        orderIdValue.setText(mOrderObject.getReceiptNumber());
        dateValue.setText(mOrderObject.getOrderDate());
    }*/

    void updateUI() {
        Resources res = getResources();
        if (orderProductList.getOrderInfo().getStatus().equalsIgnoreCase(Utils.CANCEL_BY_RETAILER)|| orderProductList.getOrderInfo().getStatus().equalsIgnoreCase(Utils.CANCEL_BY_CUSTOMER)) {
            tvStatus.setText(getString(R.string.cancel_status) + orderProductList.getOrderInfo().getStatusName());
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
        dateValue.setText(orderProductList.getOrderInfo().getOrderDate().split(" ")[0]);

        // vendor
        /*text = orderProductList.getVendorInfo().getFirstName()+" "+ orderProductList.getVendorInfo().getLastName();
        vendorName.setText(text);
        vendorNumber.setText(orderProductList.getVendorInfo().getMobileNumber());
        vendorAddress.setText(orderProductList.getVendorInfo().getCompleteAddress());*/

        // Customer info
        String text = orderProductList.getCustomerInfo().getFirstName()+" "+ orderProductList.getCustomerInfo().getLastName();
        customerName.setText(text);
        customerNumber.setText(orderProductList.getCustomerInfo().getCustomerNumber());
        customerAddress.setText(orderProductList.getCustomerInfo().getCompleteAddress());

        // payment info
        tvAmount.setText(Utils.roundOffDoubleValue(orderProductList.getOrderInfo().getOrderAmount(), "0.00"));
        double deliveryCharges = orderProductList.getOrderInfo().getOrderCharges();
        tvDeliveryCharges.setText(Utils.roundOffDoubleValue(deliveryCharges, "0.00"));
        tvTotalCharges.setText(Utils.roundOffDoubleValue(orderProductList.getOrderInfo().getTotalAmt(), "0.00"));
        tvPaymentType.setText(orderProductList.getOrderInfo().getModeOfPayment());
        setFooter();
        ProductListAdapter productAdapter = new ProductListAdapter(requireActivity(), orderProductList.getProduct(), null);
        recyclerView.setAdapter(productAdapter);
        // setValuesToUI();
    }
    private void setFooter() {
        String status = orderProductList.getOrderInfo().getStatus();
        if(status.contains(Utils.OPEN_ORDER_STATUS)) {
            isOpenOrder();
        } else if(status.contains(Utils.ACCEPTED_ORDER_STATUS)) {
            isAcceptedOrder();
        } else if(status.contains(Utils.PACKED_ORDER_STATUS)) {
            isPackedOrder();
        } else if(status.contains(Utils.SHIPPED_ORDER_STATUS)) {
            isShippedOrder();
        } else if(status.contains(Utils.DELIVERED_ORDER_STATUS)) {
            isDeliveredOrder();
        } else if(status.contains(Utils.CANCEL_BY_CUSTOMER)) {
            isReturnedOrder();
        } else if(status.contains(Utils.CANCEL_BY_RETAILER)) {
            isCanceledOrder();
        }
        footerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        String text = ((AppCompatButton)view).getText().toString();
        if (text.contains(getResources().getString(R.string.accept))) {
            updateOrderStatus(getResources().getString(R.string.accepted), Utils.ACCEPTED_ORDER_STATUS, "");
            updateUI();
        } else if (text.contains(getResources().getString(R.string.packed))) {
            updateOrderStatus(getResources().getString(R.string.packed), Utils.PACKED_ORDER_STATUS, "");
            updateUI();
        } else if (text.contains(getResources().getString(R.string.shipped))) {
            updateOrderStatus(getResources().getString(R.string.shipped), Utils.SHIPPED_ORDER_STATUS, "");
            updateUI();
        } else if (text.contains(getResources().getString(R.string.delivered))) {
            updateOrderStatus(getResources().getString(R.string.delivered), Utils.DELIVERED_ORDER_STATUS, "");
            updateUI();
        } else if (text.contains(getResources().getString(R.string.returned))) {
            updateOrderStatus(getResources().getString(R.string.canceled), Utils.CANCEL_BY_CUSTOMER, "");
            updateUI();
        } else {
            SelectOrderStatusView selectOrderStatus = SelectOrderStatusView.getInstance();
            selectOrderStatus.setCallBackListener(pObject -> {
                if (pObject instanceof String) {
                    String reason = (String) pObject;
                    updateOrderStatus(getResources().getString(R.string.canceled), Utils.CANCEL_BY_RETAILER, reason);
                }
            });
            if (!requireActivity().isFinishing()) {
                selectOrderStatus.show(requireActivity().getSupportFragmentManager(), SelectOrderStatusView.class.getName());
            }
        }
    }

    private void updateOrderStatus(String newStatus, String newStatusID, String reason) {
        if (newStatusID.equalsIgnoreCase(Utils.DELIVERED_ORDER_STATUS)) {

                if (Utils.isNetworkConnected(requireActivity())) {
                    progressDialog.show();
                    OrderService orderService = RetrofitClientInstance.getRetrofitInstance().create(OrderService.class);
                    String retailerID = "", deliveryBoy = "";
                    if (MyProfile.getInstance().getRoleID().equalsIgnoreCase(Utils.RETAILER_ID)) {
                        retailerID = MyProfile.getInstance().getUserID();
                        deliveryBoy = retailerID;
                        if (null != selectedDeliveryBoy) {
                            deliveryBoy = selectedDeliveryBoy.getUserID();
                        }
                    } else {
                        retailerID = MyProfile.getInstance().getVendorInfo().getRoleID();
                        deliveryBoy = MyProfile.getInstance().getUserID();
                    }
                    if ((deliveryBoyList.size()<=0) || (deliveryBoy != null && deliveryBoy.length()>0 )) {
                        orderService.updateOrderStatus(mOrderObject.getOrderID(), retailerID, newStatusID, reason, deliveryBoy).enqueue(new Callback<UpdatedOrderStatus>() {
                            @Override
                            public void onResponse(@NotNull Call<UpdatedOrderStatus> call, @NotNull Response<UpdatedOrderStatus> response) {
                                if (response.isSuccessful()) {
                                    UpdatedOrderStatus data = response.body();
                                    if (data != null) {
                                        if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                            String text = String.format(getString(R.string.status_update), newStatus);
                                            showDialog(data.getStatus(), text, ((dialogInterface, i) -> requireActivity().onBackPressed()));
                                        } else {
                                            showDialog(data.getMsg());
                                        }
                                    } else {
                                        showDialog(getString(R.string.no_information_available));
                                    }
                                } else {
                                    showDialog(response.message());
                                }
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(@NotNull Call<UpdatedOrderStatus> call, @NotNull Throwable t) {
                                if(t instanceof SocketTimeoutException){
                                    showDialog("", getString(R.string.network_slow));
                                } else {
                                    showDialog("", t.getMessage());
                                }
                                progressDialog.dismiss();
                            }
                        });
                    } else {
                        showDialog(getString(R.string.error_select_delivery_boy));
                    }
                } else {
                    showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
                }
        } else {
            if (Utils.isNetworkConnected(requireActivity())) {
                progressDialog.show();
                OrderService orderService = RetrofitClientInstance.getRetrofitInstance().create(OrderService.class);
                orderService.updateOrderStatus(mOrderObject.getOrderID(), MyProfile.getInstance().getUserID(), newStatusID, reason).enqueue(new Callback<UpdatedOrderStatus>() {
                    @Override
                    public void onResponse(@NotNull Call<UpdatedOrderStatus> call, @NotNull Response<UpdatedOrderStatus> response) {
                        if (response.isSuccessful()) {
                            UpdatedOrderStatus data = response.body();
                            if (data != null) {
                                if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                    String text = String.format(getString(R.string.status_update), newStatus);
                                    showDialog(data.getStatus(), text, ((dialogInterface, i) -> requireActivity().onBackPressed()));
                                } else {
                                    showDialog(data.getMsg());
                                }
                            } else {
                                showDialog(getString(R.string.no_information_available));
                            }
                        } else {
                            showDialog(response.message());
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NotNull Call<UpdatedOrderStatus> call, @NotNull Throwable t) {
                        if(t instanceof SocketTimeoutException){
                            showDialog("", getString(R.string.network_slow));
                        } else {
                            showDialog("", t.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                });
            } else {
                showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
            }
        }
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

    private void isOpenOrder() {
        mCancelOrderBtn.setBackgroundResource(R.drawable.btn_bg_canceled);
        mCancelOrderBtn.setText(R.string.cancel);

        mAcceptOrderBtn.setBackgroundResource(R.drawable.btn_bg_accepted);
        mAcceptOrderBtn.setText(R.string.accept);
    }

    private void isCanceledOrder() {
        mCancelOrderBtn.setVisibility(View.GONE);
        mAcceptOrderBtn.setVisibility(View.GONE);
    }

    private void isAcceptedOrder() {
        mAcceptOrderBtn.setBackgroundResource(R.drawable.btn_bg_shipped);
        mAcceptOrderBtn.setText(R.string.shipped);
        mCancelOrderBtn.setBackgroundResource(R.drawable.btn_bg_packed);
        mCancelOrderBtn.setText(R.string.packed);
    }

    private void isPackedOrder() {
        mCancelOrderBtn.setBackgroundResource(R.drawable.btn_bg_shipped);
        mCancelOrderBtn.setText(R.string.shipped);
        mAcceptOrderBtn.setVisibility(View.GONE);
    }

    private void isShippedOrder() {
        mAcceptOrderBtn.setBackgroundResource(R.drawable.btn_bg_delivered);
        mAcceptOrderBtn.setText(R.string.delivered);
        mCancelOrderBtn.setBackgroundResource(R.drawable.btn_bg_canceled);
        mCancelOrderBtn.setText(R.string.cancel);
        deliveryBoyInfo.setVisibility(View.VISIBLE);
        if (MyProfile.getInstance().getRoleID().equals(Utils.DELIVERY_ID)) {
            deliveryBoyNumber.setVisibility(View.VISIBLE);
            deliveryBoySpinner.setVisibility(View.GONE);
            deliveryBoyNumber.setText(MyProfile.getInstance().getMobileNumber());
            deliveryBoyName.setText(MyProfile.getInstance().getFirstName()+" "+MyProfile.getInstance().getLastName());
            deliveryBoyName.setVisibility(View.VISIBLE);
        } else {
            progressDialog.show();
            OrderService orderService = RetrofitClientInstance.getRetrofitInstance().create(OrderService.class);
            orderService.getDeliveryBoyList(MyProfile.getInstance().getMobileNumber(), MyProfile.getInstance().getUserID()).enqueue(new Callback<DeliveryBoyList>() {
                @Override
                public void onResponse(@NotNull Call<DeliveryBoyList> call, @NotNull Response<DeliveryBoyList> response) {
                    if (response.isSuccessful()) {
                        DeliveryBoyList data = response.body();
                        deliveryBoyList.clear();
                        ArrayList<ProfileResponse> _deliveryBoyList = data.getDeliveryBoys();
                        if (_deliveryBoyList.size() > 0) {
                            deliveryBoyList.addAll(_deliveryBoyList);
                            deliveryBoyAdapter = new CustomSpinnerAdapter(requireActivity(), deliveryBoyList);
                            deliveryBoySpinner.setAdapter(deliveryBoyAdapter);
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<DeliveryBoyList> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }
    void isDeliveredOrder() {
        mCancelOrderBtn.setVisibility(View.GONE);
        mAcceptOrderBtn.setVisibility(View.GONE);
        deliveryBoyInfo.setVisibility(View.VISIBLE);
        deliveryBoyName.setVisibility(View.VISIBLE);
        deliveryBoyNumber.setVisibility(View.VISIBLE);
        deliveryBoySpinner.setVisibility(View.GONE);
        deliveryBoyNumber.setText(orderProductList.getDeliveryBoyInfo().getMobileNumber());
        String deliveryBoyDetails = String.format("%s %s", orderProductList.getDeliveryBoyInfo().getFirstName(), orderProductList.getDeliveryBoyInfo().getLastName());
        deliveryBoyName.setText(deliveryBoyDetails);
    }
    void isReturnedOrder() {
        mCancelOrderBtn.setVisibility(View.GONE);
        mAcceptOrderBtn.setVisibility(View.GONE);
    }
}