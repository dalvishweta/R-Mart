package com.rmart.electricity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.rmart.R;
import com.rmart.baseclass.views.BaseActivity;
import com.rmart.electricity.api.ElecticityService;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchBill extends BaseActivity {

    public AppCompatTextView consumer_name,consumer_id,DueAmount,OrderId,DueDate,BillDate,merchant_ref,amount_bx,Date_bx,con_no,service_name;
    public AppCompatButton fetch_bill,pay_bill;
    ElecticityService eleService;
    data ob;
     String Selected_value;
    private String mobile_no;
    private String bill_unit;
    private String name;
    private String id;
    private String amt;
    private Integer orderid;
    private Integer serviceid;

    private String duedate;
    private String billdate;
    private String Merchant_ref;
    protected ProgressDialog progressBar;
    String stastus_bill="";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchbill_new);
        consumer_name=findViewById(R.id.consumer_name);
        consumer_id=findViewById(R.id.consumer_id);
        DueAmount=findViewById(R.id.DueAmount);
        DueDate=findViewById(R.id.DueDate);
        BillDate=findViewById(R.id.BillDate);
        OrderId=findViewById(R.id.OrderId);
        pay_bill=findViewById(R.id.pay_bill);
        merchant_ref=findViewById(R.id.merchant_ref);
        amount_bx=findViewById(R.id.amount_bx);
        Date_bx=findViewById(R.id.Date_bx);
        con_no=findViewById(R.id.con_no);
        service_name=findViewById(R.id.service_name);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii= new Intent(FetchBill.this,ActivityElectricity.class);

                startActivity(ii);
            }
        });
        progressBar = new ProgressDialog(getApplicationContext(), R.style.mySpinnerTheme);
        progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressBar.setCancelable(false);
        Intent ii =getIntent();
        if(ii!=null){
            Selected_value= ii.getStringExtra("selected_value");
            mobile_no= ii.getStringExtra("mobile_no");
            bill_unit= ii.getStringExtra("bill_unit");
        }

       ob = (data) getIntent().getSerializableExtra("myjson");
     name=ob.getConsumerName();
     id= String.valueOf(ob.getConsumerID());
     orderid=ob.getOrderId();
     duedate=ob.getDueDate();
        serviceid=ob.getServiceId();
     billdate=ob.getBillDate();
     //Merchant_ref=ob.getMerTxnID();
       amt= String.valueOf(ob.getDueAmount());
        consumer_name.setText(name);
         con_no.setText("Consumer Number: "+id);
        DueAmount.setText("Rs. "+amt);
        OrderId.setText(String.valueOf(orderid));
        DueDate.setText(duedate);
        amount_bx.setText("Rs.  "+amt);

        Date_bx.setText(billdate);
       // BillDate.setText("Bill Date: "+billdate);
       // merchant_ref.setText(id);



        pay_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (Utils.isNetworkConnected(FetchBill.this)) {


                    ProgressDialog progressBar = new ProgressDialog(FetchBill.this, R.style.mySpinnerTheme);
                    progressBar.setCancelable(false);
                    progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                    progressBar.show();
                    eleService = RetrofitClientInstance.getRetrofitInstance().create(ElecticityService.class);


                    eleService.electicityProcessRsaKey(MyProfile.getInstance().getUserID(),amt,serviceid.toString(),"Electricity",orderid.toString())
                   // eleService.electicityProcessRsaKey("524","160","1","electricity","123124")

                            .enqueue(new Callback<rsakeyResponse>() {
                                @Override
                                public void onResponse(Call<rsakeyResponse> call, Response<rsakeyResponse> response) {
                                    progressBar.cancel();
                                    rsakeyResponse data = response.body();
                                    if (data.getStatus().equalsIgnoreCase("success")) {
                                        Intent ii= new Intent(FetchBill.this,PaymentActivity.class);
                                        ii.putExtra("rsakeyresonse",  data.getData());
                                        ii.putExtra("cust_details",ob);
                                        ii.putExtra("mobile_number",mobile_no);
                                        ii.putExtra("bill_unit",bill_unit);
                                        ii.putExtra("operator",Selected_value);
                                        startActivity(ii);


                                    } else {
                                        showDialog("", response.body().getMsg());
                                    }

                                    progressBar.cancel();
                                }

                                @Override
                                public void onFailure(Call<rsakeyResponse> call, Throwable t) {
                                    showDialog("", t.getMessage());
                                    progressBar.cancel();
                                }
                            });
                }




            }
        });


    }

    @Override
    public void onPermissionsGranted(Integer requestCode) {

    }

    public void showDialog(String title, String msg) {
        try {
            AlertDialog.Builder builder =new AlertDialog.Builder(FetchBill.this);
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


    @Override
    public void showBadge(boolean b) {

    }

    @Override
    public void updateBadgeCount() {

    }

    @Override
    public void hideHamburgerIcon() {

    }

    @Override
    public void showHamburgerIcon() {

    }

    @Override
    public void showCartIcon() {

    }

    @Override
    public void hideCartIcon() {

    }
}
