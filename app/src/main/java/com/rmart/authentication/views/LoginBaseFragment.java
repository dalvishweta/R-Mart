package com.rmart.authentication.views;

import android.content.Context;

import androidx.annotation.NonNull;

import com.rmart.authentication.OnAuthenticationClickedListener;
import com.rmart.baseclass.views.BaseFragment;

public class LoginBaseFragment extends BaseFragment {

    OnAuthenticationClickedListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener= (OnAuthenticationClickedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
