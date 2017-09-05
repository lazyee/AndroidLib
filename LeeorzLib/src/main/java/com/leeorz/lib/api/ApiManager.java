package com.leeorz.lib.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/7/31 上午10:44
 * description:
 */
public class ApiManager {
    private static ApiManager apiManager;
    private List<ApiObj> apiList = new ArrayList();

    public static synchronized ApiManager getInstance(){
        if(apiManager == null){
            apiManager = new ApiManager();
        }
        return apiManager;
    }


    public <T>Observable<T> add(Object tag,Observable<T> observable){
        apiList.add(new ApiObj(tag,observable));
        return observable;
    }

    public void cancel(Object tag){
        for (Iterator iterator = apiList.iterator(); iterator.hasNext();) {
            ApiObj api = (ApiObj)iterator.next();
            if(api.getTag() == tag){
                iterator.remove();
            }
        }
    }

    private class ApiObj{
        private Object tag;
        private Observable observable;

        public ApiObj(Object tag, Observable observable) {
            this.tag = tag;
            this.observable = observable;
        }

        public Object getTag() {
            return tag;
        }

        public Observable getObservable() {
            return observable;
        }
    }
}
