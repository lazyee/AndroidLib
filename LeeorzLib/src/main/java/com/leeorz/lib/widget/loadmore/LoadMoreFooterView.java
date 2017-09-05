package com.leeorz.lib.widget.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leeorz.lib.R;
import com.leeorz.lib.utils.AppUtil;
import com.leeorz.lib.utils.ToastUtil;


/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/8/8 下午5:13
 * description:
 */
public class LoadMoreFooterView extends LinearLayout implements View.OnClickListener{

    private LinearLayout llLoadMore,llLoadFail;
    private TextView tvRetry,tvLoading,tvNoMore;
    public boolean isLoadMoreing = false;
    public boolean isNoMore = true;

    public int startPageNum = 0;
    public int pageNo = startPageNum;
    public int startLoadMoreItemIndex = 3;//开始加载更多的位置索引
    private OnLoadMoreListener onLoadMoreListener;

    public LoadMoreFooterView(Context context) {
        super(context);
        initView();
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.widget_load_more,this,true);
        llLoadFail = (LinearLayout) findViewById(R.id.ll_load_fail);
        llLoadMore = (LinearLayout) findViewById(R.id.ll_load_more);
        tvLoading = (TextView) findViewById(R.id.tv_loading);
        tvRetry = (TextView) findViewById(R.id.tv_retry);
        tvNoMore = (TextView) findViewById(R.id.tv_nomore);
        tvRetry.setOnClickListener(this);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /**
     * 重设
     */
    public void reset(){
        isNoMore = false;
        isLoadMoreing = false;
        pageNo = startPageNum;
    }

    /**
     * 没有更多数据了
     */
    public void noMoreData(){
        isLoadMoreing = false;
        isNoMore = true;
        if(!llLoadMore.isShown()){
            llLoadMore.setVisibility(View.VISIBLE);
            llLoadFail.setVisibility(View.GONE);
        }

        tvLoading.setVisibility(View.GONE);
        tvNoMore.setVisibility(View.VISIBLE);
    }

    public void loading(){
        isLoadMoreing = true;
        if(!llLoadMore.isShown()){
            llLoadMore.setVisibility(View.VISIBLE);
            llLoadFail.setVisibility(View.GONE);
        }
        tvLoading.setVisibility(View.VISIBLE);
        tvNoMore.setVisibility(View.GONE);
    }



    /**
     * 加载失败
     */
    public void loadFail(){
        isLoadMoreing = false;
        isNoMore = true;
        if(llLoadMore.isShown()){
            llLoadFail.setVisibility(View.VISIBLE);
            llLoadMore.setVisibility(View.GONE);
        }
        llLoadMore.setVisibility(View.GONE);
        llLoadFail.setVisibility(View.VISIBLE);
    }

    /**
     * 数据加载完成
     */
    public void complete(boolean hasNext){
        isLoadMoreing = false;
        isNoMore = !hasNext;
        if(isNoMore){
            noMoreData();
        }
        pageNo++;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_retry) {
            if(onLoadMoreListener != null){
                if(!AppUtil.isNetworkAvailable(getContext())){
                    ToastUtil.showShort(getContext(),"当前设备无网络连接，请打开网络再试...");
                    return;
                }
                loading();
                onLoadMoreListener.onLoadMore(pageNo);
            }
        }
    }
}
