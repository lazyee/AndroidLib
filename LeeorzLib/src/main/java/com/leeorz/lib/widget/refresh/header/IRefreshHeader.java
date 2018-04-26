package com.leeorz.lib.widget.refresh.header;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/6/28 上午11:31
 * description:
 */
public interface IRefreshHeader {

    int getVisibleHeight();
    int getContainerHeight();
    void onProgress(int progress);
    void onRefresh();
    void onRefreshComplete();
    void setVisibleHeight(int height);
}
