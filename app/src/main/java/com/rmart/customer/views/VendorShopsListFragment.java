package com.rmart.customer.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.adapters.VendorShopsListAdapter;
import com.rmart.customer.models.AddShopToWishListResponse;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.models.CustomerProductsResponse;
import com.rmart.customer.models.CustomerProductsShopDetailsModel;
import com.rmart.mapview.MyLocation;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.AddressResponse;
import com.rmart.utilits.pojos.BaseResponse;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

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
public class VendorShopsListFragment extends CustomerHomeFragment {

    private AppCompatEditText etProductsSearchField;
    private int currentPage = 0;
    private List<CustomerProductsShopDetailsModel> shopsList;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int PAGE_SIZE = 20;
    private int totalShopsCount = 0;
    private String searchShopName = "";
    private VendorShopsListAdapter vendorShopsListAdapter;
    private OnCustomerHomeInteractionListener onCustomerHomeInteractionListener;
    private MyProfile myProfile;
    private CustomerProductsShopDetailsModel selectedShopDetails;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;
    private SupportMapFragment mapFragment;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private LocationManager locationManager;
    private GoogleMap googleMap;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static VendorShopsListFragment getInstance() {
        return new VendorShopsListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LoggerInfo.printLog("Fragment", "VendorShopsListFragment");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        return inflater.inflate(R.layout.fragment_vendor_list_view, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnCustomerHomeInteractionListener) {
            onCustomerHomeInteractionListener = (OnCustomerHomeInteractionListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {

        myProfile = MyProfile.getInstance();

        RecyclerView vendorShopsListField = view.findViewById(R.id.products_list_field);
        AppCompatTextView tvAddressField = view.findViewById(R.id.tv_address_field);
        etProductsSearchField = view.findViewById(R.id.edt_product_search_field);
        ImageView ivSearchField = view.findViewById(R.id.iv_search_field);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_field);
        swipeRefreshLayout.setRefreshing(false);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            currentPage = 0;
            shopsList.clear();
            getShopsList();
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
                if (s.toString().trim().length() != 0) {
                    performSearch();
                }
                ivSearchField.setImageResource(R.drawable.search);
            }
        });
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
        vendorShopsListField.addItemDecoration(divider);

        vendorShopsListAdapter = new VendorShopsListAdapter(requireActivity(), shopsList, callBackListener);
        vendorShopsListField.setAdapter(vendorShopsListAdapter);

        view.findViewById(R.id.btn_change_address_field).setOnClickListener(v -> changeAddressSelected());

        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            ArrayList<AddressResponse> addressList = myProfile.getAddressResponses();
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


        mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
            fetchLocation();
        }

        AppCompatRadioButton listViewRadio = view.findViewById(R.id.list_view_radio_button);
        listViewRadio.setChecked(true);
        RadioGroup mapViewOrListViewRadioGroup = view.findViewById(R.id.map_view_or_list_view_radio_group);

        RelativeLayout mapLayout = view.findViewById(R.id.map_layout_field);

        mapViewOrListViewRadioGroup.setOnCheckedChangeListener((radioGroup, radioButtonID) -> {
            AppCompatRadioButton selectedRadioButton = radioGroup.findViewById(radioButtonID);
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

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;
                latitude = currentLocation.getLatitude();
                longitude = currentLocation.getLongitude();
                // Toast.makeText(getContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                //SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(callback);
                }
            } else {
                locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
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
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))).setNegativeButton("No", (dialog, which) -> dialog.cancel());
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
                currentLocation = locationGPS;
                latitude = locationGPS.getLatitude();
                longitude = locationGPS.getLongitude();
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
                currentLocation = location;
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                updateMap();
            }
        }
    };

    private void updateMap() {
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap map) {
            googleMap = map;
            if (currentLocation != null) {
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                googleMap.addMarker(markerOptions);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        updateToolBar();
    }

    public void updateToolBar() {
        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            int cartCount = myProfile.getCartCount().getValue();
            if (cartCount > 0) {
                ((CustomerHomeActivity) (requireActivity())).showCartIcon();
            } else {
                ((CustomerHomeActivity) (requireActivity())).hideCartIcon();
            }
        }
        requireActivity().setTitle(getString(R.string.shops_list));
    }

    public void updateShopWishListStatus(CustomerProductsShopDetailsModel vendorShopDetails) {
        int index = shopsList.indexOf(vendorShopDetails);
        if (index > -1) {
            vendorShopsListAdapter.notifyItemChanged(index);
        }
    }

    private CallBackInterface callBackListener = pObject -> {
        if (pObject instanceof CustomerProductsShopDetailsModel) {
            onCustomerHomeInteractionListener.gotoVendorProductDetails((CustomerProductsShopDetailsModel) pObject);
        } else if (pObject instanceof ContentModel) {
            ContentModel contentModel = (ContentModel) pObject;
            String status = contentModel.getStatus();
            if (status.equalsIgnoreCase(Constants.TAG_CALL)) {
                callSelected((String) contentModel.getValue());
            } else if (status.equalsIgnoreCase(Constants.TAG_MESSAGE)) {
                messageSelected();
            } else if (status.equalsIgnoreCase(Constants.TAG_SHOP_FAVOURITE)) {
                selectedShopDetails = (CustomerProductsShopDetailsModel) contentModel.getValue();
                shopFavouriteSelected();
            }
        }
    };

    private void callSelected(String shopMobileNo) {
        Utils.openDialPad(requireActivity(), shopMobileNo);
    }

    private void messageSelected() {
        Utils.openGmailWindow(requireActivity());
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
                    MyProfile.getInstance().getUserID());
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        BaseResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                showDialog(body.getMsg(), pObject -> {
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
                    progressDialog.dismiss();
                    showDialog(t.getMessage());
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
                    selectedShopDetails.getShopId(), MyProfile.getInstance().getUserID());
            call.enqueue(new Callback<AddShopToWishListResponse>() {
                @Override
                public void onResponse(@NotNull Call<AddShopToWishListResponse> call, @NotNull Response<AddShopToWishListResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        AddShopToWishListResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                showDialog(body.getMsg(), pObject -> {
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
                    progressDialog.dismiss();
                    showDialog(t.getMessage());
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void changeAddressSelected() {
        onCustomerHomeInteractionListener.gotoChangeAddress();
    }

    private void performSearch() {
        String newText = Objects.requireNonNull(etProductsSearchField.getText()).toString().trim();
        if (newText.length() < 1) {
            vendorShopsListAdapter.updateItems(new ArrayList<>());
            vendorShopsListAdapter.notifyDataSetChanged();
        } else if (newText.length() == 3) {
            searchShopName = newText;
            currentPage = 0;
            getShopsList();
        } else {
            vendorShopsListAdapter.getFilter().filter(newText);
        }
    }

    private void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        getShopsList();
    }

    private void getShopsList() {
        if (Utils.isNetworkConnected(requireActivity()) && myProfile != null) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            customerProductsService.getCustomerShopsList(clientID, currentPage, searchShopName, myProfile.getUserID(), latitude, longitude).enqueue(new Callback<CustomerProductsResponse>() {
                @Override
                public void onResponse(@NotNull Call<CustomerProductsResponse> call, @NotNull Response<CustomerProductsResponse> response) {
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()) {
                        CustomerProductsResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase("success")) {
                                totalShopsCount = data.getCustomerShopsList().getShopTotalCount();
                                List<CustomerProductsShopDetailsModel> customerProductsList = data.getCustomerShopsList().getCustomerShopsList();
                                updateAdapter(customerProductsList);
                            } else {
                                showCloseDialog(data.getMsg());
                            }
                        } else {
                            showCloseDialog(getString(R.string.no_products_error));
                        }
                    } else {
                        showCloseDialog(response.message());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CustomerProductsResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    showCloseDialog(t.getMessage());
                }
            });
        }
    }

    private void updateAdapter(List<CustomerProductsShopDetailsModel> customerShopsList) {
        shopsList.addAll(customerShopsList);
        vendorShopsListAdapter.updateItems(customerShopsList);
        vendorShopsListAdapter.notifyDataSetChanged();
        if (shopsList.size() >= totalShopsCount) {
            isLastPage = true;
        } else {
            isLoading = false;
        }

        /*for(CustomerProductsShopDetailsModel shopDetailsModel : customerShopsList) {
            createMarker(shopDetailsModel.getLatitude(), shopDetailsModel.getLongitude(), shopDetailsModel.getShopName(), shopDetailsModel.getShopAddress());
        }*/
    }


    private void createMarker(double latitude, double longitude, String title, String snippet) {

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }

    private void showCloseDialog(String message) {
        showDialog(message, pObject -> {
            //requireActivity().onBackPressed();
        });
    }
}
