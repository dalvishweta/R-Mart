package com.rmart.customer.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.utilits.Utils;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VendorProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VendorProductDetailsFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VendorProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VendorDetailsFragment.
     */

    private RecyclerView productsListField;
    private AppCompatEditText etProductsSearchField;

    public static VendorProductDetailsFragment newInstance(String param1, String param2) {
        VendorProductDetailsFragment fragment = new VendorProductDetailsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vendor_product_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);

        if(Utils.isNetworkConnected(requireActivity())) {

        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void loadUIComponents(View view) {
        productsListField = view.findViewById(R.id.products_list_field);

        etProductsSearchField = view.findViewById(R.id.edt_product_search_field);
        ImageView ivSearchField = view.findViewById(R.id.iv_search_field);
        etProductsSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() != 0) {
                    performSearch();
                }
                ivSearchField.setImageResource(R.drawable.search);
            }
        });
    }

    private void performSearch() {
        String newText = Objects.requireNonNull(etProductsSearchField.getText()).toString().trim();
        /*if (newText.length() < 1) {
            customerProductsListAdapter.updateItems(new ArrayList<>());
            customerProductsListAdapter.notifyDataSetChanged();
        } else if (newText.length() == 3) {
            searchShopName = newText;
            currentPage = 0;
            getShopsList();
        } else {
            customerProductsListAdapter.getFilter().filter(newText);
        }*/
    }
}