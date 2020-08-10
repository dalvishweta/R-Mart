package com.rmart.orders.views;

import android.content.Context;

import androidx.annotation.NonNull;

import com.rmart.baseclass.views.BaseFragment;
import com.rmart.orders.OnOrdersInteractionListener;

public class BaseOrderFragment extends BaseFragment {
    OnOrdersInteractionListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnOrdersInteractionListener) {
            mListener = (OnOrdersInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
