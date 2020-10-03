package com.rmart.customer_order.views;

import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.customer.adapters.CustomSpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Satya Seshu on 03/10/20.
 */
public class SelectOrderStatusView extends DialogFragment {

    private String selectedReason;
    private CallBackInterface callBackListener;

    public static SelectOrderStatusView getInstance() {
        return new SelectOrderStatusView();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Dialog dialog = new Dialog(requireActivity(), R.style.Theme_Custom_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(requireActivity(), R.layout.select_order_status_view, null);
        dialog.setContentView(view);
        dialog.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                closeDialog();
            }
            return false;
        });

        loadUIComponents(view);
        return dialog;
    }

    private void loadUIComponents(View view) {
        Spinner selectSpinnerField = view.findViewById(R.id.select_reason_spinner_field);
        String[] listItems = getResources().getStringArray(R.array.reasons_list);
        List<Object> reasonsList = new ArrayList<>(Arrays.asList(listItems));
        reasonsList.add(0, getString(R.string.select_reason));
        CustomSpinnerAdapter reasonAdapter = new CustomSpinnerAdapter(requireActivity(), reasonsList);
        selectSpinnerField.setAdapter(reasonAdapter);

        selectSpinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedReason = (String) reasonsList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        view.findViewById(R.id.btn_submit_field).setOnClickListener(v -> {
            submitSelected();
        });
    }

    public void setCallBackListener(CallBackInterface callBackListener) {
        this.callBackListener = callBackListener;
    }

    private void submitSelected() {
        if (selectedReason.equalsIgnoreCase(getString(R.string.select_reason))) {
            showDialog(getString(R.string.select_reason));
        } else {
            callBackListener.callBackReceived(selectedReason);
            closeDialog();
        }
    }

    private void showDialog(String msg) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AlertDialog);
            builder.setTitle(getString(R.string.message));
            builder.setMessage(msg);
            builder.setCancelable(false);
            builder.setNegativeButton("close", (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog alertDialog = builder.create();
            if (!requireActivity().isFinishing()) {
                alertDialog.show();
            }
        } catch (Exception e) {

        }
    }

    private void closeDialog() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
