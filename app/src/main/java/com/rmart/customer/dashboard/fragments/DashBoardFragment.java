package com.rmart.customer.dashboard.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.dashboard.adapters.SliderAdapter;
import com.rmart.customer.dashboard.model.SliderImages;
import com.rmart.databinding.FragmentDashBoardBinding;
import com.rmart.databinding.FragmentShopHomePageBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoardFragment extends BaseFragment {

    public DashBoardFragment() {
        // Required empty public constructor
    }


    public static DashBoardFragment newInstance() {
        DashBoardFragment fragment = new DashBoardFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       FragmentDashBoardBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dash_board, container, false);

        ArrayList<SliderImages> sliderImages = new ArrayList<>();

//        SliderImages sliderImages1 = new SliderImages("i","test","description","https://cdn.dnaindia.com/sites/default/files/styles/full/public/2020/05/21/901916-swiggy-vs-zomato.png","","","","1");
//        sliderImages.add(sliderImages1);
//        sliderImages.add(sliderImages1);
//        SliderAdapter  imageSliderAdapter = new SliderAdapter(getActivity(),sliderImages );
//        binding.pager.setAdapter(imageSliderAdapter);
//        binding.indicator.setViewPager(binding.pager);

       return binding.getRoot();
    }
}