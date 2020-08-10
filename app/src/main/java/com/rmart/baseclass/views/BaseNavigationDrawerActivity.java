package com.rmart.baseclass.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.rmart.R;

import java.util.Objects;


public abstract class BaseNavigationDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static final String SHOW_ALL_RECORDS = "show_all_records";
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    private TextView textCartItemCount;
    private View badgeBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.nav_view_content);
        setNavigationView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.badge_menu_drawer, menu);

        final MenuItem menuItem = menu.findItem(R.id.live_ticket);

        View actionView = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        badgeBase = actionView.findViewById(R.id.badge_base);
        showBadge(true);

        /*actionView.setOnClickListener(v ->

            startActivity(new Intent(BaseNavigationDrawerActivity.this, TicketBookingHistoryActivity.class))

            );*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
                return true;
        }
        return super.onOptionsItemSelected(item);
        /**/
    }

    private void setNavigationView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        ActionBar ab = getSupportActionBar();
        /*if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }*/
        navigationView = findViewById(R.id.nav_view);
        //signOut = navigationView.findViewById(R.id.sign_out_btn);
        navigationView.setNavigationItemSelectedListener(this);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*findViewById(R.id.book_ticket_layout_field).setOnClickListener(this);
        findViewById(R.id.booking_history_layout_field).setOnClickListener(this);
        findViewById(R.id.help_layout_field).setOnClickListener(this);
        findViewById(R.id.logout_layout_field).setOnClickListener(this);
        findViewById(R.id.my_profile_layout_field).setOnClickListener(this);
        findViewById(R.id.change_password_layout_field).setOnClickListener(this);*/

    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(navigationView);
        return false;
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.book_ticket_layout_field:
                startActivity(new Intent(this, TicketBookingActivity.class));
                finish();
                // Toast.makeText(this, "nav_ticket_booking", Toast.LENGTH_LONG).show();
                break;
            case R.id.booking_history_layout_field:
                mDrawerLayout.closeDrawer(navigationView);
                Intent intent = new Intent(BaseNavigationDrawerActivity.this, TicketBookingHistoryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra(SHOW_ALL_RECORDS,true);
                startActivity(intent);
                break;
            case R.id.help_layout_field:
                gotoHelpScreen();
                break;
            case R.id.logout_layout_field:
                mDrawerLayout.closeDrawer(navigationView);
                Intent i = new Intent(BaseNavigationDrawerActivity.this, AuthenticationActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                break;
            case R.id.my_profile_layout_field:
                mDrawerLayout.closeDrawer(navigationView);
                startActivity(new Intent(BaseNavigationDrawerActivity.this, ProfileActivity.class));
                break;
            case R.id.change_password_layout_field:
                mDrawerLayout.closeDrawer(navigationView);
                intent = new Intent(this, AuthenticationActivity.class);
                intent.putExtra(getString(R.string.change_password), false);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "default", Toast.LENGTH_LONG).show();
                break;
        }*/
        mDrawerLayout.closeDrawer(navigationView);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    protected void replaceFragment(BaseFragment baseFragment, String fragment_id, boolean isAddToBackStack) {
        super.replaceFragment(baseFragment, fragment_id, isAddToBackStack);
    }

    protected void addFragment(BaseFragment baseFragment, String fragment_id, boolean isAddToBackStack) {
       super.addFragment(baseFragment,fragment_id,isAddToBackStack);
    }

    // @Override
    public void showHamburgerIcon(boolean b) {
        /*if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(b);   //hide hamburger button
        }*/
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            getSupportActionBar().setTitle("Select Image");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }
    @Override
    public void showBadge(boolean b) {
        /*if(null != badgeBase) {
            if (b) {
                badgeBase.setVisibility(View.VISIBLE);
                Integer value = MyProfile.getInstance().getCurrentBookingTicketCount().getValue();
                if(value!=null && value > 0)
                    textCartItemCount.setText(String.valueOf(Math.min(value, 99)));
                else
                    badgeBase.setVisibility(View.GONE);

            } else {
                badgeBase.setVisibility(View.GONE);
            }
        }*/
    }

    @Override
    public void updateBadgeCount() {
        /*if (textCartItemCount != null) {
            MyProfile myProfile = MyProfile.getInstance();
            if (myProfile != null) {
                Integer value = myProfile.getCurrentBookingTicketCount().getValue();
                if (value != null) {
                    textCartItemCount.setText(String.valueOf(Math.min(value, 99)));
                }
            }
        }*/
    }

    private void gotoHelpScreen() {
        // startActivity(new Intent(this, HelpActivity.class));
    }
}
