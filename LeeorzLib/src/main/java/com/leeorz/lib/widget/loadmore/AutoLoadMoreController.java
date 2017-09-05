package com.leeorz.lib.widget.loadmore;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import in.srain.cube.views.GridViewWithHeaderAndFooter;


/**
 * Created by juweitu on 2017/6/3.
 */

public class AutoLoadMoreController implements AbsListView.OnScrollListener {

    protected final String TAG = getClass().getName();

    protected OnLoadMoreListener onLoadMoreListener;
    private LoadMoreFooterView loadMoreFooterView;
    private AbsListView loadMoreView;
    private Context mContext;
    public int firstVisibleItem;

    public AutoLoadMoreController(Context context, AbsListView loadMoreView) {
        this.loadMoreView = loadMoreView;
        mContext = context;
        init();
    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        loadMoreFooterView.setOnLoadMoreListener(onLoadMoreListener);
    }

    public void setStartPageNum(int startPageNum) {
        loadMoreFooterView.startPageNum = startPageNum;
    }

    public void complete(boolean hasMore){
        loadMoreFooterView.complete(hasMore);
    }

    public void loadFail(){
        loadMoreFooterView.loadFail();
    }

    public int getPageNo(){
        return loadMoreFooterView.pageNo;
    }

    /**
     * 重设
     */
    public void reset(){
        loadMoreFooterView.reset();
    }

    /**
     * 刷新
     */
    public void refresh(){
        loadMoreFooterView.pageNo = loadMoreFooterView.startPageNum;
        if(onLoadMoreListener != null){
            loadMoreFooterView.loading();
            onLoadMoreListener.onLoadMore(loadMoreFooterView.pageNo);
        }
    }

    void init(){
        loadMoreView.setOnScrollListener(this);

        loadMoreFooterView = new LoadMoreFooterView(mContext);

        if(loadMoreView instanceof ListView){
            ((ListView)loadMoreView).addFooterView(loadMoreFooterView);
        }else if(loadMoreView instanceof GridViewWithHeaderAndFooter){
            ((GridViewWithHeaderAndFooter)loadMoreView).addFooterView(loadMoreFooterView);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            //如果在暂停或者触摸的情况下完成重置
            Picasso.with(loadMoreView.getContext()).resumeTag(loadMoreView.getContext());
        } else {
            //停止更新
            Picasso.with(loadMoreView.getContext()).pauseTag(loadMoreView.getContext());
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        if(loadMoreFooterView == null)return;
        if(loadMoreFooterView.isNoMore)return;
        if((loadMoreView.getLastVisiblePosition() > loadMoreView.getCount() - loadMoreFooterView.startLoadMoreItemIndex)
                && !loadMoreFooterView.isLoadMoreing
                && onLoadMoreListener != null){
            loadMoreFooterView.loading();
            onLoadMoreListener.onLoadMore(loadMoreFooterView.pageNo);
        }
    }
}
