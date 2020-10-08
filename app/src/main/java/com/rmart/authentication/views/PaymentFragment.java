package com.rmart.authentication.views;

import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rmart.R;
import com.rmart.authentication.OnAuthenticationClickedListener;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.models.RSAKeyResponseDetails;
import com.rmart.customer.views.PaymentOptionsFragment;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.ccavenue.AvenuesParams;
import com.rmart.utilits.ccavenue.CCAvenueResponse;
import com.rmart.utilits.ccavenue.RSAUtility;
import com.rmart.utilits.ccavenue.ServiceUtility;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class PaymentFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RSAKeyResponseDetails rsaKeyResponseDetails;
    private OnAuthenticationClickedListener authenticationClickedListener;
    private String mParam1;
    private String mParam2;

    private WebView webview;

    public PaymentFragment() {
        // Required empty public constructor
    }
    public static PaymentFragment newInstance(RSAKeyResponseDetails rsaKeyResponse, String param2) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, rsaKeyResponse);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        authenticationClickedListener = (OnAuthenticationClickedListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rsaKeyResponseDetails = (RSAKeyResponseDetails) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(R.string.title_payment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webview = view.findViewById(R.id.web_view);
        initWebView();
    }
    private void initWebView() {
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
                handler.proceed();
                // Ignore SSL certificate errors
            }

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
    private void loadWebView() {
        try {
            /* An instance of this class will be registered as a JavaScript interface */
            StringBuilder params = new StringBuilder();
            String access_code, merchant_id, redirect_url, cancel_url, billing_country, merchant_param1, merchant_param2,
                    billing_name, billing_email, billing_tel, encVal = "";
            access_code = rsaKeyResponseDetails.getAccessCode(); //getString(R.string.access_code);
            merchant_id = rsaKeyResponseDetails.getMerchantId(); //getString(R.string.merchant_id);
            redirect_url = rsaKeyResponseDetails.getRedirectUrl(); // getString(R.string.redirect_url);
            cancel_url = rsaKeyResponseDetails.getCancelUrl(); // getString(R.string.cancel_url);
            billing_country = rsaKeyResponseDetails.getBillingCountry();
            // merchant_param1 = merchantParams.ticket_id;
            billing_name = rsaKeyResponseDetails.getBillingName();//"shweta";
            billing_email = rsaKeyResponseDetails.getBillingEmail(); // "shwetadalvi9@gmail.com";
            billing_tel =rsaKeyResponseDetails.getBillingTel(); // "8446399429";
            StringBuilder vEncVal = new StringBuilder();

            String amount = String.valueOf(rsaKeyResponseDetails.getAmount()).replace("," ,"");
            String currency = getString(R.string.currency);
            vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, amount));
            vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, currency));
            encVal = RSAUtility.encrypt(vEncVal.substring(0, vEncVal.length() - 1), rsaKeyResponseDetails.getRsaKey().replaceAll("[\n]", ""));

            params.append(ServiceUtility.addToPostParams(AvenuesParams.ACCESS_CODE, access_code));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_ID, merchant_id));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ORDER_ID, String.valueOf(rsaKeyResponseDetails.getOrderId())));
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
            String vTransUrl = ("https://test.ccavenue.com/transaction/initTrans");
            Log.d("vPostParams", "vPostParams :"+vPostParams);
            Log.d("vTransUrl", "vTransUrl :"+vPostParams);
            webview.postUrl(vTransUrl, vPostParams.getBytes(StandardCharsets.UTF_8));// EncodingUtils.getBytes(vPostParams, "UTF-8"));
        } catch (Exception e) {
            showDialog(e.getMessage());
        }
    }
    class MyJavaScriptInterface {
        private JsonObject jsonObject;

        @JavascriptInterface
        public void processHTML(String html) {
            Log.d("JsonObject", "html data: " + html);
            jsonObject = new JsonParser().parse(html).getAsJsonObject();
            Gson g = new Gson();
            CCAvenueResponse ccAvenueResponse = g.fromJson(html, CCAvenueResponse.class);
            if (ccAvenueResponse.getOrder_status().equalsIgnoreCase("success")) {
                requireActivity().onBackPressed();
                showSuccessDialog(ccAvenueResponse.getOrder_message() + " " +rsaKeyResponseDetails.getOTPMsg());
            }else{
                showDialog(getString(R.string.message), ccAvenueResponse.getOrder_message(), pObject -> requireActivity().getSupportFragmentManager().popBackStack());

            }
        }

        @JavascriptInterface
        public void gotMsg(String msg) {
            showDialog("", msg);
            // Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
    private void showSuccessDialog(String orderedMessage) {
        showDialog(orderedMessage, pObject -> {
            authenticationClickedListener.validateOTP(rsaKeyResponseDetails.getUserMobileNumber());
/*            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            String name = fragmentManager.getBackStackEntryAt(0).getName();
            fragmentManager.popBackStackImmediate(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
        });
    }
}