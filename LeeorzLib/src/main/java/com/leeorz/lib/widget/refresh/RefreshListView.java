package com.leeorz.lib.widget.refresh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.leeorz.lib.utils.UnitUtil;

import com.leeorz.lib.widget.loadmore.AutoLoadMoreListView;
import com.leeorz.lib.widget.refresh.header.CircleProgressHeader;


/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/6/27 下午4:28
 * description:
 */
public class RefreshListView extends RefreshLayout{

    private AutoLoadMoreListView autoLoadMoreListView;
    private CircleProgressHeader refreshHeader;

    public RefreshListView(Context context) {
        super(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void initView() {
        super.initView();
        autoLoadMoreListView = new AutoLoadMoreListView(getContext());
        autoLoadMoreListView.setOverScrollMode(OVER_SCROLL_NEVER);

        refreshHeader = new CircleProgressHeader(getContext());
        refreshHeader.setVisiableHeight(0);

        setRefreshHeader(refreshHeader);
        addView(autoLoadMoreListView);
    }

    @Override
    public boolean isMove2Top(){
        View childView = autoLoadMoreListView.getChildAt(0);
        if(childView == null)return true;
        if(childView.getTop() == 0 && autoLoadMoreListView.getController().firstVisibleItem == 0 )return true;
        return false;
    }


    public AutoLoadMoreListView getRefreshListView() {
        return autoLoadMoreListView;
    }

    /**
     * 设置分割线高度
     * @param height
     */
    public void setDividerHeight(int height){
        autoLoadMoreListView.setDividerHeight(UnitUtil.dp2px(getContext(),height));
    }

    /**
     * 设置分割线
     * @param drawable
     */
    public void setDivider(Drawable drawable){
        autoLoadMoreListView.setDivider(drawable);
    }

    /**
     * 设置选中状态
     * @param drawable
     */
    public void setListSelector(Drawable drawable){
        autoLoadMoreListView.setSelector(drawable);
    }

}
