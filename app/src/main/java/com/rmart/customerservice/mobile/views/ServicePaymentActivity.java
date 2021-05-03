package com.rmart.customerservice.mobile.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.baseclass.views.CustomLoadingDialog;
import com.rmart.customerservice.mobile.models.Vrecharge;
import com.rmart.electricity.CCavenueres;
import com.rmart.electricity.CCAvenueResponceModel;
import com.rmart.utilits.ccavenue.AvenuesParams;
import com.rmart.utilits.ccavenue.RSAUtility;
import com.rmart.utilits.ccavenue.ServiceUtility;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ServicePaymentActivity extends AppCompatActivity  {
    protected Dialog progressDialog;
    private WebView webview;
    CCavenueres ccavenue_data;
    Context context;
    public static final String RESULT= "result";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment);
        webview = findViewById(R.id.web_view);
        context=this;
        progressDialog = CustomLoadingDialog.getInstance(this);
        Intent ii =getIntent();
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(ServicePaymentActivity.this);
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
            if (ccAvenueResponse.getOrderStatus().equalsIgnoreCase("success")) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT,ccAvenueResponse);
                setResult(ServicePaymentActivity.RESULT_OK,returnIntent);
                finish();
            } else{
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT,ccAvenueResponse);
                setResult(ServicePaymentActivity.RESULT_CANCELED,returnIntent);
                finish();
            }
        }

        @JavascriptInterface
        public void gotMsg(String msg) {
            showDialog("", msg);
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
                    Intent ii = new Intent(ServicePaymentActivity.this, MobileRechargeActivity.class);
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









    public void openDialog1(Vrecharge data) {
        Dialog dialog = new Dialog(ServicePaymentActivity.this);
        dialog.setCancelable(false);
        AppCompatTextView consumer_namee, consumer_ide, DueAmounte, OrderIde, DueDatee, BillDatee, status;
        AppCompatButton pay_bill;
        dialog.setContentView(R.layout.dialogbrand_layout);
        dialog.setTitle("Receipt");
        consumer_namee = (AppCompatTextView) dialog.findViewById(R.id.consumer_name);
        consumer_ide = (AppCompatTextView) dialog.findViewById(R.id.consumer_id);
        DueAmounte = (AppCompatTextView) dialog.findViewById(R.id.DueAmount);
        OrderIde = (AppCompatTextView) dialog.findViewById(R.id.OrderId);
        DueDatee = (AppCompatTextView) dialog.findViewById(R.id.DueDate);
        BillDatee = (AppCompatTextView) dialog.findViewById(R.id.BillDate);
        pay_bill = (AppCompatButton) dialog.findViewById(R.id.pay_bill);
        status = (AppCompatTextView) dialog.findViewById(R.id.status);

            status.setText("Transaction Successful");
            consumer_namee.setText("Mobile Number: " + data.getMobileNumber());
            consumer_ide.setText("Operator: " + data.getMobileOperator());
            DueAmounte.setText("Amount: " + ccavenue_data.getCcavenueData().getAmount());
            OrderIde.setText("Order ID: " + data.getMerchantrefno());
            DueDatee.setText("Transaction No: " + data.getTransactionId());
            BillDatee.setText("Transaction_type: " + data.getTransactionType()  );


            pay_bill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ii = new Intent(ServicePaymentActivity.this, MobileRechargeActivity.class);
                    startActivity(ii);
                    finishAffinity();
                }
            });


            dialog.show();



    }

}
