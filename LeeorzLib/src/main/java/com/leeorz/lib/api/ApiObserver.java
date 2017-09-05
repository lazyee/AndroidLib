package com.leeorz.lib.api;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by lee on 17/4/3.
 */

public class ApiObserver implements Observer<ApiResult> {
    private ApiCallback callback;

    public ApiObserver(ApiCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(ApiResult value) {
        if (callback != null) {
            callback.onSuccess(value);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (callback != null) {
            callback.onFail(e);
        }
    }

    @Override
    public void onComplete() {

    }
}
