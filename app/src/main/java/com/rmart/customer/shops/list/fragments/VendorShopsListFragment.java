package com.rmart.customer.shops.list.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.dashboard.model.ShopType;
import com.rmart.customer.shops.home.listner.OnClickListner;
import com.rmart.customer.shops.home.listner.onProdcutClick;
import com.rmart.customer.shops.home.model.Category;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.list.adapters.AllProductsAdapter;
import com.rmart.customer.shops.list.adapters.VendorShopsListAdapterNew;
import com.rmart.customer.models.AddShopToWishListResponse;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.models.CustomerProductsResponse;
import com.rmart.customer.shops.list.models.ProductSearchResponce;
import com.rmart.customer.shops.list.models.SearchProducts;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.customer.shops.list.repositories.ProductRepository;
import com.rmart.customer.views.CustomerHomeActivity;
import com.rmart.customer.views.CustomerHomeFragment;
import com.rmart.mapview.MyLocation;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.CommonUtils;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.AddressResponse;
import com.rmart.utilits.BaseResponse;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Satya Seshu on 07/09/20.
 */
public class VendorShopsListFragment extends CustomerHomeFragment {

    private AppCompatEditText etProductsSearchField;

    int nextStartPage=0;
    private int PAGE_SIZE = 20;
    private int totalShopsCount = 0;
    private String searchShopName = "";
    private VendorShopsListAdapterNew vendorShopsListAdapter;
    private OnCustomerHomeInteractionListener onCustomerHomeInteractionListener;
    private MyProfile myProfile;
    private ShopDetailsModel selectedShopDetails;
    private double latitude = 0.0;
    private double longitude = 0.0;
    //private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvAddressField,shopCount;
    private ImageView ivSearchField;
    private TextView errormessage;
    private RelativeLayout map_or_list_view;
    LinearLayout changeAddressLayout;
    private AppCompatButton btnTryAgain;
    String ShopTypeID;
    View view;
    LinearLayout erorolayout;
    ImageView errorIcon;
    private ArrayList<AddressResponse> addressList = new ArrayList<>();
    RelativeLayout searchLayout;
    public static VendorShopsListFragment getInstance(String ShopTypeID) {
        VendorShopsListFragment vendorShopsListFragment = new VendorShopsListFragment();

        Bundle args = new Bundle();

        args.putString("ShopTypeID", ShopTypeID);
        vendorShopsListFragment.setArguments(args);
        return vendorShopsListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LoggerInfo.printLog("Fragment", "VendorShopsListFragment");
        return inflater.inflate(R.layout.fragment_vendor_list_view, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ShopTypeID = getArguments().getString("ShopTypeID");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCustomerHomeInteractionListener) {
            onCustomerHomeInteractionListener = (OnCustomerHomeInteractionListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        loadUIComponents();
        setErrorIcon();
    }
    private void setErrorIcon(){

        //errorIcon.setImageDrawable();
        switch (ShopTypeID){


            case "2": //Vegetable
                errorIcon.setImageResource(R.mipmap.vegetable_shop_icon);
                break;
            case "3": //Bag Shop
                errorIcon.setImageResource(R.mipmap.bag_shop_icon);
                break;
            case "4": // not define
                errorIcon.setImageResource(R.mipmap.slwo_internet_icon1);
                break;
            case "5": //Furniture Shop
                errorIcon.setImageResource(R.mipmap.furniture_shop_icon);
                break;
            case "9": //Handcrafted
                errorIcon.setImageResource(R.mipmap.handcrafted);
                break;
            default:
                errorIcon.setImageResource(R.mipmap.slwo_internet_icon1);
                break;
        }

//        Vegetable  --- 2
//        Bag Shop   -- 3
//        Furniture Shop ---5
//        Mobile Shop  --3

    }

    private void loadUIComponents() {
        myProfile = MyProfile.getInstance(getActivity());
        map_or_list_view = view.findViewById(R.id.map_or_list_view);
        changeAddressLayout = view.findViewById(R.id.changeAddressLayout);
        btnTryAgain = view.findViewById(R.id.btn_tryagain);
        searchLayout = view.findViewById(R.id.searchLayout);
        errormessage = view.findViewById(R.id.errormessage);
        RecyclerView vendorShopsListField = view.findViewById(R.id.products_list_field);
        RecyclerView searchProductsListField = view.findViewById(R.id.search_products_list_field);
        tvAddressField = view.findViewById(R.id.tv_address_field);
        shopCount = view.findViewById(R.id.shop_count);
        erorolayout = view.findViewById(R.id.erorolayout);
        errorIcon = view.findViewById(R.id.errorIcon);
        etProductsSearchField = view.findViewById(R.id.edt_product_search_field);
        ivSearchField = view.findViewById(R.id.iv_search_field);
//        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_field);
//
        btnTryAgain.setOnClickListener(view1 -> {

            getShopsList(0);
        });
        ivSearchField.setOnClickListener(v -> {
            etProductsSearchField.setText("");
            searchShopName = "";
            CommonUtils.closeVirtualKeyboard(requireActivity(), ivSearchField);
        });
//        swipeRefreshLayout.setOnRefreshListener(() -> {
//            resetShopsList();
//            getShopsList();
//        });
        AllProductsAdapter allProductsAdapter  =new AllProductsAdapter(getActivity(), new ArrayList<>(), new onProdcutClick() {
            @Override
            public void onSelected(SearchProducts productData) {
                ;
                    ProductData productData1 = new ProductData();
                    productData1.setProductId(productData.getProductId());
                    productData1.setProductImage(productData.getDisplayImage());
                    productData1.setParentCategoryId(productData.getProductCatId());
                    productData1.setProductName(productData.getProductName());
                    productData1.setUnits(productData.getProduct_unit());
                    onCustomerHomeInteractionListener.gotoVendorProductDetails( productData.getShopDetailsModel(),productData1);

            }
        });

        etProductsSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(etProductsSearchField.getText().toString().length()>0){
                    searchProductsListField.setVisibility(View.VISIBLE);
                    vendorShopsListField.setVisibility(View.GONE);

                    ProductRepository.searchProduct(getActivity(),0,latitude,longitude,etProductsSearchField.getText().toString()).observeForever(new Observer<ProductSearchResponce>() {
                        @Override
                        public void onChanged(ProductSearchResponce productSearchResponce) {

                            if(productSearchResponce.getStatus().equalsIgnoreCase("Success")){
                                allProductsAdapter.clear();
                                allProductsAdapter.addProducts(productSearchResponce.data);

                            }

                        }
                    });

                } else {
                    searchProductsListField.setVisibility(View.GONE);
                    vendorShopsListField.setVisibility(View.VISIBLE);

                }


            }
        });
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireActivity());
        searchProductsListField.setLayoutManager(layoutManager2);
        searchProductsListField.setHasFixedSize(false);
        searchProductsListField.setItemAnimator(new SlideInDownAnimator());
        searchProductsListField.setAdapter(allProductsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        vendorShopsListField.setLayoutManager(layoutManager);
        vendorShopsListField.setHasFixedSize(false);
        vendorShopsListField.setItemAnimator(new SlideInDownAnimator());

        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.recycler_decoration_divider)));
        vendorShopsListAdapter = new VendorShopsListAdapterNew(requireActivity(), new ArrayList<>(), callBackListener);
        vendorShopsListField.setAdapter(vendorShopsListAdapter);
        view.findViewById(R.id.btn_change_address_field).setOnClickListener(v -> changeAddressSelected());

        mapAddress();

        getShopsList(0);
    }

    private void mapAddress() {
        MyProfile myProfile = MyProfile.getInstance(getActivity());

        if (myProfile != null) {
            if(myProfile.getRoleID().equalsIgnoreCase(Utils.CUSTOMER_ID)){
                view.findViewById(R.id.btn_change_address_field).setVisibility(View.VISIBLE);
            } else {
                view.findViewById(R.id.btn_change_address_field).setVisibility(View.GONE);
            }
            addressList = myProfile.getAddressResponses();
            if (addressList != null && !addressList.isEmpty()) {
                if (addressList.size() == 1) {
                    AddressResponse addressResponse = addressList.get(0);
                    latitude = addressResponse.getLatitude();
                    longitude = addressResponse.getLongitude();
                    tvAddressField.setText(addressResponse.getAddress());
                } else {
                    for (AddressResponse addressResponse : addressList) {
                        if (myProfile.getPrimaryAddressId().equalsIgnoreCase(addressResponse.getId().toString())) {
                            latitude = addressResponse.getLatitude();
                            longitude = addressResponse.getLongitude();
                            tvAddressField.setText(addressResponse.getAddress());
                            break;
                        }
                    }
                }

                try {
                    Geocoder   geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 5);
                    tvAddressField.setText(addresses.get(0).getLocality());

                } catch (Exception e){

                }



            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateToolBar();
        mapAddress();
    }

    public void updateToolBar() {
        MyProfile myProfile = MyProfile.getInstance(getActivity());
        if (myProfile != null) {
            try {
                int cartCount = myProfile.getCartCount().getValue();
                /*if (cartCount > 0) {
                    ((CustomerHomeActivity) (requireActivity())).showCartIcon();
                } else {
                    ((CustomerHomeActivity) (requireActivity())).hideCartIcon();
                }*/
                ((CustomerHomeActivity) (requireActivity())).showCartIcon();
            } catch (Exception ex) {
                ((CustomerHomeActivity) (requireActivity())).hideCartIcon();
            }
        }
        requireActivity().setTitle(getString(R.string.shops_list));
    }



    private final CallBackInterface callBackListener = pObject -> {
        if(pObject!=null) {
            if (pObject instanceof ShopDetailsModel) {
                onCustomerHomeInteractionListener.gotoVendorProductDetails((ShopDetailsModel) pObject, null);
            } else if (pObject instanceof ContentModel) {
                ContentModel contentModel = (ContentModel) pObject;
                String status = contentModel.getStatus();
                if (status.equalsIgnoreCase(Constants.TAG_CALL)) {
                    callSelected((String) contentModel.getValue());
                } else if (status.equalsIgnoreCase(Constants.TAG_MESSAGE)) {
                    messageSelected((String) contentModel.getValue());
                } else if (status.equalsIgnoreCase(Constants.TAG_SHOP_FAVOURITE)) {
                    selectedShopDetails = (ShopDetailsModel) contentModel.getValue();
                }
            }
        } else {
            loadMoreItems(nextStartPage);
        }
    };
    private void callSelected(String shopMobileNo) {
        Utils.openDialPad(requireActivity(), shopMobileNo);
    }

    private void messageSelected(String emailId) {
        Utils.openGmailWindow(requireActivity(), emailId);
    }



    private void changeAddressSelected() {
        onCustomerHomeInteractionListener.gotoChangeAddress();
    }



    private void loadMoreItems(int currentPage) {
        getShopsList(currentPage);
    }



    private void getShopsList(int currentPage) {
        if (Utils.isNetworkConnected(requireActivity()) && myProfile != null) {
            //progressDialog.show();
            vendorShopsListAdapter.setLoading(true);

            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";

            customerProductsService.getCustomerShopsList(clientID, currentPage, searchShopName, myProfile.getUserID(), latitude, longitude,null,null,MyProfile.getInstance(getActivity()).getRoleID().equalsIgnoreCase(Utils.CUSTOMER_ID)?"Customer":"Retailer",ShopTypeID).enqueue(new Callback<CustomerProductsResponse>() {
                @Override
                public void onResponse(@NotNull Call<CustomerProductsResponse> call, @NotNull Response<CustomerProductsResponse> response) {
                    //progressDialog.dismiss();

                 //   swipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()) {
                        CustomerProductsResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase("success")) {
                                totalShopsCount = data.getCustomerShopsList().getShopTotalCount();
                                nextStartPage = data.getCustomerShopsList().getNextPage();
                                shopCount.setText(":"+totalShopsCount);
                                List<ShopDetailsModel> customerProductsList = data.getCustomerShopsList().getCustomerShopsList();
                                updateAdapter(customerProductsList);


                            } else {
                                if(currentPage==0) {
                                    map_or_list_view.setVisibility(View.GONE);
                                    searchLayout.setVisibility(View.GONE);
                                    changeAddressLayout.setVisibility(View.VISIBLE);

                                    erorolayout.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            showDialog(getString(R.string.no_products_error));
                        }

                    } else {

                        if(currentPage==0) {
                            map_or_list_view.setVisibility(View.GONE);
                            searchLayout.setVisibility(View.GONE);
                            changeAddressLayout.setVisibility(View.VISIBLE);
                            erorolayout.setVisibility(View.VISIBLE);
                            errormessage.setText(response.message());
                        }
                        //showDialog(response.message());
                    }
                    vendorShopsListAdapter.setLoading(false);
                }

                @Override
                public void onFailure(@NotNull Call<CustomerProductsResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        //showDialog("", getString(R.string.network_slow));
                        errormessage.setText(getString(R.string.network_slow));
                    } else {
                       // showDialog("", t.getMessage());
                        errormessage.setText(t.getMessage());
                    }
                    if(currentPage==0) {
                        map_or_list_view.setVisibility(View.GONE);
                        searchLayout.setVisibility(View.GONE);
                        changeAddressLayout.setVisibility(View.VISIBLE);

                        erorolayout.setVisibility(View.VISIBLE);
                    }

                    progressDialog.dismiss();
                 //   swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {

            if(currentPage==0) {
                map_or_list_view.setVisibility(View.GONE);
                searchLayout.setVisibility(View.GONE);
                changeAddressLayout.setVisibility(View.VISIBLE);
                errormessage.setText(getString(R.string.network_slow));
                erorolayout.setVisibility(View.VISIBLE);
            }

        }
    }


    private void updateAdapter(List<ShopDetailsModel> customerShopsList) {
        vendorShopsListAdapter.updateItems(customerShopsList);
        vendorShopsListAdapter.notifyDataSetChanged();
        if (vendorShopsListAdapter.filteredListData.size() >= totalShopsCount) {
            vendorShopsListAdapter.isLastPage = true;
        }
    }










    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }


}
