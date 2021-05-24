package com.rmart.baseclass.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.material.navigation.NavigationView;
import com.rmart.R;
import com.rmart.authentication.views.AuthenticationActivity;
import com.rmart.baseclass.Constants;
import com.rmart.customer.shops.home.fragments.ShopHomePage;
import com.rmart.customer.views.CustomerHomeActivity;
import com.rmart.customer.views.CustomerWishListActivity;
import com.rmart.customer.views.ShoppingCartFragment;
import com.rmart.customer_order.views.CustomerOrdersActivity;
import com.rmart.deeplinking.LinkGenerator;
import com.rmart.glied.GlideApp;
import com.rmart.inventory.views.AddProductToInventory;
import com.rmart.inventory.views.InventoryActivity;
import com.rmart.orders.views.OrdersActivity;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.views.MyProfileActivity;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.Permisions;
import com.rmart.utilits.RokadMartCache;
import com.rmart.utilits.UpdateCartCountDetails;
import com.rmart.utilits.Utils;
import com.rmart.wallet.view.WalletActivity;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public abstract class BaseNavigationDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout drawerLayout;
    protected ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    protected Toolbar toolbar;
    private CircleImageView ivProfileImageField;
    private AppCompatTextView nameField;
    private AppCompatTextView mobileField;
    private AppCompatTextView emailIdField;
    private TextView tvCartCountField;
    private MenuItem menuItem;
    MenuItem callItemMenu;
    MenuItem walletItemMenu;
    private int cartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.nav_view_content);

        drawerLayout = findViewById(R.id.drawer_layout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportFragmentManager().addOnBackStackChangedListener(this::checkBackStack);

        loadUIComponents();


    }

    private void loadUIComponents() {
        UpdateCartCountDetails.updateCartCountDetails.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(
                        Schedulers.io()
                ).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer count) {
                LoggerInfo.printLog("cart count info", count);
                cartCount = count;
                updateCart();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        navigationView = findViewById(R.id.navigation_view);
        findViewById(R.id.update_profile).setOnClickListener(this);
        findViewById(R.id.retailer_orders).setOnClickListener(this);
        findViewById(R.id.retailer_inventory).setOnClickListener(this);
        findViewById(R.id.customer_shopping).setOnClickListener(this);
        findViewById(R.id.customer_orders).setOnClickListener(this);
        findViewById(R.id.change_password).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.my_favourites_list).setOnClickListener(this);
        findViewById(R.id.my_wish_list).setOnClickListener(this);
        findViewById(R.id.my_wallet).setOnClickListener(this);
        findViewById(R.id.share_app).setOnClickListener(this);
        findViewById(R.id.wholesaler).setOnClickListener(this);


        ivProfileImageField = findViewById(R.id.iv_user_profile_image);
        MyProfile myProfile = MyProfile.getInstance(getApplicationContext());
        if (myProfile != null) {
            String roleId = myProfile.getRoleID();
            if(!TextUtils.isEmpty(roleId)) {
                switch (roleId) {
                    case Utils.CUSTOMER_ID:
                        findViewById(R.id.retailer_orders).setVisibility(View.GONE);
                        findViewById(R.id.retailer_inventory).setVisibility(View.GONE);
                        findViewById(R.id.retailer_orders).setVisibility(View.GONE);
                        findViewById(R.id.share_app).setVisibility(View.GONE);
                        findViewById(R.id.wholesaler).setVisibility(View.GONE);
                        break;

                    case Utils.RETAILER_ID:
                        findViewById(R.id.customer_shopping).setVisibility(View.GONE);
                        findViewById(R.id.customer_orders).setVisibility(View.VISIBLE);
                        findViewById(R.id.my_wish_list).setVisibility(View.VISIBLE);
                        findViewById(R.id.my_favourites_list).setVisibility(View.GONE);
                        findViewById(R.id.my_wallet).setVisibility(View.GONE);
                        findViewById(R.id.wholesaler).setVisibility(View.VISIBLE);
                        break;
                    case Utils.DELIVERY_ID:
                        findViewById(R.id.retailer_inventory).setVisibility(View.GONE);
                        findViewById(R.id.retailer_orders).setVisibility(View.GONE);
                        findViewById(R.id.customer_shopping).setVisibility(View.GONE);
                        findViewById(R.id.customer_orders).setVisibility(View.GONE);
                        findViewById(R.id.my_favourites_list).setVisibility(View.GONE);
                        findViewById(R.id.my_wallet).setVisibility(View.GONE);
                        findViewById(R.id.share_app).setVisibility(View.GONE);
                        findViewById(R.id.wholesaler).setVisibility(View.GONE);

                        break;
                    default:
                        break;
                }
            }

            String imageUrl = myProfile.getProfileImage();

            GlideApp.with(getApplicationContext()).load(imageUrl) .listener(new RequestListener<Drawable>() {

                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {


                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                    return false;
                }
            }).dontAnimate().
                    diskCacheStrategy(DiskCacheStrategy.ALL).
                    signature(new ObjectKey(imageUrl)).
                    error(R.mipmap.applogo).thumbnail(0.5f).into(ivProfileImageField);


            /*myProfile.getCartCount().observe(this, count -> {
                this.cartCount = count;
                if (tvCartCountField != null) {
                    tvCartCountField.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
                    tvCartCountField.setText(String.valueOf(count));
                }
            });*/
        }
        nameField = findViewById(R.id.customer_name);
        mobileField = findViewById(R.id.mobile);
        emailIdField = findViewById(R.id.email_id_field);
        TextView tvAppVersionField = findViewById(R.id.tv_version_field);
        try {
            String versionNo = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            String appVersion = String.format("%s : %s", getString(R.string.version), versionNo);
            tvAppVersionField.setText(appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateCart() {
        if (tvCartCountField != null) {
            tvCartCountField.setVisibility(cartCount == 0 ? View.GONE : View.VISIBLE);
            tvCartCountField.setText(String.valueOf(cartCount));
        }
    }

    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.badge_menu_drawer, menu);
        menuItem = menu.findItem(R.id.badge_menu);
        menu.findItem(R.id.app_logo).getActionView().setOnClickListener(view -> {
            MyProfile myProfile = MyProfile.getInstance(getApplicationContext());
            if (myProfile.getRoleID().equalsIgnoreCase(Utils.CUSTOMER_ID)) {
                Intent in = new Intent(this, CustomerHomeActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
                finish();
            } else if (myProfile.getRoleID().equalsIgnoreCase(Utils.RETAILER_ID)) {
                Intent in = new Intent(this, OrdersActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
                finish();
            } else if (myProfile.getRoleID().equalsIgnoreCase(Utils.DELIVERY_ID)) {Intent in = new Intent(this, OrdersActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
                finish();
            }


            // Toast.makeText(getApplicationContext(),"Logo selected", Toast.LENGTH_SHORT).show();
        });
        walletItemMenu = menu.findItem(R.id.my_wallet);
        walletItemMenu.getActionView().setOnClickListener(view ->{
                    Intent intent = new Intent(this,WalletActivity.class);
                    startActivity(intent);
                }
                );
        callItemMenu = menu.findItem(R.id.call);
        callItemMenu.getActionView().setOnClickListener(view ->  Utils.openDialPad(BaseNavigationDrawerActivity.this,"022-42931001"));

        MyProfile myProfile = MyProfile.getInstance(this);
        if (myProfile != null) {
            //if (myProfile.getRoleID().equalsIgnoreCase(Utils.CUSTOMER_ID)) {
                View actionView = menuItem.getActionView();
                tvCartCountField = actionView.findViewById(R.id.tv_cart_count_field);
                if (MyProfile.getInstance(this) != null) {
                    if (cartCount > 0) {
                        Fragment currentFragment = getActiveFragment();
                        if (currentFragment instanceof ShoppingCartFragment) {
                            hideCartIcon();
                        } else {
                            showCartIcon();
                        }
                        tvCartCountField.setVisibility(View.VISIBLE);
                        UpdateCartCountDetails.updateCartCountDetails.onNext(cartCount);
                    } else {
                        //tvCartCountField.setVisibility(View.VISIBLE);
                        //menuItem.setVisible(false);
                    }
                }
                actionView.setOnClickListener(v -> {
                    if(cartCount > 0) {
                        cartSelected();
                    } else {
                        Toast.makeText(this,"Cart is empty ",Toast.LENGTH_LONG).show();
                    }
                });

            if (myProfile.getRoleID().equalsIgnoreCase(Utils.CUSTOMER_ID)) {
                callItemMenu.setVisible(true);
            } else {
                menuItem.setVisible(false);
                callItemMenu.setVisible(false);
            }
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public void getToActivity(int id, boolean isSameActivity) {
        if (!isSameActivity) {
            Intent intent;
            switch (id) {
                case R.id.retailer_orders:
                    hideCartIcon();
                    intent = new Intent(BaseNavigationDrawerActivity.this, OrdersActivity.class);
                    startActivity(intent);
                    break;
                case R.id.retailer_inventory:
                    hideCartIcon();
                    intent = new Intent(BaseNavigationDrawerActivity.this, InventoryActivity.class);
                    startActivity(intent);
                    break;
                case R.id.change_password:
                    hideCartIcon();
                    intent = new Intent(BaseNavigationDrawerActivity.this, AuthenticationActivity.class);
                    intent.putExtra(getString(R.string.change_password), true);
                    startActivity(intent);
                    break;
                case R.id.logout:
                    showLogoutConfirmation();
                    // Toast.makeText(getBaseContext(), "logout", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.update_profile:
                    hideCartIcon();
                    intent = new Intent(BaseNavigationDrawerActivity.this, MyProfileActivity.class);
                    startActivity(intent);
                    break;
                case R.id.customer_orders:
                    hideCartIcon();
                    intent = new Intent(this, CustomerOrdersActivity.class);
                    startActivity(intent);
                    break;
                case R.id.customer_shopping:
                    intent = new Intent(this, CustomerHomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.my_favourites_list:
                    showCartIcon();
                    intent = new Intent(this, CustomerHomeActivity.class);
                    intent.putExtra("IsFavourites", true);
                    startActivity(intent);
                    break;
                case R.id.my_wish_list:
                    showCartIcon();
                    /*intent = new Intent(this, CustomerWishListActivity.class);
                    startActivity(intent);*/
                    gotoWisListScreen();
                    break;
                case R.id.wholesaler:
                    showCartIcon();
                    intent = new Intent(BaseNavigationDrawerActivity.this, CustomerHomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.my_wallet:
                    showCartIcon();
                    intent = new Intent(this, WalletActivity.class);
                    intent.putExtra(getString(R.string.my_wallet), true);
                    startActivity(intent);
                    break;
                case R.id.share_app:
                    MyProfile myProfile = MyProfile.getInstance(this);

                    if (Permisions.checkWriteExternlStoragePermission(this)) {
                        GlideApp.with(this)
                                .asBitmap()
                                .load(myProfile.getAddressResponses().get(0).getShopImage())
                                .into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                        String message= "रोकड मार्ट आता आपल्या शहरामध्ये!!!\n" +
                                                "आता आमचे "+myProfile.getAddressResponses().get(0).getShopName()+" रोकड मार्ट सोबत ऑनलाईन झाले आहे. \n" +
                                                "नवीन ऑफर्स आणि शॉपिंग साठी खालील लिंक वर क्लिक करा आणि अँप डाउनलोड करा.\n"+ "RokadMart brings your nearest local store online."+myProfile.getAddressResponses().get(0).getShopName() +"  is now online.Buy all the products on RokadMart with home delivery ";
                                        String deeplink = "https://www.rokadmart.com/public/Home/index?shop_id="+myProfile.getAddressResponses().get(0).getId()+"&client_id=2&created_by="+myProfile.getAddressResponses().get(0).getCreatedBy();
                                        LinkGenerator.shareLink(BaseNavigationDrawerActivity.this,message,resource,deeplink);

                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }
                                });
                    } else {
                        Permisions.requestWriteExternlStoragePermission(this);
                    }




                    break;
                default:
                    break;
            }
        }
        drawerLayout.closeDrawer(navigationView);
    }

    private void showLogoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setTitle(getString(R.string.message));
        builder.setMessage(getString(R.string.logout_confirmation_message));
        builder.setCancelable(false);
        builder.setNegativeButton(getString(R.string.no), (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
            logoutConfirmed();
            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        if (!isFinishing()) {
            alertDialog.show();
        }
    }

    private void logoutConfirmed() {
        hideCartIcon();
        MyProfile myProfile = MyProfile.getInstance(this);
        if (myProfile.getRoleID().equalsIgnoreCase(Utils.CUSTOMER_ID)) {
            RokadMartCache.putData(Constants.CACHE_CUSTOMER_DETAILS, this, null);
        } else if (myProfile.getRoleID().equalsIgnoreCase(Utils.RETAILER_ID)) {
            RokadMartCache.putData(Constants.CACHE_RETAILER_DETAILS, this, null);
        } else if (myProfile.getRoleID().equalsIgnoreCase(Utils.DELIVERY_ID)) {
            RokadMartCache.putData(Constants.CACHE_DELIVERY_DETAILS, this, null);
        }
        Intent in = new Intent(this, AuthenticationActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);

        finish();
    }

    private void gotoWisListScreen() {
        //replaceFragment(CustomerWishListFragment.getInstance(), CustomerWishListFragment.class.getName(), true);
        hideCartIcon();
        Intent intent = new Intent(BaseNavigationDrawerActivity.this, CustomerWishListActivity.class);
        startActivity(intent);
    }

    private void checkBackStack() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            toolbar.setNavigationOnClickListener(view -> {
                navigateBackStack();
            });
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
            actionBarDrawerToggle.syncState();
            //setTitle(getResources().getString(R.string.app_name));
        }

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.base_container);
        if(f instanceof ShopHomePage){
            // do something with f
            try {
                callItemMenu.setVisible(false);
            }catch (Exception e){

            }
        }
    }

    private void navigateBackStack() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private void cartSelected() {
        BaseFragment baseFragment = getActiveFragment();
        if (!(baseFragment instanceof ShoppingCartFragment)) {
            //hideCartIcon();
            Intent intent = new Intent(this, CustomerHomeActivity.class);
            intent.putExtra("ShoppingCart", true);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        //Checks if the navigation drawer is open -- If so, close it
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                this.finish();
            } else {
                Fragment fragment = getActiveFragment();
                if (fragment instanceof AddProductToInventory) {
                    ((AddProductToInventory) fragment).handleBackButton();
                } else {
                    getSupportFragmentManager().popBackStackImmediate();
                }
            }
        }
    }

    public BaseFragment getActiveFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
    }

    @Override
    public void hideHamburgerIcon() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        actionBarDrawerToggle.syncState();
        //setTitle(getResources().getString(R.string.app_name));
    }

    @Override
    public void showHamburgerIcon() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        actionBarDrawerToggle.syncState();
        //setTitle(getResources().getString(R.string.app_name));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void showCartIcon() {
        if (menuItem != null) {
            menuItem.setVisible(true);
        }
    }

    @Override
    public void hideCartIcon() {
        if (menuItem != null) {
            menuItem.setVisible(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @SuppressLint("CheckResult")
    private void updateUI() {
        MyProfile myProfile = MyProfile.getInstance(this);
        if (myProfile != null) {
            nameField.setText(myProfile.getFirstName());
            mobileField.setText(myProfile.getMobileNumber());
            emailIdField.setText(myProfile.getEmail());
            try {
                String userProfileBitmap = myProfile.getProfileImage();
                if (userProfileBitmap != null) {
                GlideApp.with(getBaseContext()).load(userProfileBitmap);
                }
            }catch (Exception e){

            }
        }
    }
}
