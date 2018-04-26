package com.leeorz.lib.widget.refresh.header;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.leeorz.lib.app.AppConfig;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/6/28 上午11:41
 * description:
 */
public class BaseRefreshHeader extends LinearLayout implements IRefreshHeader {
    protected View contentView;
    protected int containerHeight;
    protected int contentViewHeight;

    public BaseRefreshHeader(Context context) {
        super(context);
    }

    public BaseRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContentView(int resID){
        contentView = LayoutInflater.from(getContext()).inflate(resID,null);
        addView(contentView);
    }

    public void setContainerHeight(int height){
        containerHeight = height;
    }


    @Override
    public int getVisibleHeight() {
        return contentViewHeight;
//        return contentView.getLayoutParams().height;
    }

    @Override
    public int getContainerHeight() {
        return containerHeight;
    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onRefreshComplete() {

    }

    @Override
    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        contentViewHeight = height;
        LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
        lp.height = height;
        lp.width = AppConfig.SCREEN_WIDTH;
        contentView.setLayoutParams(lp);

    }
}
