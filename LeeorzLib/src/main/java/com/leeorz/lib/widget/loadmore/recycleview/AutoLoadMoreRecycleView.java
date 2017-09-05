package com.leeorz.lib.widget.loadmore.recycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.leeorz.lib.widget.loadmore.LoadMoreFooterView;
import com.leeorz.lib.widget.loadmore.OnLoadMoreListener;
import com.squareup.picasso.Picasso;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/8/8 上午9:38
 * description:
 */
public class AutoLoadMoreRecycleView extends RecyclerView{

    protected final String TAG = getClass().getName();
    public LoadMoreFooterView loadMoreFooterView;
    public AutoLoadMoreRecycleViewAdapter autoLoadMoreAdapter;

    private OnLoadMoreListener onLoadMoreListener;
    public AutoLoadMoreRecycleView(Context context) {
        super(context);
        initView();
    }

    public AutoLoadMoreRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AutoLoadMoreRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        if(loadMoreFooterView != null){
            loadMoreFooterView.setOnLoadMoreListener(onLoadMoreListener);
        }
    }

    private void initView(){
        setLayoutManager(new GridLayoutManager(getContext(), AutoLoadMoreRecycleViewAdapter.SPAN_COUNT));
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        addOnScrollListener(onScrollListener);
    }

    /**
     * 移动到指定位置
     * @param position
     */
    public void move2Position(int position){
        LayoutManager manager = getLayoutManager();
        int firstItem;
        int lastItem;
        if(manager instanceof LinearLayoutManager){
            firstItem = ((LinearLayoutManager)manager).findFirstVisibleItemPosition();
            lastItem = ((LinearLayoutManager)manager).findLastVisibleItemPosition();
        }else if(manager instanceof GridLayoutManager){
            firstItem = ((GridLayoutManager)manager).findFirstVisibleItemPosition();
            lastItem = ((GridLayoutManager)manager).findLastVisibleItemPosition();
        }else{
            return;
        }

        if (position <= firstItem) {
            scrollToPosition(position);
        } else if (position <= lastItem) {
            int top = getChildAt(position - firstItem).getTop();
            scrollBy(0, top);
        } else {
            scrollToPosition(position);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if(adapter instanceof AutoLoadMoreRecycleViewAdapter){
            this.autoLoadMoreAdapter = (AutoLoadMoreRecycleViewAdapter) adapter;
            loadMoreFooterView = ((AutoLoadMoreRecycleViewAdapter)adapter).loadMoreFooterView;
            loadMoreFooterView.setOnLoadMoreListener(onLoadMoreListener);
        }
    }

    public void complete(boolean hasNext){
        if(loadMoreFooterView == null)return;
        loadMoreFooterView.complete(hasNext);
    }

    public void loadFail(){
        if(loadMoreFooterView == null)return;
        loadMoreFooterView.loadFail();
    }

    public void reset(){
        if(loadMoreFooterView == null)return;
        loadMoreFooterView.reset();
    }

    /**
     * 滑动事件监听
     */
    private RecyclerView.OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);


            if (newState == SCROLL_STATE_IDLE) {
                //如果在暂停或者触摸的情况下完成重置
                Picasso.with(getContext()).resumeTag(getContext());
            } else {
                //停止更新
                Picasso.with(getContext()).pauseTag(getContext());
            }

            if(loadMoreFooterView == null)return;
            if(loadMoreFooterView.isNoMore)return;
            View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
            int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);
            if(lastPosition >= recyclerView.getLayoutManager().getItemCount() - loadMoreFooterView.startLoadMoreItemIndex){
                if(!loadMoreFooterView.isLoadMoreing && onLoadMoreListener != null){
                    loadMoreFooterView.loading();
                    onLoadMoreListener.onLoadMore(loadMoreFooterView.pageNo);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };
}
