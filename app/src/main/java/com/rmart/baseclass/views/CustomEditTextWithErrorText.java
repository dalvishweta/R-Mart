package com.rmart.baseclass.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.rmart.R;


public class CustomEditTextWithErrorText extends LinearLayout {
    private AppCompatEditText appCompatEditText;
    private AppCompatTextView errorTextView;

    public CustomEditTextWithErrorText(Context context) {
        super(context);
        initView(null);
    }

    public CustomEditTextWithErrorText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public CustomEditTextWithErrorText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        View view = inflate(getContext(), R.layout.custom_edittext_with_error_text, this);
        appCompatEditText = view.findViewById(R.id.edit_text_view);
        errorTextView = view.findViewById(R.id.error_text);
        appCompatEditText.setOnFocusChangeListener((arg0, hasfocus) -> {
            if (hasfocus && errorTextView.getVisibility() == View.VISIBLE) {
                errorTextView.setVisibility(GONE);
            }
        });

        if(null != attrs) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditTextWithErrorText);

            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.CustomEditTextWithErrorText_error_trxt:
                        errorTextView.setText(a.getString(attr));
                        break;
                    //note that you are accessing standard attributes using your attrs identifier
                    case R.styleable.CustomEditTextWithErrorText_android_inputType:
                        appCompatEditText.setInputType(a.getInt(attr, EditorInfo.TYPE_TEXT_VARIATION_NORMAL));
                        break;
                    case R.styleable.CustomEditTextWithErrorText_android_drawableStart:
                        Drawable drawable = a.getDrawable(attr);
                        // Drawable drawable = getContext().getResources().getDrawable(R.drawable.user);
                        appCompatEditText.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                        appCompatEditText.setCompoundDrawablePadding(62);
                        break;
                    case R.styleable.CustomEditTextWithErrorText_android_hint:
                        String text  = a.getString(attr);
                        appCompatEditText.setHint(text);
                        break;
                    case R.styleable.CustomEditTextWithErrorText_android_maxLength:
                        int value =a.getInteger(attr, 10);
                        appCompatEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(value)});
                        //.setMa(a.getInt(attr, R.string.app_name));
                        break;
                    default:
                        Log.d("TAG", "Unknown attribute for " + getClass().toString() + ": " + attr);
                        break;
                }
            }
            a.recycle();
        }
    }

    public AppCompatTextView getErrorTextView(){
        return errorTextView;
    }
    public AppCompatEditText getAppCompatEditText(){
        return appCompatEditText;
    }
}
