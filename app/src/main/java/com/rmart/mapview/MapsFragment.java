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
import com.rmart.profile.model.LocationPoints;
import com.rmart.profile.model.MyProfile;

import java.util.Objects;

public class MapsFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "ARG_PARAM1";
    private static final String ARG_PARAM2 = "ARG_PARAM2";
    public static final String PROFILE = "profile";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SupportMapFragment mapFragment;
    private LocationManager locationManager;
    // private double latitude = 0.0;
    // private double longitude = 0.0;
    GoogleMap googleMap;
    private boolean isEditable;
    private Location currentLocation;
    String isFrom;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            if (currentLocation != null) {
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                googleMap.addMarker(markerOptions);
                if(isEditable) {
                    editMyLocation(googleMap);
                }
            }
        }
    };
    // private MyProfile myProfile;

    private void editMyLocation(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMapClickListener(point -> {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title("New Marker");
            googleMap.clear();
            googleMap.addMarker(marker);
            currentLocation = new Location("");
            currentLocation.setLatitude(point.latitude);
            currentLocation.setLongitude(point.longitude);
            System.out.println(point.latitude+"---"+ point.longitude);
            // updateLocationPoints();
        });
    }

    public static MapsFragment newInstance(boolean isEditable, String isFrom) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isEditable);
        args.putString(ARG_PARAM2, isFrom);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEditable = getArguments().getBoolean(ARG_PARAM1);
            isFrom = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isEditable) {
            view.findViewById(R.id.update_location).setVisibility(View.VISIBLE);
            view.findViewById(R.id.update_location).setOnClickListener(view1 -> updateLocationPoints());
        } else {
            view.findViewById(R.id.update_location).setVisibility(View.GONE);
        }
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            if (isFrom.equals(PROFILE) ) {
                // myProfile = MyProfile.getInstance();

                if(MyProfile.getInstance().getMyLocation()!= null && MyProfile.getInstance().getMyLocations().get(0).getMyLocation()!= null) {
                    LocationPoints location = MyProfile.getInstance().getMyLocation().getMyLocation();
                    currentLocation = new Location("");
                    currentLocation.setLongitude(location.getLongitude());
                    currentLocation.setLatitude(location.getLatitude());
                } else {
                    fetchLocation();
                }
            }
            mapFragment.getMapAsync(callback);
        }


    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
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
                // latitude = locationGPS.getLatitude();
                // longitude = locationGPS.getLongitude();
                updateMap();
            } else {
                MyLocation myLocation = new MyLocation(requireActivity());
                myLocation.getLocation(locationResult);
            }
            // updateLocationPoints();
        }
    }

    private void updateLocationPoints() {
        LocationPoints myLocation = new LocationPoints(currentLocation.getLatitude(), currentLocation.getLongitude());
        if (MyProfile.getInstance().getMyLocations() == null || MyProfile.getInstance().getMyLocations().size() <= 0) {
            com.rmart.profile.model.MyLocation location = new com.rmart.profile.model.MyLocation();
            location.setMyLocation(myLocation);
            MyProfile.getInstance().getMyLocations().add(location);
        } else {
            MyProfile.getInstance().getMyLocations().get(0).setMyLocation(myLocation);
        }
        Objects.requireNonNull(getActivity()).onBackPressed();
       /* if (isFrom.equals(PROFILE) ) {
            LocationPoints myLocation = new LocationPoints(currentLocation.getLatitude(), currentLocation.getLongitude());
            if (MyProfile.getInstance().getMyLocations().size() <0) {
                com.rmart.profile.model.MyLocation location = new com.rmart.profile.model.MyLocation();
                location.setMyLocation(myLocation);
                MyProfile.getInstance().getMyLocations().add(location);
            } else {
                MyProfile.getInstance().getMyLocations().get(0).setMyLocation(myLocation);
            }
            Objects.requireNonNull(getActivity()).onBackPressed();
        }*/
    }

    public MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {

        @Override
        public void gotLocation(Location location) {
            if (location != null) {
                currentLocation = location;
                Log.d("locationGPS", "location longitude"+location.getLongitude());
                Log.d("locationGPS", "location latitude"+location.getLatitude());
                updateMap();
            }
        }
    };

    private void updateMap() {
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}