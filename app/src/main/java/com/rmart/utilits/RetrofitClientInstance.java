package com.rmart.utilits;

import android.annotation.SuppressLint;
import android.content.Context;
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
    private static  Retrofit retrofit2=null;
    public static RetrofitClientInstance apiClient;

    private static String creds = String.format("%s:%s", BuildConfig.AUTH_USERNAME, BuildConfig.AUTH_PASSWORD);
    private static String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
    private static String creds_rokad = String.format("%s:%s", BuildConfig.AUTH_USERNAME_ROKAD, BuildConfig.AUTH_PASSWORD_ROKAD);
    private static String auth_roakd = "Basic " + Base64.encodeToString(creds_rokad.getBytes(), Base64.NO_WRAP);

    public static RetrofitClientInstance getInstance() {
        if (apiClient == null) {
            apiClient = new RetrofitClientInstance();
        }
        return apiClient;
    }

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
  /*  public static Retrofit getRetrofitInstance() {
        return getRetrofitInstance(null);
    }
*/
    public static Retrofit getRetrofitInstanceRokad() {
        return getRetrofitInstanceRokad(null);
    }
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
                String test = auth.replace("\n","");
                Interceptor basicAuth = chain -> {
                    //String test = auth.replace("\n","");
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
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
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

    public static Retrofit getRetrofitInstanceForCustomer() {
        if (retrofit == null) {
            try {

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.level(HttpLoggingInterceptor.Level.BODY);
                String test = auth.replace("\n","");
                Interceptor basicAuth = chain -> {
                    //String test = auth.replace("\n","");
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type","application/x-www-form-urlencoded")
                            .addHeader("API_KEY","abcdefghijklmn")
                            .addHeader("Authorization", test)
                            .build();
                    return chain.proceed(request);
                };

                OkHttpClient client = new OkHttpClient.Builder()
                        .sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0])
                        .addInterceptor(basicAuth)
                        .addInterceptor(logging)
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build();

                Gson gs = new GsonBuilder().setLenient().create();

                retrofit = new Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL_LOGIN)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gs))
                        .build();
            } catch (Exception e) {
                Log.d("Exception", "Exception : "+e.getMessage());
            }

        }
        return retrofit;
    }

    public static Retrofit getRetrofitInstanceForAddProduct() {
        Retrofit retrofit = null;
        if (retrofit == null) {
            try {

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.level(HttpLoggingInterceptor.Level.BODY);
                String test = auth.replace("\n","");
                Interceptor basicAuth = chain -> {
                    //String test = auth.replace("\n","");
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type","application/json")
                            // .addHeader("X-API-KEY",BuildConfig.API_KEY)
                            .addHeader("Authorization", test)
                            .build();
                    return chain.proceed(request);
                };

                OkHttpClient client = new OkHttpClient.Builder()
                        .sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0])
                        .addInterceptor(basicAuth)
                        .addInterceptor(logging)
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
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

  private static Retrofit getRetrofitInstanceRokad(final Context context) {
        if (retrofit2 == null) {
            try {

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.level(HttpLoggingInterceptor.Level.BODY);
                String test = auth_roakd.replace("\n","");
                Interceptor basicAuth = chain -> {
                    //String test = auth.replace("\n","");
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type","application/x-www-form-urlencoded")
                            .addHeader("X-API-KEY","abcdefghijklmn")
                            .addHeader("Authorization", test)
                            .build();
                    return chain.proceed(request);
                };

                OkHttpClient client = new OkHttpClient.Builder()
                        .sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0])
                        .addInterceptor(basicAuth)
                        .addInterceptor(logging)
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build();

                Gson gs = new GsonBuilder().setLenient().create();

                retrofit2 = new Retrofit.Builder()
                        .baseUrl("https://rokad.in/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gs))
                        .build();
            } catch (Exception e) {
                Log.d("Exception", "Exception : "+e.getMessage());
            }

        }
        return retrofit2;
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
