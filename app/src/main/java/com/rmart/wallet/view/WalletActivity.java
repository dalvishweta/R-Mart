package com.rmart.wallet.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.rmart.R;
import com.rmart.baseclass.views.BaseActivity;

public class WalletActivity extends BaseActivity implements View.OnClickListener {
Toolbar toolbar;
private CardView billing_history_cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        toolbar = findViewById(R.id.toolbar);
        billing_history_cardView = findViewById(R.id.billing_cardView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        billing_history_cardView.setOnClickListener(this);

    }

    @Override
    public void onPermissionsGranted(Integer requestCode) {

    }


    @Override
    public void showBadge(boolean b) {

    }

    @Override
    public void updateBadgeCount() {

    }

    @Override
    public void hideHamburgerIcon() {

    }

    @Override
    public void showHamburgerIcon() {

    }

    @Override
    public void showCartIcon() {

    }

    @Override
    public void hideCartIcon() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.billing_cardView:{
                //replaceFragment(BillingHistoryFragment.getInstance(),BillingHistoryFragment.class.getName(),true);
                Toast.makeText(getBaseContext(), "This feature available soon", Toast.LENGTH_SHORT).show();

            }
        }
    }
}