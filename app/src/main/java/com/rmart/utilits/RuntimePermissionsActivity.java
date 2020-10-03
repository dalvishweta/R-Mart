package com.rmart.utilits;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseIntArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.rmart.R;

/**
 * Created by Satya Seshu on 03/10/20.
 */
public abstract class RuntimePermissionsActivity extends AppCompatActivity {

    private SparseIntArray errorString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        errorString = new SparseIntArray();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;

        for (Integer permission : grantResults) {
            permissionCheck += permission;
        }

        if (grantResults.length > 0 && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            onPermissionsGranted(requestCode);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(getString(R.string.message));
            alertDialog.setMessage(getString(R.string.runtime_permissions_txt));
            alertDialog.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                    CommonUtils.navigationToAppSettingsPage(RuntimePermissionsActivity.this);
                }
            });
            alertDialog.show();
        }
    }

    public void requestAppPermissions(String[] requestedPermissions, Integer stringId, Integer requestCode) {
        errorString.append(requestCode, stringId);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean shouldShowRequestPermissionRationale = false;

        for (String permission : requestedPermissions) {
            permissionCheck += ContextCompat.checkSelfPermission(this, permission);
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
        } else {
            onPermissionsGranted(requestCode);
        }
    }

    public abstract void onPermissionsGranted(Integer requestCode);
}
