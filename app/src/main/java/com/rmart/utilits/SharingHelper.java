package com.rmart.utilits;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

import androidx.core.content.FileProvider;

public class SharingHelper {

    public static void shareImage(File file, Activity activity,String message) {
        Intent intent = null;
        try {
            Uri uri = Uri.fromFile(file);
            uri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", file);

            intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");

            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }catch (Exception e){
            Toast.makeText(activity, "Cant Share Image", Toast.LENGTH_SHORT).show();
        }
        try {
            activity.startActivity(Intent.createChooser(intent, "Share Shop"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }
}
