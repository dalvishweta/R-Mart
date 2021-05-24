package com.rmart.wallet.view;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

import com.rmart.R;
import com.rmart.baseclass.views.BaseActivity;
import com.rmart.wallet.fragment.BillingHistoryFragment;

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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wallet_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ivNotification:
            {

            }
            break;
            case R.id.ivShoppingBasket:
            {

            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }*/

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
                replaceFragment(BillingHistoryFragment.getInstance(),BillingHistoryFragment.class.getName(),true);
            }
        }
    }
}