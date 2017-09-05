package com.leeorz.lib.widget.loadmore.recycleview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.leeorz.lib.widget.loadmore.LoadMoreFooterView;


/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/8/9 上午10:27
 * description:
 */
public abstract class AutoLoadMoreRecycleViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public final static int SPAN_COUNT = 6;
    protected List data = new ArrayList();

    private final int FOOTER = -1;//尾部
    private final int UNKOWN = 0;
    protected Context mContext;
    protected LoadMoreFooterViewHolder loadMoreFooterViewHolder;
    protected LoadMoreFooterView loadMoreFooterView;

    public AutoLoadMoreRecycleViewAdapter(Context context) {
        this.mContext = context;
        loadMoreFooterViewHolder = LoadMoreFooterViewHolder.newInstance(mContext, null);
        loadMoreFooterView = loadMoreFooterViewHolder.getLoadMoreFooterView();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case FOOTER:return loadMoreFooterViewHolder;
            case UNKOWN:return UnknownViewHolder.newInstance(mContext, parent);
            default:return UnknownViewHolder.newInstance(mContext, parent);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case FOOTER:
                break;
            case UNKOWN:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == data.size()) return FOOTER;
        return UNKOWN;
    }


    public void setData(List data) {
        this.data = data;
    }

    public List getData() { return data; }

    public void add(Object obj){
        data.add(obj);
    }

    public void addAll(List list){
        data.addAll(list);
    }

    public void clearAll(){
        data.clear();
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        if (position >= data.size()) return null;
        return data.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    int spanSize = getGridManagerSpanSize(recyclerView,gridLayoutManager,position);
                    if (spanSize <= 0 || spanSize >= 6) {
                        spanSize = SPAN_COUNT;
                    }
                    return spanSize;
                }
            });
        }
    }

    protected abstract int getGridManagerSpanSize(RecyclerView recyclerView,GridLayoutManager manager,int position);
}

