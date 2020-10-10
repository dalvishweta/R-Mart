package com.rmart.customer.views;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.tabs.TabLayout;
import com.rmart.R;
import com.rmart.baseclass.views.AutoScrollViewPager;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.adapters.CustomSpinnerAdapter;
import com.rmart.customer.models.AddProductToWishListResponse;
import com.rmart.customer.models.AddToCartResponseDetails;
import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.customer.models.CustomerProductsShopDetailsModel;
import com.rmart.customer.models.ProductDetailsDescModel;
import com.rmart.customer.models.ProductDetailsDescResponse;
import com.rmart.customer.models.ShoppingCartResponseDetails;
import com.rmart.inventory.adapters.ImageAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.BaseResponse;
import com.rmart.utilits.pojos.ImageURLResponse;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Satya Seshu on 10/09/20.
 */
public class ProductCartDetailsFragment extends BaseFragment {

    private CustomerProductDetailsModel vendorProductDataDetails;
    private CustomerProductsShopDetailsModel vendorShopDetails;
    private ProductDetailsDescModel productDetailsDescModel;

    private ImageView ivFavouriteImageField;
    private TextView tvProductDiscountField;
    private TextView tvProductNameField;
    private TextView tvSellingPriceField;
    private TextView tvTotalPriceField;
    private TextView tvNoOfQuantityField;
    private TextView tvProductDescField;
    private LinearLayout viewMoreLayoutField;
    private TextView tvQuantityField;
    private int noOfQuantity = 1;
    private boolean isViewMoreSelected = false;
    private TextView tvViewMoreField;
    private ImageView ivViewMoreImageField;
    private int productImagePosition = 1;
    private CustomerProductsDetailsUnitModel productUnitDetails;
    private Spinner quantitySpinnerField;
    private boolean isWishListProduct = false;
    private AutoScrollViewPager productsImagePagerField;

    private OnCustomerHomeInteractionListener onCustomerHomeInteractionListener;
    private TabLayout dotIndicatorLayoutField;
    private TextView tvWishListField;

    private int productId = -1;
    private boolean isAddToCartSelected = false;

