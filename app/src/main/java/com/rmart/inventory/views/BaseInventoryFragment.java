package com.rmart.inventory.views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.inventory.OnInventoryClickedListener;
import com.rmart.inventory.adapters.ProductAdapter;
import com.rmart.inventory.viewmodel.InventoryViewModel;

import java.util.Objects;

public class BaseInventoryFragment extends BaseFragment {
    OnInventoryClickedListener mListener;
    // InventoryViewModel inventoryViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnInventoryClickedListener)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inventoryViewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener =null;
    }
}
