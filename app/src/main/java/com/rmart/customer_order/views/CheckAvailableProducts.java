package com.rmart.customer_order.views;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer_order.adapters.ProductListAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.customer_orders.CustomerOrderProductList;
import com.rmart.utilits.pojos.customer_orders.CustomerOrderProductResponse;
import com.rmart.utilits.pojos.orders.Product;
import com.rmart.utilits.services.CustomerOrderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckAvailableProducts extends BaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CustomerOrderProductList order;
    private String mParam2;

    private AppCompatTextView vendorName, vendorNumber, vendorAddress,
            tvAmount, tvDeliveryCharges, tvTotalCharges, tvPaymentType,
            customerName, customerNumber, customerAddress;
    private ProductListAdapter productAdapter;
    private RecyclerView recyclerView;
    public CheckAvailableProducts() {
        // Required empty public constructor
    }

    public static CheckAvailableProducts newInstance(CustomerOrderProductList order, String param2) {
        CheckAvailableProducts fragment = new CheckAvailableProducts();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, order);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            order = (CustomerOrderProductList) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_full_order, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.check_available_products));
        // getServerData();
    }
    private void getServerData() {
        progressDialog.show();
        CustomerOrderService customerOrderService = RetrofitClientInstance.getRetrofitInstance().create(CustomerOrderService.class);
        customerOrderService.getUpdatedProductDetails("135", "9000000000", "257"/*order.getOrderInfo().getOrderID(), MyProfile.getInstance().getMobileNumber(), order.getVendorInfo().getMobileNumber()*/).enqueue(new Callback<CustomerOrderProductResponse>() {
            @Override
            public void onResponse(Call<CustomerOrderProductResponse> call, Response<CustomerOrderProductResponse> response) {
                if(response.isSuccessful()) {
                    CustomerOrderProductResponse data = response.body();
                    assert data != null;
                    if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                        order  = data.getProductList();
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
    void updateUI() {
        Resources res = getResources();
        // setFooter();
        // vendor
        String text = order.getVendorInfo().getFirstName()+" "+ order.getVendorInfo().getLastName();
        vendorName.setText(text);
        vendorNumber.setText(order.getVendorInfo().getMobileNumber());
        vendorAddress.setText(order.getVendorInfo().getCompleteAddress());

        // payment info
        /*tvAmount.setText(order.getOrderInfo().getOrderAmount());
        text = order.getOrderInfo().getOrderCharges();
        tvDeliveryCharges.setText(text);
        tvTotalCharges.setText(order.getOrderInfo().getTotalAmt());
        tvPaymentType.setText(order.getOrderInfo().getModeOfPayment());*/

        List<Product> orderedProductsList = order.getProduct();
        if (orderedProductsList != null && !orderedProductsList.isEmpty()) {
            List<Object> lUpdatedProductsList = new ArrayList<>(orderedProductsList);
            productAdapter = new ProductListAdapter(requireActivity(), lUpdatedProductsList);
            productAdapter.setOnClickListener(this);
            recyclerView.setAdapter(productAdapter);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.order_status).setVisibility(View.GONE);
        view.findViewById(R.id.status).setVisibility(View.GONE);

        //Payment info
        tvAmount = view.findViewById(R.id.amount);
        tvDeliveryCharges = view.findViewById(R.id.delivery_charges);
        tvTotalCharges = view.findViewById(R.id.total_charges);
        tvPaymentType = view.findViewById(R.id.payment_type);

        //Vendor Info
        vendorName = view.findViewById(R.id.vendor_name);
        vendorNumber = view.findViewById(R.id.vendor_number);
        vendorAddress = view.findViewById(R.id.vendor_address);

        // Customer Info
        customerName = view.findViewById(R.id.customer_name);
        customerNumber = view.findViewById(R.id.customer_number);
        customerAddress = view.findViewById(R.id.customer_address);

    }

    @Override
    public void onClick(View view) {

    }
}