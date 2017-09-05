package com.leeorz.lib.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapter<T> extends android.widget.BaseAdapter {
	
	protected Context mContext;
	protected LayoutInflater mInflater;
	protected List<T> data = new ArrayList<T>();
	
	public BaseAdapter(Context context){
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}
	
	/**
	 * 设置数据源
	 * @param d
	 */
	public void setData(List<T> d){
		data = d;
	}

	public List<T> getData() {
		return data;
	}

	/**
	 * 清空所有数据
	 */
	public void clearAll(){
		data.clear();
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public T getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}
