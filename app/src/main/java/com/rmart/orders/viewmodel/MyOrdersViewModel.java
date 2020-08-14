package com.rmart.orders.viewmodel;

import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.R;
import com.rmart.orders.models.OrderObject;
import com.rmart.orders.models.SelectedOrderGroup;
import com.rmart.orders.models.ProductObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MyOrdersViewModel extends ViewModel implements Serializable {

    private final MutableLiveData<SelectedOrderGroup> openOrders = new MutableLiveData<>();
    private final MutableLiveData<SelectedOrderGroup> acceptedOrders = new MutableLiveData<>();
    private final MutableLiveData<SelectedOrderGroup> packedOrders = new MutableLiveData<>();
    private final MutableLiveData<SelectedOrderGroup> shippedOrders = new MutableLiveData<>();
    private final MutableLiveData<SelectedOrderGroup> deliveredOrders = new MutableLiveData<>();
    private final MutableLiveData<SelectedOrderGroup> returnedOrders = new MutableLiveData<>();
    private final MutableLiveData<SelectedOrderGroup> canceledOrders = new MutableLiveData<>();
    private final MutableLiveData<SelectedOrderGroup> selectedOrderGroup = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<SelectedOrderGroup>> orderGroupsList = new MutableLiveData<>();

    public MyOrdersViewModel() {
        setOpenOrders(new SelectedOrderGroup("Open Orders", R.drawable.item_accepted_top_bg,R.drawable.item_accepted_bottom_bg, generateProductList("Open", 1, 2, false)));
        setAcceptedOrders(new SelectedOrderGroup("Accepted Orders", R.drawable.item_accepted_top_bg,R.drawable.item_accepted_bottom_bg, generateProductList("Accepted", 0, 0, false)));
        setPackedOrders(new SelectedOrderGroup("Packed Orders", R.drawable.item_packed_top_bg,R.drawable.item_packed_bottom_bg, generateProductList("Packed", 0, 0, false)));
        setShippedOrders(new SelectedOrderGroup("Shipped Orders", R.drawable.item_shipped_top_bg,R.drawable.item_shipped_bottom_bg, generateProductList("Shipped", 0, 0, false)));
        setDeliveredOrders(new SelectedOrderGroup("Delivered Orders", R.drawable.item_delivered_top_bg,R.drawable.item_delivered_bottom_bg, generateProductList("Delivered", 0, 0, true)));
        setReturnedOrders(new SelectedOrderGroup("Returned Orders", R.drawable.item_returned_top_bg,R.drawable.item_returned_bottom_bg, generateProductList("Returned", 0, 0, true)));
        setCanceledOrders(new SelectedOrderGroup("Canceled Orders", R.drawable.item_canceled_top_bg,R.drawable.item_canceled_bottom_bg, generateProductList("Canceled", 0, 0, true)));
        orderGroupsList.setValue(new ArrayList<>());
        setOrderGroupList();
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

    public void setOpenOrders(SelectedOrderGroup value) {
        this.openOrders.setValue(value);
    }

    public void setAcceptedOrders(SelectedOrderGroup value) {
        this.acceptedOrders.setValue(value);
    }
    public void setPackedOrders(SelectedOrderGroup value) {
        this.packedOrders.setValue(value);
    }
    public void setShippedOrders(SelectedOrderGroup value) {
        this.shippedOrders.setValue(value);
    }
    public void setDeliveredOrders(SelectedOrderGroup value) {
        this.deliveredOrders.setValue(value);
    }
    public void setReturnedOrders(SelectedOrderGroup value) {
        this.returnedOrders.setValue(value);
    }
    public void setCanceledOrders(SelectedOrderGroup value) {
        this.canceledOrders.setValue(value);
    }

    public void setAcceptedOrders(OrderObject value) {
        Objects.requireNonNull(this.acceptedOrders.getValue()).setOrderObjects(value);
        setOrderGroupList();
    }
    public void setPackedOrders(OrderObject value) {
        Objects.requireNonNull(this.packedOrders.getValue()).setOrderObjects(value);
        setOrderGroupList();
    }
    public void setShippedOrders(OrderObject value) {
        Objects.requireNonNull(this.shippedOrders.getValue()).setOrderObjects(value);
        setOrderGroupList();
    }
    public void setDeliveredOrders(OrderObject value) {
        Objects.requireNonNull(this.deliveredOrders.getValue()).setOrderObjects(value);
        setOrderGroupList();
    }
    public void setReturnedOrders(OrderObject value) {
        Objects.requireNonNull(this.returnedOrders.getValue()).setOrderObjects(value);
        setOrderGroupList();
    }
    public void setCanceledOrders(OrderObject value) {
        Objects.requireNonNull(this.canceledOrders.getValue()).setOrderObjects(value);
        setOrderGroupList();
    }

    public MutableLiveData<SelectedOrderGroup> getOpenOrders() {
        return openOrders;
    }
    public MutableLiveData<SelectedOrderGroup> getAcceptedOrders() {
        return acceptedOrders;
    }

    public MutableLiveData<SelectedOrderGroup> getPackedOrders() {
        return packedOrders;
    }

    public MutableLiveData<SelectedOrderGroup> getShippedOrders() {
        return shippedOrders;
    }

    public MutableLiveData<SelectedOrderGroup> getDeliveredOrders() {
        return deliveredOrders;
    }

    public MutableLiveData<SelectedOrderGroup> getReturnedOrders() {
        return returnedOrders;
    }

    public MutableLiveData<SelectedOrderGroup> getSelectedOrderGroup() {
        return selectedOrderGroup;
    }

    public MutableLiveData<SelectedOrderGroup> getCanceledOrders() {
        return canceledOrders;
    }

    public void setOrderGroupList() {
        Objects.requireNonNull(orderGroupsList.getValue()).clear();
        orderGroupsList.getValue().add(getAcceptedOrders().getValue());
        orderGroupsList.getValue().add(getPackedOrders().getValue());
        orderGroupsList.getValue().add(getShippedOrders().getValue());
        orderGroupsList.getValue().add(getDeliveredOrders().getValue());
        orderGroupsList.getValue().add(getReturnedOrders().getValue());
        orderGroupsList.getValue().add(getCanceledOrders().getValue());
    }
    public MutableLiveData<ArrayList<SelectedOrderGroup>> getOrderGroupList() {
        return orderGroupsList;
    }

    public void deleteOrder(OrderObject orderObject, Resources getResources) {
        if(orderObject.getOrderType().contains(getResources.getString(R.string.open))) {
            Objects.requireNonNull(getOpenOrders().getValue()).removeOrderObjects(orderObject);
        } else if(orderObject.getOrderType().contains(getResources.getString(R.string.accepted))) {
            Objects.requireNonNull(getAcceptedOrders().getValue()).removeOrderObjects(orderObject);
        } else if(orderObject.getOrderType().contains(getResources.getString(R.string.packed))) {
            Objects.requireNonNull(getPackedOrders().getValue()).removeOrderObjects(orderObject);
        } else if(orderObject.getOrderType().contains(getResources.getString(R.string.shipped))) {
            Objects.requireNonNull(getShippedOrders().getValue()).removeOrderObjects(orderObject);
        } else if(orderObject.getOrderType().contains(getResources.getString(R.string.delivered))) {
            Objects.requireNonNull(getDeliveredOrders().getValue()).removeOrderObjects(orderObject);
        } else if(orderObject.getOrderType().contains(getResources.getString(R.string.returned))) {
            Objects.requireNonNull(getReturnedOrders().getValue()).removeOrderObjects(orderObject);
        } else if(orderObject.getOrderType().contains(getResources.getString(R.string.canceled))) {
            Objects.requireNonNull(getCanceledOrders().getValue()).removeOrderObjects(orderObject);
        }
    }

    /*public ArrayList<SelectedOrderGroup> getRecycleArrayList() {

        return SelectedOrderGroups;
    }*/
}
