package com.rmart.inventory.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.adapters.ProductAdapter;
import com.rmart.inventory.models.Product;
import com.rmart.inventory.viewmodel.InventoryViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class MyProductsListFragment extends BaseInventoryFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PRODUCT_LIST_TYPE = 1;

    private String mParam1;
    private String mParam2;
    private RecyclerView productRecycleView;
    ProductAdapter productAdapter;
    InventoryViewModel inventoryViewModel;
    private AppCompatTextView tvTotalCount;

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
        inventoryViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(InventoryViewModel.class);
        inventoryViewModel.getProductList().observe(getActivity(), products -> {
            if(null != products) {
                updateList(products);
            }
        });
        return inflater.inflate(R.layout.fragment_inventory_product_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productRecycleView = view.findViewById(R.id.product_list);
        tvTotalCount = view.findViewById(R.id.category_count);
        view.findViewById(R.id.sort).setOnClickListener(param -> {
            PopupMenu popup = new PopupMenu(Objects.requireNonNull(getActivity()), param);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.inventory_view_products, popup.getMenu());
            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.sort_category) {
                    inventoryViewModel.setIsProductView(InventoryViewModel.CATEGORY);
                    mListener.goToHome();
                } else if(item.getItemId() == R.id.sort_sub_category) {
                    inventoryViewModel.setIsProductView(InventoryViewModel.SUB_CATEGORY);
                    mListener.goToHome();
                } else if(item.getItemId() == R.id.sort_product) {
                    inventoryViewModel.setIsProductView(InventoryViewModel.PRODUCT);
                    mListener.goToHome();
                }
                return true;
            });

            popup.show(); //showing popup menu
        });
        productRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        updateList(Objects.requireNonNull(inventoryViewModel.getProductList().getValue()));
    }

    private void updateList(ArrayList<Product> products) {
        try {
            tvTotalCount.setText(String.format(getResources().getString(R.string.total_products), products.size()));
            productAdapter = new ProductAdapter(products, view1 -> {
                mListener.showProductPreview((Product)view1.getTag());
            });
            productRecycleView.setAdapter(productAdapter);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
}