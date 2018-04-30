package com.leeorz.lib.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.leeorz.lib.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.UnsupportedEncodingException;

import okhttp3.OkHttpClient;

/**
 * Created by lee on 2017/5/23.
 */

public class PicassoUtil {

    static volatile Picasso singleton;

    public static RequestCreator load(Context context, String url){

        if(TextUtils.isEmpty(url)){
           return load(context, R.drawable.bg_loading_default);
        }else{
            url = urlEncode(url);
            return load(context,url,R.drawable.bg_loading_default);
        }
    }

    private static RequestCreator load(Context context, final String url, int placeholder){
        return getPicasso(context).load(url).placeholder(placeholder);
    }

    private static RequestCreator load(Context context, int resId){
        return getPicasso(context).load(resId).tag(context);
    }

    private static Picasso getPicasso(Context context){
        if (singleton == null) {
            synchronized (Picasso.class) {
                if (singleton == null) {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Picasso.Builder builder = new Picasso.Builder(context);
                    builder.downloader(new OkHttp3Downloader(okHttpClient));
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            exception.printStackTrace();
                        }
                    });
                    singleton = builder.build();
//                    singleton.setLoggingEnabled(false);
                }
            }
        }
        return singleton;
    }

    private  static String urlEncode(String str){
        String data="";
        try {
            for(int i=0;i<str.length();i++){
                char c=str.charAt(i);
                if(c+"".getBytes().length>1&&c!=':'&&c!='/'&&c!='?'){
                    data = data+java.net.URLEncoder.encode(c+"","utf-8");
                }else {
                    data=data+c;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally{
            data = TextUtils.isEmpty(data)?str:data;
        }
        return  data;
    }
}
