package com.rmart.inventory.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.inventory.adapters.CategoryAdapter;
import com.rmart.inventory.viewmodel.InventoryViewModel;

import java.util.Objects;

public class MyCategoryListFragment extends BaseInventoryFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final boolean isGridView = false;

    private String mParam1;
    private String mParam2;
    private RecyclerView categoryRecycleView;
    private AppCompatTextView tvTotalCount;
    private InventoryViewModel inventoryViewModel;

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
        inventoryViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(InventoryViewModel.class);
        return inflater.inflate(R.layout.fragment_inventory_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryRecycleView = view.findViewById(R.id.category_list);
        tvTotalCount = view.findViewById(R.id.category_count);
        tvTotalCount.setText(String.format(getResources().getString(R.string.total_categories), Objects.requireNonNull(inventoryViewModel.getCategories().getValue()).keySet().size()));
        view.findViewById(R.id.sort).setOnClickListener(param -> {
                    PopupMenu popup = new PopupMenu(Objects.requireNonNull(getActivity()), param);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.inventory_view_category, popup.getMenu());
                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(item -> {
                        if (item.getItemId() == R.id.sort_category) {
                            inventoryViewModel.setIsProductView(InventoryViewModel.CATEGORY);
                            mListener.goToHome();
                        } else if (item.getItemId() == R.id.sort_sub_category) {
                            inventoryViewModel.setIsProductView(InventoryViewModel.SUB_CATEGORY);
                            mListener.goToHome();
                        } else if (item.getItemId() == R.id.sort_product) {
                            inventoryViewModel.setIsProductView(InventoryViewModel.PRODUCT);
                            mListener.goToHome();
                        }
                        return true;
                    });

                    popup.show(); //showing popup menu

                });
        CategoryAdapter categoryAdapter = new CategoryAdapter(isGridView, view1 -> mListener.showMySubCategories());
        if(isGridView) {
            categoryRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            categoryRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        categoryRecycleView.setAdapter(categoryAdapter);
    }

}