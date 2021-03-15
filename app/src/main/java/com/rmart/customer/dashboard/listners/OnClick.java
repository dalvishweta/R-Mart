package com.rmart.customer.dashboard.listners;

import com.rmart.customer.dashboard.model.BigShopType;
import com.rmart.customer.dashboard.model.ServiceOffer;
import com.rmart.customer.dashboard.model.ShopType;

public interface OnClick {

    public void onServiceClick(ServiceOffer serviceOffer);
    public void onShopClick(ShopType serviceOffer);
    public void onBigShopClick(BigShopType bigShopType);

}
