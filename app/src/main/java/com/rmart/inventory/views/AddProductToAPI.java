package com.rmart.inventory.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.models.ContentModel;
import com.rmart.inventory.adapters.ImageUploadAdapter;
import com.rmart.inventory.models.Product;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class AddProductToAPI extends BaseInventoryFragment implements View.OnClickListener {

    private List<Object> imagesList;
    private int selectedPosition = -1;
    private ImageUploadAdapter imageUploadAdapter;

    public AddProductToAPI() {
        // Required empty public constructor
    }

    public static AddProductToAPI newInstance() {
        return new AddProductToAPI();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.add_new_product));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LoggerInfo.printLog("Fragment", "AddProductToAPI");
        return inflater.inflate(R.layout.fragment_add_product_to_api, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.save).setOnClickListener(this);
        imagesList = new ArrayList<>();
        imagesList.add(ImageUploadAdapter.DEFAULT);
        imagesList.add(ImageUploadAdapter.DEFAULT);
        imagesList.add(ImageUploadAdapter.DEFAULT);
        imagesList.add(ImageUploadAdapter.DEFAULT);

        imageUploadAdapter = new ImageUploadAdapter(requireActivity(), imagesList, callBackListener);
        RecyclerView recyclerView = view.findViewById(R.id.product_image);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(imageUploadAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save) {
            showDialog(String.format(getString(R.string.hello), MyProfile.getInstance(getActivity()).getFirstName()), getString(R.string.request_new_product_msg),
                    (dialogInterface, i) -> Objects.requireNonNull(requireActivity()).onBackPressed());
        }
    }

    private CallBackInterface callBackListener = pObject -> {
        if (pObject instanceof ContentModel) {
            ContentModel contentModel = (ContentModel) pObject;
            String status = contentModel.getStatus();
            Object value = contentModel.getValue();
            if (status.equalsIgnoreCase(Constants.TAG_UPLOAD_NEW_PRODUCT_IMAGE)) {
                selectedPosition = (int) value;
                photoUploadSelected();
            } else if (status.equalsIgnoreCase(Constants.TAG_EDIT_PRODUCT_IMAGE)) {
                selectedPosition = (int) value;
                photoUploadSelected();
            }
        }
    };

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
                        InputStream imageStream = requireActivity().getContentResolver().openInputStream(profileImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                        updateImage(bitmap);
                    }
                } catch (Exception ex) {
                    showDialog("", ex.getMessage());
                }
            }
        }
    }

    private void updateImage(Bitmap bitmap) {
        imagesList.set(selectedPosition, bitmap);
        imageUploadAdapter.notifyItemChanged(selectedPosition);
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