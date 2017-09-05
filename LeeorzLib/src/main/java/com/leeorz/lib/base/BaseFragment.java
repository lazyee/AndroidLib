package com.leeorz.lib.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment implements BaseView{

	protected LayoutInflater mInflater;
	protected View contentView;
	protected String TAG = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = getClass().getSimpleName();


	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mInflater = inflater;
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 跳转Activity
	 * @param activity
	 * @param bundle
	 */
	protected void gotoActivity(Class<? extends Activity> activity, Bundle bundle){
		Intent intent = new Intent(getActivity(),activity);
		if(bundle != null){
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}


	/**
	 * 跳转Activity
	 * @param activity
	 */
	protected  void gotoActivity(Class<? extends Activity> activity){
		Intent intent = new Intent(getActivity(),activity);
		startActivity(intent);

	}
	
	/**
	 * 跳转activity for result
	 * @param activity
	 * @param bundle
	 * @param requestCode
	 */
	protected void gotoActivityForResult(Class<? extends Activity> activity, Bundle bundle, int requestCode){
		Intent intent = new Intent(getActivity(),activity);
		if(bundle != null){
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
	}



	@Override
	public void onUserTokenInvalid() {

	}
}
