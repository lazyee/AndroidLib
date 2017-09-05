package com.leeorz.lib.widget.loadmore;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/8/8 下午5:23
 * description:
 */
public interface AutoLoadMoreCallBack {
    void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener);
    void complete(boolean hasNext);
    void reset();
    void loadFail();
    int getPageNo();
    void refresh();
    void setStartPageNum(int num);
}
