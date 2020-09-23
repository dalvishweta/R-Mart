package com.rmart.baseclass.views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.utilits.Utils;


public class BaseFragment extends Fragment {

    protected Dialog progressDialog;
    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = CustomLoadingDialog.getInstance(getActivity());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*if(!internetConnectionAvailable()) {
            try {
                AlertDialog.Builder builder =new AlertDialog.Builder(requireActivity());
                builder.setTitle(R.string.error_internet);
                builder.setMessage(R.string.error_internet_text);
                builder.setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Objects.requireNonNull(getActivity()).onBackPressed();
                        // System.exit(0);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } catch (Exception e) {
                Log.d("Exception", "Exception: " + e.getMessage());
            }
        }*/
        if(!Utils.isNetworkConnected(requireActivity())) {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text), new CallBackInterface() {

                @Override
                public void callBackReceived(Object pObject) {

                }
            });
        }
    }
    protected void showDialog(String title, String msg) {
        try {
            AlertDialog.Builder builder = new
                    AlertDialog.Builder(
                    requireActivity(),
                    R.style.AlertDialog
            );
            if (TextUtils.isEmpty(title)) {
                title = requireActivity().getString(R.string.message);
            }
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setNegativeButton(requireActivity().getString(R.string.close), null);
            AlertDialog alertDialog = builder.create();
            alertDialog.setOnShowListener(arg0 -> alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireActivity(), R.color.button_bg)));
            if (!requireActivity().isFinishing()) {
                alertDialog.show();
            }
        } catch (Exception e) {
            // LoggerInfo.errorLog("show dialog exception", e.getMessage());
        }
    }

    protected void showDialog(String msg) {
        try {
            AlertDialog.Builder builder = new
                    AlertDialog.Builder(
                    requireActivity(),
                    R.style.AlertDialog
            );
            builder.setTitle(requireActivity().getString(R.string.message));
            builder.setMessage(msg);
            builder.setNegativeButton(requireActivity().getString(R.string.close), null);
            AlertDialog alertDialog = builder.create();
            alertDialog.setOnShowListener(arg0 -> alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireActivity(), R.color.button_bg)));
            if (!requireActivity().isFinishing()) {
                alertDialog.show();
            }
        } catch (Exception e) {
            // LoggerInfo.errorLog("show dialog exception", e.getMessage());
        }
    }


    protected void showDialog(String title, StringBuffer msg) {
        try {
            AlertDialog.Builder builder = new
                    AlertDialog.Builder(
                    requireActivity(),
                    R.style.AlertDialog
            );
            if (TextUtils.isEmpty(title)) {
                title = requireActivity().getString(R.string.message);
            }
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setNegativeButton(requireActivity().getString(R.string.close), null);
            AlertDialog alertDialog = builder.create();
            alertDialog.setOnShowListener(arg0 -> alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireActivity(), R.color.button_bg)));
            if (!requireActivity().isFinishing()) {
                alertDialog.show();
            }
        } catch (Exception e) {
            // LoggerInfo.errorLog("show dialog exception", e.getMessage());
        }
    }

    protected void showDialog(String title, String msg, DialogInterface.OnClickListener  callBackInterface) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AlertDialog);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setNegativeButton("close", callBackInterface);
            AlertDialog alertDialog = builder.create();
            if (!requireActivity().isFinishing()) {
                alertDialog.show();
            }
        } catch (Exception e) {

        }
    }

    protected void showDialog(String title, String msg, CallBackInterface  callBackInterface) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AlertDialog);
            if(TextUtils.isEmpty(title)) builder.setTitle(getString(R.string.message));
            else builder.setTitle(title);
            builder.setMessage(msg);
            builder.setNegativeButton("close", (dialogInterface, i) -> callBackInterface.callBackReceived(Constants.TAG_SUCCESS));
            AlertDialog alertDialog = builder.create();
            if (!requireActivity().isFinishing()) {
                alertDialog.show();
            }
        } catch (Exception e) {

        }
    }

    protected void showDialog(String msg, CallBackInterface  callBackInterface) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AlertDialog);
            builder.setTitle(getString(R.string.message));
            builder.setMessage(msg);
            builder.setNegativeButton("close", (dialogInterface, i) -> callBackInterface.callBackReceived(Constants.TAG_SUCCESS));
            AlertDialog alertDialog = builder.create();
            if (!requireActivity().isFinishing()) {
                alertDialog.show();
            }
        } catch (Exception e) {

        }
    }

    public void showConfirmationDialog(String msg, CallBackInterface callBackInterface) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AlertDialog);
            builder.setTitle(getString(R.string.message));
            builder.setMessage(msg);
            builder.setNegativeButton("close", null);
            builder.setPositiveButton("Ok", (dialogInterface, i) -> callBackInterface.callBackReceived(Constants.TAG_SUCCESS));
            AlertDialog alertDialog = builder.create();
            if (!requireActivity().isFinishing()) {
                alertDialog.show();
            }
        } catch (Exception e) {

        }
    }

}
