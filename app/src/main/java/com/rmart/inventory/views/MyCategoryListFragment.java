package com.rmart.inventory.views;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.inventory.OnInventoryClickedListener;
import com.rmart.inventory.adapters.CategoryAdapter;

import java.util.Objects;

public class MyCategoryListFragment extends BaseInventoryFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int LIST_TYPE = 1;

    private String mParam1;
    private String mParam2;
    private RecyclerView categoryRecycleView;

    public MyCategoryListFragment() {
        // Required empty public constructor
    }

    public static MyCategoryListFragment newInstance(String param1, String param2) {
        MyCategoryListFragment fragment = new MyCategoryListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.category_list));
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
        return inflater.inflate(R.layout.fragment_inventory_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryRecycleView = view.findViewById(R.id.category_list);
        CategoryAdapter categoryAdapter = new CategoryAdapter(LIST_TYPE, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.showMySubCategories();
            }
        });
        if(LIST_TYPE == 1) {
            categoryRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            categoryRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        categoryRecycleView.setAdapter(categoryAdapter);
    }

}