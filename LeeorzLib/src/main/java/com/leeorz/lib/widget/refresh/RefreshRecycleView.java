package com.leeorz.lib.widget.refresh;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.leeorz.lib.widget.loadmore.recycleview.AutoLoadMoreRecycleView;
import com.leeorz.lib.widget.refresh.header.CircleProgressHeader;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/8/8 上午9:36
 * description:
 */
public class RefreshRecycleView extends RefreshLayout {
    private AutoLoadMoreRecycleView autoLoadMoreRecycleView;
    private CircleProgressHeader refreshHeader;
    public RefreshRecycleView(Context context) {
        super(context);
    }

    public RefreshRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        super.initView();

        autoLoadMoreRecycleView = new AutoLoadMoreRecycleView(getContext());
        refreshHeader = new CircleProgressHeader(getContext());

        refreshHeader.setVisibleHeight(0);

        setRefreshHeader(refreshHeader);
        addView(autoLoadMoreRecycleView);
    }

    public AutoLoadMoreRecycleView getAutoLoadMoreRecycleView() {
        return autoLoadMoreRecycleView;
    }

    @Override
    boolean isMove2Top() {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) autoLoadMoreRecycleView.getLayoutManager();
        int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
        if(firstVisibleItemPosition == 0){
            View firstView = autoLoadMoreRecycleView.getLayoutManager().findViewByPosition(firstVisibleItemPosition);
            return firstView.getTop() == 0;
        }

        return false;
    }


}
