package com.leeorz.lib.widget.h5;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.Map;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/8/16 下午4:34
 * description:
 */
public class H5WebChromeClient extends WebChromeClient {

    private ProgressBar progressbar;
    private WebViewChangeTitleCallback webViewChangeTitleCallback;


    //记录URL和title
    private String currentUrl;
    private Map<String, String> titles = new HashMap<String, String>();

    public void setProgressbar(ProgressBar progressbar) {
        this.progressbar = progressbar;
    }

    public void setWebViewChangeTitleCallback(WebViewChangeTitleCallback webViewChangeTitleCallback) {
        this.webViewChangeTitleCallback = webViewChangeTitleCallback;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if(progressbar == null)return;
        if (newProgress == 100) {
            progressbar.setVisibility(View.GONE);
        } else {
            if (!progressbar.isShown())
                progressbar.setVisibility(View.VISIBLE);
            progressbar.setProgress(newProgress);
        }

        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (TextUtils.isEmpty(title))title = "";

        titles.put(currentUrl, title);
        String currentTitle = titles.get(currentUrl);
        if (!TextUtils.isEmpty(currentTitle)) {
            if(webViewChangeTitleCallback != null){
                webViewChangeTitleCallback.onWebViewTitleChange(currentTitle);
            }
        }
    }

    public interface WebViewChangeTitleCallback{
        void onWebViewTitleChange(String title);
    }

}
