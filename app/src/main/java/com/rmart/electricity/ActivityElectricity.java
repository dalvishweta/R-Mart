package com.rmart.electricity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.gson.Gson;
import com.rmart.R;
import com.rmart.electricity.api.ElecticityService;
import com.rmart.electricity.fetchbill.model.ElecProcessPOJO;
import com.rmart.electricity.fetchbill.model.BillDetails;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityElectricity extends AppCompatActivity {

    public Spinner sppiner_opertor;
    public AppCompatEditText bill_unit_two,consumer_no_one,mobile_two;
      public TextView bill_unit_one;
    public LinearLayout lin_two,lin_one;
    public AppCompatButton fetch_bill,pay_bill;
    ElecticityService eleService;
    public String SelectedValue="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_electricity_new);
        bill_unit_two= findViewById(R.id.bill_unit_two);
        bill_unit_one= findViewById(R.id.bill_unit_one);
        consumer_no_one= findViewById(R.id.consumer_no_one);
        mobile_two= findViewById(R.id.mobile_two);
        sppiner_opertor=findViewById(R.id.sppiner_opertor);
        fetch_bill=findViewById(R.id.fetch_bill);
        lin_two=findViewById(R.id.lin_two);
        lin_one=findViewById(R.id.lin_one);
        String[] arraySpinner = new String[] {
                "Select operator", "MSEDC Limited", "Adani Electricity Mumbai Limited"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sppiner_opertor.setAdapter(adapter);
        sppiner_opertor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if(sppiner_opertor.getSelectedItem().toString().equalsIgnoreCase("MSEDC Limited")){
                    SelectedValue="MSE";
                    bill_unit_one.setVisibility(View.VISIBLE);
                    bill_unit_two.setVisibility(View.VISIBLE);


                }else if(sppiner_opertor.getSelectedItem().toString().equalsIgnoreCase("Adani Electricity Mumbai Limited")){
                    SelectedValue="ADE";
                    bill_unit_one.setVisibility(View.GONE);
                    bill_unit_two.setVisibility(View.GONE);


                }
               else if(sppiner_opertor.getSelectedItem().toString().equalsIgnoreCase("Select operator")) {
                    SelectedValue = "op";
                    bill_unit_one.setVisibility(View.VISIBLE);
                    bill_unit_two.setVisibility(View.VISIBLE);

                }
                // Toast.makeText(ActivityElectricity.this, sppiner_opertor.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


        fetch_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SelectedValue.equalsIgnoreCase("MSE")){
                    if (SelectedValue.equalsIgnoreCase("")||SelectedValue.equalsIgnoreCase("Select operator")) {
                        Toast.makeText(getApplicationContext(), "Please Select operator", Toast.LENGTH_LONG).show();
                    }
                    else if (consumer_no_one.getText().toString() == null || consumer_no_one.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please Enter Consumer number", Toast.LENGTH_LONG).show();
                    }

                    else   if (bill_unit_two.getText().toString() == null || bill_unit_two.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please Enter bill unit", Toast.LENGTH_LONG).show();
                    }


                    else if (mobile_two.getText().toString() == null || mobile_two.getText().toString().isEmpty()||mobile_two.getText().toString().length()<10) {
                        Toast.makeText(getApplicationContext(), "Please Enter mobile number", Toast.LENGTH_LONG).show();
                    }


                    else {
                        if (Utils.isNetworkConnected(ActivityElectricity.this)) {

                            ProgressDialog progressBar = new ProgressDialog(ActivityElectricity.this, R.style.mySpinnerTheme);
                            progressBar.setCancelable(false);
                            progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                            progressBar.show();

                            eleService = RetrofitClientInstance.getInstance().getRetrofitInstanceRokad().create(ElecticityService.class);
                            eleService.electicityProcess(MyProfile.getInstance().getUserID(), SelectedValue, consumer_no_one.getText().toString(), bill_unit_two.getText().toString(),
                                    mobile_two.getText().toString())
                                    .enqueue(new Callback<ElecProcessPOJO>() {
                                        @Override
                                        public void onResponse(Call<ElecProcessPOJO> call, Response<ElecProcessPOJO> response) {

                                            progressBar.cancel();
                                            ElecProcessPOJO data = response.body();
                                            if (data.getStatus().equalsIgnoreCase("success")) {

                                                //setData(data);
                                                if (null != data.getData().getOrderId()) {
                                                    Gson gson = new Gson();
                                                    String myJson = gson.toJson(data.getData());
                                                    Intent intent = new Intent(ActivityElectricity.this, FetchBill.class);
                                                    intent.putExtra("myjson", data.getData());
                                                    intent.putExtra("selected_value", SelectedValue);
                                                    intent.putExtra("mobile_no", mobile_two.getText().toString());
                                                    intent.putExtra("bill_unit", bill_unit_two.getText().toString());
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "No bill data available", Toast.LENGTH_LONG).show();

                                                }
                                            } else {
                                                BillDetails error =data.getData();
                                                if(error!=null){
                                                    Gson gson = new Gson();
                                                    String myJson = gson.toJson(error);
                                                    try {
                                                        JSONObject js =new JSONObject(myJson);
                                                        if (js.has("mobile_number")) {
                                                            //get Value of video
                                                            String video = js.optString("mobile_number");
                                                            showDialog("", video);

                                                        }else {
                                                            showDialog("", data.getMsg());
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }



                                                }

                                                //Toast.makeText(getApplicationContext(), "No bill data available", Toast.LENGTH_LONG).show();

                                            }

                                            progressBar.cancel();
                                        }

                                        @Override
                                        public void onFailure(Call<ElecProcessPOJO> call, Throwable t) {
                                            // Toast.makeText(getApplicationContext(), "No bill data available", Toast.LENGTH_LONG).show();
                                            showDialog("", t.getMessage());
                                            progressBar.cancel();
                                            // Toast.makeText(getApplicationContext(), "No bill data available", Toast.LENGTH_LONG).show();

                                        }
                                    });

                        }else{
                            showDialog("Sorry!!", getString(R.string.error_internet));

                        }
                    }
                }
                if(SelectedValue.equalsIgnoreCase("ADE")){
                    if (SelectedValue.equalsIgnoreCase("")||SelectedValue.equalsIgnoreCase("Select operator")) {
                        Toast.makeText(getApplicationContext(), "Please Select operator", Toast.LENGTH_LONG).show();
                    }
                    else if (consumer_no_one.getText().toString() == null || consumer_no_one.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please Enter Consumer number", Toast.LENGTH_LONG).show();
                    }


                    else if (mobile_two.getText().toString() == null || mobile_two.getText().toString().isEmpty()||mobile_two.getText().toString().length()<10) {
                        Toast.makeText(getApplicationContext(), "Please Enter mobile number", Toast.LENGTH_LONG).show();
                    }


                    else {
                        if (Utils.isNetworkConnected(ActivityElectricity.this)) {

                            ProgressDialog progressBar = new ProgressDialog(ActivityElectricity.this, R.style.mySpinnerTheme);
                            progressBar.setCancelable(false);
                            progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                            progressBar.show();

                            eleService = RetrofitClientInstance.getInstance().getRetrofitInstanceRokad().create(ElecticityService.class);
                            eleService.electicityProcess(MyProfile.getInstance().getUserID(), SelectedValue, consumer_no_one.getText().toString(), bill_unit_two.getText().toString(),
                                    mobile_two.getText().toString())
                                    .enqueue(new Callback<ElecProcessPOJO>() {
                                        @Override
                                        public void onResponse(Call<ElecProcessPOJO> call, Response<ElecProcessPOJO> response) {

                                            progressBar.cancel();
                                            ElecProcessPOJO data = response.body();
                                            if (data.getStatus().equalsIgnoreCase("success")) {

                                                //setData(data);
                                                if (null != data.getData().getOrderId()) {
                                                    Gson gson = new Gson();
                                                    String myJson = gson.toJson(data.getData());
                                                    Intent intent = new Intent(ActivityElectricity.this, FetchBill.class);
                                                    intent.putExtra("myjson", data.getData());
                                                    intent.putExtra("selected_value", SelectedValue);
                                                    intent.putExtra("mobile_no", mobile_two.getText().toString());
                                                    intent.putExtra("bill_unit", bill_unit_two.getText().toString());
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "No bill data available", Toast.LENGTH_LONG).show();

                                                }
                                            } else {
                                                BillDetails error =data.getData();
                                                if(error!=null){
                                                    Gson gson = new Gson();
                                                    String myJson = gson.toJson(error);
                                                    try {
                                                        JSONObject js =new JSONObject(myJson);
                                                        if (js.has("mobile_number")) {
                                                            //get Value of video
                                                            String video = js.optString("mobile_number");
                                                            showDialog("", video);

                                                        }else {
                                                            showDialog("", data.getMsg());
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                               /* if (error..getBillUnit() != null) {
                                                    showDialog("", error.getBillUnit());
                                                } else if (error.getConsumerID()!=null) {
                                                    showDialog("",error.getConsumerID());
                                                } else if (error.getMobileNumbe() != null) {
                                                    showDialog("", error.getMobileNumbe());
                                                }*/

                                                }

                                                //Toast.makeText(getApplicationContext(), "No bill data available", Toast.LENGTH_LONG).show();

                                            }

                                            progressBar.cancel();
                                        }

                                        @Override
                                        public void onFailure(Call<ElecProcessPOJO> call, Throwable t) {
                                            // Toast.makeText(getApplicationContext(), "No bill data available", Toast.LENGTH_LONG).show();
                                            showDialog("", t.getMessage());
                                            progressBar.cancel();
                                            // Toast.makeText(getApplicationContext(), "No bill data available", Toast.LENGTH_LONG).show();

                                        }
                                    });

                        }else{
                            showDialog("Sorry!!", getString(R.string.error_internet));

                        }
                    }
                }
                if(SelectedValue.equalsIgnoreCase("op")){
                    Toast.makeText(ActivityElectricity.this,"Please Select Operator",Toast.LENGTH_LONG).show();
                }



            }
        });






    }



    public void showDialog(String title, String msg) {
        try {
            AlertDialog.Builder builder =new AlertDialog.Builder(ActivityElectricity.this);
            builder.setTitle(title);
            builder.setCancelable(false);
            builder.setMessage(msg);
            builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {

        }

    }



}
