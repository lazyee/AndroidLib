package com.leeorz.lib.widget.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lee on 2017/4/21.
 */

public class AutoLoadMoreListView extends ListView implements AutoLoadMoreCallBack{

    private AutoLoadMoreController autoLoadMoreController;
    public AutoLoadMoreListView(Context context) {
        super(context);
        autoLoadMoreController = new AutoLoadMoreController(context,this);
    }

    public AutoLoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        autoLoadMoreController = new AutoLoadMoreController(context,this);
    }

    public AutoLoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        autoLoadMoreController = new AutoLoadMoreController(context,this);
    }

    @Override
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        autoLoadMoreController.setOnLoadMoreListener(onLoadMoreListener);
    }

    public AutoLoadMoreController getController() {
        return autoLoadMoreController;
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
