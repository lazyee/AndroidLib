package com.leeorz.lib.api;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by lee on 17/4/2.
 */

public class API{
    private Disposable disposable;
    private final String TAG = "API";
    private Retrofit retrofit;
    public static Map<String,API> apiInstanceMap = new HashMap<>();

    public static <T> T getInstance(Class<T> clazz) {
        return getInstance(clazz,ApiConfig.HOST);
    }

    public static <T> T getInstance(Class<T> clazz,String host){

        if(apiInstanceMap.get(host) == null){
            API api = new API(host);
            apiInstanceMap.put(host,api);
        }



        return apiInstanceMap.get(host).create(clazz);
    }

    private API(String host) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();
            okHttpClientBuilder.connectTimeout(ApiConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(ApiConfig.DEFAULT_TIMEOUT,TimeUnit.SECONDS);
            okHttpClientBuilder.writeTimeout(ApiConfig.DEFAULT_TIMEOUT,TimeUnit.SECONDS);
            okHttpClientBuilder.addInterceptor(getHttpLoggingInterceptor());


            okHttpClientBuilder.sslSocketFactory(sslSocketFactory);
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            okHttpClientBuilder.retryOnConnectionFailure(true);
//            retrofit = new Retrofit.Builder().baseUrl(ApiConfig.HOST)
            retrofit = new Retrofit.Builder().baseUrl(host)
                    .client(okHttpClientBuilder.build())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T create(Class<T> clazz){
        return retrofit.create(clazz);
    }

    /**
     * 设置日志拦截
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG,message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

}
