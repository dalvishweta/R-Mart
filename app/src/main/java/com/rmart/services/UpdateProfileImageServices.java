package com.rmart.services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.rmart.baseclass.Constants;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.BaseResponse;
import com.rmart.utilits.services.ProfileService;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Satya Seshu on 23/09/20.
 */
public class UpdateProfileImageServices extends JobIntentService {

    private String photoImagePath;
    private String imageType;

    public static void enqueueWork(Context context, String imageType, String photoImagePath) {
        Intent intent = new Intent(context, UpdateProfileImageServices.class);
        intent.putExtra("PhotoImagePath", photoImagePath);
        intent.putExtra("ImageType", imageType);
        enqueueWork(context, UpdateProfileImageServices.class, Constants.JOB_INSTANT_MESSENGER, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            photoImagePath = extras.getString("PhotoImagePath");
            imageType = extras.getString("ImageType");
        }
        updateImageDetails();
    }

    private void updateImageDetails() {
        if(Utils.isNetworkConnected(this)) {
            String clientID = "2";
            try {
                File file = new File(photoImagePath);
                Uri profileImageUri = Uri.fromFile(file);
                if (profileImageUri != null) {
                    InputStream imageStream = getContentResolver().openInputStream(profileImageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                    ProfileService profileService = RetrofitClientInstance.getRetrofitInstance().create(ProfileService.class);
                    Call<BaseResponse> call = profileService.uploadPhotoImage(clientID, MyProfile.getInstance().getUserID(),
                            imageType, getEncodedImage(bitmap));
                    call.enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                            if (response.isSuccessful()) {
                                BaseResponse body = response.body();
                                if (body != null) {
                                    LoggerInfo.errorLog("Image Upload response", body.getMsg());
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<BaseResponse> call, @NotNull Throwable t) {
                            LoggerInfo.errorLog("Image Upload onFailure", t.getMessage());
                        }
                    });
                }
            } catch (Exception ex) {
                LoggerInfo.errorLog("Image Upload exception", ex.getMessage());
            }
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