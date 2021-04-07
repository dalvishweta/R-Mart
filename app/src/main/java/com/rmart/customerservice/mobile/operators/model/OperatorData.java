package com.rmart.customerservice.mobile.operators.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OperatorData {
@SerializedName("pre_operators")
public ArrayList<Operator> preOperators;
@SerializedName("post_operators")
public ArrayList<Operator> postOperators;
@SerializedName("dth_operators")
public ArrayList<Operator> dthOperators;


}
