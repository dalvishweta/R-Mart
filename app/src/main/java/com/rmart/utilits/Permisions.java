package com.rmart.utilits;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permisions {

    public static boolean checkWriteExternlStoragePermission(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    public static void requestWriteExternlStoragePermission(Context c) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) c, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(c, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions((Activity)c, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

}
