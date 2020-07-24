package com.rmart.baseclass.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.rmart.R;

import java.util.Objects;

public class CustomLoadingDialog extends DialogFragment {

    public static Dialog getInstance(Context context) {
        Dialog dialog =  new Dialog(context, R.style.CustomLoadingDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflate = View.inflate(context, R.layout.custom_loading_progress, null);
        dialog.setContentView(inflate);
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        return getInstance(requireActivity());
    }
}
