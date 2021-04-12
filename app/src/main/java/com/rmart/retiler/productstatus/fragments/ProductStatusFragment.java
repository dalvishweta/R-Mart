package com.rmart.retiler.productstatus.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.views.BaseInventoryFragment;
import com.rmart.retiler.inventory.product_from_inventory.model.Product;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductStatusFragment extends BaseInventoryFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Product> sm;
    public ProductStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductStatusFragment newInstance(String param1, String param2) {
        ProductStatusFragment fragment = new ProductStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static BaseInventoryFragment newInstance(List<Product> sm) {
        ProductStatusFragment fragment = new ProductStatusFragment();
        Bundle args = new Bundle();
        args.putSerializable("sm", (Serializable) sm);
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
        View view = inflater.inflate(R.layout.fragment_product_status, container, false);

        RecyclerView smsPlansList = view.findViewById(R.id.sms_plans);

        if (sm.isEmpty()){
            smsPlansList.setVisibility(View.GONE);
            view.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        } else {
           // ProductFromInventorySearchListAdapter adapter = new ProductFromInventorySearchListAdapter(this, getContext(), sm);
            //smsPlansList.setAdapter(adapter);
        }

        return view;

    }
}