package com.rmart.utilits;

public class Curruncy {

    public static String getCurruncy(String  amount){

        try {

            return "₹. " + String.format("%.2f", Double.parseDouble(amount));
        }catch (Exception e){
            return "₹. 0.00";
        }

    }
    public static String getDiscountCurruncy(String  amount) {


        return "₹. " + String.format("%.2f",Double.parseDouble(amount));


    }

}