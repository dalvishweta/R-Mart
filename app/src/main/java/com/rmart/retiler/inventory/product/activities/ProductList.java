package com.rmart.retiler.inventory.product.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
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
import com.rmart.retiler.inventory.brand.activities.BrandFilterActivity;
import com.rmart.retiler.inventory.brand.model.Brand;
import com.rmart.retiler.inventory.category.activities.CategoryFilterActivity;
import com.rmart.retiler.inventory.category.model.Category;
import com.rmart.retiler.inventory.product.adapters.ProductSearchListAdapter;
import com.rmart.retiler.inventory.product.model.ProductListResponse;
import com.rmart.retiler.inventory.product.viewmodel.ProductViewModel;
import com.rmart.retiler.product.view.AddNewProductActivity;
import com.rmart.utilits.GridSpacesItemDecoration;
import com.rmart.utilits.Utils;

import java.util.ArrayList;

import static com.rmart.inventory.views.SelectProductFromInventory.REQUEST_FILTERED_DATA_ID;

public class ProductList extends BaseInventoryFragment {

    ActivityProductlistRetailerBinding binding;
    ProductSearchListAdapter  productSearchListAdapter;
    final int CATEGORY_REQUEST=2;
    final int BRAND_REQUEST=3;
    int page=0;
    int total_product_count= 0;
    ProductViewModel productViewModel;
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
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_productlist_retailer, container, false);
        productViewModel.getProductList( page+"");
        binding.setProductViewModel(productViewModel);
        binding.setLifecycleOwner(this);
        binding.addCustomProduct.tvProductType.setText("Custom Product");
        binding.addCustomProduct.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddNewProductActivity.class);
                startActivity(intent);
            }
        });

        binding.rvBrands.addItemDecoration(new GridSpacesItemDecoration(15));
        binding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(requireActivity(), binding.filter);
                popup.getMenu().add(Menu.NONE, 4, 4, Utils.CATEGORY);
                popup.getMenu().add(Menu.NONE, 3, 3, Utils.BRAND);
                popup.getMenu().add(Menu.NONE, 5, 5, "Clear Filter");
                popup.show();
                popup.setOnMenuItemClickListener(item -> {

                    if(item.getTitle().toString().equalsIgnoreCase(Utils.CATEGORY)){
                        Intent intent=new Intent(getContext(), CategoryFilterActivity.class);
                        startActivityForResult(intent, CATEGORY_REQUEST);
                    }
                    if(item.getTitle().toString().equalsIgnoreCase(Utils.BRAND)) {
                        Intent intent=new Intent(getContext(), BrandFilterActivity.class);
                        startActivityForResult(intent, BRAND_REQUEST);

                    }
                    if(item.getTitle().toString().equalsIgnoreCase("Clear Filter")){
                        productViewModel.categoryID.setValue(null);
                        productViewModel.brandID.setValue(null);
                        page=0;
                        productSearchListAdapter.products.clear();
                        productSearchListAdapter.notifyDataSetChanged();
                        productViewModel.getProductList( page+"");
                    }

                    return true;
                });
            }
        });
        productSearchListAdapter = new ProductSearchListAdapter(getActivity(),new ArrayList<>(),mListener);
        binding.rvBrands.setAdapter(productSearchListAdapter);
        //
        productViewModel.productListResponseMutableLiveData.observeForever(new Observer<ProductListResponse>() {
            @Override
            public void onChanged(ProductListResponse productListResponse) {
                try {
                    if(page==0) {
                        productSearchListAdapter.products.clear();
                        productSearchListAdapter.notifyDataSetChanged();
                    }
                    productSearchListAdapter.addProducts(productListResponse.getProduct());
                    total_product_count = productListResponse.getTotal_count();

                } catch (Exception e){

                }
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

                if (!productViewModel.isLoading.getValue()) {
                    if (productViewModel.productListResponseMutableLiveData.getValue().isNext_value()) {
                        page++;
                        productViewModel.getProductList( page+"");

                    }
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(Activity.RESULT_OK==resultCode) {
           if (requestCode == CATEGORY_REQUEST) {
             Category category = (Category) data.getExtras().getSerializable("data");
             productViewModel.categoryID.setValue(category);
           }
           if (requestCode == BRAND_REQUEST) {
               Brand  brand= (Brand) data.getExtras().getSerializable("data");
               productViewModel.brandID.setValue(brand);

           }
           page=0;

           productViewModel.getProductList( page+"");
       }


    }
}
