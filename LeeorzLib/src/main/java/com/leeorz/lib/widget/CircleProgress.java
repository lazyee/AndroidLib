package com.leeorz.lib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/6/29 上午11:59
 * description:
 */
public class CircleProgress extends View {
    private int angle = 0;
    public CircleProgress(Context context) {
        super(context);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#90dab26a"));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        RectF oval = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawArc(oval,-90,angle,true,paint);
    }

    public void draw(int progress){
        angle = (int) (360 * (progress / 100.0f));
        invalidate();
    }
}
