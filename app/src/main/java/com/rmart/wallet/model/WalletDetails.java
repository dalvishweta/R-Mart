package com.rmart.wallet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WalletDetails  implements Serializable {

    @SerializedName("wallet_id")
    @Expose
    private int walletId;
    @SerializedName("wallet_amount")
    @Expose
    private String walletAmount;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("comment")
    @Expose
    private String comment;

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
