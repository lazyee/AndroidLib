package com.leeorz.lib.api;

/**
 * Created by lee on 17/2/16.
 */
public class ApiResult<T> {
    private final int DEF_RESULT_CODE = -999;
    private int status = DEF_RESULT_CODE;
    private T result;
    private String message;

    @Deprecated
    private int code = DEF_RESULT_CODE;
    @Deprecated
    private T obj;
    @Deprecated
    public T getObj() {
        return obj;
    }
    @Deprecated
    public void setObj(T obj) {
        this.obj = obj;
    }
    @Deprecated
    public int getCode() {
        return code;
    }
    @Deprecated
    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        if(status == DEF_RESULT_CODE){
            return getCode();
        }
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        if(result == null){
           return getObj();
        }
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
