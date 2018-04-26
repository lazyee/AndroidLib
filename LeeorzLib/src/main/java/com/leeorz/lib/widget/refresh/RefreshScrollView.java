package com.leeorz.lib.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.leeorz.lib.widget.refresh.header.CircleProgressHeader;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/6/29 下午9:31
 * description:
 */
public class RefreshScrollView extends RefreshLayout {

    private View rootLayout;
    private MyScrollView scrollView;
    private CircleProgressHeader refreshHeader;
    private OnScrollListener onScrollListener;

    public RefreshScrollView(Context context) {
        super(context);
    }

    public RefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected void initView() {
        super.initView();

        scrollView = new MyScrollView(getContext());
        scrollView.setOverScrollMode(OVER_SCROLL_NEVER);

        refreshHeader = new CircleProgressHeader(getContext());
        refreshHeader.setVisibleHeight(0);
    }

    @Override
    public boolean isMove2Top() {
        return scrollView.getScrollY() == 0;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if(getChildCount()>1) throw new IllegalStateException("RefreshScrollView can only contains 1 children");
        if(rootLayout == null){
            rootLayout = getChildAt(0);
            removeView(rootLayout);
            scrollView.addView(rootLayout);

            setRefreshHeader(refreshHeader);
            addView(scrollView);
        }
    }


    private class MyScrollView extends ScrollView{

        public MyScrollView(Context context) {
            super(context);
        }

        @Override
        protected void onScrollChanged(int x, int y, int oldx, int oldy) {
            super.onScrollChanged(x, y, oldx, oldy);
            if (onScrollListener != null) {
                onScrollListener.onScroll();
            }
        }

        @Override
        public void computeScroll() {
            super.computeScroll();
            if(onScrollListener!=null){
                onScrollListener.onScroll();
            }
        }
    }

    public interface OnScrollListener{
        void onScroll();
    }
}
