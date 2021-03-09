package com.rmart.customer.shops.list.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Satya Seshu on 07/09/20.
 */
public class VendorShopsListFragment extends CustomerHomeFragment implements OnMapReadyCallback {

    private AppCompatEditText etProductsSearchField;
    private int currentPage = 0;
    private List<ShopDetailsModel> shopsList;
    private boolean isLoading = false;
    private boolean isLastPage = false;
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
    private GoogleMap googleMap;
    private TextView tvAddressField;
    private Circle currentCircle = null;
    private AppCompatRadioButton selectedRadioButton = null;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SupportMapFragment mapsFragment;
    private LocationManager locationManager;
    private ImageView ivSearchField;
    private Location currentLocation;
    private TextView errormessage;
    private RelativeLayout map_or_list_view;
    LinearLayout changeAddressLayout;
    private AppCompatButton btnTryAgain;
    String venderID, shopId;
    View view;
    LinearLayout erorolayout;
    private ArrayList<AddressResponse> addressList = new ArrayList<>();
    RadioGroup mapViewOrListViewRadioGroup;
    RelativeLayout searchLayout;
    public static VendorShopsListFragment getInstance(String VenderID,String shopId) {
        VendorShopsListFragment vendorShopsListFragment = new VendorShopsListFragment();

        Bundle args = new Bundle();
        args.putString("VenderID", VenderID);
        args.putString("shopId", shopId);
        vendorShopsListFragment.setArguments(args);
        return vendorShopsListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LoggerInfo.printLog("Fragment", "VendorShopsListFragment");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(requireActivity()));
        return inflater.inflate(R.layout.fragment_vendor_list_view, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            venderID = getArguments().getString("VenderID");
            shopId = getArguments().getString("shopId");
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
    }

