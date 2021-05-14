package com.rmart.utilits;

import android.content.Context;
import android.widget.Toast;

public class ActionCall {


    public static void createCall(Context context,String mobile_number){

        if(Utils.isValidMobile(mobile_number)){
            Utils.openDialPad(context,mobile_number);

        }
        else{
            Toast.makeText(context,"Unable to dial your number",Toast.LENGTH_LONG).show();
        }
    }

}
