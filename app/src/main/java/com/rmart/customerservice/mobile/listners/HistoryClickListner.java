package com.rmart.customerservice.mobile.listners;

import com.rmart.customerservice.mobile.circle.model.Circle;
import com.rmart.customerservice.mobile.models.LastTransaction;

public interface HistoryClickListner {

    public void onSelect(LastTransaction lastTransaction);
}
