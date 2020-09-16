package com.rmart.customer_order.views;

import android.content.Context;

import androidx.annotation.NonNull;

import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer_order.OnCustomerOrdersInteractionListener;

public class BaseOrderFragment extends BaseFragment {
    OnCustomerOrdersInteractionListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnCustomerOrdersInteractionListener) {
            mListener = (OnCustomerOrdersInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
