package com.rmart.inventory.views;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.adapters.SubCategoryAdapter;

import java.util.Objects;

import static com.rmart.inventory.views.MyCategoryListFragment.isGridView;

public class MySubCategoriesListFragment extends BaseInventoryFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView subCategoryRecycleView;

    public MySubCategoriesListFragment() {
        // Required empty public constructor
    }

    public static MySubCategoriesListFragment newInstance(String param1, String param2) {
        MySubCategoriesListFragment fragment = new MySubCategoriesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.sub_category_list));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory_sub_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subCategoryRecycleView = view.findViewById(R.id.sub_categories);
        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(isGridView, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.showMyProducts();
            }
        });
        if(isGridView) {
            subCategoryRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            subCategoryRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        subCategoryRecycleView.setAdapter(subCategoryAdapter);
        setSearchView(view, subCategoryAdapter);
    }
    protected void setSearchView(@NonNull View view, SubCategoryAdapter subCategoryAdapter) {
        if(null != subCategoryAdapter) {
            SearchView searchView = view.findViewById(R.id.searchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    subCategoryAdapter.getFilter().filter(query);
                    // Toast.makeText(getActivity(), "onQueryTextSubmit "+query, Toast.LENGTH_LONG).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    subCategoryAdapter.getFilter().filter(newText);
                    return false;
                }
            });
        }
    }
}