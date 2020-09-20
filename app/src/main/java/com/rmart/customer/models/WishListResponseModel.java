package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

import java.util.List;

/**
 * Created by Satya Seshu on 17/09/20.
 */
public class WishListResponseModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private WishListResponseModel.WishListResponseDataResponse wishListResponseDataResponse;

    public WishListResponseDataResponse getWishListResponseDataResponse() {
        return wishListResponseDataResponse;
    }

    public void setWishListResponseDataResponse(WishListResponseDataResponse wishListResponseDataResponse) {
        this.wishListResponseDataResponse = wishListResponseDataResponse;
    }

    public static class WishListResponseDataResponse {
        @SerializedName("wishlist_data")
        @Expose
        private List<WishListResponseDetails> wishListResponseDetails;

        @SerializedName("total_wishlist_count")
        @Expose
        private Integer totalWishlistCount;
        @SerializedName("next_start_page")
        @Expose
        private Integer nextStartPage;

        public List<WishListResponseDetails> getWishListResponseDetails() {
            return wishListResponseDetails;
        }

        public void setWishListResponseDetails(List<WishListResponseDetails> wishListResponseDetails) {
            this.wishListResponseDetails = wishListResponseDetails;
        }

        public Integer getTotalWishlistCount() {
            return totalWishlistCount;
        }

        public void setTotalWishlistCount(Integer totalWishlistCount) {
            this.totalWishlistCount = totalWishlistCount;
        }

        public Integer getNextStartPage() {
            return nextStartPage;
        }

        public void setNextStartPage(Integer nextStartPage) {
            this.nextStartPage = nextStartPage;
        }
    }
}
