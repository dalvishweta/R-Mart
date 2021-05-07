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
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.baseclass.views.CustomLoadingDialog;
import com.rmart.electricity.CCavenueres;
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
            String accessCode, merchantId, redirect_url, cancel_url, billing_country, merchant_param1, merchant_param2, billing_name, billing_email, billing_tel, encVal = "";
            accessCode = ccavenue_data.getCcavenueData().getAccessCode(); //getString(R.string.access_code);
            merchantId = ccavenue_data.getCcavenueData().getMerchantId(); //getString(R.string.merchant_id);
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

            params.append(ServiceUtility.addToPostParams(AvenuesParams.ACCESS_CODE, accessCode));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_ID, merchantId));
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
            webview.postUrl(vTransUrl, vPostParams.getBytes(StandardCharsets.UTF_8));// EncodingUtils.getBytes(vPostParams, "UTF-8"));
        } catch (Exception e) {
            showDialog(e.getMessage(),"");
        }
    }



    class MyJavaScriptInterface {

        @JavascriptInterface
        public void processHTML(String html) {
            Log.d("JsonObject", "html data: " + html);


                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT, html);
                setResult(ServicePaymentActivity.RESULT_OK, returnIntent);
                finish();
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
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(RESULT, msg);
                    setResult(ServicePaymentActivity.RESULT_OK, returnIntent);
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            // LoggerInfo.errorLog("show dialog exception", e.getMessage());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please click the close/cancel button on the top right corner to cancel the payment.", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
