package com.leeorz.lib.widget.h5;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.Map;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/7/3 下午4:12
 * description:
 */
public class H5WebView extends WebView {


    private H5WebChromeClient h5WebChromeClient;
    private WebViewInterceptUrlCallback webViewInterceptUrlCallback;

    public H5WebView(Context context) {
        super(context);
        initView();
    }

    public H5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public H5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setWebViewChangeTitleCallback(H5WebChromeClient.WebViewChangeTitleCallback callback) {
        this.h5WebChromeClient.setWebViewChangeTitleCallback(callback);
    }

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        if(client instanceof H5WebChromeClient){
            h5WebChromeClient = (H5WebChromeClient) client;
        }
        super.setWebChromeClient(client);
    }

    void initView(){

//        setFocusable(true);
//        setFocusableInTouchMode(false);

        h5WebChromeClient = new H5WebChromeClient();
        getSettings().setUseWideViewPort(true);
        getSettings().setJavaScriptEnabled(true);
        setWebChromeClient(h5WebChromeClient);

        getSettings().setPluginState(WebSettings.PluginState.ON);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setAllowFileAccess(true);
        getSettings().setDefaultTextEncodingName("UTF-8");
        getSettings().setGeolocationEnabled(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().setDatabaseEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(webViewInterceptUrlCallback != null){
                    if(!webViewInterceptUrlCallback.onInterceptUrl(url)){
                        view.loadUrl(url);
                    }
                }else{
                    view.loadUrl(url);
                }
                return true;
            }
        });
    }


    public void setProgressbar(ProgressBar progressbar){
        h5WebChromeClient.setProgressbar(progressbar);
    }

    public void setWebViewInterceptUrlCallback(WebViewInterceptUrlCallback callback) {
        this.webViewInterceptUrlCallback = callback;
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
        h5WebChromeClient.setCurrentUrl(url);
    }

    public interface WebViewInterceptUrlCallback{
        boolean onInterceptUrl(String url);
    }
}
