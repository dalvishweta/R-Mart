package com.rmart.customer.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.rmart.R;
import com.rmart.authentication.views.AuthenticationActivity;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.dashboard.fragments.DashBoardFragment;
import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.models.ProductBaseModel;
import com.rmart.customer.models.ShopWiseWishListResponseDetails;
import com.rmart.customer.models.ShoppingCartResponseDetails;
import com.rmart.customer.order.summary.fragments.OrderSummaryFragment;
import com.rmart.customer.shops.home.fragments.ShopHomePage;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.BaseResponse;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.AddressResponse;
import com.rmart.utilits.services.AuthenticationService;

import java.net.SocketTimeoutException;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerHomeActivity extends BaseNavigationDrawerActivity implements OnCustomerHomeInteractionListener {
SharedPreferences sharedPreferences;
String userLoginStatus;
    //private VendorShopsListFragment vendorShopsListFragment;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_authentication);

        sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), 0);
        userLoginStatus = sharedPreferences.getString("userLoginStatus", null);
        if (userLoginStatus.equalsIgnoreCase("yes")) {
        }
        else {
            Intent  intent = new Intent(this, AuthenticationActivity.class);
            startActivity(intent);
            finish();
        }
        Bundle data = getIntent().getExtras();
        checkRegistration();
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Uri deepLink = null;
                        String shopId="";
                        String VenderID="";
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();

                           String url = deepLink.getEncodedFragment();
                          String[] str =  deepLink.getQuery().split("&");
                          for (int i=0;i<str.length;i++){
                           String[] key =  str[i].split("=");
                              String parameter= key[0];
                              String value = key[1];

                              if(parameter.equalsIgnoreCase("shop_id")){
                                  shopId = value;
                              }
                              if(parameter.equalsIgnoreCase("created_by")){ // vendor_id
                                  VenderID=value;
                              }

                            }

                        }
                        loadShopListFragment(data,VenderID,shopId);



                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("error", "getDynamicLink:onFailure", e);
                        loadShopListFragment(data,null,null);

                    }
     });

    }

    private void loadShopListFragment(Bundle data,String VenderID,String shopId) {
        //vendorShopsListFragment = VendorShopsListFragment.getInstance(VenderID,shopId);
         DashBoardFragment  vendorShopsListFragment =  DashBoardFragment.newInstance();
        if (data != null) {
            //addFragment(ShoppingCartFragment.getInstance(), ShoppingCartFragment.class.getName(), true);
            boolean isShoppingCart = data.getBoolean("ShoppingCart");
            boolean isFavouritesSelected = data.getBoolean("IsFavourites", false);
            if (isShoppingCart) {
                replaceFragment(ShoppingCartFragment.getInstance(), ShoppingCartFragment.class.getName(), false);
            } else if (isFavouritesSelected) {
                replaceFragment(CustomerFavouritesFragment.getInstance(), CustomerFavouritesFragment.class.getName(), false);
            }
        } else {
            if(!vendorShopsListFragment.isAdded()) {
                replaceFragment(vendorShopsListFragment, DashBoardFragment.class.getName(), false);
            }
        }
    }

    @Override
    public void onPermissionsGranted(Integer requestCode) {

    }

    @Override
    public void onClick(View view) {
        getToActivity(view.getId(), view.getId() == R.id.customer_shopping);
    }

    @Override
    public void gotoChangeAddress() {
        replaceFragment(ChangeAddressFragment.getInstance(),ChangeAddressFragment.class.getName(),true);
    }

    @Override
    public void gotoVendorProductDetails(ShopDetailsModel customerProductsModel, ProductData productData) {
        showCartIcon();

        //  replaceFragment(VendorProductDetailsFragment.getInstance(customerProductsModel), VendorProductDetailsFragment.class.getName(), true);
        replaceFragment(ShopHomePage.newInstance(customerProductsModel,productData), VendorProductDetailsFragment.class.getName(), true);
    }

    @Override
    public void gotoProductDescDetails(CustomerProductDetailsModel vendorProductDetails, ShopDetailsModel vendorShopDetails) {
        showCartIcon();
        replaceFragment(ProductCartDetailsFragment.getInstance(vendorProductDetails, vendorShopDetails), ProductCartDetailsFragment.class.getName(), true);
    }

    @Override
    public void gotoShoppingCartScreen() {
        hideCartIcon();
        replaceFragment(ShoppingCartFragment.getInstance(), ShoppingCartFragment.class.getName(), false);
    }

    @Override
    public void gotoShoppingCartDetails(ShoppingCartResponseDetails shoppingCartResponseDetails) {
        hideCartIcon();
        replaceFragment(ShoppingCartDetailsFragment.getInstance(shoppingCartResponseDetails), ShoppingCartDetailsFragment.class.getName(), true);
    }

    @Override
    public void gotoVendorSameProductListScreen(ProductBaseModel productCategoryDetails, ShopDetailsModel vendorShopDetails) {
        showCartIcon();
        replaceFragment(VendorSameProductsListScreen.getInstance(productCategoryDetails, vendorShopDetails), VendorSameProductsListScreen.class.getName(), true);
    }

    @Override
    public void gotoConfirmedOrderStatusScreen() {

    }

    @Override
    public void updateShopWishListStatus(ShopDetailsModel vendorShopDetails) {
       // vendorShopsListFragment.updateShopWishListStatus(vendorShopDetails);
    }

    @Override
    public void gotoCompleteOrderDetailsScreen(ShopDetailsModel vendorShopDetails) {
        hideCartIcon();
       // replaceFragment(CustomerOrderDetailsFragment.getInstance(vendorShopDetails), PaymentOptionsFragment.class.getName(), true);
        replaceFragment(OrderSummaryFragment.newInstance(vendorShopDetails), PaymentOptionsFragment.class.getName(), true);
    }

    @Override
    public void gotoWishListDetailsScreen(ShopWiseWishListResponseDetails shopWiseWishListResponseDetails) {

    }

    @Override
    public void gotoEditProfile() {

    }

    @Override
    public void gotoEditAddress(AddressResponse address) {

    }

    @Override
    public void gotoViewProfile() {

    }

    @Override
    public void gotoMapView() {

    }

    @Override
    public void getMapGeoCoordinates(LatLng latLng) {

    }

    @Override
    public void gotoPaymentOptionsScreen(ShopDetailsModel vendorShopDetails, int selectedPaymentType) {
        hideCartIcon();
        replaceFragment(PaymentOptionsFragment.getInstance(vendorShopDetails,selectedPaymentType), PaymentOptionsFragment.class.getName(), true);
    }

    @Override
    public void showBadge(boolean b) {

    }

    @Override
    public void updateBadgeCount() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle data = intent.getExtras();
        if (data != null) {
            boolean isShoppingCart = data.getBoolean("ShoppingCart");
            boolean isFavouritesSelected = data.getBoolean("IsFavourites", false);
            if (isShoppingCart) {
                replaceFragment(ShoppingCartFragment.getInstance(), ShoppingCartFragment.class.getName(), true);
            } else if (isFavouritesSelected) {
                replaceFragment(CustomerFavouritesFragment.getInstance(), CustomerFavouritesFragment.class.getName(), true);
            }
        } else {
//            if(vendorShopsListFragment!=null && !vendorShopsListFragment.isAdded()) {
//                replaceFragment(vendorShopsListFragment, VendorShopsListFragment.class.getName(), false);
//            }
        }
    }
    private void checkRegistration() {
        if (Utils.isNetworkConnected(getApplicationContext())) {



            AuthenticationService getUserDataService = RetrofitClientInstance.getInstance().getRetrofitInstanceRokad().create(AuthenticationService.class);
            Call<BaseResponse> user = getUserDataService.registrationRokad(MyProfile.getInstance(getApplicationContext()).getFirstName(),(MyProfile.getInstance(getApplicationContext()).getLastName()),(MyProfile.getInstance(getApplicationContext()).getMobileNumber()),(MyProfile.getInstance(getApplicationContext()).getEmail()), "rokad","rokad",(MyProfile.getInstance(getApplicationContext()).getUserID()));//("", "");
            user.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    Log.d("onResponse", "onResponse: Login Fragment");

                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                        //  showDialog("success", response.body().getMsg());

                    } else if (response.body() != null){
                        /// showDialog("Sorry..", response.body().getMsg());
                    } else {

                    }

                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    // Log.d("onFailure", "onFailure: Login Fragment ");

                    if(t instanceof SocketTimeoutException){
                    } else {
//                            Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    // showDialog("Sorry..", getString(R.string.internet_failed_login_case));
                }
            });


        } else {
        }
    }

}
