package com.rmart.inventory.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.models.ContentModel;
import com.rmart.inventory.adapters.ImageUploadAdapter;
import com.rmart.inventory.adapters.ProductUnitAdapter;
import com.rmart.inventory.models.UnitObject;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.HttpsTrustManager;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.custom_views.CustomDatePicker;
import com.rmart.utilits.custom_views.CustomNetworkImageView;
import com.rmart.utilits.pojos.APIBrandListResponse;
import com.rmart.utilits.pojos.APIBrandResponse;
import com.rmart.utilits.pojos.APIStockListResponse;
import com.rmart.utilits.pojos.APIUnitMeasureListResponse;
import com.rmart.utilits.pojos.APIUnitMeasureResponse;
import com.rmart.utilits.pojos.AddProductToInventoryResponse;
import com.rmart.utilits.pojos.ImageURLResponse;
import com.rmart.utilits.pojos.ProductResponse;
import com.rmart.utilits.pojos.ShowProductResponse;
import com.rmart.utilits.services.APIService;
import com.rmart.utilits.services.VendorInventoryService;
import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AddProductToInventory extends BaseInventoryFragment implements View.OnClickListener {

    private static final String ARG_PRODUCT = "product";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM1 = "param1";
    public static final int INT_ADD_UNIT = 101;
    public static final int INT_UPDATE_UNIT = 102;
    public static final String UNIT_VALUE = "unit_value";

    private ProductResponse mClonedProduct;
    private boolean isEdit;

    ArrayList<APIUnitMeasureResponse> unitMeasurements = new ArrayList<>();
    ArrayList<APIBrandResponse> apiBrandResponses = new ArrayList<>();

    public AppCompatTextView chooseCategory, chooseSubCategory, chooseProduct, productBrand;
    // CustomStringAdapter customStringAdapter;
    // Spinner productBrand;
    AppCompatEditText productRegionalName, deliveryDays, productDescription;
    AppCompatTextView expiry;
    private RecyclerView unitsRecyclerView;
    APIService apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
    private ArrayList<String> availableBrands = new ArrayList<>();
    private APIStockListResponse stockListResponse = null;
    private ArrayList<ImageURLResponse> images;
    private int selectedImagePosition = -1;
    private CustomNetworkImageView ivProductImageOneField;
    private CustomNetworkImageView ivProductImageTwoField;
    private CustomNetworkImageView ivProductImageThreeField;
    private CustomNetworkImageView ivProductImageFourField;
    private CustomNetworkImageView ivProductImageFiveField;
    private ProductUnitAdapter unitBaseAdapter;

    public AddProductToInventory() {
        // Required empty public constructor
    }

    public static AddProductToInventory newInstance(ProductResponse product, boolean isEdit, APIStockListResponse stockListResponse) {
        AddProductToInventory fragment = new AddProductToInventory();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        args.putSerializable(ARG_PARAM1, stockListResponse);
        args.putBoolean(ARG_PARAM2, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    private CallBackInterface callBackListener = pObject -> {
        if (pObject instanceof ContentModel) {
            ContentModel contentModel = (ContentModel) pObject;
            String status = contentModel.getStatus();
            Object value = contentModel.getValue();
            if (status.equalsIgnoreCase(Constants.TAG_UPLOAD_NEW_PRODUCT_IMAGE)) {
                //selectedPosition = (int) value;
                photoUploadSelected();
            } else if (status.equalsIgnoreCase(Constants.TAG_EDIT_PRODUCT_IMAGE)) {
                //selectedPosition = (int) value;
                photoUploadSelected();
            } else if (status.equalsIgnoreCase(Constants.TAG_DELETE)) {
                deleteUnits((UnitObject) value);
            }
        }
    };

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        LoggerInfo.printLog("Fragment", "AddProductToInventory");

        getUnitMeasuresFromAPI();
        //getBrandFromAPI();
        return inflater.inflate(R.layout.fragment_edit_product, container, false);
    }

    private void getBrandFromAPI() {
        progressDialog.show();
        apiService.getAPIBrandList().enqueue(new Callback<APIBrandListResponse>() {
            @Override
            public void onResponse(@NotNull Call<APIBrandListResponse> call, @NotNull Response<APIBrandListResponse> response) {
                if (response.isSuccessful()) {
                    APIBrandListResponse data = response.body();
                    if (data != null) {
                        apiBrandResponses = data.getArrayList();
                        for (APIBrandResponse apiBrandResponse : apiBrandResponses) {
                            availableBrands.add(apiBrandResponse.getBrandName());
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                } else {
                    showDialog("", response.message());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<APIBrandListResponse> call, @NotNull Throwable t) {
                showDialog("", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void getUnitMeasuresFromAPI() {
        progressDialog.show();
        apiService.getAPIUnitMeasureList().enqueue(new Callback<APIUnitMeasureListResponse>() {
            @Override
            public void onResponse(@NotNull Call<APIUnitMeasureListResponse> call, @NotNull Response<APIUnitMeasureListResponse> response) {
                if (response.isSuccessful()) {
                    APIUnitMeasureListResponse data = response.body();
                    if (data != null) {
                        unitMeasurements = data.getArrayList();
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                } else {
                    showDialog("", response.message());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<APIUnitMeasureListResponse> call, @NotNull Throwable t) {
                showDialog("", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ProductResponse mProduct = (ProductResponse) getArguments().getSerializable(ARG_PRODUCT);
            //stockListResponse = (APIStockListResponse) getArguments().getSerializable(ARG_PARAM1);

            if (null == mProduct) {
                mClonedProduct = new ProductResponse();
                //mProduct = new ProductResponse();
            } else {
                try {
                    mClonedProduct = new ProductResponse(mProduct);
                } catch (Exception e) {
                    showDialog("", e.getMessage());
                }
            }
            isEdit = getArguments().getBoolean(ARG_PARAM2);
        }
    }

    private void updateUI() {
        // chooseCategory.setText(mClonedProduct.getCategory());
        if (null != mClonedProduct) {
            chooseSubCategory.setText(mClonedProduct.getSubCategory());
            chooseProduct.setText(mClonedProduct.getName());
            // productBrand.setText(mClonedProduct.getBrand());
            productRegionalName.setText(mClonedProduct.getRegionalName());
            productDescription.setText(mClonedProduct.getDescription());
            expiry.setText(mClonedProduct.getExpiry_date());
            deliveryDays.setText(mClonedProduct.getDelivery_days());
            productBrand.setText(mClonedProduct.getBrandName());
            updateList();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chooseCategory = view.findViewById(R.id.choose_category);
        chooseSubCategory = view.findViewById(R.id.choose_sub_category);
        chooseProduct = view.findViewById(R.id.choose_product);
        productBrand = view.findViewById(R.id.product_brand);
        /*productBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                mClonedProduct.setBrand(apiBrandResponses.get(pos).getBrandName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        productRegionalName = view.findViewById(R.id.product_regional_name);

        productDescription = view.findViewById(R.id.product_description);
        expiry = view.findViewById(R.id.expiry);
        expiry.setOnClickListener(this);
        deliveryDays = view.findViewById(R.id.delivery_days);
        unitsRecyclerView = view.findViewById(R.id.unit_base);

        unitsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // addUnit = view.findViewById(R.id.add_unit);
        AppCompatButton save = view.findViewById(R.id.save);
        if (isEdit) {
            save.setText(getString(R.string.update));
        } else {
            save.setText(getString(R.string.save));
        }
        save.setOnClickListener(this);
        view.findViewById(R.id.add_unit).setOnClickListener(this);

        //imageUploadAdapter = new ImageUploadAdapter(imagesList, callBackListener);
        //imagesRecyclerView.setAdapter(imageUploadAdapter);
        ivProductImageOneField = view.findViewById(R.id.iv_product_image_one_field);
        ivProductImageTwoField = view.findViewById(R.id.iv_product_image_two_field);
        ivProductImageThreeField = view.findViewById(R.id.iv_product_image_three_field);
        ivProductImageFourField = view.findViewById(R.id.iv_product_image_four_field);
        ivProductImageFiveField = view.findViewById(R.id.iv_product_image_five_field);

        images = new ArrayList<>();
        if (mClonedProduct != null) {
            List<ImageURLResponse> clonedImagesList = mClonedProduct.getImageDataObject();
            int difference = 5 - clonedImagesList.size();
            images.addAll(clonedImagesList);
            /*for (int i = 0; i < difference; i++) {
                images.add(ImageUploadAdapter.DEFAULT);
            }*/
        }
        ImageLoader imageLoader = RMartApplication.getInstance().getImageLoader();
        HttpsTrustManager.allowAllSSL();
        for (int i = 0; i < images.size(); i++) {
            Object object = images.get(i);
            if (object instanceof ImageURLResponse) {
                ImageURLResponse imageURLResponse = (ImageURLResponse) object;
                String productUrl = imageURLResponse.getImageURL();
                if (!TextUtils.isEmpty(productUrl)) {
                    switch (i) {
                        case 0:
                            imageLoader.get(productUrl, ImageLoader.getImageListener(ivProductImageOneField,
                                    R.mipmap.ic_launcher, android.R.drawable
                                            .ic_dialog_alert));
                            ivProductImageOneField.setImageUrl(productUrl, imageLoader);
                            break;
                        case 1:
                            imageLoader.get(productUrl, ImageLoader.getImageListener(ivProductImageTwoField,
                                    R.mipmap.ic_launcher, android.R.drawable
                                            .ic_dialog_alert));
                            ivProductImageTwoField.setImageUrl(productUrl, imageLoader);
                            break;
                        case 2:
                            imageLoader.get(productUrl, ImageLoader.getImageListener(ivProductImageThreeField,
                                    R.mipmap.ic_launcher, android.R.drawable
                                            .ic_dialog_alert));
                            ivProductImageThreeField.setImageUrl(productUrl, imageLoader);
                            break;
                        case 3:
                            imageLoader.get(productUrl, ImageLoader.getImageListener(ivProductImageFourField,
                                    R.mipmap.ic_launcher, android.R.drawable
                                            .ic_dialog_alert));
                            ivProductImageFourField.setImageUrl(productUrl, imageLoader);
                            break;
                        case 4:
                            imageLoader.get(productUrl, ImageLoader.getImageListener(ivProductImageFiveField,
                                    R.mipmap.ic_launcher, android.R.drawable
                                            .ic_dialog_alert));
                            ivProductImageFiveField.setImageUrl(productUrl, imageLoader);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        ivProductImageOneField.setOnClickListener(this);
        ivProductImageTwoField.setOnClickListener(this);
        ivProductImageThreeField.setOnClickListener(this);
        ivProductImageFourField.setOnClickListener(this);
        ivProductImageFiveField.setOnClickListener(this);

        updateUI();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add_unit:
                mListener.addUnit(new UnitObject(unitMeasurements), this, INT_ADD_UNIT);
                break;
            case R.id.save:
                saveSelected();
                break;
            case R.id.expiry:
                new CustomDatePicker((AppCompatTextView) view, getActivity(), Utils.YYYY_MM_DD);
                break;
            case R.id.iv_product_image_one_field:
                selectedImagePosition = 0;
                captureImageSelected();
                break;
            case R.id.iv_product_image_two_field:
                selectedImagePosition = 1;
                captureImageSelected();
                break;
            case R.id.iv_product_image_three_field:
                selectedImagePosition = 2;
                captureImageSelected();
                break;
            case R.id.iv_product_image_four_field:
                selectedImagePosition = 3;
                captureImageSelected();
                break;
            case R.id.iv_product_image_five_field:
                selectedImagePosition = 4;
                captureImageSelected();
                break;
            default:
                break;
        }
    }

    private void captureImageSelected() {
        CropImage.activity()
                .start(requireActivity(), this);
    }

    private void saveSelected() {
        if (Objects.requireNonNull(expiry.getText()).toString().length() <= 2) {
            showDialog("", "Please enter valid expiry date.");
            return;
        } else if (Objects.requireNonNull(productRegionalName.getText()).toString().length() <= 1) {
            showDialog("", "Please enter valid Regional name.");
            return;
        } /*else if( Objects.requireNonNull(deliveryDays.getText()).toString().length()<=0) {
                showDialog("", "Please enter valid days to delivery.");
                return;
            } */ else if (Objects.requireNonNull(productDescription.getText()).toString().length() <= 1) {
            showDialog("", "Please enter valid product description");
            return;
        } else if (Objects.requireNonNull(mClonedProduct).getUnitObjects().size() < 1) {
            showDialog("", "Please add at least one unit");
            return;
        }

        mClonedProduct.setExpiry_date(expiry.getText().toString());
        mClonedProduct.setRegionalName(productRegionalName.getText().toString());
        mClonedProduct.setDelivery_days(Objects.requireNonNull(deliveryDays.getText()).toString());
        mClonedProduct.setDescription(productDescription.getText().toString());
        ArrayList<ImageURLResponse> lUpdateImagesList = new ArrayList<>();
        setImageURL(lUpdateImagesList);
        mClonedProduct.setImageDataObject(lUpdateImagesList);
        progressDialog.show();

        if (isEdit) {
                /*Objects.requireNonNull(inventoryViewModel.getProductList().getValue()).remove(mProduct.getProductID());
                Objects.requireNonNull(inventoryViewModel.getProductList().getValue()).put(mClonedProduct.getProductID(), mClonedProduct);
                Integer index = inventoryViewModel.getSelectedProduct().getValue();
                inventoryViewModel.getSelectedProduct().setValue(index);*/
            Gson gson = new GsonBuilder().create();
            JsonElement jsonElement = gson.toJsonTree(mClonedProduct);
            JsonObject jsonObject = (JsonObject) jsonElement;

            jsonObject.addProperty("stock_id", "5");
            jsonObject.addProperty("quantity", "2");
            jsonObject.addProperty("brand", 2);
            jsonObject.addProperty("expiry_date", mClonedProduct.getExpiry_date());

            jsonObject.addProperty("delivery_days", "3");
            jsonObject.addProperty("client_id", "2");
            jsonObject.addProperty("user_id", MyProfile.getInstance().getUserID());
            jsonObject.addProperty("product_video_link", "https://www.youtube.com/watch?v=pWjfA4hBNe8&ab_channel=CricketCloud");
            RetrofitClientInstance.getRetrofitInstance().create(VendorInventoryService.class).editProductToInventory(jsonObject).enqueue(new Callback<AddProductToInventoryResponse>() {
                @Override
                public void onResponse(@NotNull Call<AddProductToInventoryResponse> call, @NotNull Response<AddProductToInventoryResponse> response) {
                    if (response.isSuccessful()) {
                        AddProductToInventoryResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                showDialog(mClonedProduct.getName() + " " + getString(R.string.add_success_product),
                                        pObject -> requireActivity().onBackPressed());
                            } else {
                                showDialog("", data.getMsg(),
                                        pObject -> requireActivity().onBackPressed());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog("", response.message());
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<AddProductToInventoryResponse> call, @NotNull Throwable t) {
                    showDialog("", t.getMessage());
                    progressDialog.dismiss();
                }
            });
        } else {

            VendorInventoryService vendorInventoryService = RetrofitClientInstance.getRetrofitInstance().create(VendorInventoryService.class);
            // Gson gson = new Gson();
            Gson gson = new GsonBuilder().create();
            mClonedProduct.setImageDataObject(images);
            JsonElement jsonElement = gson.toJsonTree(mClonedProduct);
            JsonObject jsonObject = (JsonObject) jsonElement;
            jsonObject.addProperty("stock_id", "5");
            jsonObject.addProperty("quantity", "2");
            jsonObject.addProperty("brand", 2);
            jsonObject.addProperty("expiry_date", mClonedProduct.getExpiry_date());
            jsonObject.addProperty("delivery_days", MyProfile.getInstance().getDeliveryInDays());
            jsonObject.addProperty("client_id", "2");
            jsonObject.addProperty("user_id", MyProfile.getInstance().getUserID());
            jsonObject.addProperty("product_video_link", "https://www.youtube.com/watch?v=pWjfA4hBNe8&ab_channel=CricketCloud");

            // JsonObject product = gson.toJson(mClonedProduct);
            // JsonObject jsonObject = new JsonObject();
            vendorInventoryService.addProductToInventory(jsonObject).enqueue(new Callback<AddProductToInventoryResponse>() {
                @Override
                public void onResponse(@NotNull Call<AddProductToInventoryResponse> call, @NotNull Response<AddProductToInventoryResponse> response) {
                    if (response.isSuccessful()) {
                        AddProductToInventoryResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                showDialog("", mClonedProduct.getName() + " " + getString(R.string.add_success_product),
                                        pObject -> requireActivity().onBackPressed());
                            } else {
                                showDialog("", data.getMsg(),
                                        pObject -> requireActivity().onBackPressed());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog("", response.message());
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<AddProductToInventoryResponse> call, @NotNull Throwable t) {
                    showDialog("", t.getMessage());
                    progressDialog.dismiss();
                }
            });

        }
    }

    private void setImageURL(ArrayList<ImageURLResponse> lUpdateImagesList) {
        for (Object lObject : images) {
            if (lObject instanceof ImageURLResponse) {
                Bitmap bitmap = null;
                try {
                    ImageURLResponse imageURLResponse = (ImageURLResponse) lObject;
                    Uri imageUri =  Uri.parse(imageURLResponse.getImageURL()); // imageURLResponse.getImageUri();
                    if (imageUri != null) {
                        InputStream imageStream = requireActivity().getContentResolver().openInputStream(imageUri);
                        bitmap = BitmapFactory.decodeStream(imageStream);
                        imageURLResponse.setImageRawData(getEncodedImage(bitmap));
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
        }
    }

    private void updateList() {
        unitBaseAdapter = new ProductUnitAdapter(mClonedProduct.getUnitObjects(), callBackListener, true);
        unitsRecyclerView.setAdapter(unitBaseAdapter);
    }

    private void updateUnitsUI(UnitObject unitObject) {
        ArrayList<UnitObject> unitsList = mClonedProduct.getUnitObjects();
        int index = unitsList.indexOf(unitObject);
        if (index > -1) {
            unitsList.remove(index);
            unitBaseAdapter.notifyItemRemoved(index);
            mClonedProduct.setUnitObjects(unitsList);
        }
    }

    private void deleteUnits(UnitObject unitObject) {
        progressDialog.show();
        VendorInventoryService inventoryService = RetrofitClientInstance.getRetrofitInstance().create(VendorInventoryService.class);
        inventoryService.deleteProductUnit(MyProfile.getInstance().getUserID(), unitObject.getUnitID()).enqueue(new Callback<ShowProductResponse>() {
            @Override
            public void onResponse(@NotNull Call<ShowProductResponse> call, @NotNull Response<ShowProductResponse> response) {
                if (response.isSuccessful()) {
                    ShowProductResponse data = response.body();
                    if (data != null) {
                        if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                            updateUnitsUI(unitObject);
                        } else {
                            showDialog(data.getMsg());
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                } else {
                    showDialog(response.message());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<ShowProductResponse> call, @NotNull Throwable t) {
                showDialog(t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void photoUploadSelected() {
        CropImage.activity()
                .start(requireActivity(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                    showDialog("", ex.getMessage());
                }
            }
        } else if (requestCode == INT_ADD_UNIT) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    UnitObject unitData = (UnitObject) bundle.getSerializable(UNIT_VALUE);
                    mClonedProduct.getUnitObjects().add(unitData);
                    updateList();
                }
            }
        }
    }

    private void updateImage(Uri imageUri) {
        Object lObject = images.get(selectedImagePosition);
        if (lObject instanceof String) {
            ImageURLResponse imageUrlResponse = new ImageURLResponse();
            //imageUrlResponse.setImageRawData(getEncodedImage(bitmap));
            images.set(selectedImagePosition, imageUrlResponse);
        } else if (lObject instanceof ImageURLResponse) {
            ImageURLResponse imageUrlResponse = (ImageURLResponse) lObject;
            //imageUrlResponse.setImageRawData(getEncodedImage(bitmap));
            images.set(selectedImagePosition, imageUrlResponse);
        }
        displayImagesUI(imageUri);
    }

    private void displayImagesUI(Uri imageUri) {
        switch (selectedImagePosition) {
            case 0:
                ivProductImageOneField.setLocalImageUri(imageUri);
                break;
            case 1:
                ivProductImageTwoField.setLocalImageUri(imageUri);
                break;
            case 2:
                ivProductImageThreeField.setLocalImageUri(imageUri);
                break;
            case 3:
                ivProductImageFourField.setLocalImageUri(imageUri);
                break;
            case 4:
                ivProductImageFiveField.setLocalImageUri(imageUri);
                break;
            default:
                break;
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
}