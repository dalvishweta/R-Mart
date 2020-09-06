package com.rmart.inventory.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.utilits.pojos.APIStockResponse;
import com.rmart.utilits.pojos.ProductResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
    MutableLiveData<ArrayList<APIStockResponse>> apiStocks;

    public InventoryViewModel() {
        isProductView = new MutableLiveData<>(PRODUCT);
        productList = new MutableLiveData<>(new HashMap<>());
        categories = new MutableLiveData<>(new HashMap<>());
        subCategories = new MutableLiveData<>(new HashMap<>());
        selectedProduct = new MutableLiveData<>(-1);
        showLoadingDialog = new MutableLiveData<>(false);
        apiStocks = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<APIStockResponse>> getApiStocks() {
        return apiStocks;
    }

    public void setApiStocks(ArrayList<APIStockResponse> apiStocks) {
        this.apiStocks.setValue(apiStocks);
    }

    public MutableLiveData<Integer> getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct( Integer selectedProduct) {
        this.selectedProduct.setValue(selectedProduct);
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
