package com.rmart.inventory.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.inventory.models.Product;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.ProductListResponse;
import com.rmart.utilits.pojos.ProductResponse;
import com.rmart.utilits.services.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryViewModel extends ViewModel {

    public static final String CATEGORY = "category";
    public static final String SUB_CATEGORY = "sub_category";
    public static final String PRODUCT = "product";

    MutableLiveData<String> isProductView;
    MutableLiveData<HashMap<String , ProductResponse>> productList;
    MutableLiveData<HashMap<String , ArrayList<Integer>>> categories;
    MutableLiveData<HashMap<String , ArrayList<Integer>>> subCategories;
    MutableLiveData<Boolean> showLoadingDialog;
    MutableLiveData<String> showText;
    MutableLiveData<Integer> selectedProduct;

    public InventoryViewModel() {
        isProductView = new MutableLiveData<>(PRODUCT);
        productList = new MutableLiveData<>(new HashMap<>());
        categories = new MutableLiveData<>(new HashMap<>());
        subCategories = new MutableLiveData<>(new HashMap<>());
        selectedProduct = new MutableLiveData<>(-1);
        showLoadingDialog = new MutableLiveData<>(false);
    }

    public MutableLiveData<Integer> getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct( Integer selectedProduct) {
        this.selectedProduct.setValue(selectedProduct);
    }
    public void updateProductList() {
        setShowLoadingDialog(true);
        ProductService productService = RetrofitClientInstance.getRetrofitInstance().create(ProductService.class);
        productService.getProducts("0", "100").enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if(response.isSuccessful()) {
                    ProductListResponse data = response.body();
                    assert data != null;
                    if(data.getStatus().equals(Utils.SUCCESS)) {
                        ArrayList<ProductResponse> products = data.getProductList();
                    } else {

                    }
                }
                setShowLoadingDialog(false);
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                setShowLoadingDialog(false);
            }
        });
    }

    public MutableLiveData<Boolean> getShowLoadingDialog() {
        return showLoadingDialog;
    }

    public void setShowLoadingDialog(Boolean showLoadingDialog) {
        this.showLoadingDialog.setValue(showLoadingDialog);
    }

    public MutableLiveData<String> getIsProductView() {
        return isProductView;
    }

    public void setIsProductView(String isProductView) {
        this.isProductView.setValue(isProductView);
    }

    public MutableLiveData<HashMap<String, ProductResponse>> getProductList() {
        return productList;
    }

    public void setProductList(ProductResponse product) {
        if (!Objects.requireNonNull(productList.getValue()).containsKey(product.getProductID())) {
            this.productList.getValue().put(product.getProductID(), product);
        }
    }

    public MutableLiveData<HashMap<String, ArrayList<Integer>>> getCategories() {
        return categories;
    }

    public void setCategories(MutableLiveData<HashMap<String, ArrayList<Integer>>> categories) {
        this.categories = categories;
    }

    public MutableLiveData<HashMap<String, ArrayList<Integer>>> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(MutableLiveData<HashMap<String, ArrayList<Integer>>> subCategories) {
        this.subCategories = subCategories;
    }
}
