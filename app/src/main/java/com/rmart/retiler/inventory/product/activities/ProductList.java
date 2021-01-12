package com.rmart.retiler.inventory.product.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.databinding.ActivityProductlistRetailerBinding;
import com.rmart.inventory.views.BaseInventoryFragment;
import com.rmart.inventory.views.MyProductsListFragment;
import com.rmart.retiler.inventory.product.adapters.ProductSearchListAdapter;
import com.rmart.retiler.inventory.product.model.ProductListResponse;
import com.rmart.retiler.inventory.product.viewmodel.ProductViewModel;
import com.rmart.utilits.GridSpacesItemDecoration;

import java.util.ArrayList;

public class ProductList extends BaseInventoryFragment {

    ActivityProductlistRetailerBinding binding;

    int page=0;
    int total_product_count= 0;
    public ProductList() {
        // Required empty public constructor
    }

    public static ProductList newInstance() {
        ProductList fragment = new ProductList();

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.my_product_list));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ProductViewModel productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_productlist_retailer, container, false);
        productViewModel.getProductList( page+"");
        binding.setProductViewModel(productViewModel);
        binding.setLifecycleOwner(this);
        binding.rvBrands.addItemDecoration(new GridSpacesItemDecoration(15));

        ProductSearchListAdapter  productSearchListAdapter= new ProductSearchListAdapter(getActivity(),new ArrayList<>(),mListener);
        binding.rvBrands.setAdapter(productSearchListAdapter);
        //
        productViewModel.productListResponseMutableLiveData.observeForever(new Observer<ProductListResponse>() {
            @Override
            public void onChanged(ProductListResponse productListResponse) {
                productSearchListAdapter.addProducts(productListResponse.getProduct());
                total_product_count = Integer.parseInt(productListResponse.getTotal_count());
                page++;
            }
        });
        binding.edtProductSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String text = charSequence.toString();
                if(text!=null && text.length()>0) {

                    productViewModel.searchPhrase.setValue(text);

                } else {
                    productViewModel.searchPhrase.setValue(null);
                }
                page = 0;
                productSearchListAdapter.products.clear();
                productSearchListAdapter.notifyDataSetChanged();
                productViewModel.getProductList( page+"");

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.rvBrands.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) binding.rvBrands.getLayoutManager();


                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (!productViewModel.isLoading.getValue()) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= productSearchListAdapter.products.size() && total_product_count>productSearchListAdapter.products.size()) {

                        productViewModel.getProductList( page+"");

                    }
                }
            }
        });

        return binding.getRoot();
    }

}
