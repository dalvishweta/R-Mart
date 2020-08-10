package com.rmart.inventory.views;

import android.content.Context;

import androidx.annotation.NonNull;

import com.rmart.baseclass.views.BaseFragment;
import com.rmart.inventory.OnInventoryClickedListener;

public class BaseInventoryFragment extends BaseFragment {
    OnInventoryClickedListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnInventoryClickedListener)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener =null;
    }
}
