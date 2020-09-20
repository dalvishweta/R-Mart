package com.rmart.inventory.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.inventory.adapters.ImageUploadAdapter;
import com.rmart.inventory.models.Product;
import com.rmart.profile.model.MyProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddProductToAPI extends BaseInventoryFragment implements View.OnClickListener {

    public static final int REQUEST_FILTERED_DATA_ID = 102;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    Product product;
    public AddProductToAPI() {
        // Required empty public constructor
    }

    public static AddProductToAPI newInstance(String param1, String param2) {
        AddProductToAPI fragment = new AddProductToAPI();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
//        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
//        searchView.clearFocus();
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.add_new_product));
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
        return inflater.inflate(R.layout.fragment_add_product_to_api, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.save).setOnClickListener(this);
        List<Object> imagePath = new ArrayList<>();
        imagePath.add("1");
        imagePath.add("2");
        imagePath.add("1");
        imagePath.add("1");
        ImageUploadAdapter imageUploadAdapter = new ImageUploadAdapter(imagePath, callBackListener);
        RecyclerView recyclerView = view.findViewById(R.id.product_image);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(imageUploadAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save) {
            showDialog(String.format(getString(R.string.hello), MyProfile.getInstance().getFirstName()), getString(R.string.request_new_product_msg), (dialogInterface, i) -> {
                Objects.requireNonNull(requireActivity()).onBackPressed();
            });
            // mListener.requestNewProduct(this, REQUEST_FILTERED_DATA_ID);
        }
    }

    private CallBackInterface callBackListener = pObject -> {

    };
}