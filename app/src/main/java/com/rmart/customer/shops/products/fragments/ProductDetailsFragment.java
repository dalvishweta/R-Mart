package com.rmart.customer.shops.products.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.customer.models.ProductDetailsDescModel;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.customer.shops.products.adapters.UnitAdapter;
import com.rmart.customer.shops.products.model.ProductDetailsDescResponse;
import com.rmart.customer.shops.products.viewmodel.ProductDetailsViewModel;
import com.rmart.customer.views.CustomerHomeActivity;
import com.rmart.databinding.ProductDetailsFragmentBinding;
import com.rmart.inventory.adapters.ImageAdapter;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.ImageURLResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsFragment extends Fragment {

    private ProductDetailsViewModel mViewModel;
    private ShopDetailsModel vendorShopDetails;
    private ProductData vendorProductDataDetails;
    private OnCustomerHomeInteractionListener onCustomerHomeInteractionListener;
    ProductDetailsFragmentBinding binding;
    UnitAdapter unitAdapter;


    public static ProductDetailsFragment getInstance2(ProductData vendorProductDataDetails, ShopDetailsModel vendorShopDetails) {
        ProductDetailsFragment productCartDetailsFragment = new ProductDetailsFragment();
        Bundle extras = new Bundle();
        extras.putSerializable("ProductCartDetails", vendorProductDataDetails);
        extras.putSerializable("VendorShopDetails", vendorShopDetails);
        productCartDetailsFragment.setArguments(extras);
        return productCartDetailsFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            vendorProductDataDetails = (ProductData) extras.getSerializable("ProductCartDetails");
            vendorShopDetails = (ShopDetailsModel) extras.getSerializable("VendorShopDetails");
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_details_fragment, container, false);
        binding.actualprice.setPaintFlags(binding.actualprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        mViewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);
        mViewModel.vendorProductDataDetails.setValue(vendorProductDataDetails);
        mViewModel.vendorShopDetails.setValue(vendorShopDetails);
        binding.setProductDetailsViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        binding.btnMinusField.setOnClickListener(v -> {
            if (mViewModel.customerProductsDetailsUnitModelMutableLiveData.getValue().getTotalProductCartQty() > 1) {
                mViewModel.addToCart(binding.btnMinusField,0);
            }
        });

        binding.btnAddField.setOnClickListener(v -> {

            if (mViewModel.customerProductsDetailsUnitModelMutableLiveData.getValue().getTotalProductCartQty() < mViewModel.customerProductsDetailsUnitModelMutableLiveData.getValue().getProductUnitQuantity()) {
                mViewModel.addToCart(binding.btnMinusField,1);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.getProductDetails(getContext());

        binding.viewPager.startAutoScroll();
        binding.viewPager.setInterval(1000);
        binding.viewPager.setCycle(true);
        binding.viewPager.setStopScrollWhenTouch(true);

        mViewModel.customerProductsDetailsUnitModelMutableLiveData.observeForever(customerProductsDetailsUnitModel -> {
            if (unitAdapter != null) {
                unitAdapter.setSelectedCustomerProductsDetailsUnitModel(customerProductsDetailsUnitModel);
            }
        });
        mViewModel.productDetailsDescResponseMutableLiveData.observeForever(new Observer<ProductDetailsDescResponse>() {
            @Override
            public void onChanged(ProductDetailsDescResponse productDetailsDescResponse) {

                showProductImage(productDetailsDescResponse);
                unitAdapter = new UnitAdapter(getActivity(), (ArrayList<CustomerProductsDetailsUnitModel>) productDetailsDescResponse.getProductDetailsDescProductDataModel().getProductDetailsDescModel().getUnits(), category -> {
                    mViewModel.customerProductsDetailsUnitModelMutableLiveData.setValue(category);
                });
                binding.setUnitAdapter(unitAdapter);
                List<CustomerProductsDetailsUnitModel> units = productDetailsDescResponse.getProductDetailsDescProductDataModel().getProductDetailsDescModel().getUnits();
                if(units!=null){
                    for (int i = units.size()-1;i>=0;i-- ) {
                        CustomerProductsDetailsUnitModel unitModel =  units.get(i);
                        mViewModel.customerProductsDetailsUnitModelMutableLiveData.setValue(unitModel);
                        if(unitModel.getProductDiscount()>0) {

                            break;
                        } else {

                        }
                    }

                }
            }
        });



    }

    private void showProductImage(ProductDetailsDescResponse productDetailsDescResponse) {
        ProductDetailsDescModel productDetailsDescModel = productDetailsDescResponse.getProductDetailsDescProductDataModel().getProductDetailsDescModel();
        if (productDetailsDescModel != null) {
            List<String> productsImagesList = new ArrayList<>(productDetailsDescModel.getProductImage());
            if (!productsImagesList.isEmpty()) {
                List<ImageURLResponse> lUpdatedImagesList = new ArrayList<>();
                for (String imageUrl : productsImagesList) {
                    ImageURLResponse imageURLResponse = new ImageURLResponse();
                    imageURLResponse.setDisplayImage(imageUrl);
                    imageURLResponse.setProductVideoSelected(false);
                    lUpdatedImagesList.add(imageURLResponse);
                }
                String videoLink = productDetailsDescModel.getProductVideoLink();
                if (!TextUtils.isEmpty(videoLink)) {
                    if (Utils.isValidYoutubeUrl(videoLink)) {
                        String productVideoUrl = Utils.getYoutubeThumbnailUrlFromVideoUrl(videoLink);
                        if (!TextUtils.isEmpty(productVideoUrl)) {
                            ImageURLResponse imageURLResponse = new ImageURLResponse();
                            imageURLResponse.setDisplayImage(productVideoUrl);
                            imageURLResponse.setImageURL(videoLink);
                            imageURLResponse.setProductVideoSelected(true);
                            lUpdatedImagesList.add(imageURLResponse);
                        }
                    }
                }
                try {
                    ImageAdapter imageAdapter = new ImageAdapter(requireActivity(), lUpdatedImagesList);
                    imageAdapter.setCallBackListener(pObject -> {
                        if (pObject instanceof ImageURLResponse) {
                            ImageURLResponse imageURLResponse = (ImageURLResponse) pObject;
                            showProductPreviewSelected(imageURLResponse.getImageURL());
                        }
                    });
                    binding.viewPager.setAdapter(imageAdapter);
                    binding.productImagesDotIndicatorField.setVisibility(lUpdatedImagesList.size() == 1 ? View.GONE : View.VISIBLE);
                    binding.productImagesDotIndicatorField.setupWithViewPager(binding.viewPager);
                } catch ( Exception e ){

                }
            }
        }
    }

    private void showProductPreviewSelected(String productVideoLink) {
        if (!TextUtils.isEmpty(productVideoLink)) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(productVideoLink));
            try {
                this.startActivity(webIntent);
            } catch (ActivityNotFoundException ex) {

            }
        } else {

        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnCustomerHomeInteractionListener) {
            onCustomerHomeInteractionListener = (OnCustomerHomeInteractionListener) context;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        updateToolBar();
    }

    public void updateToolBar() {
        requireActivity().setTitle(vendorShopDetails.getShopName());
        ((CustomerHomeActivity)(requireActivity())).showCartIcon();
    }


}