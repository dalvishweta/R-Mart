package com.rmart.utilits;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rmart.BuildConfig;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;

    private static String creds = String.format("%s:%s", BuildConfig.AUTH_USERNAME, BuildConfig.AUTH_PASSWORD);
    private static String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
    /*public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            try {

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.level(HttpLoggingInterceptor.Level.BODY);

                Interceptor basicAuth = chain -> {
                    String test = auth.replace("\n","");
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type","application/x-www-form-urlencoded")
                            // .addHeader("X-API-KEY",BuildConfig.API_KEY)
                            // .addHeader("Authorization", test)
                            .build();
                    return chain.proceed(request);
                };

                OkHttpClient client = new OkHttpClient.Builder()
                         .sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0])
                           .addInterceptor(basicAuth)
                        // .addInterceptor(logging)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                Gson gs = new GsonBuilder().setLenient().create();

                retrofit = new Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gs))
                        .build();
            } catch (Exception e) {
                Log.d("Exception", "Exception : "+e.getMessage());
            }

        }
        return retrofit;
    }*/

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            try {

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.level(HttpLoggingInterceptor.Level.BODY);

                Interceptor basicAuth = chain -> {
                    String test = auth.replace("\n","");
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type","application/x-www-form-urlencoded")
                            // .addHeader("X-API-KEY",BuildConfig.API_KEY)
                            .addHeader("Authorization", test)
                            .build();
                    return chain.proceed(request);
                };

                OkHttpClient client = new OkHttpClient.Builder()
                        .sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0])
                        .addInterceptor(basicAuth)
                        .addInterceptor(logging)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                Gson gs = new GsonBuilder().setLenient().create();

                retrofit = new Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gs))
                        .build();
            } catch (Exception e) {
                Log.d("Exception", "Exception : "+e.getMessage());
            }

        }
        return retrofit;
    }
    private final static TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };
}
