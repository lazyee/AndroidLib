package com.leeorz.lib.widget.loadmore;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by lee on 2017/4/21.
 */

public class AutoLoadMoreGridView extends GridViewWithHeaderAndFooter implements AutoLoadMoreCallBack{

    private AutoLoadMoreController autoLoadMoreController;
    public AutoLoadMoreGridView(Context context) {
        super(context);
        autoLoadMoreController = new AutoLoadMoreController(context,this);

    }

    public AutoLoadMoreGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        autoLoadMoreController = new AutoLoadMoreController(context,this);
    }

    public AutoLoadMoreGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        autoLoadMoreController = new AutoLoadMoreController(context,this);
    }

    @Override
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        autoLoadMoreController.setOnLoadMoreListener(onLoadMoreListener);
    }

    @Override
    public void complete(boolean hasNext) {
        autoLoadMoreController.complete(hasNext);
    }

    @Override
    public void reset() {
        autoLoadMoreController.reset();
    }

    @Override
    public void loadFail() {
        autoLoadMoreController.loadFail();
    }

    @Override
    public int getPageNo() {
        return autoLoadMoreController.getPageNo();
    }

    @Override
    public void refresh() {
        autoLoadMoreController.refresh();
    }

    @Override
    public void setStartPageNum(int num) {
        autoLoadMoreController.setStartPageNum(num);
    }

}
