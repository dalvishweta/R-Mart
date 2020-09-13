package com.rmart.customer.views;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.adapters.CustomSpinnerAdapter;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.customer.models.VendorProductDataResponse;
import com.rmart.utilits.HttpsTrustManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satya Seshu on 10/09/20.
 */
public class ProductCartDetailsFragment extends BaseFragment {

    private VendorProductDataResponse vendorProductDataDetails;

    private ImageView ivFavouriteImageField;
    private TextView tvDiscountDetailsField;
    private NetworkImageView ivProductImageField;
    private ImageView ivLeftArrowImageField;
    private ImageView ivRightArrowImageField;
    private TextView tvProductNameField;
    private TextView tvQuantityField;
    private TextView tvCurrentPriceField;
    private TextView tvTotalPriceField;
    private Button btnMinusField;
    private Button btnPlusField;
    private TextView tvNoOfQuantityField;
    private TextView tvProductDescTitleField;
    private TextView tvProductDescField;
    private LinearLayout viewMoreLayoutField;
    private Button btnAddToCartField;
    private LinearLayout btnBuyNowField;
    private RecyclerView relatedProductsListField;
    private ImageLoader imageLoader;
    private Spinner quantitySpinnerField;
    private int noOfQuantity = 1;
    private boolean isViewMoreSelected = false;
    private TextView tvViewMoreField;
    private ImageView ivViewMoreImageField;

    static ProductCartDetailsFragment getInstance(VendorProductDataResponse vendorProductDataDetails) {
        ProductCartDetailsFragment productCartDetailsFragment = new ProductCartDetailsFragment();
        Bundle extras = new Bundle();
        extras.putSerializable("ProductCartDetails", vendorProductDataDetails);
        productCartDetailsFragment.setArguments(extras);
        return productCartDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vendorProductDataDetails = (VendorProductDataResponse) getArguments().getSerializable("ProductCartDetails");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_product_card_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadUIComponents(view);

        updateUI();
    }

    private void loadUIComponents(View view) {
        ivFavouriteImageField = view.findViewById(R.id.iv_favourite_image_field);
        tvDiscountDetailsField = view.findViewById(R.id.tv_discount_percent_field);
        ivProductImageField = view.findViewById(R.id.iv_product_image_field);
        ivLeftArrowImageField = view.findViewById(R.id.iv_left_arrow_field);
        ivRightArrowImageField = view.findViewById(R.id.iv_right_arrow_field);
        tvProductNameField = view.findViewById(R.id.tv_product_name_field);
        tvQuantityField = view.findViewById(R.id.tv_quantity_field);
        tvCurrentPriceField = view.findViewById(R.id.tv_current_price_field);
        tvTotalPriceField = view.findViewById(R.id.tv_total_price_field);
        btnMinusField = view.findViewById(R.id.btn_minus_field);
        tvNoOfQuantityField = view.findViewById(R.id.tv_no_of_quantity_field);
        btnPlusField = view.findViewById(R.id.btn_add_field);
        tvProductDescTitleField = view.findViewById(R.id.tv_product_description_title_field);
        tvProductDescField = view.findViewById(R.id.tv_product_description_field);
        viewMoreLayoutField = view.findViewById(R.id.view_more_layout_field);
        btnAddToCartField = view.findViewById(R.id.btn_add_to_cart_field);
        btnBuyNowField = view.findViewById(R.id.btn_buy_now_field);
        relatedProductsListField = view.findViewById(R.id.related_products_list_field);
        quantitySpinnerField = view.findViewById(R.id.quantity_spinner_field);
        tvViewMoreField = view.findViewById(R.id.tv_view_more_field);
        ivViewMoreImageField = view.findViewById(R.id.iv_view_more_image_field);

        imageLoader = RMartApplication.getInstance().getImageLoader();

        btnMinusField.setOnClickListener(v -> {
            if (noOfQuantity > 0) {
                noOfQuantity--;
                updateQuantityDetails();
            }
        });

        btnPlusField.setOnClickListener(v -> {
            if (noOfQuantity < 10) {
                noOfQuantity++;
                updateQuantityDetails();
            }
        });

        viewMoreLayoutField.setOnClickListener(v -> {
            viewMoreSelected();
        });

        updateQuantityDetails();
    }

    private void viewMoreSelected() {
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (!isViewMoreSelected) {
            isViewMoreSelected = true;
            tvViewMoreField.setText(getString(R.string.show_less));
            ivViewMoreImageField.setImageResource(R.drawable.drop_down);
            buttonLayoutParams.topMargin = 10;
            viewMoreLayoutField.setLayoutParams(buttonLayoutParams);
            tvProductDescField.setMaxLines(Integer.MAX_VALUE);
        } else {
            tvViewMoreField.setText(getString(R.string.view_more));
            ivViewMoreImageField.setImageResource(R.drawable.drop_down);
            buttonLayoutParams.topMargin = -20;
            isViewMoreSelected = false;
            tvProductDescField.setMaxLines(5);
        }
        buttonLayoutParams.gravity = Gravity.CENTER;
        viewMoreLayoutField.setLayoutParams(buttonLayoutParams);
    }

    private void updateQuantityDetails() {
        tvNoOfQuantityField.setText(String.valueOf(noOfQuantity));
    }

    private void updateUI() {
        String productImageUrl = vendorProductDataDetails.getProductImage();
        if (!TextUtils.isEmpty(productImageUrl)) {
            HttpsTrustManager.allowAllSSL();
            imageLoader.get(productImageUrl, ImageLoader.getImageListener(ivProductImageField,
                    R.mipmap.ic_launcher, android.R.drawable
                            .ic_dialog_alert));
            ivProductImageField.setImageUrl(productImageUrl, imageLoader);
        }
        tvProductNameField.setText(vendorProductDataDetails.getProductName());

        List<CustomerProductsDetailsUnitModel> unitsList = vendorProductDataDetails.getUnits();
        if (unitsList != null && !unitsList.isEmpty()) {
            ArrayList<Object> updatedUnitsList = new ArrayList<>(unitsList);
            CustomSpinnerAdapter unitsAdapter = new CustomSpinnerAdapter(requireActivity(), updatedUnitsList);
            quantitySpinnerField.setAdapter(unitsAdapter);

        }
    }
}
