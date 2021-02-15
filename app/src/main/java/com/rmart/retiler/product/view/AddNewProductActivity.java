package com.rmart.retiler.product.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.AdapterViewBindingAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.Resource;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.baseclass.DateTimeInterface;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.order.summary.viewmodel.OrderSumaryViewModel;
import com.rmart.databinding.ActivityAddNewProductBinding;
import com.rmart.inventory.adapters.ImageUploadAdapter;
import com.rmart.inventory.adapters.ProductImagesAdapter;
import com.rmart.inventory.adapters.ProductUnitAdapter;
import com.rmart.inventory.models.APIUnitMeasures;
import com.rmart.inventory.models.UnitObject;
import com.rmart.inventory.views.AddUnitDialog;
import com.rmart.profile.model.MyProfile;
import com.rmart.retiler.product.BaseInterface;
import com.rmart.retiler.product.OnUnitSaveListner;
import com.rmart.retiler.product.model.brand.Brand;
import com.rmart.retiler.product.model.brand.BrandListRequest;
import com.rmart.retiler.product.model.brand.BrandListResponse;
import com.rmart.retiler.product.model.category.Category;
import com.rmart.retiler.product.model.category.CategoryListRequest;
import com.rmart.retiler.product.model.category.CategoryListResponse;
import com.rmart.retiler.product.model.product.Product;
import com.rmart.retiler.product.model.product.ProductListRequest;
import com.rmart.retiler.product.model.product.ProductListResponse;
import com.rmart.retiler.product.model.product.addproduct.AddProductRequest;
import com.rmart.retiler.product.model.product.addproduct.AddProductResponse;
import com.rmart.retiler.product.model.subCategory.SubCategory;
import com.rmart.retiler.product.model.subCategory.SubCategoryListRequest;
import com.rmart.retiler.product.model.subCategory.SubCategoryListResponse;
import com.rmart.retiler.product.viewModel.AddNewProductViewModel;
import com.rmart.utilits.CustomDatePickerDialog;
import com.rmart.utilits.DateUtilities;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RecyclerTouchListener;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.APIStockListResponse;
import com.rmart.utilits.pojos.APIStockResponse;
import com.rmart.utilits.pojos.APIUnitMeasureListResponse;
import com.rmart.utilits.pojos.APIUnitMeasureResponse;
import com.rmart.utilits.pojos.ImageURLResponse;
import com.rmart.utilits.services.APIService;
import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewProductActivity extends AppCompatActivity implements BaseInterface, OnItemSelectedListener,
        OnUnitSaveListner {

    private ActivityAddNewProductBinding binding;

    private int selectedPosUploadImg = -1;
    private List<ImageURLResponse> imagesList;
    private ProductImagesAdapter productImagesAdapter;

    private final ArrayList<UnitObject> unitsList = new ArrayList<>();
    private ProductUnitAdapter unitBaseAdapter;
    public static final String UNIT_VALUE = "unit_value";
    private ArrayList<APIUnitMeasureResponse> unitMeasurements = new ArrayList<>();
    private APIStockListResponse apiStockListResponse;
    public static final int INT_ADD_UNIT = 101;
    public static final int INT_UPDATE_UNIT = 102;

    private HashSet<Category> categoryHashSet = new HashSet<>();
    private HashSet<SubCategory> subCategoryHashSet = new HashSet<>();
    private HashSet<Brand> brandHashSet = new HashSet<>();

    private ArrayList<String> categoryList, subCategoryList, brandList;
    private String selectedCategory = "", selectedSubCategory = "", //selectedProduct = "",
            selectedBrand = "", selectedExpiryDate = "", selectedStockIds = "";

    private int selectedCategoryId = 0;

    private AddNewProductViewModel addNewProductViewModel;

    private Context context;
    private String TAG = AddNewProductActivity.class.getSimpleName();

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_product);
        initilization();
        setListener();
        loadDefaultData();
    }

    @Override
    public void initilization() {
        context = this;
        addNewProductViewModel = new ViewModelProvider(this).get(AddNewProductViewModel.class);
        categoryList = new ArrayList<>();
        subCategoryList = new ArrayList<>();
        brandList = new ArrayList<>();
        setObservers();
    }

    @Override
    public void setListener() {
        binding.btnSaveProduct.setOnClickListener(this);
        binding.tvExpiry.setOnClickListener(this);
        binding.tvAddUnit.setOnClickListener(this);
        binding.tvAddUnitRetailer.setOnClickListener(this);
        binding.tvChooseCategory.setOnClickListener(this);
        binding.tvChooseSubCategory.setOnClickListener(this);
        binding.tvProductBrand.setOnClickListener(this);
    }

    @Override
    public void loadDefaultData() {
        setRecyclerView();
        setCategorySpinner();
        setSubCategorySpinner();
        setBrandSpinner();
        getDataFromServer();
    }

    private void setObservers() {
        addNewProductViewModel.categoryListResponse.observeForever(categoryListResponse -> {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            Log.d(TAG, "categoryListResponse : " + categoryListResponse.toString());
            if (categoryListResponse.getCategories() != null && categoryListResponse.getCount() > 0) {
                categoryHashSet.addAll(categoryListResponse.getCategories());
                Log.e(TAG, "categoryHashSet : " + categoryHashSet);
                setCategorySpinner();
            } else Log.e(TAG, "Categories list is empty");
        });
        addNewProductViewModel.subCategoryListResponse.observe(this, new Observer<SubCategoryListResponse>() {
            @Override
            public void onChanged(SubCategoryListResponse subCategoryListResponse) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                Log.e(TAG, "subCategoryListResponse : " + subCategoryListResponse.toString());
                if (subCategoryListResponse.getSubCategories() != null && subCategoryListResponse.getCount() > 0) {
                    subCategoryHashSet.clear();
                    subCategoryHashSet.addAll(subCategoryListResponse.getSubCategories());
                    Log.e(TAG, "subCategoryHashSet : " + subCategoryHashSet);
                    setSubCategorySpinner();
                } else {
                    Log.e(TAG, "subCategory list is empty");
                    subCategoryHashSet.clear();
                    Toast.makeText(context, ""+subCategoryListResponse.getMsg(), Toast.LENGTH_SHORT).show();
                    setSubCategorySpinner();
                }
            }
        });
        addNewProductViewModel.brandListResponseMutableLiveData.observeForever(new Observer<BrandListResponse>() {
            @Override
            public void onChanged(BrandListResponse brandListResponse) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                Log.d(TAG, "brandListResponse : " + brandListResponse.toString());
                if (brandListResponse.getBrand() != null && brandListResponse.getCount() > 0) {
                    brandHashSet.addAll(brandListResponse.getBrand());
                    Log.e(TAG, "productHashSet : " + brandHashSet);
                    setBrandSpinner();
                } else Log.e(TAG, "Product list is empty");
            }
        });

        addNewProductViewModel.addProductResponse.observeForever(new Observer<AddProductResponse>() {
            @Override
            public void onChanged(AddProductResponse addProductResponse) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                try {
                    Log.d(TAG, "AddProductResponse: " + addProductResponse.toString());
                    if (addProductResponse.getStatus().trim().equalsIgnoreCase("Success")) {
                        Log.d(TAG, "Product successfully added to server.");
                        showDialog(addProductResponse.getMsg());
                    }else
                        Toast.makeText(context, "" + addProductResponse.getMsg(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });

        addNewProductViewModel.apiStockResponse.observeForever(new Observer<APIStockListResponse>() {
            @Override
            public void onChanged(APIStockListResponse response) {
                Log.d(TAG, "apiStockListResponse: " + response.toString());
                apiStockListResponse = response;
                if (apiStockListResponse.getArrayList() != null) {
                    for (APIStockResponse apiStockResponse : apiStockListResponse.getArrayList()) {
                        if (apiStockResponse.getStockID().equalsIgnoreCase("5")) {
                            apiStockListResponse.getArrayList().remove(apiStockResponse);
                            apiStockListResponse.getArrayList().add(0, apiStockResponse);
                            break;
                        }
                    }
                }
                Log.d(TAG, "apiStockListResponse list: " + apiStockListResponse.getArrayList());
            }
        });

        addNewProductViewModel.apiUnitMeasureResponse.observeForever(new Observer<APIUnitMeasureListResponse>() {
            @Override
            public void onChanged(APIUnitMeasureListResponse response) {
                Log.d(TAG, "APIUnitMeasureListResponse: " + response.toString());
                APIUnitMeasureListResponse data = response;
                if (data != null) {
                    for (APIUnitMeasureResponse apiUnitMeasureResponse : data.getArrayList()) {
                        apiUnitMeasureResponse.setAttributesName(apiUnitMeasureResponse.getAttributesName().replace(".", ""));
                    }
                    unitMeasurements = data.getArrayList();
                } else {
                    Log.e(TAG, getString(R.string.no_information_available));
                }
                Log.d(TAG, "unitMeasurements: " + unitMeasurements);
            }
        });

    }

    AlertDialog alertDialog;

    protected void showDialog(String msg) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog);
            builder.setTitle(getString(R.string.message));
            builder.setMessage(msg);
            builder.setCancelable(false);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                            finish();
                        }
                    });
            alertDialog = builder.create();
            if (!isFinishing()) {
                alertDialog.show();
            }
        } catch (Exception e) {

        }
    }

    private void getDataFromServer() {
        if (Utils.isNetworkConnected(context)) {
            progressDialog = ProgressDialog.show(context, "Loading Data", "Please wait...");
            progressDialog.show();

            sendCategoryRequestToServer(null);
            sendSubCategoryRequestToServer(null);
//            sendProductRequestToServer(null);
            sendBrandRequestToServer(null);
            sendRequestToGetUnitMeasureList();
            sendRequestToGetStockList();
        } else
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
    }

    private void sendCategoryRequestToServer(String categoryId) {
        addNewProductViewModel.getAllCategories(new CategoryListRequest(categoryId, "0", "100"));
    }

    private void sendSubCategoryRequestToServer(String selectedCategoryId) {
        addNewProductViewModel.getAllSubCategories(new SubCategoryListRequest("0", "5000", selectedCategoryId));
    }

    private void sendProductRequestToServer(String stockType) {
        addNewProductViewModel.getAllProducts(new ProductListRequest("0", "100",
                MyProfile.getInstance().getMobileNumber(), stockType));//"1,2,3,4,5,6,7"
    }

    private void sendBrandRequestToServer(String brandId) {
        addNewProductViewModel.getAllBrands(new BrandListRequest(brandId, "0", "100"));
    }

    private void sendRequestToGetStockList() {
        addNewProductViewModel.getStockList();
    }

    private void sendRequestToGetUnitMeasureList() {
        addNewProductViewModel.getUnitMeasureList();
    }

    private void showSpinnerDialog(ArrayAdapter adapter, TextView textView, String type){
        new AlertDialog.Builder(this)
                .setTitle(type)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        dialog.dismiss();
                        textView.setText(adapter.getItem(position).toString());
                        switch (type){
                            case Utils.ICustomProduct.CHOOSE_CATEGORY:
                                    selectedCategory = categoryList.get(position);
                                    selectedCategoryId = getSelectedCategoryId(selectedCategory);
                                    updateSubCategoryList(selectedCategory);
                                    binding.tvChooseSubCategory.setText(Utils.ICustomProduct.COOSE_SUB_CATEGORY);
                                    selectedSubCategory = "";
                                    binding.llCategory.setBackground(getDrawable(R.drawable.edit_product_field_bg));
                                break;
                            case Utils.ICustomProduct.COOSE_SUB_CATEGORY :
                                selectedSubCategory = subCategoryList.get(position);
                                binding.llSubCategory.setBackground(getDrawable(R.drawable.edit_product_field_bg));
                                break;
                            case Utils.ICustomProduct.CHOOSE_BRAND :
                                selectedBrand = brandList.get(position);
                                binding.llBrand.setBackground(getDrawable(R.drawable.edit_product_field_bg));
                                break;
                        }
                    }
                }).create().show();
    }

    private ArrayAdapter setArrayAdapter(Spinner spinner, ArrayList<String> list) {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
            spinner.setSelection(0, true);  //must
        }
        return adapter;
    }

    private void setCategorySpinner() {
        if (categoryHashSet != null && categoryHashSet.size() > 0) {
            HashSet<String> set = new HashSet<>();
            for (Category category : categoryHashSet) {
                set.add(category.getName());
            }
            categoryList.addAll(set);
            Collections.sort(categoryList);
            Log.e(TAG, "categoryList : " + categoryList);
//            setArrayAdapter(binding.tvChooseCategory, categoryList);
        } else if (categoryList != null) {
            Log.e(TAG, "categoryList : " + categoryList);
//            setArrayAdapter(binding.tvChooseCategory, categoryList);
        }
    }

    private void setSubCategorySpinner() {
        if (subCategoryHashSet != null && subCategoryHashSet.size() > 0) {
            HashSet<String> set = new HashSet<>();
            if (!selectedCategory.trim().equalsIgnoreCase("")) {
                for (SubCategory subCategory : subCategoryHashSet) {
                    if ((""+subCategory.getCategoryName()).equalsIgnoreCase(selectedCategory)) {
                        set.add(subCategory.getSubCategoryName());
                    }
                }
            }else {
                for (SubCategory subCategory : subCategoryHashSet) {
                    set.add(subCategory.getSubCategoryName());
                }
            }
            subCategoryList.addAll(set);
            Collections.sort(subCategoryList);
            Log.e(TAG, "subCategoryList : " + subCategoryList);
//            setArrayAdapter(binding.tvChooseSubCategory, subCategoryList);
        } else {
            if (subCategoryList != null && subCategoryList.size() > 0)
                subCategoryList.clear();
            subCategoryList = new ArrayList<>();
//            subCategoryList.add(".Choose Sub Category *");
            Log.e(TAG, "subCategoryList : " + subCategoryList);
//            setArrayAdapter(binding.tvChooseSubCategory, subCategoryList);
        }
    }

    private void setBrandSpinner() {
        if (brandHashSet != null && brandHashSet.size() > 0) {
            HashSet<String> set = new HashSet<>();
            for (Brand brand : brandHashSet) {
                set.add(brand.getName());
            }
            brandList.addAll(set);
            Collections.sort(brandList);
            Log.e(TAG, "brandList : " + brandList);
//            setArrayAdapter(binding.tvProductBrand, brandList);
        } else if (brandList != null) {
            Log.e(TAG, "brandList : " + brandList);
//            setArrayAdapter(binding.tvProductBrand, brandList);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_product:
                progressDialog = ProgressDialog.show(context, "Data is sending", "Please wait...");
                progressDialog.show();
                if (Utils.isNetworkConnected(context)) {
                    if (isValid()) {
                        sendAddProductRequestToServer();
                    } else {
                        Toast.makeText(context, "Please enter all mandatory fields", Toast.LENGTH_SHORT).show();
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                } else {
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(context, "Please check internet connection", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_expiry:
                showExpiryDateDialog();
                break;
            case R.id.tv_add_unit:
                UnitObject newObject = new UnitObject();
                newObject.setProductUnitID("");
                newObject.setProductUpdated(false);
                showAddUnitDialog(INT_ADD_UNIT, newObject,AddUnitDialog.UNIT_FOR_CUSTOMER );
                break;

            case R.id.tv_add_unit_retailer:
                UnitObject newObject2 = new UnitObject();
                newObject2.setProductUnitID("");
                newObject2.setProductUpdated(false);
                showAddUnitDialog(INT_ADD_UNIT, newObject2,AddUnitDialog.UNITFORRETAILER );
                break;


            case R.id.tv_choose_category :
                if (categoryList != null && categoryList.size() > 0) {
                    showSpinnerDialog(setArrayAdapter(null, categoryList), binding.tvChooseCategory, Utils.ICustomProduct.CHOOSE_CATEGORY);
                }else {
                    Toast.makeText(context, "Categories are not available.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Category list empty : "+categoryList);
                }
                break;

            case R.id.tv_choose_sub_category :
                if (subCategoryList != null && subCategoryList.size() > 0) {
                    showSpinnerDialog(setArrayAdapter(null, subCategoryList), binding.tvChooseSubCategory, Utils.ICustomProduct.COOSE_SUB_CATEGORY);
                }else {
                    Toast.makeText(context, "Sub categories are not available.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Sub category list empty : "+subCategoryList);
                }
                break;

            case R.id.tv_product_brand :
                if (brandList != null && brandList.size() > 0) {
                    showSpinnerDialog(setArrayAdapter(null, brandList), binding.tvProductBrand, Utils.ICustomProduct.CHOOSE_BRAND);
                }else {
                    Toast.makeText(context, "Brand are not available.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Brand list empty : " + brandList);
                }
                break;
        }
    }

    private void showAddUnitDialog(int requestCode, UnitObject unitObject,int businessfor) {
        unitBaseAdapter = new ProductUnitAdapter(unitsList, callBackListener, true);
        binding.rvUnitBaseList.setLayoutManager(new LinearLayoutManager(context));
        binding.rvUnitBaseList.setAdapter(unitBaseAdapter);
        AddUnitDialog addUnitDialog = AddUnitDialog.newInstance(unitObject, false, apiStockListResponse,
                new APIUnitMeasures(unitMeasurements),businessfor,this);
        addUnitDialog.setCancelable(false);
        addUnitDialog.setRequestCode(requestCode);
        addUnitDialog.show(getSupportFragmentManager(), AddUnitDialog.class.getName());
    }

    private void setRecyclerView() {
        imagesList = new ArrayList<>();
        ImageURLResponse img = new ImageURLResponse();
        imagesList.add(img);
//        imagesList.add(ImageUploadAdapter.DEFAULT);

        productImagesAdapter = new ProductImagesAdapter(context, imagesList);
        binding.rvProductImagesList.setLayoutManager(new GridLayoutManager(context, 3));
        binding.rvProductImagesList.setAdapter(productImagesAdapter);

//        imageUploadAdapter = new ImageUploadAdapter(context, imagesList, callBackListener);
//        binding.rvProductImagesList.setAdapter(imageUploadAdapter);

        binding.rvProductImagesList.addOnItemTouchListener(new RecyclerTouchListener(context, "", binding.rvProductImagesList,
                new RecyclerTouchListener.ClickListener() {

                    @Override
                    public void onClick(View view, int position) {
                        if (imagesList.size() > 4 && position > 3) {
                            Toast.makeText(context, "You can add only 4 images", Toast.LENGTH_SHORT).show();
                        } else {
                            selectedPosUploadImg = position;
                            photoUploadSelected();
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
    }

    private CallBackInterface callBackListener = pObject -> {
        if (pObject instanceof ContentModel) {
            ContentModel contentModel = (ContentModel) pObject;
            String status = contentModel.getStatus();
            Object value = contentModel.getValue();
            if (status.equalsIgnoreCase(Constants.TAG_UPLOAD_NEW_PRODUCT_IMAGE)) {
                selectedPosUploadImg = (int) value;
                photoUploadSelected();
            } else if (status.equalsIgnoreCase(Constants.TAG_EDIT_PRODUCT_IMAGE)) {
                selectedPosUploadImg = (int) value;
                photoUploadSelected();
            } else if (status.equalsIgnoreCase(Constants.TAG_EDIT_UNIT)) {
                UnitObject unitObject = (UnitObject) value;
                showAddUnitDialog(INT_UPDATE_UNIT, unitObject,unitObject.getBuisness_type().equalsIgnoreCase("C")?AddUnitDialog.UNIT_FOR_CUSTOMER:AddUnitDialog.UNITFORRETAILER );
                // deleteUnits((UnitObject) value);
            }
        }
    };

    private void photoUploadSelected() {
        CropImage.activity().start(this);
    }

    private void showExpiryDateDialog() {
        Calendar expiryDateCalendar = Calendar.getInstance();
        CustomDatePickerDialog customDatePickerDialog = CustomDatePickerDialog.getInstance(Constants.CALENDAR_MIN_TYPE,
                expiryDateCalendar.get(Calendar.YEAR), expiryDateCalendar.get(Calendar.MONTH), expiryDateCalendar.get(Calendar.DAY_OF_MONTH));
        customDatePickerDialog.setCallBack(new DateTimeInterface() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDateSet(int year, int month, int dayOfMonth) {
                expiryDateCalendar.set(Calendar.YEAR, year);
                expiryDateCalendar.set(Calendar.MONTH, month);
                expiryDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                selectedExpiryDate = format.format(expiryDateCalendar.getTime());
                binding.tvExpiry.setText(selectedExpiryDate);
                binding.tvExpiry.setBackground(getDrawable(R.drawable.edit_product_field_bg));
            }

            @Override
            public void onTimeSet(int hour, int minutes, String amOrPm) {

            }
        });
        customDatePickerDialog.show(getSupportFragmentManager(), CustomDatePickerDialog.class.getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "requestCode : " + requestCode + " resultCode : " + resultCode + " data : " + data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null) {
                try {
                    Uri profileImageUri = result.getUri();
                    if (profileImageUri != null) {
                        updateImage(profileImageUri);
                    }
                } catch (Exception ex) {
                    Log.e(TAG, ex.getMessage());
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void updateList() {
        unitBaseAdapter.updateItems(unitsList);
        unitBaseAdapter.notifyDataSetChanged();
    }

    private void updateImage(Uri imageUri) {
        if (selectedPosUploadImg < imagesList.size() - 1) {
            ImageURLResponse imageUrlResponse = imagesList.get(selectedPosUploadImg);
            imageUrlResponse.setImageUri(imageUri.getPath());
            imagesList.set(selectedPosUploadImg, imageUrlResponse);
            productImagesAdapter.notifyItemChanged(selectedPosUploadImg);
        } else {
            ImageURLResponse imageUrlResponse = new ImageURLResponse();
            imageUrlResponse.setImageUri(imageUri.getPath());
            imageUrlResponse.setId(-1);
            int size = imagesList.size();
            imagesList.add(selectedPosUploadImg, imageUrlResponse);
            productImagesAdapter.notifyItemInserted(selectedPosUploadImg);
            int updatedSize = imagesList.size();
            if (updatedSize == 6) {
                imagesList.remove(size);
                productImagesAdapter.notifyItemRemoved(size);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*Log.d(TAG, "position: " + position);
        switch (parent.getId()) {
            case R.id.spinner_choose_category:
                Log.d(TAG, "category position: " + position);
                if (position > 0) {
                    selectedCategory = categoryList.get(position);
                    selectedCategoryId = getSelectedCategoryId(selectedCategory);
                    updateSubCategoryList(selectedCategory);
                    binding.llCategory.setBackground(getDrawable(R.drawable.edit_product_field_bg));
                } else {
                    selectedCategoryId = 0;
                    selectedCategory = "";
                }
                break;
            case R.id.spinner_choose_sub_category:
                Log.d(TAG, "sub category position: " + position);
                if (position > 0) {
                    selectedSubCategory = subCategoryList.get(position);
//                    updateProductList(selectedSubCategory);
                    binding.llSubCategory.setBackground(getDrawable(R.drawable.edit_product_field_bg));
                } else selectedSubCategory = "";
                break;
           *//* case R.id.spinner_choose_product:
                Log.d(TAG, "product position: " + position);
                if (position > 0) {
                    selectedProduct = productList.get(position);
                    binding.llProduct.setBackground(getDrawable(R.drawable.edit_product_field_bg));
                }
                break;*//*
            case R.id.spinner_product_brand:
                Log.d(TAG, "brand position: " + position);
                if (position > 0) {
                    selectedBrand = brandList.get(position);
                    binding.llBrand.setBackground(getDrawable(R.drawable.edit_product_field_bg));
                } else selectedBrand = "";
                break;
        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(TAG, "Nothing selected: ");
    }

    private int getSelectedCategoryId(String categoryName) {
        if (categoryHashSet != null) {
            for (Category category : categoryHashSet) {
                if (category.getName().trim().equalsIgnoreCase(categoryName.trim()))
                    return category.getId();
            }
        }
        return 0;
    }

    private String getSelectedBrandId(String brandName) {
        if (brandHashSet != null) {
            for (Brand brand : brandHashSet) {
                if (brand.getName().trim().equalsIgnoreCase(brandName.trim()))
                    return "" + brand.getId();
            }
        }
        return "";
    }

    private void updateSubCategoryList(String categoryName) {
        Log.d(TAG, "categoryName: " + categoryName);
        if (subCategoryHashSet != null && subCategoryHashSet.size() > 0) {
            subCategoryList = new ArrayList<>();
//            subCategoryList.add(".Choose Sub Category *");
            HashSet<String> set = new HashSet<>();
            for (SubCategory subCategory : subCategoryHashSet) {
                if ((""+subCategory.getCategoryName()).equalsIgnoreCase(categoryName)) {
                    set.add(subCategory.getSubCategoryName());
                }
            }
            subCategoryList.addAll(set);
            Log.d(TAG, "subCategoryList: " + subCategoryList);
            if (subCategoryList.size() <= 0){
                progressDialog = ProgressDialog.show(context, "Loading Sub Category", "Please wait");
                progressDialog.show();
                sendSubCategoryRequestToServer(""+selectedCategoryId);
            }
        }else {
            progressDialog = ProgressDialog.show(context, "Loading Sub Category", "Please wait");
            progressDialog.show();
            sendSubCategoryRequestToServer(""+selectedCategoryId);
        }
    }

    private void sendAddProductRequestToServer() {
        AddProductRequest request = getAddProductRequest();
        addNewProductViewModel.addProduct(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean isValid() {
        boolean result = true;
        if (selectedCategory.trim().equalsIgnoreCase("")) {
            result = false;
            binding.llCategory.setBackground(getDrawable(R.drawable.edit_product_field_bg_error));
        } else
            binding.llCategory.setBackground(getDrawable(R.drawable.edit_product_field_bg));

        if (selectedSubCategory.trim().equalsIgnoreCase("")) {
            result = false;
            binding.llSubCategory.setBackground(getDrawable(R.drawable.edit_product_field_bg_error));
        } else binding.llSubCategory.setBackground(getDrawable(R.drawable.edit_product_field_bg));

        if (binding.etProductName.getText().toString().trim().equalsIgnoreCase("")) {
            result = false;
//            binding.productNameLayout.setBackground(getDrawable(R.drawable.edit_product_field_bg_error));
        } //else binding.productNameLayout.setBackground(getDrawable(R.drawable.edit_product_field_bg));

        if (selectedBrand.trim().equalsIgnoreCase("")) {
            result = false;
            binding.llBrand.setBackground(getDrawable(R.drawable.edit_product_field_bg_error));
        } else binding.llBrand.setBackground(getDrawable(R.drawable.edit_product_field_bg));

        if (selectedExpiryDate.trim().equalsIgnoreCase("")) {
            result = false;
            binding.tvExpiry.setBackground(getDrawable(R.drawable.edit_product_field_bg_error));
        } else binding.tvExpiry.setBackground(getDrawable(R.drawable.edit_product_field_bg));

        Log.e(TAG, "Unit list : " + unitsList);

        if (unitsList != null && unitsList.size() <= 0) {
            result = false;
            Log.e(TAG, "Unit list is empty " + unitsList);
            Toast.makeText(context, "Please add at least one unit", Toast.LENGTH_SHORT).show();
        }

        Log.e(TAG, "isValid result: " + result);
        return result;
    }

    private AddProductRequest getAddProductRequest() {
//        String jsonRequest = "";
//        JSONObject object = null;
        AddProductRequest addProductRequest = new AddProductRequest();
        JsonObject jsonObject = new JsonObject();
        try {
//            jsonObject = new JSONObject();
            jsonObject.addProperty("brand_id", getSelectedBrandId(selectedBrand));
            jsonObject.addProperty("brand_name", selectedBrand.trim().replaceAll("&", "And"));
            jsonObject.addProperty("category_name", selectedCategory.trim().replaceAll("&", "And"));
            jsonObject.addProperty("category_id", ""+selectedCategoryId);
            jsonObject.addProperty("product_desc", binding.etProductDescription.getText().toString().replaceAll("&", "And"));//"04-12-2020"
            jsonObject.addProperty("expiry_date", selectedExpiryDate);
            jsonObject.addProperty("product_name", binding.etProductName.getText().toString()
                    .trim().replaceAll("&", "And"));
            jsonObject.addProperty("product_regional_name", binding.etProductRegionalName.getText().toString());
            jsonObject.addProperty("sub_category_name", selectedSubCategory.trim().replaceAll("&", "And"));
//            jsonObjecaddProperty();ut("type", "");
            jsonObject.addProperty("msg", "");
            jsonObject.addProperty("status", "");
            jsonObject.addProperty("quantity", "1");
            jsonObject.addProperty("brand", "1");
            jsonObject.addProperty("client_id", "2");
            jsonObject.addProperty("user_id", MyProfile.getInstance().getUserID());//"449");//
            jsonObject.addProperty("product_video_link", "" + binding.etProductVideoLink.getText().toString().replaceAll("&", "And"));
            jsonObject.add("units", getUnitJsonArray()); // array
            jsonObject.addProperty("stock_id", selectedStockIds);

            ArrayList<ImageURLResponse> updateImagesList = new ArrayList<>();
            setImageURL(updateImagesList);

            jsonObject.add("images", getImageDetailsJsonArray(updateImagesList)); //array

//                Gson gson = new Gson();
//                String registrionJSonArray = gson.toJson(jsonObject);
//                object = new JSONObject(jsonObject.toString().replaceAll("^\"|\"$&", ""));
//                int string_length = object.length();

            addProductRequest.setJsonObject(jsonObject);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        Log.e(TAG, "object: " + jsonObject);
//        Log.e(TAG, "object: " + object);
//        Log.e(TAG, "jsonRequest: " + jsonRequest);
        return addProductRequest;
    }

    private JsonElement getUnitJsonArray() {
//        JsonElement element = null;
        JsonArray array = new JsonArray();
        try {
            if (unitsList != null && unitsList.size() > 0) {
                for (UnitObject unit : unitsList) {
                    JsonObject object = new JsonObject();
                    object.addProperty("unit_price", unit.getActualCost());
                    object.addProperty("discount", unit.getDiscount());
                    object.addProperty("unit_name", unit.getStockName());
                    object.addProperty("selling_price", unit.getFinalCost());
                    object.addProperty("isActive", unit.isActive());
                    object.addProperty("isProductUpdated", unit.isProductUpdated());
                    object.addProperty("maxDiscount", unit.getMaxDiscount());
                    object.addProperty("minDiscount", unit.getMinDiscount());
                    object.addProperty("position", unit.getPosition());
                    object.addProperty("product_unit_id", unit.getProductUnitID());
                    object.addProperty("quantity", unit.getQuantity());
                    object.addProperty("buisness_type", unit.getBuisness_type());
                    object.addProperty("stock_id", unit.getStockID());
//                    if (selectedStockIds.trim().equalsIgnoreCase(""))
                    selectedStockIds = unit.getStockID();
//                    else selectedStockIds = selectedStockIds + "," + unit.getStockID();

                    object.addProperty("stock_name", unit.getStockName());
                    object.addProperty("timeStamp", (unit.getTimeStamp() == -1) ? 0 : unit.getTimeStamp());
                    object.addProperty("unit_id", unit.getUnitID());
                    object.addProperty("unitMeasure", unit.getUnitMeasure());
                    object.addProperty("unit_number", unit.getUnitNumber());

                    array.add(object);
                }
//                Gson gson = new GsonBuilder().create();
//                element = gson.toJsonTree(array);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "unitarray: " + array);
        return array;
    }

    private void setImageURL(ArrayList<ImageURLResponse> lUpdateImagesList) {
        try {
            for (ImageURLResponse imageURLResponse : imagesList) {
                Bitmap bitmap = null;
                try {
                    String imageUri = imageURLResponse.getImageUri();
                    if (imageUri != null) {
                        Uri uri = Uri.fromFile(new File(imageUri));
                        if (uri != null) {
                            InputStream imageStream = getContentResolver().openInputStream(uri);
                            bitmap = BitmapFactory.decodeStream(imageStream);
                            bitmap = Bitmap.createScaledBitmap(bitmap, 900, 900, true);

                            imageURLResponse.setImageRawData(getEncodedImage(bitmap));
                            imageURLResponse.setIsImageUpdated(0);
                            lUpdateImagesList.add(imageURLResponse);
                        }
                    } else if ((null != imageURLResponse.getDisplayImage() && imageURLResponse.getDisplayImage().length() > 10) ||
                            (null != imageURLResponse.getImageURL() && imageURLResponse.getImageURL().length() > 10)) {
                        lUpdateImagesList.add(imageURLResponse);
                    }
                } catch (Exception ex) {
                    LoggerInfo.printLog("image error", "exception " + ex.getMessage());
                } finally {
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "error image: " + e.getMessage());
        }
    }

    private String getEncodedImage(Bitmap bitmap) {
        try {
            if (bitmap != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] b = byteArrayOutputStream.toByteArray();
                return Base64.encodeToString(b, Base64.DEFAULT);
            }
        } catch (Exception ex) {
            LoggerInfo.errorLog("encode image exception", ex.getMessage());
        }
        return "";
    }

    private JsonElement getImageDetailsJsonArray(ArrayList<ImageURLResponse> lUpdateImagesList) {
//        JsonElement element = null;
        JsonArray array = new JsonArray();
        try {
            if (lUpdateImagesList != null && lUpdateImagesList.size() > 0) {
                for (ImageURLResponse image : lUpdateImagesList) {
                    JsonObject object = new JsonObject();
                    object.addProperty("image_name", image.getImageName());
                    object.addProperty("image_show", image.getImageShow());
                    object.addProperty("image_rawdata", image.getImageRawData());
                    object.addProperty("isImageUpdated", image.getIsImageUpdated());
                    object.addProperty("isPrimary", image.isPrimary());
                    object.addProperty("isProductVideoSelected", image.isProductVideoSelected());
                    object.addProperty("msg", "");
                    object.addProperty("status", "");

                    array.add(object);
                }
//                Gson gson = new GsonBuilder().create();
//                element = gson.toJsonTree(array);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "image Array: " + array);
        return array;
    }

    @Override
    public void onSaveUnit(int requestCode, Intent data) {
        Log.e(TAG, "requestCode : " + requestCode + " data : " + data);
        binding.llUnitTitle.setVisibility(View.VISIBLE);
        if (requestCode == INT_ADD_UNIT) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    UnitObject unitData = (UnitObject) bundle.getSerializable(UNIT_VALUE);
                    unitsList.add(unitData);
                    updateList();
                }
            }
        } else if (requestCode == INT_UPDATE_UNIT) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    if (bundle.getBoolean("IS_DELETED", false)) {
                        UnitObject unitData = (UnitObject) bundle.getSerializable(UNIT_VALUE);
                        if (unitData != null) {
                            unitsList.remove(unitData.getPosition());
                            unitBaseAdapter.updateItems(unitsList);
                            unitBaseAdapter.notifyDataSetChanged();
                        }
                    } else {
                        UnitObject unitData = (UnitObject) bundle.getSerializable(UNIT_VALUE);
                        if (unitData != null) {
                            unitsList.set(unitData.getPosition(), unitData);
                            unitBaseAdapter.updateItems(unitsList);
                            unitBaseAdapter.notifyDataSetChanged();
                        }
                    }
                    updateList();
                }
            }
        }
    }

    private void addHintWithArrayAdapter(ArrayList<String> list) {
        HintAdapter hintAdapter = new HintAdapter(context, android.R.layout.simple_spinner_dropdown_item, list);
        hintAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        setSelection(hintAdapter.count)
    }

    class HintAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> list;

        public HintAdapter(@NonNull Context context, int resource, ArrayList<String> list) {
            super(context, resource);
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            int count = super.getCount();
            // The last item will be the hint.
            return (count > 0) ? (count - 1) : count;
        }
    }

}
