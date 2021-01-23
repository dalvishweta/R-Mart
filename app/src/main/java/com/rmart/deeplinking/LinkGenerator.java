package com.rmart.deeplinking;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.rmart.utilits.Utils;

import androidx.annotation.NonNull;

public class LinkGenerator {

    public static void shareLink(Activity context, String message, Bitmap finalBitmap,String deepLink){

        Task<ShortDynamicLink> dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(deepLink))
                .setDomainUriPrefix("https://rokad.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder("com.rokad.mart.customerprod").build())
                // Open links with com.example.ios on iOS
                .setIosParameters(new DynamicLink.IosParameters.Builder(deepLink).build())
                .buildShortDynamicLink().addOnCompleteListener(context, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri dynamicLinkUri = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();


                            Utils.shareImage(finalBitmap, "shop.png",context ,message+dynamicLinkUri);
                        } else {
                            // Error
                            // ...
                        }
                    }
                });
    }
}
