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
     * 跳转Activity
     *
     * @param clazz
     * @param bundle
     */
    protected void gotoActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转Activity
     *
     * @param clazz
     */
    protected void gotoActivity(Class clazz) {
        gotoActivity(clazz,null);
    }

    protected void gotoActivity(String className){
        gotoActivity(className,null);
    }

    protected void gotoActivity(String className,Bundle bundle){
        try {
            Intent intent = new Intent(this,Class.forName(className));
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 跳转activity for result
     *
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    protected void gotoActivityForResult(Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void gotoActivityForResult(Class clazz,int requestCode){
        gotoActivityForResult(clazz,null,requestCode);
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
