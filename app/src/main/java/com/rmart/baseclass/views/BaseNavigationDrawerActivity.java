package com.rmart.baseclass.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.rmart.R;
import com.rmart.inventory.views.InventoryActivity;
import com.rmart.orders.views.OrdersActivity;

import java.util.Objects;


public abstract class BaseNavigationDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.nav_view_content);

        dl = findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        nv = findViewById(R.id.nav_view);
        findViewById(R.id.update_profile).setOnClickListener(this);
        findViewById(R.id.orders).setOnClickListener(this);
        findViewById(R.id.inventory).setOnClickListener(this);
        findViewById(R.id.change_password).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
    }

    public void getToActivity(int id, boolean isSameActivity) {
        if(!isSameActivity) {
            Intent intent;
            switch (id) {
                case R.id.orders:
                    intent = new Intent(BaseNavigationDrawerActivity.this, OrdersActivity.class);
                    startActivity(intent);
                    break;
                case R.id.inventory:
                    intent = new Intent(BaseNavigationDrawerActivity.this, InventoryActivity.class);
                    startActivity(intent);
                    break;
                case R.id.change_password:
                    Toast.makeText(getBaseContext(), "change_password", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.logout:
                    Toast.makeText(getBaseContext(), "logout", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.update_profile:
                    break;
                default:
                    break;
            }
        }
        dl.closeDrawer(nv);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void showBadge(boolean b) {

    }

    @Override
    public void updateBadgeCount() {

    }
}
