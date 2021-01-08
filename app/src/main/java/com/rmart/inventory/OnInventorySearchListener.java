package com.rmart.inventory;

import com.rmart.inventory.models.APIUnitMeasures;
import com.rmart.inventory.models.UnitObject;
import com.rmart.inventory.views.BaseInventoryFragment;
import com.rmart.utilits.pojos.ProductResponse;

public interface OnInventorySearchListener {
    void getProductListt(String stock_typee);

}
