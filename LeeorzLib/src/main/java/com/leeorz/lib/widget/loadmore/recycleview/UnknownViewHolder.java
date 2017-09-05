package com.leeorz.lib.widget.loadmore.recycleview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leeorz.lib.R;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/8/8 上午11:19
 * description:
 */
public class UnknownViewHolder extends BaseViewHolder<String>{

    private TextView tvText;

    public static UnknownViewHolder newInstance(Context context, ViewGroup viewGroup) {
        return new UnknownViewHolder(context,getView(context,viewGroup, R.layout.template_unkown));
    }

    public UnknownViewHolder(Context context,View itemView) {
        super(context,itemView);
        tvText = (TextView) itemView.findViewById(R.id.tv_text);

    }

    @Override
    public void bindViewHolder(String str) {
        tvText.setText(str);
    }
}
