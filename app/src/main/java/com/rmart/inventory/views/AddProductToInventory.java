package com.rmart.inventory.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
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
import com.rmart.inventory.adapters.ProductUnitAdapter;
import com.rmart.inventory.models.APIUnitMeasures;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
                UnitObject unitObject = (UnitObject) value;
                UnitObject unitObject1 = new UnitObject(unitObject);
                mListener.addUnit(unitObject1,new APIUnitMeasures(unitMeasurements), this, INT_UPDATE_UNIT);
                // deleteUnits((UnitObject) value);
            }/* else if (status.equalsIgnoreCase(Constants.TAG_EDIT_UNIT)) {
                mListener.addUnit((UnitObject) value,new APIUnitMeasures(unitMeasurements), this, INT_UPDATE_UNIT);
            }*/
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
                            //availableBrands.add(apiBrandResponse.getBrandName());
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
                    for (ImageURLResponse imageURLResponse: mClonedProduct.getImageDataObject()) {
                        if (null != imageURLResponse.getDisplayImage() && imageURLResponse.getDisplayImage().length()>10)
                        imageURLResponse.setImageURL(imageURLResponse.getDisplayImage());
                    }
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
            images.addAll(clonedImagesList);
        }
        ImageLoader imageLoader = RMartApplication.getInstance().getImageLoader();
        HttpsTrustManager.allowAllSSL();
        for (int i = 0; i < images.size(); i++) {
            ImageURLResponse imageURLResponse = images.get(i);
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
                UnitObject newObject = new UnitObject();
                // newObject.setDefaultValues();
                mListener.addUnit(newObject, new APIUnitMeasures(unitMeasurements), this, INT_ADD_UNIT);
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


        long previousTime = Calendar.getInstance().getTimeInMillis();
        long presentTime = -1;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(Utils.  YYYY_MM_DD); // here set the pattern as you date in string was containing like date/month/year
            Date d = sdf.parse(expiry.getText().toString());
            Calendar temp = Calendar.getInstance();
            temp.setTime(d);
            temp.set(Calendar.HOUR_OF_DAY, 23);
            temp.set(Calendar.MINUTE, 59);

            presentTime = temp.getTimeInMillis();

        }catch(ParseException ex){
            // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
        }
        if (Objects.requireNonNull(productRegionalName.getText()).toString().length() <= 1) {
            showDialog("", getString(R.string.erroe_regional_name));
            return;
        } else if (Objects.requireNonNull(productDescription.getText()).toString().length() <= 1) {
            showDialog("", getString(R.string.error_product_description));
            return;
        } else if (Objects.requireNonNull(mClonedProduct).getUnitObjects().size() < 1) {
            showDialog("", getString(R.string.unit_required));
            return;
        } else if (Objects.requireNonNull(expiry.getText()).toString().length() <= 2 || previousTime > presentTime) {
            showDialog("", getString(R.string.erroe_expiry_date));
            return;
        }

        mClonedProduct.setExpiry_date(expiry.getText().toString());
        mClonedProduct.setRegionalName(productRegionalName.getText().toString());
        mClonedProduct.setDelivery_days(Objects.requireNonNull(deliveryDays.getText()).toString());
        mClonedProduct.setDescription(productDescription.getText().toString());
        ArrayList<ImageURLResponse> updateImagesList = new ArrayList<>();
        setImageURL(updateImagesList);
        mClonedProduct.setImageDataObject(updateImagesList);
        progressDialog.show();

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

        VendorInventoryService vendorInventoryService = RetrofitClientInstance.getRetrofitInstance().create(VendorInventoryService.class);
        if (isEdit) {
            vendorInventoryService.editProductToInventory(jsonObject).enqueue(new Callback<AddProductToInventoryResponse>() {
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

    private void photoUploadSelected() {
        CropImage.activity()
                .start(requireActivity(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_OK)
            return;
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
        } else if (requestCode == INT_UPDATE_UNIT) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {

                   if (bundle.getBoolean("IS_DELETED", false)) {
                       UnitObject unitData = (UnitObject) bundle.getSerializable(UNIT_VALUE);
                       assert unitData != null;
                       if (unitData.getTimeStamp() != -1) {
                           for (int i = 0; i < mClonedProduct.getUnitObjects().size(); i++) {
                               if (mClonedProduct.getUnitObjects().get(i).getTimeStamp() == unitData.getTimeStamp()) {
                                   mClonedProduct.getUnitObjects().remove(i);
                                   break;
                               }
                           }
                       } else {
                           for (int i = 0; i < mClonedProduct.getUnitObjects().size(); i++) {
                               if (mClonedProduct.getUnitObjects().get(i).getProductUnitID().equalsIgnoreCase(unitData.getProductUnitID())) {
                                   mClonedProduct.getUnitObjects().remove(i);
                                   break;
                               }
                           }
                       }

                   } else {
                       UnitObject unitData = (UnitObject) bundle.getSerializable(UNIT_VALUE);
                       assert unitData != null;
                       if (unitData.getTimeStamp() != -1) {
                           for (int i = 0; i < mClonedProduct.getUnitObjects().size(); i++) {
                               if (mClonedProduct.getUnitObjects().get(i).getTimeStamp() == unitData.getTimeStamp()) {
                                   mClonedProduct.getUnitObjects().remove(i);
                                   mClonedProduct.getUnitObjects().add(i, unitData);
                                   break;
                               }
                           }
                       } else {
                           for (int i = 0; i < mClonedProduct.getUnitObjects().size(); i++) {
                               if (mClonedProduct.getUnitObjects().get(i).getProductUnitID().equalsIgnoreCase(unitData.getProductUnitID())) {
                                   mClonedProduct.getUnitObjects().remove(i);
                                   mClonedProduct.getUnitObjects().add(i, unitData);
                                   break;
                               }
                           }
                       }
                   }
                    // mClonedProduct.getUnitObjects().add(unitData);
                    updateList();
                }
            }
        }
    }

    private void updateImage(Uri imageUri) {
        ImageURLResponse imageUrlResponse = images.get(selectedImagePosition);
        images.set(selectedImagePosition, imageUrlResponse);
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