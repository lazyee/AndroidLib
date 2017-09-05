package com.leeorz.lib.base;

import com.leeorz.lib.api.ApiManager;

/**
 * Created by lee on 17/3/12.
 */

public class BaseModel {
    void cancelRequest(){
        ApiManager.getInstance().cancel(this);
    }
}
