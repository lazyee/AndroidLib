package com.leeorz.app.leeorzlib;

import android.os.Bundle;
import android.view.View;

import com.leeorz.lib.base.BaseActivity;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2018/5/14 下午6:33
 * description:
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                gotoActivity(RefreshListViewActivity.class);
                break;
            case R.id.btn2:
                gotoActivity(RefreshListViewActivity.class);
                break;
        }
    }
}
