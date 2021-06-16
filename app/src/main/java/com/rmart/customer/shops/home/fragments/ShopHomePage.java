package com.rmart.customer.shops.home.fragments;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.shops.category.CategoryListFragment;
import com.rmart.customer.shops.home.adapters.ShopHomeAdapter;
import com.rmart.customer.shops.home.listner.OnClickListner;
import com.rmart.customer.shops.home.listner.onProdcutClick;
import com.rmart.customer.shops.home.model.Category;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.home.viewmodel.ShopHomeViewModel;
import com.rmart.customer.shops.list.adapters.AllProductsAdapter;
import com.rmart.customer.shops.list.models.ProductSearchResponce;
import com.rmart.customer.shops.list.models.SearchProducts;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.customer.shops.list.repositories.ProductRepository;
import com.rmart.customer.shops.products.fragments.ProductDetailsFragment;
import com.rmart.customer.shops.products.fragments.ProductListFragment;
import com.rmart.customer.views.ProductCartDetailsFragment;
import com.rmart.databinding.FragmentShopHomePageBinding;
import com.rmart.utilits.ActionCall;
import com.rmart.utilits.CommonUtils;
import com.rmart.wallet.billing_history.models.Datum;

import java.util.ArrayList;

public class ShopHomePage extends BaseFragment {
    private ShopDetailsModel productsShopDetailsModel;
    private static final String ARG_SHOP = "shop_details";
    private static final String ARG_PRODUCT = "prodcut_details";
    private ShopHomeViewModel shopHomeViewModel;
    private ProductData productData;
    private String searchShopName = "";
    private OnCustomerHomeInteractionListener onCustomerHomeInteractionListener;

    public ShopHomePage() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCustomerHomeInteractionListener) {
            onCustomerHomeInteractionListener = (OnCustomerHomeInteractionListener) context;
        }
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
        ((BaseNavigationDrawerActivity)getActivity()).setTitle(productsShopDetailsModel.getShopName());
        binding.setShopDetails(productsShopDetailsModel);
        binding.setProductData(productData);
        binding.setLifecycleOwner(this);
        shopHomeViewModel.loadShopHomePage(getContext(),productsShopDetailsModel);
        binding.ivSearchField.setOnClickListener(v -> {
            binding.edtProductSearchField.setText("");
            searchShopName = "";
            CommonUtils.closeVirtualKeyboard(requireActivity(), binding.ivSearchField);
        });
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionCall.createCall(getActivity(),productsShopDetailsModel.getShopMobileNo());

            }
        });

        binding.btnTryagain.setOnClickListener(view -> shopHomeViewModel.loadShopHomePage(getContext(),productsShopDetailsModel));

        shopHomeViewModel.shopHomePageResponceMutableLiveData.observeForever(shopHomePageResponce -> {

            ShopHomeAdapter shopHomeAdapter = new ShopHomeAdapter(getActivity(), shopHomePageResponce.results, productsShopDetailsModel, productData,new OnClickListner() {
                @Override
                public void onCategorySelected(Category category) {
                    if(category.chield_check) {
                        CategoryListFragment baseFragment = CategoryListFragment.newInstance(category, productsShopDetailsModel);
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.base_container, baseFragment, ProductCartDetailsFragment.class.getName());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {
                        ProductListFragment baseFragment=  ProductListFragment.newInstance(productsShopDetailsModel,category,null );
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.base_container, baseFragment, ProductCartDetailsFragment.class.getName());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }

                @Override
                public void onViewSelected(String viewmoreType) {
                    ProductListFragment baseFragment=  ProductListFragment.newInstance(productsShopDetailsModel,null,viewmoreType );
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
        AllProductsAdapter allProductsAdapter  =new AllProductsAdapter(getActivity(), new ArrayList<>(), new onProdcutClick() {
            @Override
            public void onSelected(SearchProducts productData) {
                ;
                ProductData productData1 = new ProductData();
                Log.d("PRODUCTDATA",productData.getProductName());
                productData1.setProductId(productData.getProductId());
                productData1.setProductImage(productData.getDisplayImage());
                productData1.setParentCategoryId(productData.getProductCatId());
                productData1.setProductName(productData.getProductName());
                productData1.setUnits(productData.getProduct_unit());
                onCustomerHomeInteractionListener.gotoVendorProductDetails( productData.getShopDetailsModel(),productData1);

            }
        });
        binding.edtProductSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if( binding.edtProductSearchField.getText().toString().length()>0){
                    binding.progressBar2.setVisibility(View.VISIBLE);
                    binding.searchProductsListField.setVisibility(View.VISIBLE);
                    binding.productsListField.setVisibility(View.GONE);

                    ProductRepository.searchShopProduct(getActivity(),0,0.0,0.0,binding.edtProductSearchField.getText().toString()).observeForever(new Observer<ProductSearchResponce>() {
                        @Override
                        public void onChanged(ProductSearchResponce productSearchResponce) {
                            ArrayList<SearchProducts> searchProductsArrayList = (ArrayList<SearchProducts>) productSearchResponce.data;
                            if(productSearchResponce.getStatus().equalsIgnoreCase("Success")){
                                binding.progressBar2.setVisibility(View.GONE);
                                allProductsAdapter.clear();
                                allProductsAdapter.addProducts(searchProductsArrayList);
                                binding.setMyAdapterSearch(allProductsAdapter);

                            }

                        }
                    });

                }


            }
        });


        return binding.getRoot();
    }

}