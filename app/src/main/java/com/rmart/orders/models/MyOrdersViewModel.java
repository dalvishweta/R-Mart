package com.rmart.orders.models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyOrdersViewModel extends ViewModel implements Serializable {

    private final MutableLiveData<OrdersByType> openOrders = new MutableLiveData<>();
    private final MutableLiveData<OrdersByType> acceptedOrders = new MutableLiveData<>();
    private final MutableLiveData<OrdersByType> packedOrders = new MutableLiveData<>();
    private final MutableLiveData<OrdersByType> shippedOrders = new MutableLiveData<>();
    private final MutableLiveData<OrdersByType> deliveredOrders = new MutableLiveData<>();
    private final MutableLiveData<OrdersByType> rejectedOrders = new MutableLiveData<>();
    private final MutableLiveData<OrdersByType> canceledOrders = new MutableLiveData<>();

    public MyOrdersViewModel() {
        setOpenOrders(new OrdersByType("Open Orders", R.drawable.item_accepted_top_bg,R.drawable.item_accepted_bottom_bg, generateProductList("Open", 6, 5, false)));
        setAcceptedOrders(new OrdersByType("Accepted Orders", R.drawable.item_accepted_top_bg,R.drawable.item_accepted_bottom_bg, generateProductList("Accepted", 10, 17, false)));
        setPackedOrders(new OrdersByType("Packed Orders", R.drawable.item_packed_top_bg,R.drawable.item_packed_bottom_bg, generateProductList("Packed", 15, 8, false)));
        setShippedOrders(new OrdersByType("Shipped Orders", R.drawable.item_shipped_top_bg,R.drawable.item_shipped_bottom_bg, generateProductList("Shipped", 9, 25, false)));
        setDeliveredOrders(new OrdersByType("Delivered Orders", R.drawable.item_delivered_top_bg,R.drawable.item_delivered_bottom_bg, generateProductList("Delivered", 13, 6, true)));
        setRejectedOrders(new OrdersByType("Rejected Orders", R.drawable.item_rejected_top_bg,R.drawable.item_rejected_bottom_bg, generateProductList("Returned", 8, 14, true)));
        setCanceledOrders(new OrdersByType("Canceled Orders", R.drawable.item_canceled_top_bg,R.drawable.item_canceled_bottom_bg, generateProductList("Canceled", 7, 20, true)));
    }

    ArrayList<OrderObject> generateProductList(String order, int orders, int products, boolean isDue) {
        ArrayList<OrderObject> orderObjects = new ArrayList<>();
        Date date = Calendar.getInstance().getTime();

        for (int i = 0; i < orders; i++) {
            ArrayList<ProductObject> productObjects = new ArrayList<>();
            for (int j = 0; j < products; j++ ) {
                productObjects.add(new ProductObject(order + j+" Aashirvad Multigrain Atta" + date.getTime(), "1KG", "1", "100"));
            }
            orderObjects.add(new OrderObject(i+" Aug 2020", date.getTime() + "", productObjects, order, isDue));
        }
        return orderObjects;
    }
    public void setOpenOrders(OrdersByType value) {
        this.openOrders.setValue(value);
    }

    public void setAcceptedOrders(OrdersByType value) {
        this.acceptedOrders.setValue(value);
    }

    public void setPackedOrders(OrdersByType value) {
        this.packedOrders.setValue(value);
    }

    public void setShippedOrders(OrdersByType value) {
        this.shippedOrders.setValue(value);
    }

    public void setDeliveredOrders(OrdersByType value) {
        this.deliveredOrders.setValue(value);
    }

    public void setRejectedOrders(OrdersByType value) {
        this.rejectedOrders.setValue(value);
    }

    public void setCanceledOrders(OrdersByType value) {
        this.canceledOrders.setValue(value);
    }

    public MutableLiveData<OrdersByType> getOpenOrders() {
        return openOrders;
    }

    public MutableLiveData<OrdersByType> getAcceptedOrders() {
        return acceptedOrders;
    }

    public MutableLiveData<OrdersByType> getPackedOrders() {
        return packedOrders;
    }

    public MutableLiveData<OrdersByType> getShippedOrders() {
        return shippedOrders;
    }

    public MutableLiveData<OrdersByType> getDeliveredOrders() {
        return deliveredOrders;
    }

    public MutableLiveData<OrdersByType> getRejectedOrders() {
        return rejectedOrders;
    }

    public MutableLiveData<OrdersByType> getCanceledOrders() {
        return canceledOrders;
    }
    public ArrayList<OrdersByType> getRecycleArrayList() {
        ArrayList<OrdersByType> ordersByTypes = new ArrayList<>();
        ordersByTypes.add(getAcceptedOrders().getValue());
        ordersByTypes.add(getPackedOrders().getValue());
        ordersByTypes.add(getShippedOrders().getValue());
        ordersByTypes.add(getDeliveredOrders().getValue());
        ordersByTypes.add(getRejectedOrders().getValue());
        ordersByTypes.add(getCanceledOrders().getValue());
        return ordersByTypes;
    }
}
