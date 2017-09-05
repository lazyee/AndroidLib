package com.leeorz.lib.widget.loadmore.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/8/8 上午11:12
 * description:
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{
    protected Context mContext;
    private String tag = "";
    public BaseViewHolder(Context context,View itemView) {
        super(itemView);
        mContext = context;
    }

    public static View getView(Context context,ViewGroup viewGroup,int resID){
        if(viewGroup == null){
            return LayoutInflater.from(context).inflate(resID,null);
        }else{
            return LayoutInflater.from(context).inflate(resID,viewGroup,false);
        }
    }


    public abstract void bindViewHolder(T t);

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
