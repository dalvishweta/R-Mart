package com.rmart.utilits.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Satya Seshu on 21/09/20.
 */
public class CustomNetworkImageView extends NetworkImageView {

    private Bitmap mLocalBitmap = null;

    private Uri uri = null;

    private boolean mShowLocal;

    private Drawable mDrawable = null;

    public void setLocalImageBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mShowLocal = true;
        }
        this.mLocalBitmap = bitmap;
        requestLayout();
    }

    public void setLocalImageUri(Uri uri) {
        if (uri != null) {
            mShowLocal = true;
        }
        this.uri = uri;
        requestLayout();
    }

    public void setLocalDrawable(Drawable drawable) {
        if (uri != null) {
            mShowLocal = false;
        }
        this.mDrawable = drawable;
        requestLayout();
    }

    @Override
    public void setImageUrl(String url, ImageLoader imageLoader) {
        mShowLocal = false;
        super.setImageUrl(url, imageLoader);
    }

    public CustomNetworkImageView(Context context) {
        this(context, null);
    }

    public CustomNetworkImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);
        if (mShowLocal) {
            if (uri != null) {
                setImageURI(uri);
            } else if (mLocalBitmap != null) {
                setImageBitmap(mLocalBitmap);
            } else if (mDrawable != null) {
                setBackground(mDrawable);
            }
        }
    }
}
