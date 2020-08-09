package com.rmart.inventory.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.inventory.OnInventoryClickedListener;
import com.rmart.inventory.adapters.ProductAdapter;

import java.util.Objects;

public class MyProductsListFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PRODUCT_LIST_TYPE = 1;

    private String mParam1;
    private String mParam2;
    private RecyclerView productRecycleView;
    private OnInventoryClickedListener mListener;

    public MyProductsListFragment() {
        // Required empty public constructor
    }

    public static MyProductsListFragment newInstance(String param1, String param2) {
        MyProductsListFragment fragment = new MyProductsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnInventoryClickedListener)context;
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.my_product_list));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory_product_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productRecycleView = view.findViewById(R.id.product_list);
        ProductAdapter productAdapter = new ProductAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.showProductPreview();
            }
        });
        productRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        productRecycleView.setAdapter(productAdapter);
    }
}