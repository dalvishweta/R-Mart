package com.rmart.customerservice;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import com.rmart.R;

public class ActivityMobileService extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rechargeTypeGroup;
    private AppCompatTextView noServiceTxtView;
    private LinearLayout amountLayout;
    private AppCompatButton nxtBtn;
    private AppCompatSpinner stateSelector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_home);

        stateSelector = findViewById(R.id.state_select);
        rechargeTypeGroup = findViewById(R.id.recharge_type_group);
        noServiceTxtView = findViewById(R.id.no_service_msg);
        amountLayout = findViewById(R.id.amount);
        rechargeTypeGroup.setOnCheckedChangeListener(this::onCheckedChanged);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {


    }
}
