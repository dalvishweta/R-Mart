package com.rmart.wallet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable {

 @SerializedName("amount")
 @Expose
 private String amount;
 @SerializedName("tracking_id")
 @Expose
 private String trackingId;

 public String getAmount() {
  return amount;
 }

 public void setAmount(String amount) {
  this.amount = amount;
 }

 public String getTrackingId() {
  return trackingId;
 }

 public void setTrackingId(String trackingId) {
  this.trackingId = trackingId;
 }
 }
