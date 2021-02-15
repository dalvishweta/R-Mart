package com.rmart.customer.shops.home.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.shops.home.adapters.ShopHomeAdapter;
import com.rmart.customer.shops.home.listner.OnClickListner;
import com.rmart.customer.shops.home.model.Category;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.home.viewmodel.ShopHomeViewModel;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.customer.shops.products.fragments.ProductDetailsFragment;
import com.rmart.customer.shops.products.fragments.ProductListFragment;
import com.rmart.customer.views.ProductCartDetailsFragment;
import com.rmart.databinding.FragmentShopHomePageBinding;

public class ShopHomePage extends BaseFragment {
    private ShopDetailsModel productsShopDetailsModel;
    private static final String ARG_SHOP = "shop_details";
    private static final String ARG_PRODUCT = "prodcut_details";
    private ShopHomeViewModel shopHomeViewModel;
    private ProductData productData;

    public ShopHomePage() {
        // Required empty public constructor
    }

    public static ShopHomePage newInstance(ShopDetailsModel param1,ProductData productData) {
        ShopHomePage fragment = new ShopHomePage();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SHOP, param1);
        args.putSerializable(ARG_PRODUCT, productData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productsShopDetailsModel = (ShopDetailsModel) getArguments().getSerializable(ARG_SHOP);
            productData = (ProductData) getArguments().getSerializable(ARG_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentShopHomePageBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_home_page, container, false);
        shopHomeViewModel = ViewModelProviders.of(this).get(ShopHomeViewModel.class);
        binding.setShopViewModel(shopHomeViewModel);
        binding.setShopDetails(productsShopDetailsModel);
        binding.setProductData(productData);
        binding.setLifecycleOwner(this);
        shopHomeViewModel.loadShopHomePage(productsShopDetailsModel);



        shopHomeViewModel.shopHomePageResponceMutableLiveData.observeForever(shopHomePageResponce -> {

            ShopHomeAdapter shopHomeAdapter = new ShopHomeAdapter(getActivity(), shopHomePageResponce.results, productsShopDetailsModel, productData,new OnClickListner() {
                @Override
                public void onCategorySelected(Category category) {
                    ProductListFragment baseFragment=  ProductListFragment.newInstance(productsShopDetailsModel,category );
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.base_container, baseFragment, ProductCartDetailsFragment.class.getName());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                @Override
                public void onProductSelected(ProductData productData) {
                    ProductDetailsFragment baseFragment=  ProductDetailsFragment.getInstance2(productData,productsShopDetailsModel );
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.base_container, baseFragment, ProductDetailsFragment.class.getName());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            binding.setMyAdapter(shopHomeAdapter);
        });


        return binding.getRoot();
    }
}