package com.leeorz.lib.widget.refresh.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.leeorz.lib.R;
import com.leeorz.lib.util.UnitUtils;
import com.leeorz.lib.widget.CircleProgress;


/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/6/27 下午4:27
 * description:
 */
public class CircleProgressHeader extends BaseRefreshHeader {

    private CircleProgress circleProgress;
    private ImageView ivCircle;
    private RelativeLayout rlLayout;
    private RotateAnimation rotateAnimation;
    public CircleProgressHeader(Context context) {
        super(context);
        initView();
    }

    public CircleProgressHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CircleProgressHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView(){

        rotateAnimation = new RotateAnimation(360f,0f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        rotateAnimation.setRepeatMode(Animation.INFINITE);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(1000);

        setContentView(R.layout.widget_circle_header);
        circleProgress = (CircleProgress) contentView.findViewById(R.id.circleProgress);
        ivCircle = (ImageView) contentView.findViewById(R.id.iv_circle);
        rlLayout = (RelativeLayout) contentView.findViewById(R.id.rl_layout);
        setContainerHeight(UnitUtils.dp2px(getContext(),60));
    }


    @Override
    public void onProgress(int progress) {
        if(!rlLayout.isShown())rlLayout.setVisibility(VISIBLE);
        circleProgress.draw(progress);
    }

    @Override
    public void onRefresh() {
        ivCircle.startAnimation(rotateAnimation);
    }

    @Override
    public void onRefreshComplete() {
        rlLayout.setVisibility(GONE);
        ivCircle.clearAnimation();
    }
}
