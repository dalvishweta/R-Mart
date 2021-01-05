package com.rmart.customer.order.summary.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.models.CustomerOrderProductOrderedDetails;
import com.rmart.customer.order.summary.adapters.ProductsAdapter;
import com.rmart.customer.order.summary.model.OrderedSummaryResponse;
import com.rmart.customer.order.summary.viewmodel.OrderSumaryViewModel;
import com.rmart.customer.shops.home.adapters.CategoryAdapter;
import com.rmart.customer.shops.list.models.CustomerProductsShopDetailsModel;
import com.rmart.customer.shops.products.viewmodel.ProductListViewModel;
import com.rmart.customer.views.CustomerOrderDetailsFragment;
import com.rmart.databinding.FragmentOrderSummaryBinding;
import com.rmart.databinding.FragmentProductListBinding;
import com.rmart.profile.model.MyProfile;

import java.util.ArrayList;

import static com.rmart.customer.order.summary.viewmodel.OrderSumaryViewModel.DELIVERY;

public class OrderSummaryFragment extends BaseFragment {


    private CustomerProductsShopDetailsModel vendorShoppingCartDetails;


    public OrderSummaryFragment() {
        // Required empty public constructor
    }


    public static OrderSummaryFragment newInstance(CustomerProductsShopDetailsModel vendorShopDetails) {
        OrderSummaryFragment fragment = new OrderSummaryFragment();
        Bundle extras = new Bundle();
        extras.putSerializable("VendorShopDetails", vendorShopDetails);
        fragment.setArguments(extras);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            vendorShoppingCartDetails = (CustomerProductsShopDetailsModel) extras.getSerializable("VendorShopDetails");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentOrderSummaryBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_summary, container, false);
        OrderSumaryViewModel  orderSumaryViewModel = ViewModelProviders.of(this).get(OrderSumaryViewModel.class);
        binding.setOrderSumaryViewModel(orderSumaryViewModel);
        binding.setLifecycleOwner(this);
        MyProfile myProfile = MyProfile.getInstance();
        orderSumaryViewModel.vendorShoppingCartDetails.setValue(vendorShoppingCartDetails);
        orderSumaryViewModel.DiscountCode.setValue("");
        orderSumaryViewModel.showOrderSummary(vendorShoppingCartDetails.getVendorId(),vendorShoppingCartDetails.getShopId(),myProfile.getPrimaryAddressId(),DELIVERY,"");
        orderSumaryViewModel.orderedSummaryResponseMutableLiveData.observeForever(new Observer<OrderedSummaryResponse>() {
            @Override
            public void onChanged(OrderedSummaryResponse orderedSummaryResponse) {

                ProductsAdapter categoryAdapter = new ProductsAdapter(getActivity(), (ArrayList<CustomerOrderProductOrderedDetails>) orderedSummaryResponse.getCustomerOrderedDataResponseModel().getCustomerOrderProductDetailsList());
                binding.setProductAdapter(categoryAdapter);
            }
        });


        return binding.getRoot();
    }
}