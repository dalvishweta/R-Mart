package com.rmart.customer.views;

import android.net.http.SslError;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.customer.models.ProductOrderedResponseModel;
import com.rmart.customer.models.RSAKeyResponseDetails;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.UpdateCartCountDetails;
import com.rmart.utilits.Utils;
import com.rmart.utilits.ccavenue.AvenuesParams;
import com.rmart.utilits.ccavenue.CCAvenueResponse;
import com.rmart.utilits.ccavenue.RSAUtility;
import com.rmart.utilits.ccavenue.ServiceUtility;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentOptionsFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView ivCashOnDeliveryImageField;
    private ImageView ivInternetBankingImageField;
    private ImageView ivMyWalletImageField;
    private int selectedPaymentType = -1;
    private ShopDetailsModel vendorShopDetails;
    private RSAKeyResponseDetails rsaKeyResponseDetails;
    private WebView webview;
    private LinearLayout paymentOptionsLayoutField;

    public PaymentOptionsFragment() {
        // Required empty public constructor
    }

    public static PaymentOptionsFragment getInstance(ShopDetailsModel vendorShopDetails, int selectedPaymentType) {
        PaymentOptionsFragment fragment = new PaymentOptionsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, vendorShopDetails);
        args.putInt(ARG_PARAM2, selectedPaymentType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vendorShopDetails = (ShopDetailsModel) getArguments().getSerializable(ARG_PARAM1);
            selectedPaymentType =  getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoggerInfo.printLog("Fragment", "PaymentOptionsFragment");
        return inflater.inflate(R.layout.fragment_payment_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        ivCashOnDeliveryImageField = view.findViewById(R.id.iv_cash_on_delivery_field);
        ivInternetBankingImageField = view.findViewById(R.id.iv_internet_banking_field);
        ivMyWalletImageField = view.findViewById(R.id.iv_my_wallet_field);

        webview = view.findViewById(R.id.web_view);

        paymentOptionsLayoutField = view.findViewById(R.id.payment_options_layout_field);
        view.findViewById(R.id.cash_on_delivery_layout_field).setOnClickListener(v -> cashOnDeliverySelected());
        view.findViewById(R.id.internet_banking_layout_field).setOnClickListener(v -> internetBankingSelected());
        view.findViewById(R.id.my_wallet_layout_field).setOnClickListener(v -> myWalletSelected());
        view.findViewById(R.id.btn_proceed_field).setOnClickListener(v -> {
            proceedSelected();
        }
        );
        proceedSelected();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateToolBar();
    }

    public void updateToolBar() {
        requireActivity().setTitle(getString(R.string.make_payment));
        ((CustomerHomeActivity) (requireActivity())).hideCartIcon();
    }

    private void cashOnDeliverySelected() {
        resetItems();
        selectedPaymentType = 1;
        ivCashOnDeliveryImageField.setImageResource(R.drawable.ic_checked);
    }

    private void internetBankingSelected() {
        resetItems();
        selectedPaymentType = 2;
        ivInternetBankingImageField.setImageResource(R.drawable.ic_checked);
    }

    private void myWalletSelected() {
        resetItems();
        selectedPaymentType = 3;
        ivMyWalletImageField.setImageResource(R.drawable.ic_checked);
    }

    private void resetItems() {
        ivCashOnDeliveryImageField.setImageResource(R.drawable.ic_un_checked);
        ivInternetBankingImageField.setImageResource(R.drawable.ic_un_checked);
        ivMyWalletImageField.setImageResource(R.drawable.ic_un_checked);
    }

    private void proceedSelected() {
        if (selectedPaymentType == -1) {
            showDialog(getString(R.string.please_select_payment_type));
            return;
        }
        //listener.gotoChangeAddress();
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            MyProfile myProfile = MyProfile.getInstance();
            Call<ProductOrderedResponseModel> call = customerProductsService.savePlaceToOrder(clientID, vendorShopDetails.getVendorId(), myProfile.getPrimaryAddressId(),
                    myProfile.getUserID(), vendorShopDetails.getShopId(), selectedPaymentType,vendorShopDetails.deliveryMethod,MyProfile.getInstance().getRoleID());
            call.enqueue(new Callback<ProductOrderedResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<ProductOrderedResponseModel> call, @NotNull Response<ProductOrderedResponseModel> response) {

                    if (response.isSuccessful()) {
                        ProductOrderedResponseModel body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                ProductOrderedResponseModel.ProductOrderedResponseDetails productOrderedResponseDetails = body.getProductOrderedResponseDetails();
                                if (selectedPaymentType == 1) {
                                    showSuccessDialog(productOrderedResponseDetails.getOrderMessage());
                                    UpdateCartCountDetails.updateCartCountDetails.onNext(productOrderedResponseDetails.getTotalCartCount());
                                } else if (selectedPaymentType == 2) {
                                    paymentOptionsLayoutField.setVisibility(View.GONE);
                                    webview.setVisibility(View.VISIBLE);
                                    rsaKeyResponseDetails = productOrderedResponseDetails.getRsaKeyResponseDetails();
                                    initWebView();
                                }
                            } else {
                                showDialog(body.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<ProductOrderedResponseModel> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
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
            billing_name = MyProfile.getInstance().getFirstName();//"shweta";
            billing_email = MyProfile.getInstance().getEmail(); // "shwetadalvi9@gmail.com";
            billing_tel = MyProfile.getInstance().getMobileNumber(); // "8446399429";
            StringBuilder vEncVal = new StringBuilder();

            String amount = String.valueOf(rsaKeyResponseDetails.getAmount());
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

            String vTransUrl = (BuildConfig.PAYMENT_URL);
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
            updateCartCount(ccAvenueResponse);
        }

        @JavascriptInterface
        public void gotMsg(String msg) {
            showDialog("", msg);
            // Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCartCount(CCAvenueResponse ccAvenueResponse) {
        requireActivity().runOnUiThread(() -> {
            if (ccAvenueResponse.getOrderStatus().equalsIgnoreCase("success")) {
                UpdateCartCountDetails.updateCartCountDetails.onNext(ccAvenueResponse.getTotalCartCount());
                showSuccessDialog(ccAvenueResponse.getOrderMessage());
            }else{
                showDialog(getString(R.string.message), ccAvenueResponse.getOrderMessage(), pObject -> {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    String name = fragmentManager.getBackStackEntryAt(0).getName();
                    fragmentManager.popBackStackImmediate(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                });
            }
        });
    }

    private void showSuccessDialog(String orderedMessage) {
        showDialog(orderedMessage, pObject -> {
            requireActivity().runOnUiThread(() -> {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                String name = fragmentManager.getBackStackEntryAt(0).getName();
                fragmentManager.popBackStackImmediate(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            });
        });
    }
}