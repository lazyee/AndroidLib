package com.leeorz.app.leeorzlib;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.leeorz.lib.base.BaseActivity;
import com.leeorz.lib.utils.photo.OnDealImageListener;
import com.leeorz.lib.utils.photo.Photo;
import com.leeorz.lib.utils.photo.PhotoUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends BaseActivity implements OnDealImageListener{

    private PhotoUtil photoUtil;
    private ImageView ivImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        photoUtil = new PhotoUtil(getActivity());
        photoUtil.setFreeCrop(true);
        photoUtil.setOnDealImageListener(this);

        ivImage = (ImageView) findViewById(R.id.ivImage);

        findViewById(R.id.btnOpenAlbum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoUtil.openAlbum(1);
            }
        });

        findViewById(R.id.btnOpenCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoUtil.openCamera();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode:" + requestCode + ";resultCode:" + resultCode + ";data:" + data);
        photoUtil.dealImage(requestCode,resultCode,data);
    }

    @Override
    public void onDealSingleImageComplete(Photo photo) {
        Picasso.with(getActivity())
                .load("file://" + photo.getUrl())
                .into(ivImage);
    }

    @Override
    public void onDealMultiImageComplete(List<Photo> list) {
    }
}
