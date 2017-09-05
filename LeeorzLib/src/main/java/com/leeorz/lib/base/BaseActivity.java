package com.leeorz.lib.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.leeorz.lib.app.AppConfig;
import com.leeorz.lib.app.AppManager;


public class BaseActivity extends AppCompatActivity implements BaseView {

    /**
     * 使用Log来调试时使用此变量，定位包名
     */
    public String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TAG = getClass().getName();
        AppManager.getAppManager().addActivity(this); //将activity推入管理栈
        AppConfig.init(this); //初始化参数

        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onDestroy() { //销毁时activity出栈
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();

    }


    /**
     * 设置默认内容
     *
     * @param layoutResID
     */
    public void setContentViewDefault(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        this.setContentView(view);
    }

    /**
     * 跳转Activity
     *
     * @param activity
     * @param bundle
     */
    protected void gotoActivity(Class<? extends Activity> activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转Activity
     *
     * @param activity
     */
    protected void gotoActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);

    }


    /**
     * 跳转activity for result
     *
     * @param activity
     * @param bundle
     * @param requestCode
     */
    protected void gotoActivityForResult(Class<? extends Activity> activity, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);

    }

    /**
     * 获取当前activity实例
     *
     * @return
     */
    protected Activity getActivity() {
        return this;
    }


    @Override
    public void onUserTokenInvalid() {

    }
}
