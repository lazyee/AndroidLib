package com.leeorz.app.leeorzlib;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leeorz.lib.app.AppConfig;
import com.leeorz.lib.base.BaseActivity;
import com.leeorz.lib.base.BaseAdapter;
import com.leeorz.lib.widget.refresh.OnRefreshListener;
import com.leeorz.lib.widget.refresh.RefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2018/5/14 下午6:32
 * description:
 */
public class RefreshListViewActivity extends BaseActivity implements OnRefreshListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_refresh_list_view);

        RefreshListView refreshListView = (RefreshListView) findViewById(R.id.lv_content);
        refreshListView.setOnRefreshListener(this);
        TextView tv = new TextView(getActivity());
        tv.setText("123121");
        tv.setLayoutParams(new AbsListView.LayoutParams(AppConfig.SCREEN_WIDTH,500));
        refreshListView.getRefreshListView().addHeaderView(tv);
        TextViewAdapter textViewAdapter = new TextViewAdapter(getActivity());
        List<String> stringList = new ArrayList<>();
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");
        stringList.add("1312312");

        textViewAdapter.setData(stringList);

        refreshListView.getRefreshListView().setAdapter(textViewAdapter);


    }

    @Override
    public void onRefresh() {

    }

    private class TextViewAdapter extends BaseAdapter<String> {

        public TextViewAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            super.getView(position, convertView, parent);
            TextView tv = new TextView(getActivity());
            tv.setText("0000000");
            tv.setLayoutParams(new AbsListView.LayoutParams(AppConfig.SCREEN_WIDTH,100));
            return tv;
        }
    }
}
