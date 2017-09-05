package com.leeorz.lib.api;

/**
 * Created by lee on 17/4/3.
 */

public interface ApiCallback<T> {

    void onSuccess(ApiResult<T> result);
    void onFail(Throwable e);
}
