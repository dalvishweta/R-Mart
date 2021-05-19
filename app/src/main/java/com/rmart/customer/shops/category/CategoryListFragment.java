package com.rmart.customer.shops.category;

import android.app.Activity;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.customer.shops.category.model.CategoryBaseResponse;
import com.rmart.customer.shops.category.viewmodel.ProductCategoryViewModel;
import com.rmart.customer.shops.home.adapters.CategoryAdapter;
import com.rmart.customer.shops.home.listner.OnClickListner;
import com.rmart.customer.shops.home.model.Category;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.customer.shops.products.fragments.ProductListFragment;
import com.rmart.customer.views.ProductCartDetailsFragment;
import com.rmart.databinding.FragmentCategoryListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CATEGORY = "category";
    private static final String ARG_SHOPDETAILS = "venderID";

    // TODO: Rename and change types of parameters
    private Category category;

    private ShopDetailsModel shopDetailsModel;

    public CategoryListFragment() {
        // Required empty public constructor
    }

    public static CategoryListFragment newInstance(Category category,  ShopDetailsModel shopDetailsModel) {
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY, category);
        args.putSerializable(ARG_SHOPDETAILS, shopDetailsModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = (Category) getArguments().getSerializable(ARG_CATEGORY);
            shopDetailsModel = (ShopDetailsModel) getArguments().getSerializable(ARG_SHOPDETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentCategoryListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_list, container, false);
        ProductCategoryViewModel shopHomeViewModel = ViewModelProviders.of(this).get(ProductCategoryViewModel.class);
        binding.setProductCategoryViewModel(shopHomeViewModel);
        binding.setLifecycleOwner(this);
        shopHomeViewModel.loadCategoryData(getContext(),shopDetailsModel.getVendorId()+"",category);
        binding.categoryList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ((BaseNavigationDrawerActivity)getActivity()).setTitle(category.categoryName);

        shopHomeViewModel.productCategoryMutableLiveData.observeForever(categoryBaseResponse -> {

            if(categoryBaseResponse.getStatus()==200) {
                CategoryAdapter categoryAdapter = new CategoryAdapter((Activity) getContext(), categoryBaseResponse.getResult().getCategory(), new OnClickListner() {
                    @Override
                    public void onCategorySelected(Category category) {
                        ProductListFragment baseFragment = ProductListFragment.newInstance(shopDetailsModel, category, null);
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.base_container, baseFragment, ProductCartDetailsFragment.class.getName());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void onViewSelected(String viewmoreType) {

                    }

                    @Override
                    public void onProductSelected(ProductData productData) {

                    }
                }, CategoryAdapter.HOMEPAGECATEGORY);
                binding.categoryList.setAdapter(categoryAdapter);
            }
        });
        return  binding.getRoot();
    }
}