    private void loadUIComponents() {
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        myProfile = MyProfile.getInstance();
        map_or_list_view = view.findViewById(R.id.map_or_list_view);
        changeAddressLayout = view.findViewById(R.id.changeAddressLayout);
        btnTryAgain = view.findViewById(R.id.btn_tryagain);
        searchLayout = view.findViewById(R.id.searchLayout);
        errormessage = view.findViewById(R.id.errormessage);
        RecyclerView vendorShopsListField = view.findViewById(R.id.products_list_field);
        RecyclerView searchProductsListField = view.findViewById(R.id.search_products_list_field);
        tvAddressField = view.findViewById(R.id.tv_address_field);
        erorolayout = view.findViewById(R.id.erorolayout);
        etProductsSearchField = view.findViewById(R.id.edt_product_search_field);
        ivSearchField = view.findViewById(R.id.iv_search_field);
//        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_field);
//
        btnTryAgain.setOnClickListener(view1 -> {
            resetShopsList();
            getShopsList();
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
                    mapViewOrListViewRadioGroup.setVisibility(View.GONE);

                    ProductRepository.searchProduct(0,latitude,longitude,etProductsSearchField.getText().toString()).observeForever(new Observer<ProductSearchResponce>() {
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
                    mapViewOrListViewRadioGroup.setVisibility(View.VISIBLE);

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
        vendorShopsListField.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        loadMoreItems();
                    }
                }
            }
        });
        shopsList = new ArrayList<>();
        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.recycler_decoration_divider)));
        vendorShopsListAdapter = new VendorShopsListAdapterNew(requireActivity(), shopsList, callBackListener);
        vendorShopsListField.setAdapter(vendorShopsListAdapter);
        view.findViewById(R.id.btn_change_address_field).setOnClickListener(v -> changeAddressSelected());

        mapAddress();
        mapsFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapsFragment != null) {
            mapsFragment.getMapAsync(this);
        }
        RelativeLayout mapLayout = view.findViewById(R.id.map_layout_field);

        if (selectedRadioButton != null) {
            String selectedText = selectedRadioButton.getText().toString();
            if(!TextUtils.isEmpty(selectedText)) {
                AppCompatRadioButton mapViewRadio = view.findViewById(R.id.map_view_radio_button);
                AppCompatRadioButton listViewRadio = view.findViewById(R.id.list_view_radio_button);
                if (selectedText.equalsIgnoreCase(getString(R.string.map_view))) {
                    listViewRadio.setChecked(false);
                    mapViewRadio.setChecked(true);
                    mapLayout.setVisibility(View.VISIBLE);
                    vendorShopsListField.setVisibility(View.GONE);
                } else {
                    mapViewRadio.setChecked(false);
                    listViewRadio.setChecked(true);
                    mapLayout.setVisibility(View.GONE);
                    vendorShopsListField.setVisibility(View.VISIBLE);
                }
            }
        }
        mapViewOrListViewRadioGroup = view.findViewById(R.id.map_view_or_list_view_radio_group);

        mapViewOrListViewRadioGroup.setOnCheckedChangeListener((radioGroup, radioButtonID) -> {
            selectedRadioButton = radioGroup.findViewById(radioButtonID);
            String selectedText = selectedRadioButton.getText().toString();
            if (selectedText.equalsIgnoreCase(getString(R.string.map_view))) {
                mapLayout.setVisibility(View.VISIBLE);
                vendorShopsListField.setVisibility(View.GONE);
            } else {
                mapLayout.setVisibility(View.GONE);
                vendorShopsListField.setVisibility(View.VISIBLE);
            }
        });

        getShopsList();
    }

    private void mapAddress() {
        MyProfile myProfile = MyProfile.getInstance();

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
        MyProfile myProfile = MyProfile.getInstance();
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

    public void updateShopWishListStatus(ShopDetailsModel vendorShopDetails) {
        int index = shopsList.indexOf(vendorShopDetails);
        if (index > -1) {
            vendorShopsListAdapter.notifyItemChanged(index);
        }
    }

    private final CallBackInterface callBackListener = pObject -> {
        if (pObject instanceof ShopDetailsModel) {
            onCustomerHomeInteractionListener.gotoVendorProductDetails((ShopDetailsModel) pObject,null);
        } else if (pObject instanceof ContentModel) {
            ContentModel contentModel = (ContentModel) pObject;
            String status = contentModel.getStatus();
            if (status.equalsIgnoreCase(Constants.TAG_CALL)) {
                callSelected((String) contentModel.getValue());
            } else if (status.equalsIgnoreCase(Constants.TAG_MESSAGE)) {
                messageSelected((String) contentModel.getValue());
            } else if (status.equalsIgnoreCase(Constants.TAG_SHOP_FAVOURITE)) {
                selectedShopDetails = (ShopDetailsModel) contentModel.getValue();
                shopFavouriteSelected();
            }
        }
    };
    private void callSelected(String shopMobileNo) {
        Utils.openDialPad(requireActivity(), shopMobileNo);
    }

    private void messageSelected(String emailId) {
        Utils.openGmailWindow(requireActivity(), emailId);
    }

    private void shopFavouriteSelected() {
        boolean isWishListShop = selectedShopDetails.getShopWishListStatus() == 1;
        if (isWishListShop) deleteShopFromWishList();
        else addShopFromWishList();
    }

    private void deleteShopFromWishList() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<BaseResponse> call = customerProductsService.deleteShopFromWishList(clientID, selectedShopDetails.getVendorId(), selectedShopDetails.getShopId(),
                    MyProfile.getInstance().getUserID(),MyProfile.getInstance().getRoleID());
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        BaseResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                showDialog(getString(R.string.shop_removed_from_favourites_successfully), pObject -> {
                                    selectedShopDetails.setShopWishListStatus(0);
                                    selectedShopDetails.setShopWishListId(-1);
                                    updateShopDetailsAdapter();
                                });
                            } else {
                                showDialog(body.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<BaseResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException) {
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void updateShopDetailsAdapter() {
        int index = shopsList.indexOf(selectedShopDetails);
        if (index > -1) {
            shopsList.set(index, selectedShopDetails);
            vendorShopsListAdapter.notifyItemChanged(index);
        }
    }

    private void addShopFromWishList() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<AddShopToWishListResponse> call = customerProductsService.addShopToWishList(clientID, selectedShopDetails.getVendorId(),
                    selectedShopDetails.getShopId(), MyProfile.getInstance().getUserID(),MyProfile.getInstance().getRoleID());
            call.enqueue(new Callback<AddShopToWishListResponse>() {
                @Override
                public void onResponse(@NotNull Call<AddShopToWishListResponse> call, @NotNull Response<AddShopToWishListResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        AddShopToWishListResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                showDialog(getString(R.string.shop_added_to_favourites_successfully), pObject -> {
                                    int shopWishId = body.getShopToWishListDataResponse().getShopWishListId();
                                    selectedShopDetails.setShopWishListId(shopWishId);
                                    selectedShopDetails.setShopWishListStatus(1);
                                    updateShopDetailsAdapter();
                                });
                            } else {
                                showDialog(body.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddShopToWishListResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void changeAddressSelected() {
        onCustomerHomeInteractionListener.gotoChangeAddress();
    }



    private void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        getShopsList();
    }

    private void resetShopsList() {
        shopsList.clear();
        vendorShopsListAdapter.updateItems(shopsList);
        vendorShopsListAdapter.notifyDataSetChanged();
        currentPage = 0;
    }

    private void getShopsList() {
        if (Utils.isNetworkConnected(requireActivity()) && myProfile != null) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";

            customerProductsService.getCustomerShopsList(clientID, currentPage, searchShopName, myProfile.getUserID(), latitude, longitude,venderID,shopId,MyProfile.getInstance().getRoleID().equalsIgnoreCase(Utils.CUSTOMER_ID)?"Customer":"Retailer").enqueue(new Callback<CustomerProductsResponse>() {
                @Override
                public void onResponse(@NotNull Call<CustomerProductsResponse> call, @NotNull Response<CustomerProductsResponse> response) {
                    progressDialog.dismiss();
                    resetShopsList();
                 //   swipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()) {
                        CustomerProductsResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase("success")) {
                                totalShopsCount = data.getCustomerShopsList().getShopTotalCount();
                                List<ShopDetailsModel> customerProductsList = data.getCustomerShopsList().getCustomerShopsList();
                                updateAdapter(customerProductsList);
                                try {
                                    if (customerProductsList != null && customerProductsList.get(0) != null && shopId != null && !shopId.equalsIgnoreCase("") && Integer.parseInt(shopId) == customerProductsList.get(0).getShopId()) {
                                        callBackListener.callBackReceived(customerProductsList.get(0));
                                        shopId = null;
                                        venderID = null;
                                    }
                                } catch (Exception e ){

                                }

                            } else {
                                showDialog(data.getMsg());
                                //displayDefaultMapLocation();
                                //getLocation();
                            }
                        } else {
                            showDialog(getString(R.string.no_products_error));
                            displayDefaultMapLocation();
                        }
                        if(currentPage==0) {
                            erorolayout.setVisibility(View.GONE);
                            map_or_list_view.setVisibility(View.VISIBLE);
                            searchLayout.setVisibility(View.VISIBLE);
                            changeAddressLayout.setVisibility(View.VISIBLE);
                            mapViewOrListViewRadioGroup.setVisibility(View.VISIBLE);
                        }

                    } else {

                        if(currentPage==0) {
                            map_or_list_view.setVisibility(View.GONE);
                            searchLayout.setVisibility(View.GONE);
                            changeAddressLayout.setVisibility(View.GONE);
                            mapViewOrListViewRadioGroup.setVisibility(View.GONE);
                            erorolayout.setVisibility(View.VISIBLE);
                            errormessage.setText(response.message());
                        }
                        //showDialog(response.message());
                        displayDefaultMapLocation();
                    }
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
                        changeAddressLayout.setVisibility(View.GONE);
                        mapViewOrListViewRadioGroup.setVisibility(View.GONE);

                        erorolayout.setVisibility(View.VISIBLE);
                    }

                    progressDialog.dismiss();
                 //   swipeRefreshLayout.setRefreshing(false);
                    displayDefaultMapLocation();
                }
            });
        } else {

            if(currentPage==0) {
                map_or_list_view.setVisibility(View.GONE);
                searchLayout.setVisibility(View.GONE);
                changeAddressLayout.setVisibility(View.GONE);
                mapViewOrListViewRadioGroup.setVisibility(View.GONE);
                errormessage.setText(getString(R.string.network_slow));
                erorolayout.setVisibility(View.VISIBLE);
            }

        }
    }

    private void displayDefaultMapLocation() {
        boolean isCoordinatesValid = isValidLatLng(latitude, longitude);
        if (isCoordinatesValid) {
            try {
                LatLng latLng = new LatLng(latitude, longitude);
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(tvAddressField.getText().toString());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                googleMap.addMarker(markerOptions);
            } catch (Exception e){

            }
        }
    }

    private void updateAdapter(List<ShopDetailsModel> customerShopsList) {
        shopsList.addAll(customerShopsList);
        updateShopsListMap();
        vendorShopsListAdapter.updateItems(customerShopsList);
        vendorShopsListAdapter.notifyDataSetChanged();
        if (shopsList.size() >= totalShopsCount) {
            isLastPage = true;
        } else {
            isLoading = false;
        }
    }

    public boolean isValidLatLng(double lat, double lng) {
        if (lat < -90 || lat > 90) {
            return false;
        } else return !(lng < -180) && !(lng > 180);
    }

    @Override
    public void onMapReady(GoogleMap map) {
       this.googleMap = map;
        googleMap.clear();
        if (currentLocation != null && addressList.isEmpty()) {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(tvAddressField.getText().toString());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            googleMap.addMarker(markerOptions);


            addCircleToMap();
        } else if(!addressList.isEmpty()) {
            if (addressList.size() == 1) {
                AddressResponse addressResponse = addressList.get(0);
                latitude = addressResponse.getLatitude();
                longitude = addressResponse.getLongitude();
            } else {
                for (AddressResponse addressResponse : addressList) {
                    if (myProfile.getPrimaryAddressId().equalsIgnoreCase(addressResponse.getId().toString())) {
                        latitude = addressResponse.getLatitude();
                        longitude = addressResponse.getLongitude();
                        break;
                    }
                }
            }
            LatLng latLng = new LatLng(latitude, longitude);
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(tvAddressField.getText().toString());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            googleMap.addMarker(markerOptions);

            addCircleToMap();
        }
    }

    private void updateShopsListMap() {
        if (googleMap == null) return;
        if(!shopsList.isEmpty()) {
            for (ShopDetailsModel shopDetailsModel : shopsList) {
                boolean isCoordinatesValid = isValidLatLng(shopDetailsModel.getShopLatitude(), shopDetailsModel.getShopLongitude());
                if (isCoordinatesValid) {
                    LatLng latLng = new LatLng(shopDetailsModel.getShopLatitude(), shopDetailsModel.getShopLongitude());
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(shopDetailsModel.getShopName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.google_map_icon2));
                    //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
                    //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
                    Marker marker = googleMap.addMarker(markerOptions);
                    marker.showInfoWindow();
                }
            }
        } else {
            fetchLocation();
        }
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                Objects.requireNonNull(requireActivity()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(requireActivity()), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;
                if (mapsFragment != null) {
                    mapsFragment.getMapAsync(this);
                }
            } else {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    onGPS();
                } else {
                    getLocation();
                }
            }
        });
    }

    private void onGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", (dialog, which) -> {
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 100);
            //
        }).setNegativeButton("No", (dialog, which) -> dialog.cancel());
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                //latitude = locationGPS.getLatitude();
                //longitude = locationGPS.getLongitude();
                currentLocation = locationGPS;
                updateMap();
            } else {
                MyLocation myLocation = new MyLocation(requireActivity());
                myLocation.getLocation(locationResult);
            }
        }
    }

    public MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {

        @Override
        public void gotLocation(Location location) {
            if (location != null) {
                //latitude = location.getLatitude();
                //longitude = location.getLongitude();
                currentLocation = location;
                updateMap();
            }
        }
    };

    private void updateMap() {
        if (mapsFragment != null) {
            mapsFragment.getMapAsync(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }

    private void addCircleToMap() {
        if (currentLocation != null) {
            double lat = currentLocation.getLatitude();
            double longi = currentLocation.getLongitude();
            googleMap.addCircle(new CircleOptions()
                    .center(new LatLng(lat, longi))
                    .radius(15000)
                    .strokeWidth(2)
                    .strokeColor(ContextCompat.getColor(requireActivity(), R.color.grey_color_five))
                    .fillColor(Color.argb(128, 0, 0, 0))
                    .clickable(true));
        } else if(latitude != 0.0 && longitude != 0.0) {
            googleMap.addCircle(new CircleOptions()
                    .center(new LatLng(latitude, longitude))
                    .radius(15000)
                    .strokeWidth(2)
                    .strokeColor(ContextCompat.getColor(requireActivity(), R.color.grey_color_five))
                    .fillColor(Color.argb(128, 0, 0, 0))
                    .clickable(true));
        }
    }
}
