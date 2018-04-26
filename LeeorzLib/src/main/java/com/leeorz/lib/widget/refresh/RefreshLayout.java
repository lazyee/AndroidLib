package com.leeorz.lib.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.leeorz.lib.widget.refresh.header.BaseRefreshHeader;


/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/6/29 下午8:10
 * description:
 */
public abstract class RefreshLayout extends LinearLayout {

    private BaseRefreshHeader refreshHeader;
    private Scroller scroller;
    private static final float SCROLL_RATIO = 0.5f;// 阻尼系数
    private boolean isRefreshing = false;
    private boolean isRefreshComplete = false;
    private OnRefreshListener onRefreshListener;
    private int TOUCH_ACTION = -1;
    public RefreshLayout(Context context) {
        super(context);
        initView();
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public void setRefreshHeader(BaseRefreshHeader header){
        refreshHeader = header;
        addView(refreshHeader,0);
    }

    protected void initView() {
        setOrientation(VERTICAL);
        scroller = new Scroller(getContext());
    }

    public void setOnRefreshListener(OnRefreshListener l) {
        this.onRefreshListener = l;
    }

    /**
     * 自动刷新
     */
    public void autoRefresh(){
        if(!isMove2Top() || isRefreshing)return;
        isRefreshComplete = false;
        isRefreshing = false;
        scroller.startScroll(0,0,0,refreshHeader.getContainerHeight() + 50,500);
        invalidate();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(500);
                    refreshHeader.post(new Runnable() {
                        @Override
                        public void run() {
                            judeCanRefresh();
                            resetHeaderHeight();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private float lastY, moveY,startX,startY;
    private boolean isMoveX = false;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        TOUCH_ACTION = ev.getAction();
        switch (TOUCH_ACTION) {
            case MotionEvent.ACTION_DOWN:
                isMoveX = false;
                lastY = (int) ev.getRawY();

                startY = ev.getY();
                startX = ev.getX();

                dispatchTouchEventSupper(ev);
                return true;
            case MotionEvent.ACTION_MOVE:

                float nowY = ev.getRawY();
                moveY = nowY - lastY;
                lastY = nowY;

                float distanceX = Math.abs(ev.getX() - startX);
                float distanceY = Math.abs(ev.getY() - startY);
                if(distanceX  > distanceY) {
                    isMoveX = true;
                }

                if ((isMove2Top() && !isMoveX) || refreshHeader.getVisibleHeight() > 0) {
                    int totalY = (int) (refreshHeader.getVisibleHeight() + Math.ceil(moveY * SCROLL_RATIO));
                    totalY = totalY > 0 ? totalY : 0;
                    refreshHeader.setVisibleHeight(totalY);
                    calculateProgress();

                    if(refreshHeader.getVisibleHeight() > 0 ){
                        MotionEvent cancelEvent = ev;
                        MotionEvent e = MotionEvent.obtain(cancelEvent.getDownTime(),
                                cancelEvent.getEventTime() + ViewConfiguration.getLongPressTimeout(),
                                MotionEvent.ACTION_CANCEL,
                                cancelEvent.getX(),
                                cancelEvent.getY(),
                                cancelEvent.getMetaState());
                        dispatchTouchEventSupper(e);
                        return true;
                    }
                }

                if(isMoveX){
                    dispatchTouchEventSupper(ev);
                    return false;
                }


                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                judeCanRefresh();
                resetHeaderHeight();
                isMoveX = false;
                break;
        }
        return dispatchTouchEventSupper(ev);
    }

    private void judeCanRefresh(){
        if (refreshHeader.getVisibleHeight() >= refreshHeader.getContainerHeight() && !isRefreshing && onRefreshListener != null) {
            isRefreshing = true;
            refreshHeader.onRefresh();
            onRefreshListener.onRefresh();
        }
    }

    public boolean dispatchTouchEventSupper(MotionEvent ev){
        return super.dispatchTouchEvent(ev);
    }

    private void resetHeaderHeight() {

        int height = refreshHeader.getVisibleHeight();
        if (height == 0) return;

        if (isRefreshing && height <= refreshHeader.getContainerHeight()) {
            return;
        }

        int finalHeight = 0;
        if (isRefreshing && height > refreshHeader.getContainerHeight()) {
            finalHeight = refreshHeader.getContainerHeight();
        }
        scroller.startScroll(0, height, 0, finalHeight - height, 800);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset() && TOUCH_ACTION != MotionEvent.ACTION_MOVE) {
            refreshHeader.setVisibleHeight(scroller.getCurrY());
            calculateProgress();
            postInvalidate();
        }else{
            isRefreshComplete = false;
        }
        super.computeScroll();
    }

    /**
     * 刷新完成
     */
    public void refreshComplete() {
        isRefreshing = false;
        isRefreshComplete = true;
        refreshHeader.onRefreshComplete();
        resetHeaderHeight();

    }

    /**
     * 计算进度
     */
    private void calculateProgress() {
        if(isRefreshComplete || isRefreshing)return;
        int progress = (int) (refreshHeader.getVisibleHeight() / (refreshHeader.getContainerHeight() * 1.0f) * 100);
        progress = progress > 100 ? 100 : progress;
        refreshHeader.onProgress(progress);
    }

    abstract boolean isMove2Top();

}