    static ProductCartDetailsFragment getInstance(CustomerProductDetailsModel vendorProductDataDetails, CustomerProductsShopDetailsModel vendorShopDetails) {
        ProductCartDetailsFragment productCartDetailsFragment = new ProductCartDetailsFragment();
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
            vendorProductDataDetails = (CustomerProductDetailsModel) extras.getSerializable("ProductCartDetails");
            vendorShopDetails = (CustomerProductsShopDetailsModel) extras.getSerializable("VendorShopDetails");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnCustomerHomeInteractionListener) {
            onCustomerHomeInteractionListener = (OnCustomerHomeInteractionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LoggerInfo.printLog("Fragment", "ProductCartDetailsFragment");
        return inflater.inflate(R.layout.fragment_product_card_details, container, false);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadUIComponents(view);

        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<ProductDetailsDescResponse> call = customerProductsService.getVendorProductDetails(clientID, vendorShopDetails.getVendorId(), vendorShopDetails.getShopId(),
                    vendorProductDataDetails.getProductId(), MyProfile.getInstance().getUserID());
            call.enqueue(new Callback<ProductDetailsDescResponse>() {
                @Override
                public void onResponse(@NotNull Call<ProductDetailsDescResponse> call, @NotNull Response<ProductDetailsDescResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        ProductDetailsDescResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                ProductDetailsDescResponse.ProductDetailsDescProductDataModel productDetailsDescProductDataModel = body.getProductDetailsDescProductDataModel();
                                productDetailsDescModel = productDetailsDescProductDataModel.getProductDetailsDescModel();
                                if (productDetailsDescModel != null) {
                                    updateUI();
                                } else {
                                    showCloseDialog(getString(R.string.no_information_available));
                                }
                            } else {
                                showCloseDialog(body.getMsg());
                            }
                        } else {
                            showCloseDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showCloseDialog(response.message());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ProductDetailsDescResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    showCloseDialog(t.getMessage());
                }
            });
        } else {
            showCloseDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void loadUIComponents(View view) {
        ivFavouriteImageField = view.findViewById(R.id.iv_favourite_image_field);
        tvProductDiscountField = view.findViewById(R.id.tv_product_discount_field);
        productsImagePagerField = view.findViewById(R.id.view_pager);
        tvProductNameField = view.findViewById(R.id.tv_product_name_field);
        tvQuantityField = view.findViewById(R.id.tv_quantity_field);
        tvSellingPriceField = view.findViewById(R.id.tv_selling_price_field);
        tvTotalPriceField = view.findViewById(R.id.tv_total_price_field);
        AppCompatButton btnMinusField = view.findViewById(R.id.btn_minus_field);
        tvNoOfQuantityField = view.findViewById(R.id.tv_no_of_quantity_field);
        AppCompatButton btnPlusField = view.findViewById(R.id.btn_add_field);
        tvProductDescField = view.findViewById(R.id.tv_product_description_field);
        viewMoreLayoutField = view.findViewById(R.id.view_more_layout_field);
        Button btnAddToCartField = view.findViewById(R.id.btn_add_to_cart_field);
        quantitySpinnerField = view.findViewById(R.id.quantity_spinner_field);
        tvViewMoreField = view.findViewById(R.id.tv_view_more_field);
        ivViewMoreImageField = view.findViewById(R.id.iv_view_more_image_field);
        tvWishListField = view.findViewById(R.id.tv_wish_list_field);

        productsImagePagerField.startAutoScroll();
        productsImagePagerField.setInterval(1000);
        productsImagePagerField.setCycle(true);
        productsImagePagerField.setStopScrollWhenTouch(true);

        // imageLoader = RMartApplication.getInstance().getImageLoader();

        btnMinusField.setOnClickListener(v -> {
            if (noOfQuantity > 1) {
                noOfQuantity--;
                updateQuantityDetails();
            }
        });

        btnPlusField.setOnClickListener(v -> {
            if (noOfQuantity < productUnitDetails.getProductUnitQuantity()) {
                noOfQuantity++;
                updateQuantityDetails();
            }
        });

        quantitySpinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object lSelectedItem = quantitySpinnerField.getSelectedItem();
                if (lSelectedItem instanceof CustomerProductsDetailsUnitModel) {
                    productUnitDetails = (CustomerProductsDetailsUnitModel) lSelectedItem;
                    updateUnitPriceDetails();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        viewMoreLayoutField.setOnClickListener(v -> viewMoreSelected());

        btnAddToCartField.setOnClickListener(v -> addToCartSelected());

        view.findViewById(R.id.btn_move_to_wish_list_field).setOnClickListener(v -> {
            if (isWishListProduct) deleteProductFromWishList();
            else addProductToWishList();
        });

        ivFavouriteImageField.setOnClickListener(v -> {
            if (isWishListProduct) deleteProductFromWishList();
            else addProductToWishList();
        });

        dotIndicatorLayoutField = view.findViewById(R.id.product_images_dot_indicator_field);

        updateQuantityDetails();
    }

    private void updateAddToWishListButton() {
        if (isWishListProduct) tvWishListField.setText(getString(R.string.remove_from_wish_list));
        else tvWishListField.setText(getString(R.string.move_to_wish_list));
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

        if (productUnitDetails != null) {
            updateUnitPriceDetails();
        }
    }

    private void updateUI() {
        if (productDetailsDescModel != null) {
            List<String> productsImagesList = productDetailsDescModel.getProductImage();
            if (productsImagesList != null && !productsImagesList.isEmpty()) {
                List<ImageURLResponse> lUpdatedImagesList = new ArrayList<>();
                for (String imageUrl : productsImagesList) {
                    ImageURLResponse imageURLResponse = new ImageURLResponse();
                    imageURLResponse.setDisplayImage(imageUrl);
                    lUpdatedImagesList.add(imageURLResponse);
                }
                ImageAdapter imageAdapter = new ImageAdapter(requireActivity(), lUpdatedImagesList);
                productsImagePagerField.setAdapter(imageAdapter);

                dotIndicatorLayoutField.setVisibility(lUpdatedImagesList.size() == 1 ? View.GONE : View.VISIBLE);
                dotIndicatorLayoutField.setupWithViewPager(productsImagePagerField);
            }
            tvProductNameField.setText(productDetailsDescModel.getProductName());

            List<CustomerProductsDetailsUnitModel> unitsList = productDetailsDescModel.getUnits();
            if (unitsList != null && !unitsList.isEmpty()) {
                ArrayList<Object> updatedUnitsList = new ArrayList<>(unitsList);
                CustomSpinnerAdapter unitsAdapter = new CustomSpinnerAdapter(requireActivity(), updatedUnitsList);
                quantitySpinnerField.setAdapter(unitsAdapter);
            }
            tvProductDescField.setText(productDetailsDescModel.getProductDetails());
            tvProductDescField.post(() -> {
                int lineCount = tvProductDescField.getLineCount();
                viewMoreLayoutField.setVisibility(lineCount >= 5 ? View.VISIBLE : View.GONE);
            });

            tvProductDescField.setText(productDetailsDescModel.getProductDetails());
            isWishListProduct = productDetailsDescModel.getWishList();

            ivFavouriteImageField.setImageResource(isWishListProduct ? R.drawable.heart_active : R.drawable.heart_black);
            updateAddToWishListButton();
        } else {
            showCloseDialog(getString(R.string.no_product_details_found));
        }
    }

    private void updateUnitPriceDetails() {
        double sellingPriceValue = noOfQuantity * productUnitDetails.getSellingPrice();
        String sellingPrice = String.format("Rs. %s", sellingPriceValue);
        tvSellingPriceField.setText(sellingPrice);
        Double totalPrice = noOfQuantity * productUnitDetails.getUnitPrice();
        tvTotalPriceField.setText(Utils.roundOffDoubleValue(totalPrice));
        tvTotalPriceField.setPaintFlags(tvTotalPriceField.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        int quantityNoDetails = noOfQuantity * productUnitDetails.getUnitNumber();
        String quantityDetails = String.format(Locale.getDefault(), "%d %s", quantityNoDetails, productUnitDetails.getShortUnitMeasure());
        tvQuantityField.setText(quantityDetails);
        tvNoOfQuantityField.setText(String.valueOf(noOfQuantity));

        int productDiscount = productUnitDetails.getProductDiscount();
        String productDiscountDetails = productDiscount + "% \n Off";
        tvProductDiscountField.setText(productDiscountDetails);
    }

    private void showCloseDialog(String title, String message) {
        showDialog(title, message, pObject -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void showCloseDialog(String message) {
        showDialog(message, pObject -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void addToCartSelected() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<AddToCartResponseDetails> call = customerProductsService.addToCart(clientID, vendorShopDetails.getVendorId(), MyProfile.getInstance().getUserID(),
                    productUnitDetails.getProductUnitId(), noOfQuantity);
            call.enqueue(new Callback<AddToCartResponseDetails>() {
                @Override
                public void onResponse(@NotNull Call<AddToCartResponseDetails> call, @NotNull Response<AddToCartResponseDetails> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        AddToCartResponseDetails body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                productId = -1;
                                isAddToCartSelected = true;
                                AddToCartResponseDetails.AddToCartDataResponse addToCartDataResponse = body.getAddToCartDataResponse();
                                if (addToCartDataResponse != null) {
                                    Integer totalCartCount = addToCartDataResponse.getTotalCartCount();
                                    MyProfile.getInstance().setCartCount(totalCartCount);
                                    showDialog(body.getMsg());
                                } else {
                                    showDialog(getString(R.string.no_information_available));
                                }
                            } else {
                                showDialog(body.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddToCartResponseDetails> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    showDialog(t.getMessage());
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void buyNowSelected() {
        ShoppingCartResponseDetails shoppingCartResponseDetails = new ShoppingCartResponseDetails();
        shoppingCartResponseDetails.setVendorId(vendorShopDetails.getVendorId());
        shoppingCartResponseDetails.setMobileNumber(vendorShopDetails.getShopMobileNo());
        shoppingCartResponseDetails.setShopName(vendorShopDetails.getShopName());
        shoppingCartResponseDetails.setProductId(!isAddToCartSelected ? productDetailsDescModel.getProductId() : -1);
        onCustomerHomeInteractionListener.gotoShoppingCartDetails(shoppingCartResponseDetails);
    }

    private void deleteProductFromWishList() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<BaseResponse> call = customerProductsService.deleteProductFromWishList(clientID, productDetailsDescModel.getWishListId());
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        BaseResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                showDialog(body.getMsg(), pObject -> {
                                    isWishListProduct = !isWishListProduct;
                                    ivFavouriteImageField.setImageResource(R.drawable.heart_black);
                                    productDetailsDescModel.setWishList(false);
                                    updateAddToWishListButton();
                                });
                            } else {
                                showDialog(body.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<BaseResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    showDialog(t.getMessage());
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void addProductToWishList() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<AddProductToWishListResponse> call = customerProductsService.moveToWishList(clientID, vendorShopDetails.getVendorId(),
                    MyProfile.getInstance().getUserID(), productDetailsDescModel.getProductId());
            call.enqueue(new Callback<AddProductToWishListResponse>() {
                @Override
                public void onResponse(@NotNull Call<AddProductToWishListResponse> call, @NotNull Response<AddProductToWishListResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        AddProductToWishListResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                showDialog(body.getMsg(), pObject -> {
                                    isWishListProduct = !isWishListProduct;
                                    ivFavouriteImageField.setImageResource(R.drawable.heart_active);
                                    String productWishListId = body.getAddProductToWishListDataResponse().getProductWishListId();
                                    productDetailsDescModel.setWishListId(productWishListId);
                                    productDetailsDescModel.setWishList(true);
                                    onCustomerHomeInteractionListener.updateShopWishListStatus(vendorShopDetails);
                                    updateAddToWishListButton();
                                });
                            } else {
                                showDialog(body.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddProductToWishListResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    showDialog(t.getMessage());
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }
}
