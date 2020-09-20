package com.rmart.customer;

import com.rmart.customer.models.ShopWiseWishListResponseDetails;

/**
 * Created by Satya Seshu on 17/09/20.
 */
public interface OnCustomerWishListInteractionListener {

    void gotoWishListDetailsScreen(ShopWiseWishListResponseDetails shopWiseWishListResponseDetails);

}
