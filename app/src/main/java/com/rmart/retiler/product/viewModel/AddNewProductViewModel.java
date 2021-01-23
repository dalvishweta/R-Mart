package com.rmart.retiler.product.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rmart.retiler.product.model.brand.BrandListRequest;
import com.rmart.retiler.product.model.brand.BrandListResponse;
import com.rmart.retiler.product.model.category.CategoryListRequest;
import com.rmart.retiler.product.model.category.CategoryListResponse;
import com.rmart.retiler.product.model.product.ProductListRequest;
import com.rmart.retiler.product.model.product.ProductListResponse;
import com.rmart.retiler.product.model.product.addproduct.AddProductRequest;
import com.rmart.retiler.product.model.product.addproduct.AddProductResponse;
import com.rmart.retiler.product.model.subCategory.SubCategoryListRequest;
import com.rmart.retiler.product.model.subCategory.SubCategoryListResponse;
import com.rmart.retiler.product.repository.AddProductRepository;
import com.rmart.retiler.product.repository.BrandListRepository;
import com.rmart.retiler.product.repository.CategoryRepository;
import com.rmart.retiler.product.repository.ProductRepository;
import com.rmart.retiler.product.repository.SubCategoryRepository;
import com.rmart.retiler.product.repository.UnitDetailsRepository;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.pojos.APIStockListResponse;
import com.rmart.utilits.pojos.APIStockResponse;
import com.rmart.utilits.pojos.APIUnitMeasureListResponse;
import com.rmart.utilits.services.APIService;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewProductViewModel extends ViewModel {
    public MutableLiveData<CategoryListResponse> categoryListResponse = new MutableLiveData<>();
    public MutableLiveData<SubCategoryListResponse> subCategoryListResponse = new MutableLiveData<>();
    public MutableLiveData<ProductListResponse> productListResponse = new MutableLiveData<>();
    public MutableLiveData<BrandListResponse> brandListResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<AddProductResponse> addProductResponse = new MutableLiveData<>();
    public MutableLiveData<APIStockListResponse> apiStockResponse = new MutableLiveData<APIStockListResponse>();
    public MutableLiveData<APIUnitMeasureListResponse> apiUnitMeasureResponse = new MutableLiveData<APIUnitMeasureListResponse>();

    public void getAllCategories(CategoryListRequest request) {
        MutableLiveData<CategoryListResponse> _categoryListResponse = CategoryRepository.getAllCategories(request);
        _categoryListResponse.observeForever(new Observer<CategoryListResponse>() {
            @Override
            public void onChanged(CategoryListResponse response) {
                categoryListResponse.setValue(response);
                Log.d("AddNewProductActivity21", categoryListResponse.getValue().getStatus());
            }
        });

    }

    public void getAllSubCategories(SubCategoryListRequest request) {
        MutableLiveData<SubCategoryListResponse> _subCategoryListResponse = SubCategoryRepository.getAllSubCategories(request);
        _subCategoryListResponse.observeForever(new Observer<SubCategoryListResponse>() {
            @Override
            public void onChanged(SubCategoryListResponse response) {
                subCategoryListResponse.setValue(response);
                Log.d("AddNewProductActivity22", subCategoryListResponse.getValue().getStatus());
            }
        });
    }

    public void getAllProducts(ProductListRequest request) {
        MutableLiveData<ProductListResponse> _productListResponse = ProductRepository.getAllProducts(request);
        _productListResponse.observeForever(new Observer<ProductListResponse>() {
            @Override
            public void onChanged(ProductListResponse response) {
                productListResponse.setValue(response);
                Log.d("AddNewProductActivity23", productListResponse.getValue().getStatus());
            }
        });
    }

    public void getAllBrands(BrandListRequest request) {
        MutableLiveData<BrandListResponse> resultMutableLiveData = BrandListRepository.getBransList(request);
        resultMutableLiveData.observeForever(new Observer<BrandListResponse>() {
            @Override
            public void onChanged(BrandListResponse brandListResponse) {
                brandListResponseMutableLiveData.setValue(brandListResponse);
            }
        });
    }

    public void addProduct(AddProductRequest request) {
        MutableLiveData<AddProductResponse> result = AddProductRepository.addProduct(request);
        result.observeForever(new Observer<AddProductResponse>() {
            @Override
            public void onChanged(AddProductResponse response) {
                Log.d("AddProductResponse: ", ""+response);
                addProductResponse.setValue(response);
            }
        });
    }

    public void getStockList() {

        MutableLiveData<APIStockListResponse> result = UnitDetailsRepository.getAPIStockList();
        result.observeForever(new Observer<APIStockListResponse>() {
            @Override
            public void onChanged(APIStockListResponse response) {
                apiStockResponse.setValue(response);
            }
        });
    }

    public void getUnitMeasureList() {
        MutableLiveData<APIUnitMeasureListResponse> result = UnitDetailsRepository.getUnitMeasureList();
        result.observeForever(new Observer<APIUnitMeasureListResponse>() {
            @Override
            public void onChanged(APIUnitMeasureListResponse response) {
                apiUnitMeasureResponse.setValue(response);
            }
        });
    }
}
