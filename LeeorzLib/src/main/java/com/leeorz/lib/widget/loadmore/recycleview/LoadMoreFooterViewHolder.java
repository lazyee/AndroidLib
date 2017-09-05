package com.leeorz.lib.widget.loadmore.recycleview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import com.leeorz.lib.R;
import com.leeorz.lib.widget.loadmore.LoadMoreFooterView;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/8/8 上午11:03
 * description:
 */
public class LoadMoreFooterViewHolder extends BaseViewHolder{

    public LoadMoreFooterView loadMoreFooterView;

    public static LoadMoreFooterViewHolder newInstance(Context context, ViewGroup viewGroup){
        return new LoadMoreFooterViewHolder(context,getView(context,viewGroup, R.layout.template_load_more));
    }

    public LoadMoreFooterViewHolder(Context context,View itemView) {
        super(context,itemView);
        loadMoreFooterView = (LoadMoreFooterView) itemView.findViewById(R.id.loadMoreFooterView);
    }

    @Override
    public void bindViewHolder(Object o) {

    }

    public LoadMoreFooterView getLoadMoreFooterView() {
        return loadMoreFooterView;
    }
}
