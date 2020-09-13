package com.rmart.utilits.custom_views;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Satya Seshu on 10/09/20.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int spaceHeight;
    public SpacesItemDecoration(int spaceHeight) {
        this.spaceHeight = spaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = spaceHeight;
        outRect.right = spaceHeight;
    }
}
