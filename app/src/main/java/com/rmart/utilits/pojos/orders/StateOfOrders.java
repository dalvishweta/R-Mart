package com.rmart.utilits.pojos.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.R;
import com.rmart.utilits.Utils;

import java.io.Serializable;

import static com.rmart.utilits.Utils.CANCEL_BY_RETAILER;
import static com.rmart.utilits.Utils.DELIVERED_ORDER_STATUS;
import static com.rmart.utilits.Utils.CANCEL_BY_CUSTOMER;
import static com.rmart.utilits.Utils.SHIPPED_ORDER_STATUS;

public class StateOfOrders implements Serializable {
    @SerializedName("count")
    @Expose
    private String count;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("status_name")
    @Expose
    private String statusName;

    int bgTop;
    int bgBottom;
    int position;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getBgTop() {
        return bgTop;
    }

    public void setBgTop(int bgTop) {
        this.bgTop = bgTop;
    }

    public int getBgBottom() {
        return bgBottom;
    }

    public void setBgBottom(int bgBottom) {
        this.bgBottom = bgBottom;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void updateBackgroundColor() {
        statusName = statusName.replace("_STATUS", "S");
        statusName = statusName.replace("_", " ");
        switch (status) {
            case Utils.OPEN_ORDER_STATUS:
                setPosition(0);
                break;
            case Utils.ACCEPTED_ORDER_STATUS:
                setPosition(1);
                bgTop = R.drawable.item_accepted_top_bg;
                bgBottom = R.drawable.item_accepted_bottom_bg;
                break;
            case Utils.PACKED_ORDER_STATUS:
                setPosition(2);
                bgTop = R.drawable.item_packed_top_bg;
                bgBottom = R.drawable.item_packed_bottom_bg;
                break;
            case SHIPPED_ORDER_STATUS:
                setPosition(3);
                statusName = "OUT FOR DELIVERY";
                bgTop = R.drawable.item_shipped_top_bg;
                bgBottom = R.drawable.item_shipped_bottom_bg;
                break;
            case DELIVERED_ORDER_STATUS:
                setPosition(4);
                bgTop = R.drawable.item_delivered_top_bg;
                bgBottom = R.drawable.item_delivered_bottom_bg;
                break;
            case CANCEL_BY_CUSTOMER:
                setPosition(5);
                bgTop = R.drawable.item_returned_top_bg;
                bgBottom = R.drawable.item_returned_bottom_bg;
                statusName = "CANCELLED";
                break;
            case CANCEL_BY_RETAILER:
                setPosition(6);
                bgTop = R.drawable.item_canceled_top_bg;
                bgBottom = R.drawable.item_canceled_bottom_bg;
                statusName = "CANCEL BY ME";
                break;
        }
    }
}
