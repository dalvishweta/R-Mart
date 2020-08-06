package com.rmart.inventory.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.inventory.OnInventoryClickedListener;

public class InventoryActivity extends BaseNavigationDrawerActivity implements OnInventoryClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}