package com.rmart.profile.views;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.views.CustomerHomeActivity;
import com.rmart.databinding.FragmentAddAddressBinding;
import com.rmart.mapview.MapsFragment;
import com.rmart.profile.adapters.CustomAdapter;
import com.rmart.profile.model.BusinessType;
import com.rmart.profile.model.CreditDetails;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.model.ShopType;
import com.rmart.profile.model.ShopTypeResponce;
import com.rmart.profile.repositories.ProfileRepository;
import com.rmart.profile.viewmodel.EditAdreesViewModel;
import com.rmart.utilits.BaseResponse;
import com.rmart.utilits.InputFilterIntMinMax;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.custom_views.CustomTimePicker;
import com.rmart.utilits.pojos.AddressListResponse;
import com.rmart.utilits.pojos.AddressResponse;
import com.rmart.utilits.services.ProfileService;
import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.rmart.profile.model.MyProfile.PREF_NAME;

public class EditAddressFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String AADHAR_FRONT_IMAGE = "aadhar_front_image";
    private static final String AADHAR_BACK_IMAGE = "aadhar_back_image";
    private static final String PANCARD_IMAGE = "pancard_image";
    private static final String SHOP_IMAGE = "shop_image";
    FragmentAddAddressBinding binding;
    private AddressResponse myAddress;
    private boolean SellingToConsumer=false;
    private boolean CreditOption=false;
    private boolean isAddNewAddress,isImageLoading;
    private int selectedPhotoType = -1;
    private double latitude;
    private double longitude;
    private ShopTypeResponce shopTypeResponce;
    private CreditDetails creditDetails;
    private MapsFragment mapsFragment;
    private EditAdreesViewModel editAdreesViewModel;
    public EditAddressFragment() {
    }

    public static EditAddressFragment newInstance(boolean isAddNewAddress, AddressResponse myAddress) {
        EditAddressFragment fragment = new EditAddressFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isAddNewAddress);
        args.putSerializable(ARG_PARAM2, myAddress);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myAddress = (AddressResponse) getArguments().getSerializable(ARG_PARAM2);
            isAddNewAddress = getArguments().getBoolean(ARG_PARAM1);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoggerInfo.printLog("Fragment", "EditAddressFragment");


        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_add_address, container, false);
        editAdreesViewModel = ViewModelProviders.of(getActivity()).get(EditAdreesViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setMyAddress(editAdreesViewModel);
        editAdreesViewModel.addressResponseMutableLiveData.setValue(myAddress);
        creditDetails=new CreditDetails();
        textWatchers();
        if(MyProfile.getInstance(getActivity()).getRoleID()==MyProfile.RETAILER) {
            binding.radioyes.setChecked(MyProfile.getInstance(getActivity()).getWholeselar());
            binding.radiono.setChecked(!MyProfile.getInstance(getActivity()).getWholeselar());
            boolean b = MyProfile.getInstance(getActivity()).getCredit_option();
            binding.switchon.setChecked(b);
            binding.radioGroup.setOnCheckedChangeListener((rg, checkedId) -> {


                switch (checkedId) {

                    case R.id.radioyes:
                        SellingToConsumer = true;
                        creditDetails.setSellingConsumer(true);
                        break;
                    case R.id.radiono:
                        SellingToConsumer = false;
                        creditDetails.setSellingConsumer(false);
                        break;
                }

            });
            if (binding.switchon.isChecked()) {
                CreditOption = true;
                creditDetails.setCreditoption(true);


            } else {
                CreditOption = false;
                creditDetails.setCreditoption(false);
            }
        }
        return binding.getRoot();
    }
    private void textWatchers() {
        binding.shopName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorShopNameStringMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.shopNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorShopNoStringMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.shopAct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorShopActStringMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.gstNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorgstNoStringMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.deliveryRadius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorgstDeliveryRadiusMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.deliveryCharges.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorgstDeliveryChargesMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.minimumOrder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorgstMinimumOrderMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.openTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorgstOpenTimeMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.closeTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorgstCloseTimeMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.deliveryDaysBeforeTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorgstBeforeCloseMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.deliveryDaysAfterTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorgstsfterCloseMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.tvAadharNumberNoField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorAadharNoMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.panNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorPanNumberMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.streetAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorStreetAdressMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorCityMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.district.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorDistrictMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorStateMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.pinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorPinCodeMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.bankAccNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorBanckAccountStringMutableLiveData.setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.bankIfsc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editAdreesViewModel.errorBanckIFSCStringMutableLiveData .setValue(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.deliveryRadius.setFilters(new InputFilter[]{new InputFilterIntMinMax(1, 999)});
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (myAddress != null) {
            latitude = myAddress.getLatitude();
            longitude = myAddress.getLongitude();
        }
        binding.scrollView.requestDisallowInterceptTouchEvent(true);

        if (myAddress != null) {
            if (myAddress.getId() == -1) {
                mapsFragment = MapsFragment.newInstance(true, true, 0.0, 0.0);
            } else {
                mapsFragment = MapsFragment.newInstance(false, true, latitude, longitude);
            }
        } else {
            myAddress = new AddressResponse();
            myAddress.setId(-1);
            mapsFragment = MapsFragment.newInstance(true, true, 0.0, 0.0);
        }
        ProfileRepository.getVenderProducts().observeForever(shopTypeResponce -> {

            if(shopTypeResponce.getStatus()==200){
                this.shopTypeResponce=shopTypeResponce;
                BusinessType businessType = new BusinessType();
                businessType.name="Select Business Type";
                shopTypeResponce.results.businessTypes.add(0,businessType);

                ShopType shopType = new ShopType();
                shopType.shop_type_name="Select Shop Type";
                shopTypeResponce.results.shopTypes.add(0,shopType);
                try {
                    CustomAdapter adaptercity = new CustomAdapter(getActivity(), R.layout.spinner_item, shopTypeResponce.results.businessTypes);
                    binding.businessType.setAdapter(adaptercity);
                    for (BusinessType businessType1: shopTypeResponce.results.businessTypes) {
                        if(businessType1.name.equalsIgnoreCase(myAddress.getBusinessType())) {
                            int i = shopTypeResponce.results.businessTypes.indexOf(businessType1);
                            binding.businessType.setSelection(i);

                        }
                    }

                } catch (Exception e){

                }
                try {
                    CustomAdapter adaptercity = new CustomAdapter(getActivity(), R.layout.spinner_item, shopTypeResponce.results.shopTypes);
                    binding.shopType.setAdapter(adaptercity);

                    for (ShopType businessType1: shopTypeResponce.results.shopTypes) {
                        if(businessType1.shop_type_name.equalsIgnoreCase(myAddress.getShopTypeName())) {
                            int i = shopTypeResponce.results.shopTypes.indexOf(businessType1);
                            binding.shopType.setSelection(i);
                        }
                    }
                } catch (Exception e){

                }

            }

        });
        fragmentTransaction.replace(R.id.map_layout_field, mapsFragment, MapsFragment.class.getName());
        fragmentTransaction.commit();
        mapsFragment.setLocationUpdateListner(latLng -> setAdress( latLng));
        binding.addAddress.setOnClickListener(this);
        binding.shopImageLayoutField.setOnClickListener(this);
        binding.panCardLayoutField.setOnClickListener(this);
        binding.aadharBackLayoutField.setOnClickListener(this);
        binding.aadharFrontLayoutField.setOnClickListener(this);
        binding.openTime.setOnClickListener(this);
        binding.closeTime.setOnClickListener(this);

        updateUI();
        try {
            if (TextUtils.isEmpty(editAdreesViewModel.shopImageUrl.getValue())) {

            }
        } catch (Exception e){

        }
        binding.shopType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.shopTypeLayout.setBackground(getResources().getDrawable(R.drawable.grey_rounded_borders_bg));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.businessType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.shopBusinessLayout.setBackground(getResources().getDrawable(R.drawable.grey_rounded_borders_bg));
                if(i==2)
                {
                    binding.linOne.setVisibility(View.VISIBLE);

                }else{
                    binding.linOne.setVisibility(View.GONE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchAdress(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }
    private void setAdress(LatLng latLng){
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {

            //addresses = geocoder.getFromLocation(latitude, langitude, 5); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String line1 = "",lines2 = "";
            try {


                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);


                line1 = addresses.get(0).getAddressLine(0);
                lines2 = addresses.get(0).getFeatureName();

            }catch (Exception e ){
                Toast.makeText(getActivity(),"Point6",Toast.LENGTH_LONG).show();
            }
            try {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                String city = addresses.get(0).getLocality();




                binding.city.setText(city);
                binding.streetAddress.setText(address);
                binding.pinCode.setText(addresses.get(0).getPostalCode());
                binding.state.setText(addresses.get(0).getAdminArea());

            } catch (Exception e){
                Toast.makeText(getActivity(),"POint 1",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

        }



    }
    private void searchAdress(String text){
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            if(mapsFragment!=null) {
                addresses = geocoder.getFromLocationName(text, 5);
                mapsFragment.setUpdateIcon(new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude()));
            }
        }
        catch (Exception e ){
            Toast.makeText(getActivity(),"Address Can't Search",Toast.LENGTH_LONG).show();
        }




    }
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(myAddress.getId() == -1 ? getString(R.string.add_address) : getString(R.string.update_address));
    }
    private void updateUI() {
        if (myAddress.getId() != -1) {
            binding.shopName.setText(myAddress.getShopName());
            binding.panNumber.setText(myAddress.getPan_no());
            binding.gstNumber.setText(myAddress.getGstInNo());
            binding.shopAct.setText(myAddress.getShopACT());
            binding.streetAddress.setText(myAddress.getAddress());
            binding.shopNo.setText(myAddress.getStore_number());
            binding.state.setText(myAddress.getState());
            binding.pinCode.setText(myAddress.getPinCode());
            binding.deliveryRadius.setText(myAddress.getDeliveryRadius());
            binding.city.setText(myAddress.getCity());
            binding.tvAadharNumberNoField.setText(myAddress.getAadhaarCardNo());
            binding.deliveryCharges.setText(myAddress.getDeliveryCharges());
            binding.minimumOrder.setText(myAddress.getMinimumOrder());
            binding.openTime.setText(myAddress.getOpeningTime());
            binding.closeTime.setText(myAddress.getClosingTime());
            binding.deliveryDaysAfterTime.setText(myAddress.getDeliveryDaysAfterTime());
            binding.deliveryDaysBeforeTime.setText(myAddress.getDeliveryDaysBeforeTime());
            binding.bankAccNo.setText(myAddress.getBankAccNo());
            binding.bankIfsc.setText(myAddress.getIfscCode());
            binding.bankName.setText(myAddress.getBankName());
            binding.bankBranch.setText(myAddress.getBranchName());


            Location location = new Location("");
            location.setLatitude(myAddress.getLatitude());
            location.setLongitude(myAddress.getLongitude());
            mapsFragment.setLocation(location);
        } else {
        }
        if (BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.RETAILER_ID)) {
            binding.retailerView.setVisibility(View.VISIBLE);
            binding.searchView.setVisibility(View.GONE);
            if (myAddress.getId() != -1) {
                binding.streetAddress.setText(myAddress.getAddress());
            }
        } else {
            binding.retailerView.setVisibility(View.GONE);
            binding.searchView.setVisibility(View.VISIBLE);
        }
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(Utils.isValidPinCode(query)){

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        editAdreesViewModel.loadImage(binding.shopImageLayoutField, myAddress.getShopImage(), R.id.iv_shop_image_field, R.id.selectedgreeting, R.drawable.ic_shop, url -> editAdreesViewModel.shopImageUrl.setValue(url));
        editAdreesViewModel.loadImage(binding.aadharFrontLayoutField, myAddress.getAadharFrontImage(), R.id.adharcard_front_image_field, R.id.adharcard_loader, R.drawable.ic_aadhar_front, url -> editAdreesViewModel.aadharFrontImageUrl.setValue(url));
        editAdreesViewModel.loadImage(binding.aadharBackLayoutField, myAddress.getAadharBackImage(), R.id.adharcard_back_image_field, R.id.adharcard_back_loader, R.drawable.ic_aadhar_back, url -> editAdreesViewModel.aadharBackImageUrl.setValue(url));
        editAdreesViewModel.loadImage(binding.panCardLayoutField, myAddress.getPanCardImage(), R.id.pan_card_image_field, R.id.pan_card_loader, R.drawable.ic_pan, url -> editAdreesViewModel.aadharBackImageUrl.setValue(url));
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add_address:
                addAddressSelected();
                break;
            case R.id.aadhar_front_layout_field:
                selectedPhotoType = 0;
                isImageLoading=true;
                capturePhotoSelected();
                break;
            case R.id.aadhar_back_layout_field:
                selectedPhotoType = 1;
                isImageLoading=true;
                capturePhotoSelected();
                break;
            case R.id.pan_card_layout_field:
                selectedPhotoType = 2;
                isImageLoading=true;
                capturePhotoSelected();
                break;
            case R.id.shop_image_layout_field:
                selectedPhotoType = 3;
                isImageLoading=true;
                capturePhotoSelected();
                break;
            case R.id.open_time:
            case R.id.close_time:
                new CustomTimePicker((EditText) view, getActivity());
                break;
            default:
                break;
        }
    }
    private void capturePhotoSelected() {
        CropImage.activity().start(requireActivity(), this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null) {
                if (resultCode == RESULT_OK) {
                    try {
                        Uri profileImageUri = result.getUri();
                        if (profileImageUri != null) {
                            showConfirmDialog(profileImageUri);

                        }
                    } catch (Exception ex) {
                        showDialog("", ex.getMessage());
                    }

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    LoggerInfo.errorLog("cropping error", error.getMessage());
                }
            }
        }
    }
    private void showConfirmDialog(Uri profileImageUri) {
        showConfirmationDialog(getString(R.string.image_saving_confirmation_alert), pObject -> {
            progressDialog.show();
            if (selectedPhotoType == 0) {
                editAdreesViewModel.aadharFrontImageUrl.setValue(profileImageUri.getPath());
                updateImageDetails(profileImageUri, AADHAR_FRONT_IMAGE);
            } else if (selectedPhotoType == 1) {
                editAdreesViewModel.aadharBackImageUrl.setValue(profileImageUri.getPath());
                updateImageDetails(profileImageUri, AADHAR_BACK_IMAGE);
            } else if (selectedPhotoType == 2) {
                editAdreesViewModel.panCardImageUrl.setValue(profileImageUri.getPath());
                updateImageDetails(profileImageUri, PANCARD_IMAGE);
            } else if (selectedPhotoType == 3) {
                editAdreesViewModel.shopImageUrl.setValue(profileImageUri.getPath());
                updateImageDetails(profileImageUri, SHOP_IMAGE);
            } else {
                progressDialog.dismiss();
            }
        });
    }
    private void updateImageDetails(Uri photoImagePath, String imageType) {
        if (Utils.isNetworkConnected(requireActivity())) {
            String clientID = "2";
            try {
                if (photoImagePath != null) {
                    InputStream imageStream = requireActivity().getContentResolver().openInputStream(photoImagePath);
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                    ProfileService profileService = RetrofitClientInstance.getRetrofitInstance().create(ProfileService.class);
                    Call<BaseResponse> call = profileService.uploadPhotoImage(clientID, MyProfile.getInstance(getActivity()).getUserID(),
                            imageType, getEncodedImage(bitmap));
                    call.enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                BaseResponse body = response.body();
                                Log.d("AddressINFO",response.message());
                                if (body != null) {
                                    showDialog(body.getMsg());
                                    updateImageUI(photoImagePath, imageType);
                                    isImageLoading=false;
                                } else {
                                    showDialog(getString(R.string.image_upload_error));
                                }
                            } else {
                                showDialog(response.message());
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<BaseResponse> call, @NotNull Throwable t) {
                            progressDialog.dismiss();
                            LoggerInfo.errorLog("Image Upload onFailure", t.getMessage());
                            showDialog(t.getMessage());
                        }
                    });
                }
            } catch (Exception ex) {
                progressDialog.dismiss();
                showDialog(ex.getMessage());
                LoggerInfo.errorLog("Image Upload exception", ex.getMessage());
            }
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
            progressDialog.dismiss();
        }
    }
    private void updateImageUI(Uri profileImageUri, String imageType) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), profileImageUri);
            if (bitmap != null) {
                if (imageType.equalsIgnoreCase(SHOP_IMAGE)) {
                    binding.ivShopImageField.setImageBitmap(Utils.getCompressBitmapImage(bitmap));
                    binding.shopImageLayoutField.setBackground(getResources().getDrawable(R.drawable.image_border));

                } else if (imageType.equalsIgnoreCase(AADHAR_FRONT_IMAGE)) {
                    binding.adharcardFrontImageField.setImageBitmap(Utils.getCompressBitmapImage(bitmap));
                    binding.aadharFrontLayoutField.setBackground(getResources().getDrawable(R.drawable.image_border));
                } else if (imageType.equalsIgnoreCase(AADHAR_BACK_IMAGE)) {
                    binding.adharcardBackImageField.setImageBitmap(Utils.getCompressBitmapImage(bitmap));
                    binding.aadharBackLayoutField.setBackground(getResources().getDrawable(R.drawable.image_border));

                } else {
                    binding.panCardImageField.setImageBitmap(Utils.getCompressBitmapImage(bitmap));
                    binding.panCardLayoutField.setBackground(getResources().getDrawable(R.drawable.image_border));
                }
            }
        } catch (Exception ex) {

        } finally {

        }
    }
    private String getEncodedImage(Bitmap bitmap) {
        try {
            if (bitmap != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] b = byteArrayOutputStream.toByteArray();
                return Base64.encodeToString(b, Base64.DEFAULT);
            }
        } catch (Exception ex) {
            LoggerInfo.errorLog("encode image exception", ex.getMessage());
        }
        return "";
    }
    private void addAddressSelected() {
        boolean validation=true;
        String aadharNo="";
        if ( binding.retailerView.getVisibility() == View.VISIBLE) {

            boolean isAdharValid =true;
            boolean isAdharNoValid =true;
            boolean isAdharFrontImageValid =true;
            boolean isAdharBackImageValid =true;
            boolean isPanValid =true;
            boolean isShopActValid =true;

            String shopName = Objects.requireNonNull(binding.shopName.getText()).toString().trim();
            if (TextUtils.isEmpty(shopName)) {
                editAdreesViewModel.errorShopNameStringMutableLiveData.setValue(getString(R.string.shop_name_required));
                //showDialog(getString(R.string.shop_name_required));
                validation =false;
            }


            if (binding.businessType.getSelectedItemPosition()==0) {
                //editAdreesViewModel.errorBanckIFSCStringMutableLiveData.setValue(getString(R.string.bank_ifsc_required));
                //showDialog(getString(R.string.shop_name_required));
                binding.shopBusinessLayout.setBackground(getResources().getDrawable(R.drawable.error_image_border));

                validation =false;
            }
            if (binding.shopType.getSelectedItemPosition()==0) {
                //editAdreesViewModel.errorBanckIFSCStringMutableLiveData.setValue(getString(R.string.bank_ifsc_required));
                //showDialog(getString(R.string.shop_name_required));
                binding.shopTypeLayout.setBackground(getResources().getDrawable(R.drawable.error_image_border));

                validation =false;
            }





            String shopNO = Objects.requireNonNull(binding.shopNo.getText()).toString().trim();
            if (TextUtils.isEmpty(shopNO)) {
                //showDialog(getString(R.string.shop_no_required));
                editAdreesViewModel.errorShopNoStringMutableLiveData.setValue(getString(R.string.shop_no_required));
                validation =false;
            }

            String deliveryRadius = Objects.requireNonNull(binding.deliveryRadius.getText()).toString().trim();
            if (TextUtils.isEmpty(deliveryRadius)) {
                //showDialog(getString(R.string.delivery_radius_required));
                editAdreesViewModel.errorgstDeliveryRadiusMutableLiveData.setValue(getString(R.string.delivery_radius_required));

                validation =false;
            } else if (deliveryRadius.equals("0")) {
                //showDialog(getString(R.string.delivery_radius_required));
                editAdreesViewModel.errorgstDeliveryRadiusMutableLiveData.setValue(getString(R.string.delivery_radius_required));

                validation =false;
            }

            String deliveryCharges = Objects.requireNonNull(binding.deliveryCharges.getText()).toString().trim();
            if (TextUtils.isEmpty(deliveryCharges)) {
                // showDialog(getString(R.string.delivery_charges_required));
                editAdreesViewModel.errorgstDeliveryChargesMutableLiveData.setValue(getString(R.string.delivery_charges_required));

                validation =false;
            }

            String minimumOrder = Objects.requireNonNull(binding.minimumOrder.getText()).toString().trim();
            if (TextUtils.isEmpty(minimumOrder)) {
                editAdreesViewModel.errorgstMinimumOrderMutableLiveData.setValue(getString(R.string.minimum_charges_required));
                validation =false;
            }

            String openingTime = Objects.requireNonNull(binding.openTime.getText()).toString().trim();
            if (TextUtils.isEmpty(openingTime)) {
                // showDialog(getString(R.string.opening_time_required));
                editAdreesViewModel.errorgstOpenTimeMutableLiveData.setValue(getString(R.string.opening_time_required));
                validation =false;
            }

            String closingTime = Objects.requireNonNull(binding.closeTime.getText()).toString().trim();
            if (TextUtils.isEmpty(closingTime)) {
//                showDialog(getString(R.string.closing_time_required));
                editAdreesViewModel.errorgstCloseTimeMutableLiveData.setValue(getString(R.string.closing_time_required));

                validation =false;
            }

            String deliveryDaysBeforeTime = Objects.requireNonNull(binding.deliveryDaysBeforeTime.getText()).toString().trim();
            if (TextUtils.isEmpty(deliveryDaysBeforeTime)) {
//                showDialog(getString(R.string.delivery_days_before_time));
                editAdreesViewModel.errorgstBeforeCloseMutableLiveData.setValue(getString(R.string.delivery_days_before_time));

                validation =false;
            }


            String deliveryDaysAfterTime = Objects.requireNonNull(binding.deliveryDaysAfterTime.getText()).toString().trim();
            if (TextUtils.isEmpty(deliveryDaysAfterTime)) {
//                showDialog(getString(R.string.delivery_days_after_time));
                editAdreesViewModel.errorgstsfterCloseMutableLiveData.setValue(getString(R.string.delivery_days_after_time));

                validation =false;
            }
            String streetAddress = Objects.requireNonNull(binding.streetAddress.getText()).toString().trim();
            if (TextUtils.isEmpty(streetAddress)) {
//                showDialog(getString(R.string.street_address_required));
                editAdreesViewModel.errorStreetAdressMutableLiveData.setValue(getString(R.string.street_address_required));

                validation =false;
            }

            String city = Objects.requireNonNull(binding.city.getText()).toString().trim();
            if (TextUtils.isEmpty(city)) {
//                showDialog(getString(R.string.city_required));
                editAdreesViewModel.errorCityMutableLiveData.setValue(getString(R.string.city_required));

                validation =false;
            }
        } else {
            String streetAddress = Objects.requireNonNull(binding.streetAddress.getText()).toString().trim();
            if (TextUtils.isEmpty(streetAddress)) {
//                showDialog(getString(R.string.street_address_required));
                editAdreesViewModel.errorStreetAdressMutableLiveData.setValue(getString(R.string.street_address_required));

                validation =false;
            }

            String city = Objects.requireNonNull(binding.city.getText()).toString().trim();
            if (TextUtils.isEmpty(city)) {
//                showDialog(getString(R.string.city_required));
                editAdreesViewModel.errorCityMutableLiveData.setValue(getString(R.string.city_required));

                validation =false;
            }

            String state = Objects.requireNonNull(binding.state.getText()).toString().trim();
            if (TextUtils.isEmpty(state)) {
//                showDialog(getString(R.string.state_address_required));
                editAdreesViewModel.errorStateMutableLiveData.setValue(getString(R.string.state_address_required));

                validation =false;
            }
        }

        if(binding.switchon.isChecked()){
            CreditOption=true;
            creditDetails.setCreditoption(true);


        } else {
            CreditOption=false;
            creditDetails.setCreditoption(false);
        }
        String zipCode = Objects.requireNonNull(binding.pinCode.getText()).toString().trim();
        if (TextUtils.isEmpty(zipCode)) {
//            showDialog(getString(R.string.pin_code_required));
            editAdreesViewModel.errorPinCodeMutableLiveData.setValue(getString(R.string.pin_code_required));

            validation = false;
        } else  if(zipCode.length() != 6 || !Utils.isValidPinCode(zipCode)) {
//            showDialog(getString(R.string.invalid_pin_code));
            editAdreesViewModel.errorPinCodeMutableLiveData.setValue(getString(R.string.invalid_pin_code));

            validation =false;
        }
        if(!Utils.isNetworkConnected(requireActivity())) {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
            validation =false;
        }

        if(validation) {
            progressDialog.show();
            if (myAddress.getId() == -1) {
                getAddressData();


                ProfileService profileService = RetrofitClientInstance.getRetrofitInstance().create(ProfileService.class);
                profileService.addAddress(myAddress.getShopACT(), myAddress.getMinimumOrder(), myAddress.getShopName(), myAddress.getPan_no(), myAddress.getGstInNo(), myAddress.getStore_number(),
                        myAddress.getAddress(), myAddress.getCity(), myAddress.getState(), myAddress.getPinCode(), myAddress.getLatitude(),
                        myAddress.getLongitude(), MyProfile.getInstance(getActivity()).getUserID(), MyProfile.getInstance(getActivity()).getRoleID(),
                        myAddress.getDeliveryRadius(), Utils.CLIENT_ID, aadharNo, myAddress.getDeliveryCharges(),
                        myAddress.getOpeningTime(), myAddress.getClosingTime(), myAddress.getDeliveryDaysAfterTime(), myAddress.getDeliveryDaysBeforeTime(),myAddress.getBusinessType(),myAddress.getShopTypeId()+"",myAddress.getBankName(),myAddress.getIfscCode(),myAddress.getBranchName(),myAddress.getBankAccNo(),CreditOption,SellingToConsumer).enqueue(new Callback<AddressListResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AddressListResponse> call, @NotNull Response<AddressListResponse> response) {
                        if (response.isSuccessful()) {
                            AddressListResponse data = response.body();
                            if (data != null) {
                                if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                    Log.d("ADDRESS123",data.getMsg());
                                    saveAddress(data);


                                    showDialog("", data.getMsg());
                                    Intent in = new Intent(getActivity(), CustomerHomeActivity.class);
                                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                    startActivity(in);



                                } else {
                                    myAddress = null;
                                    showDialog("", data.getMsg());
                                }
                            } else {
                                showDialog(getString(R.string.no_information_available));
                            }
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NotNull Call<AddressListResponse> call, @NotNull Throwable t) {
                        if (t instanceof SocketTimeoutException) {
                            showDialog("", getString(R.string.network_slow));
                        } else {
                            showDialog("", t.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                });
            } else {
                getAddressData();
                ProfileService profileService = RetrofitClientInstance.getRetrofitInstance().create(ProfileService.class);
                profileService.updateAddress(myAddress.getShopACT(), myAddress.getMinimumOrder(), myAddress.getShopName(), myAddress.getPan_no(), myAddress.getGstInNo(), myAddress.getStore_number(),
                        myAddress.getAddress(), myAddress.getCity(), myAddress.getState(), myAddress.getPinCode(), myAddress.getLatitude(),
                        myAddress.getLongitude(), MyProfile.getInstance(getActivity()).getUserID(), MyProfile.getInstance(getActivity()).getRoleID(),
                        myAddress.getDeliveryRadius(), Utils.CLIENT_ID, myAddress.getId(), aadharNo, myAddress.getDeliveryCharges(),
                        myAddress.getOpeningTime(), myAddress.getClosingTime(), myAddress.getDeliveryDaysAfterTime(), myAddress.getDeliveryDaysBeforeTime(), myAddress.getId().toString(), myAddress.getBusinessType(),myAddress.getShopTypeId()+"",myAddress.getBankName(),myAddress.getIfscCode(),myAddress.getBranchName(),myAddress.getBankAccNo(),CreditOption,SellingToConsumer).enqueue(new Callback<AddressListResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AddressListResponse> call, @NotNull Response<AddressListResponse> response) {
                        if (response.isSuccessful()) {
                            AddressListResponse data = response.body();
                            if (data != null) {
                                if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                    saveAddress(data);
                                    showDialog(data.getMsg(), pObject -> {

                                        Objects.requireNonNull(requireActivity()).onBackPressed();
                                    });
                                } else {
                                    showDialog(data.getMsg());
                                }
                            } else {
                                showDialog(getString(R.string.no_information_available));
                            }
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NotNull Call<AddressListResponse> call, @NotNull Throwable t) {
                        if (t instanceof SocketTimeoutException) {
                            showDialog("", getString(R.string.network_slow));
                        } else {
                            showDialog("", t.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        } else
        {
            return;
        }
    }

    //need to refactore or Recdde
    private void saveAddress(AddressListResponse data) {
        MyProfile profile=  MyProfile.getInstance(getActivity());
        profile.setAddressResponses(data.getResponse(),getContext());
        profile.setCredit_option(CreditOption);
        profile.setWholeselar(SellingToConsumer);
        Gson gson = new Gson();
        String str = gson.toJson(profile);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(MyProfile.CUSTOMER, str);
        editor.apply();
        editor.commit();
    }



    private void getAddressData() {
        myAddress.setLongitude(longitude);
        myAddress.setLatitude(latitude);
        myAddress.setShopName(Objects.requireNonNull(binding.shopName.getText()).toString());
        myAddress.setPan_no(Objects.requireNonNull(binding.panNumber.getText()).toString());
        myAddress.setGstInNo(Objects.requireNonNull(binding.gstNumber.getText()).toString());
        myAddress.setShopACT(Objects.requireNonNull(binding.shopAct.getText()).toString());
        myAddress.setStore_number(Objects.requireNonNull(binding.shopNo.getText()).toString());
        myAddress.setAddress(Objects.requireNonNull(binding.streetAddress.getText()).toString());
        myAddress.setCity(Objects.requireNonNull(binding.city.getText()).toString());
        myAddress.setState(Objects.requireNonNull(binding.state.getText()).toString());
        myAddress.setDeliveryRadius(Objects.requireNonNull(binding.deliveryRadius.getText()).toString());
        myAddress.setPinCode(Objects.requireNonNull(binding.pinCode.getText()).toString());

        myAddress.setDeliveryCharges(Objects.requireNonNull(binding.deliveryCharges.getText()).toString());
        myAddress.setMinimumOrder(Objects.requireNonNull(binding.minimumOrder.getText()).toString());

        myAddress.setOpeningTime(Objects.requireNonNull(binding.openTime.getText()).toString());
        myAddress.setClosingTime(Objects.requireNonNull(binding.closeTime.getText()).toString());

        myAddress.setDeliveryDaysAfterTime(Objects.requireNonNull(binding.deliveryDaysAfterTime.getText()).toString());
        myAddress.setDeliveryDaysBeforeTime(Objects.requireNonNull(binding.deliveryDaysBeforeTime.getText()).toString());
        myAddress.setBankAccNo(Objects.requireNonNull(binding.bankAccNo.getText().toString()));
        myAddress.setIfscCode(Objects.requireNonNull(binding.bankIfsc.getText().toString()));
        myAddress.setBranchName(Objects.requireNonNull(binding.bankBranch.getText().toString()));
        myAddress.setBankName(Objects.requireNonNull(binding.bankName.getText().toString()));
        if(this.shopTypeResponce!=null ) {
            myAddress.setShopTypeId(shopTypeResponce.results.shopTypes.get((binding.shopType.getSelectedItemPosition())).shop_type_id);
        }
        if(this.shopTypeResponce!=null ) {
            myAddress.setBusinessType(shopTypeResponce.results.businessTypes.get((binding.businessType.getSelectedItemPosition())).name);
        }

    }
    public void updateLocationDetails(LatLng latLng) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;
    }
}