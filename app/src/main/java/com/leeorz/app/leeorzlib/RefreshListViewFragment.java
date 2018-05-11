package com.leeorz.app.leeorzlib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leeorz.lib.base.BaseFragment;
import com.leeorz.lib.widget.refresh.RefreshListView;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2018/5/11 下午2:27
 * description:
 */
public class RefreshListViewFragment extends BaseFragment {

    public static RefreshListViewFragment newInstance() {

        Bundle args = new Bundle();

        RefreshListViewFragment fragment = new RefreshListViewFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        contentView = inflater.inflate(R.layout.fragment_refresh_list_view,null);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RefreshListView listView = (RefreshListView) contentView.findViewById(R.id.lv_content);
        listView.autoRefresh();
    }
}
