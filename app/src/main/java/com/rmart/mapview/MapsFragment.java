package com.rmart.mapview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.profile.OnMyProfileClickedListener;

import java.util.Objects;

public class MapsFragment extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMyLocationButtonClickListener {

    private static final String ARG_PARAM1 = "ARG_PARAM1";
    private static final String ARG_PARAM2 = "ARG_PARAM2";
    public static final String PROFILE = "profile";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SupportMapFragment mapFragment;
    private LocationManager locationManager;
    GoogleMap googleMap;
    private boolean isEditable;
    private Location currentLocation;
    private String isFrom;
    private OnCustomerHomeInteractionListener mListener;
    private OnLocationUpdateListner onLocationUpdateListner;
    private boolean isMapClickable = false;
    // private MyProfile myProfile;


    public void setLocationUpdateListner(OnLocationUpdateListner onLocationUpdateListner) {
        this.onLocationUpdateListner = onLocationUpdateListner;
    }
    public static MapsFragment newInstance(boolean isEditable, boolean isClickable, double latitude, double longitude) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isEditable);
        args.putBoolean(ARG_PARAM2, isClickable);
        args.putDouble("Latitude", latitude);
        args.putDouble("Longitude", longitude);
        fragment.setArguments(args);
        return fragment;
    }

    public void setLocation(Location location) {
        currentLocation = location;
        updateMapLocation();
    }

    private void updateMapLocation() {
        if (googleMap != null) {
            googleMap.clear();
            try {
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                googleMap.addMarker(markerOptions);
            } catch (Exception ex) {

            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEditable = getArguments().getBoolean(ARG_PARAM1);
            isMapClickable = getArguments().getBoolean(ARG_PARAM2);
            if(!isEditable) {
                double latitude = getArguments().getDouble("Latitude");
                double longitude = getArguments().getDouble("Longitude");
                currentLocation = new Location("");
                currentLocation.setLongitude(longitude);
                currentLocation.setLatitude(latitude);
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnCustomerHomeInteractionListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //AddressViewModel addressViewModel = new ViewModelProvider(Objects.requireNonNull(requireActivity())).get(AddressViewModel.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(requireActivity()));
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        if (mapFragment != null) {
            if (isEditable) {
                if (currentLocation == null) {
                    fetchLocation();
                } else {
                    if (currentLocation.getLatitude() == 0.0 && currentLocation.getLongitude() == 0.0) {
                        fetchLocation();
                    }
                }
            }
            mapFragment.getMapAsync(this);
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
                // latitude = currentLocation.getLatitude();
                // longitude = currentLocation.getLongitude();
                // Toast.makeText(getContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                //SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(this);
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
                currentLocation = locationGPS;
                updateMap();
            } else {
                MyLocation myLocation = new MyLocation(requireActivity());
                myLocation.getLocation(locationResult);
            }
            // updateLocationPoints();
        }
    }

    private void updateLocationPoints() {
        Objects.requireNonNull(requireActivity()).onBackPressed();
    }

    public MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {

        @Override
        public void gotLocation(Location location) {
            if (location != null) {
                currentLocation = location;
                updateMap();
            }
        }
    };

    private void updateMap() {
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title("New Marker");
        googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        googleMap.addMarker(marker);
        currentLocation = new Location("");
        currentLocation.setLatitude(latLng.latitude);
        currentLocation.setLongitude(latLng.longitude);
        mListener.getMapGeoCoordinates(latLng);
        onLocationUpdateListner.onLocationUpdate(latLng);
    }
    public void setUpdateIcon(LatLng latLng) {
        MarkerOptions marker = new MarkerOptions().position(latLng).title("New Marker");
        googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        googleMap.addMarker(marker);
        currentLocation = new Location("");
        currentLocation.setLatitude(latLng.latitude);
        currentLocation.setLongitude(latLng.longitude);
        mListener.getMapGeoCoordinates(latLng);
        onLocationUpdateListner.onLocationUpdate(latLng);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (currentLocation != null) {
            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            this.googleMap = map;
            googleMap.clear();
            googleMap.setMyLocationEnabled(true);
            if (isMapClickable) {
                googleMap.setOnMyLocationButtonClickListener(this);
                googleMap.setOnMapClickListener(this);
            }

            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            googleMap.addMarker(markerOptions);
            mListener.getMapGeoCoordinates(latLng);
            onLocationUpdateListner.onLocationUpdate(latLng);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        getLocation();
        return false;
    }
}