package com.rmart.retiler.product;

import android.view.View;

public interface BaseInterface extends View.OnClickListener {
    void initilization();
    void setListener();
    void loadDefaultData();
}
