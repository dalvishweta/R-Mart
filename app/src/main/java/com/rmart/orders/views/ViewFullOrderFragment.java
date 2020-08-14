package com.rmart.orders.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.orders.adapters.ProductListAdapter;
import com.rmart.orders.models.OrderObject;

import java.util.Objects;

public class ViewFullOrderFragment extends BaseOrderFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private OrderObject mOrderObject;
    private String mParam2;

    AppCompatButton mLeftButton, mRightButton;
    public ViewFullOrderFragment() {
        // Required empty public constructor
    }


    public static ViewFullOrderFragment newInstance(OrderObject param1, String param2) {
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
            mOrderObject = (OrderObject) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle("Order details");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_full_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.product_list);
        mLeftButton = view.findViewById(R.id.left_button);
        mRightButton = view.findViewById(R.id.right_button);
        setFooter();
        Resources res = getResources();
        String text = String.format(res.getString(R.string.status_order), mOrderObject.getOrderType());
        //Customer Info
        ((AppCompatTextView)view.findViewById(R.id.status)).setText(text);
        ((AppCompatTextView)view.findViewById(R.id.date_value)).setText(mOrderObject.getDate());
        ((AppCompatTextView)view.findViewById(R.id.order_id_value)).setText(mOrderObject.getOrderID());
        //Payment info

        ((AppCompatTextView)view.findViewById(R.id.amount)).setText(mOrderObject.getOrderAmount());
        ((AppCompatTextView)view.findViewById(R.id.delivery_charges)).setText(mOrderObject.getCharges());
        ((AppCompatTextView)view.findViewById(R.id.total_charges)).setText(mOrderObject.getTotalAmount());
        ((AppCompatTextView)view.findViewById(R.id.payment_type)).setText(mOrderObject.getModeType());

        ProductListAdapter productAdapter = new ProductListAdapter(mOrderObject.getProductObjects(), this);
        recyclerView.setAdapter(productAdapter);
    }

    private void setFooter() {
        if(mOrderObject.getOrderType().contains(getResources().getString(R.string.open))) {
            isOpenOrder();
        } else if(mOrderObject.getOrderType().contains(getResources().getString(R.string.accepted))) {
            isAcceptedOrder();
        } else if(mOrderObject.getOrderType().contains(getResources().getString(R.string.packed))) {
            isPackedOrder();
        } else if(mOrderObject.getOrderType().contains(getResources().getString(R.string.shipped))) {
            isShippedOrder();
        } else if(mOrderObject.getOrderType().contains(getResources().getString(R.string.delivered))) {
            isDeliveredOrder();
        } else if(mOrderObject.getOrderType().contains(getResources().getString(R.string.returned))) {
            isReturnedOrder();
        } else if(mOrderObject.getOrderType().contains(getResources().getString(R.string.canceled))) {
            isCanceledOrder();
        }
    }

    @Override
    public void onClick(View view) {
    }

    void updateToCancel() {

    }
    void updateToAccepted() {

    }
    void updateToPacked() {

    }
    void updateToShipped() {

    }
    void updateToDelivered() {

    }
    void updateToRejected() {

    }

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
    }
    void isReturnedOrder() {
        mLeftButton.setVisibility(View.GONE);
        mRightButton.setVisibility(View.GONE);
    }
}