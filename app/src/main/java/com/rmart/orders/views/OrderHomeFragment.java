package com.rmart.orders.views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.rmart.R;
import com.rmart.orders.adapters.OrdersHomeAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.Permisions;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.AddressResponse;
import com.rmart.utilits.pojos.orders.OrderStateListResponse;
import com.rmart.utilits.pojos.orders.StateOfOrders;
import com.rmart.utilits.services.OrderService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rmart.utilits.Utils.OPEN_ORDER_STATUS;

public class OrderHomeFragment extends BaseOrderFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<StateOfOrders> orderStatus;
    private HashMap<String, Integer> mapOrderStatus = new HashMap<>();
    private RecyclerView recyclerView;
    private AppCompatTextView openOrderCount;
    TextView shopname,address;
    ImageView ivShareField,shopiamge,loader;
    RelativeLayout shop_details;
    public OrderHomeFragment() {
        // Required empty public constructor
    }


    public static OrderHomeFragment newInstance(String param1, String param2) {
        OrderHomeFragment fragment = new OrderHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.all_orders));
        getOrderStatusFromServer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // myOrdersViewModel = new ViewModelProvider(requireActivity()).get(MyOrdersViewModel.class);
        return inflater.inflate(R.layout.fragment_order_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            ((AppCompatTextView) view.findViewById(R.id.shop_name)).setText(String.format(getString(R.string.shop_name), MyProfile.getInstance().getAddressResponses().get(0).getShopName()));
            view.findViewById(R.id.accepted_orders).setOnClickListener(this);
            openOrderCount = view.findViewById(R.id.open_order_count);
            recyclerView = view.findViewById(R.id.other_order_names);
            shopiamge = view.findViewById(R.id.shopiamge);
            loader = view.findViewById(R.id.loader);
            shopname = view.findViewById(R.id.shopname);
            address = view.findViewById(R.id.address);
            shop_details = view.findViewById(R.id.shop_details);

            ivShareField = view.findViewById(R.id.iv_share_field);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getOrderStatusFromServer() {
        if(!Utils.isNetworkConnected(requireActivity())) {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
            return;
        }
        progressDialog.show();
        OrderService orderService = RetrofitClientInstance.getRetrofitInstance().create(OrderService.class);
        MyProfile myProfile = MyProfile.getInstance();
        if(myProfile != null) {
            String userId = myProfile.getUserID();
            orderService.getOrderHome(userId).enqueue(new Callback<OrderStateListResponse>() {
                @Override
                public void onResponse(@NotNull Call<OrderStateListResponse> call, @NotNull Response<OrderStateListResponse> response) {
                    if (response.isSuccessful()) {
                        OrderStateListResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                                orderStatus = data.getOrderStates();
                                for (int i = 0; i < orderStatus.size(); i++) {
                                    orderStatus.get(i).updateBackgroundColor();
                                    mapOrderStatus.put(orderStatus.get(i).getStatus(), i);
                                }
                                updateUI();
                            } else {
                                showDialog(data.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }

                    } else {
                        showDialog(response.message());
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<OrderStateListResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void updateUI() {
//        int position = mapOrderStatus.get(OPEN_ORDER_STATUS);
//        StateOfOrders data = orderStatus.get(position);
//        openOrderCount.setText(data.getCount());
//        ArrayList<StateOfOrders> list = new ArrayList<>();//(ArrayList<StateOfOrders>) orderStatus.clone();
//        list.add(orderStatus.get( mapOrderStatus.get(ACCEPTED_ORDER_STATUS)));
//        list.add(orderStatus.get( mapOrderStatus.get(PACKED_ORDER_STATUS)));
//        list.add(orderStatus.get( mapOrderStatus.get(SHIPPED_ORDER_STATUS)));
//        list.add(orderStatus.get( mapOrderStatus.get(DELIVERED_ORDER_STATUS)));
//        list.add(orderStatus.get( mapOrderStatus.get(CANCEL_BY_RETAILER)));
//        list.add(orderStatus.get( mapOrderStatus.get(CANCEL_BY_CUSTOMER)));
        try {
            AddressResponse addressResponse = MyProfile.getInstance().getAddressResponses().get(0);
            shopname.setText(addressResponse.getShopName());
            address.setText(addressResponse.getAddress());
            ivShareField.setOnClickListener(view -> {
                if (Permisions.checkWriteExternlStoragePermission((Activity) getContext())) {
                    Bitmap bitmap = null;
                    try {
                        shopiamge.setDrawingCacheEnabled(true);
                        bitmap = Bitmap.createBitmap(shopiamge.getDrawingCache());
                        shopiamge.setDrawingCacheEnabled(false);

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "pont1", Toast.LENGTH_LONG).show();
                    }
                    final String appPackageName = getContext().getPackageName();
                    String message = "रोकड मार्ट आता आपल्या शहरामध्ये!!!\n" +
                            "आता " + addressResponse.getShopName() + "शॉप रोकड मार्ट सोबत ऑनलाईन झाले आहे. \n" +
                            "नवीन ऑफर्स आणि शॉपिंग साठी खालील लिंक वर क्लिक करा आणि अँप डाउनलोड करा.\n";
                    Utils.shareImage(bitmap, "shop.png", (Activity) getContext(), message + "https://play.google.com/store/apps/details?id=" + appPackageName);
                } else {
                    Permisions.requestWriteExternlStoragePermission(getContext());
                }
            });
            loader.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(addressResponse.getShopImage()).listener(new RequestListener<Drawable>() {

                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    loader.setVisibility(View.GONE);

                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    loader.setVisibility(View.GONE);
                    return false;
                }
            }).dontAnimate().
                    diskCacheStrategy(DiskCacheStrategy.ALL).
                    signature(new ObjectKey(addressResponse.getShopImage() == null ? "" : addressResponse.getShopImage())).
                    error(R.mipmap.applogo).thumbnail(0.5f).into(shopiamge);

        } catch (Exception e){

        }
        recyclerView.setAdapter(new OrdersHomeAdapter(orderStatus, this));
        /*ordersListAdapter = new OrdersListAdapter(orderStatus, this);
        orderList.setAdapter(ordersListAdapter);*/

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.accepted_orders) {
            int position = mapOrderStatus.get(OPEN_ORDER_STATUS);
            mListener.showOrderList(orderStatus.get(position));
            /*myOrdersViewModel.getSelectedOrderGroup().setValue(myOrdersViewModel.getOpenOrders().getValue());
            mListener.showOrderList();*/
        } else {
            StateOfOrders stateOfOrders = (StateOfOrders) view.getTag();
            mListener.showOrderList(stateOfOrders);
        }
    }
}