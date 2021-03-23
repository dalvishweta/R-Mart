package com.rmart.customerservice.mobile.activities;

import android.os.Bundle;

import com.rmart.R;
import com.rmart.customerservice.mobile.fragments.FragmentMobileRecharge;
import com.rmart.electricity.selectoperator.fragments.SelectOperatorFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MobileRechargeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity2);

        final FragmentManager manager = getSupportFragmentManager();
        loadFragment(new FragmentMobileRecharge());



    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}