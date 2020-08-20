package com.rmart.inventory.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.inventory.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class InventoryViewModel extends ViewModel {
    public static final String CATEGORY = "category";
    public static final String SUB_CATEGORY = "sub_category";
    public static final String PRODUCT = "product";

    MutableLiveData<String> isProductView;
    MutableLiveData<ArrayList<Product>> productList;
    MutableLiveData<HashMap<String , ArrayList<Integer>>> categories;
    MutableLiveData<HashMap<String , ArrayList<Integer>>> subCategories;
    public InventoryViewModel() {
        isProductView = new MutableLiveData<>(PRODUCT);
        productList = new MutableLiveData<>(new ArrayList<>());
        categories = new MutableLiveData<>(new HashMap<>());
        subCategories = new MutableLiveData<>(new HashMap<>());
        getProducts();
    }

    void getProducts() {
        for(int i = 0; i<20; i++) {
            for(int j = 0; j<20; j++) {
                Product product = new Product(i, j);
                Objects.requireNonNull(productList.getValue()).add(product);
            }
        }
        for (int i = 0; i< productList.getValue().size(); i++) {
            if (Objects.requireNonNull(categories.getValue()).containsKey(productList.getValue().get(i).getCategory())) {
                Objects.requireNonNull(Objects.requireNonNull(categories.getValue()).get(productList.getValue().get(i).getCategory())).add(i);
            } else {
                ArrayList<Integer> arrayList = new ArrayList<>();
                arrayList.add(i);
                String key = productList.getValue().get(i).getCategory();
                categories.getValue().put(key, arrayList);
            }
        }
    }

    public MutableLiveData<String> getIsProductView() {
        return isProductView;
    }

    public void setIsProductView(String isProductView) {
        this.isProductView.setValue(isProductView);
    }

    public MutableLiveData<ArrayList<Product>> getProductList() {
        return productList;
    }

    public void setProductList(MutableLiveData<ArrayList<Product>> productList) {
        this.productList = productList;
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
