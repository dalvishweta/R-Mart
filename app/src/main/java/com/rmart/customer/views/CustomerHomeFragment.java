package com.rmart.customer.views;

import android.content.Context;

import androidx.annotation.NonNull;

import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.OnCustomerHomeInteractionListener;

public class CustomerHomeFragment extends BaseFragment {

    public OnCustomerHomeInteractionListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCustomerHomeInteractionListener) {
            mListener = (OnCustomerHomeInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
