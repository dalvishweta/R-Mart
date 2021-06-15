package com.rmart.electricity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.baseclass.views.CustomLoadingDialog;
import com.rmart.customerservice.mobile.fragments.PaymentStatusFragment;
import com.rmart.customerservice.mobile.models.mobileRecharge.Recharge;
import com.rmart.customerservice.mobile.models.mobileRecharge.RechargeBaseClass;
import com.rmart.electricity.activities.ElectricityActivity;
import com.rmart.electricity.api.ElecticityService;
import com.rmart.electricity.fetchbill.model.BillDetails;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.ccavenue.AvenuesParams;
import com.rmart.utilits.ccavenue.RSAUtility;
import com.rmart.utilits.ccavenue.ServiceUtility;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class PaymentActivity extends AppCompatActivity  {
    protected Dialog progressDialog;
    private WebView webview;
    CCavenueres ccavenue_data;
    String mobile_number,bill_unit,operator;
    BillDetails ob;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment);
        webview = findViewById(R.id.web_view);
        progressDialog = CustomLoadingDialog.getInstance(this);

        Intent ii =getIntent();
        mobile_number=ii.getStringExtra("mobile_number");
        bill_unit=ii.getStringExtra("bill_unit");
        operator=ii.getStringExtra("operator");
        ob = (BillDetails) getIntent().getSerializableExtra("cust_details");

        ccavenue_data = (CCavenueres) getIntent().getSerializableExtra("rsakeyresonse");
        initWebView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void initWebView() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT"); // javaScriptInterface
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(webview, url);
                if (null != progressDialog && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError er) {
                //handler.proceed();
                // Ignore SSL certificate errors
                final AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                builder.setMessage("The Loading Web Page is not SSL certified");
                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                showDialog("", rerr.getDescription().toString());
                if (null != progressDialog && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
        loadWebView();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadWebView() {
        try {
            /* An instance of this class will be registered as a JavaScript interface */
            StringBuilder params = new StringBuilder();
            String access_code, merchant_id, redirect_url, cancel_url, billing_country, merchant_param1, merchant_param2, billing_name, billing_email, billing_tel, encVal = "";
            access_code = ccavenue_data.getCcavenueData().getAccessCode(); //getString(R.string.access_code);
            merchant_id = ccavenue_data.getCcavenueData().getMerchantId(); //getString(R.string.merchant_id);
            redirect_url = ccavenue_data.getCcavenueData().getRedirectUrl(); // getString(R.string.redirect_url);
            cancel_url = ccavenue_data.getCcavenueData().getCancelUrl(); // getString(R.string.cancel_url);
            billing_country = ccavenue_data.getCcavenueData().getBillingCountry();
            // merchant_param1 = merchantParams.ticket_id;
            billing_name = ccavenue_data.getCcavenueData().getBillingName();//"shweta";
            billing_email = ccavenue_data.getCcavenueData().getBillingEmail(); // "shwetadalvi9@gmail.com";
            billing_tel =ccavenue_data.getCcavenueData().getBillingTel(); // "8446399429";
            StringBuilder vEncVal = new StringBuilder();

            String amount = String.valueOf(ccavenue_data.getCcavenueData().getAmount()).replace("," ,"");
            String currency = getString(R.string.currency);
            vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, amount));
            vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, currency));
            encVal = RSAUtility.encrypt(vEncVal.substring(0, vEncVal.length() - 1), ccavenue_data.getCcavenueData().getRsaKey().replaceAll("[\n]", ""));

            params.append(ServiceUtility.addToPostParams(AvenuesParams.ACCESS_CODE, access_code));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_ID, merchant_id));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ORDER_ID, String.valueOf(ccavenue_data.getCcavenueData().getOrderId())));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.REDIRECT_URL, redirect_url));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.CANCEL_URL, cancel_url));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ENC_VAL, URLEncoder.encode(encVal, "UTF-8")));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_NAME, billing_name));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_EMAIL, billing_email));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_TEL, billing_tel));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_COUNTRY, billing_country));
            //params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_PARAM1, merchant_param1));//ticket id
            params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_PARAM2, "android"));//platform

            String vPostParams = params.substring(0, params.length() - 1);
            String vTransUrl = (BuildConfig.PAYMENT_URL);
            Log.d("vPostParams", "vPostParams :"+vPostParams);
            Log.d("vTransUrl", "vTransUrl :"+vPostParams);
            webview.postUrl(vTransUrl, vPostParams.getBytes(StandardCharsets.UTF_8));// EncodingUtils.getBytes(vPostParams, "UTF-8"));
        } catch (Exception e) {
            showDialog(e.getMessage(),"");
        }
    }
    class MyJavaScriptInterface {
        private JsonObject jsonObject;

        @JavascriptInterface
        public void processHTML(String html) {
            Log.d("JsonObject", "html data: " + html);
            jsonObject = new JsonParser().parse(html).getAsJsonObject();
            Gson g = new Gson();
            CCAvenueResponceModel ccAvenueResponse = g.fromJson(html, CCAvenueResponceModel.class);
            CcavenueRequestData(ccAvenueResponse,html);
            /*if (ccAvenueResponse.getOrderStatus().equalsIgnoreCase("success")) {

            }else{
                RechargeBaseClass rs = new RechargeBaseClass();
                rs.setStatus(400);
                rs.setMsg("Transaction Unsuccessful");
                Recharge recharge = new Recharge();
                recharge.setAmount(String.valueOf(ccAvenueResponse.getAmount()));
                recharge.setTrackingId(ccAvenueResponse.getTrackingId());
                recharge.setTransDate(ccAvenueResponse.getTransDate());
                //recharge.setMessage(ccAvenueResponse.getMessage());
                //recharge.setBillingName(ob.getConsumerName());
               // recharge.setTransDate(ob.getBillDate());
                //recharge.setServiceId(Long.getLong(ob.getConsumerID()));

                rs.setData(recharge);
                displayStatus(rs);
            }*/
        }

        @JavascriptInterface
        public void gotMsg(String msg) {
            showDialog("", msg);
            // Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
    protected void showDialog(String title, String msg) {
        try {
            AlertDialog.Builder builder = new
                    AlertDialog.Builder(
                    this,
                    R.style.AlertDialog
            );
            if (TextUtils.isEmpty(title)) {
                title = this.getString(R.string.message);
            }
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setCancelable(false);
            builder.setNegativeButton(this.getString(R.string.close), null);

            builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent ii = new Intent(PaymentActivity.this, ElectricityActivity.class);
                    startActivity(ii);
                    finishAffinity();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            // LoggerInfo.errorLog("show dialog exception", e.getMessage());
        }
    }



    public void CcavenueRequestData(CCAvenueResponceModel ccAvenueResponse, String html){

        if (Utils.isNetworkConnected(PaymentActivity.this)) {

            ProgressDialog progressBar = new ProgressDialog(PaymentActivity.this, R.style.mySpinnerTheme);
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressBar.show();
            Recharge recharge = new Recharge();
            RechargeBaseClass rs = new RechargeBaseClass();
       /*     ArrayList<CCAvenueResponceModel> ccabledata = new ArrayList<>();
            ccabledata.add(ccAvenueResponse);

            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(ccabledata, new TypeToken<List<CCAvenueResponceModel>>() {
            }.getType());

            if (!element.isJsonArray()) {
                // fail appropriately
                throw new NullPointerException();
            }
           JsonArray ccavenuejsonArray = element.getAsJsonArray();*/
            ElecticityService eleService = RetrofitClientInstance.getInstance().getRetrofitInstanceForAddProduct().create(ElecticityService.class);
            eleService.electicitybillProcess(MyProfile.getInstance(getApplicationContext()).getUserID(), operator,String.valueOf(ob.getConsumerID()),bill_unit,
                   mobile_number,String.valueOf(ob.getDueAmount()),ob.getConsumerName(),String.valueOf(ob.getOrderId()),html,ccavenue_data.getCcavenue(),ccavenue_data.getRokadOrderId(),ccavenue_data.isWallet())
                    .enqueue(new Callback<paybill>() {
                        @Override
                        public void onResponse(Call<paybill> call, Response<paybill> response) {

                            progressBar.cancel();
                            paybill datap = response.body();

                            if(datap!=null){
                                if (datap.getStatus()==200) {
                                    //Toast.makeText(getApplicationContext(), datap.getData().getDescription(), Toast.LENGTH_LONG).show();

                                    //setData(data);
                                    // openDialog1(datap);

                                    rs.setStatus(200);
                                    rs.setMsg("Transaction Successful");
                                    recharge.setAmount((ccAvenueResponse.getAmount()));
                                    recharge.setTrackingId(ccAvenueResponse.getTrackingId());
                                    //  recharge.setMessage(datap.getData().getDescription());
                                    //  recharge.setBillingName(ob.getConsumerName());
                                    recharge.setTransDate(ccAvenueResponse.getTransDate());
                                    // recharge.setServiceId((int) datap.getData().getConsumerID());

                                    rs.setData(recharge);
                                    displayStatus(rs);
                                }
                                else {
                                    rs.setStatus(400);
                                    rs.setMsg("Transaction Unsuccessful");
                                    //recharge.setAmount(String.valueOf(ccAvenueResponse.getAmount()));
                                    recharge.setTrackingId(ccAvenueResponse.getTrackingId());
                                    //recharge.setTrackingId(datap.getData().getMerTxnID());
                                    // recharge.setMessage(datap.getData().getDescription());
                                    //recharge.setBillingName(ob.getConsumerName());
                                    recharge.setAmount(ccAvenueResponse.getAmount());
                                    recharge.setTransDate(ccAvenueResponse.getTransDate());
                                    rs.setData(recharge);
                                    displayStatus(rs);
                                }

                            }else{
                                rs.setStatus(400);
                                rs.setMsg("Transaction Unsuccessful");
                                recharge.setAmount((ccAvenueResponse.getAmount()));
                                recharge.setTrackingId(ccAvenueResponse.getTrackingId());
                                recharge.setMessage(ccAvenueResponse.getMessage());
                                recharge.setTransDate(ccAvenueResponse.getTransDate());
                                rs.setData(recharge);
                                displayStatus(rs);
                            }


                            progressBar.cancel();
                        }

                        @Override
                        public void onFailure(Call<paybill> call, Throwable t) {
                            rs.setStatus(400);
                            rs.setMsg("Transaction Unsuccessful");
                            recharge.setAmount((ccAvenueResponse.getAmount()));
                            recharge.setTrackingId(ccAvenueResponse.getTrackingId());
                            recharge.setMessage(ccAvenueResponse.getMessage());
                            recharge.setTransDate(ccAvenueResponse.getTransDate());
                            rs.setData(recharge);
                            displayStatus(rs);
                        }
                    });

        }else{
            showDialog("Sorry!!", getString(R.string.error_internet));

        }

    }


    private void displayStatus(RechargeBaseClass paymentResponse) {
        PaymentActivity.this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, PaymentStatusFragment.newInstance(paymentResponse, null, null, paymentResponse.getData().getAmount()))
                .commit();

    }
}

