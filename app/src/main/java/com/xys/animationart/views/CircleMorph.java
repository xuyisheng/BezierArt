package com.xys.animationart.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Circle Morphing
 * <p/>
 * Created by xuyisheng on 16/7/13.
 */
public class CircleMorph extends View implements View.OnClickListener {

    private Paint mPaint;

    private float mCircleOneX;
    private float mCircleOneY;
    private float mCircleTwoX;
    private float mCircleTwoY;

    private float mCircleOneRadius;
    private float mCircleTwoRadius;

    private float mRadiusNormal;
    private ObjectAnimator mOneAnimator;
    private ObjectAnimator mTwoAnimator;
    private AnimatorSet mAnimatorSet;

    private Path mPath;

    public CircleMorph(Context context) {
        super(context);
    }

    public CircleMorph(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
        mCircleOneX = 200;
        mCircleOneY = 200;
        mCircleTwoX = 700;
        mCircleTwoY = 900;
        mRadiusNormal = 200;
        mCircleOneRadius = mRadiusNormal;
        mCircleTwoRadius = mRadiusNormal;

        mOneAnimator = ObjectAnimator.ofFloat(this, "circleOneX", mCircleTwoX, mCircleOneX);
        mOneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });

        mTwoAnimator = ObjectAnimator.ofFloat(this, "circleOneY", mCircleTwoY, mCircleOneY);
        mTwoAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.setInterpolator(new DecelerateInterpolator());
        mAnimatorSet.playTogether(mOneAnimator, mTwoAnimator);

        setOnClickListener(this);
    }

    public CircleMorph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCircleOneX, mCircleOneY, mCircleOneRadius, mPaint);
        canvas.drawCircle(mCircleTwoX, mCircleTwoY, mCircleOneRadius, mPaint);

//        metaBallVersion1(canvas);
        metaBallVersion2(canvas);
    }

    private void metaBallVersion2(Canvas canvas) {
        float x = mCircleTwoX;
        float y = mCircleTwoY;
        float startX = mCircleOneX;
        float startY = mCircleOneY;
        float controlX = (startX + x) / 2;
        float controlY = (startY + y) / 2;

        float distance = (float) Math.sqrt((controlX - startX) * (controlX - startX) + (controlY - startY) * (controlY - startY));
        double a = Math.acos(mRadiusNormal / distance);

        double b = Math.acos((controlX - startX) / distance);
        float offsetX1 = (float) (mRadiusNormal * Math.cos(a - b));
        float offsetY1 = (float) (mRadiusNormal * Math.sin(a - b));
        float tanX1 = startX + offsetX1;
        float tanY1 = startY - offsetY1;

        double c = Math.acos((controlY - startY) / distance);
        float offsetX2 = (float) (mRadiusNormal * Math.sin(a - c));
        float offsetY2 = (float) (mRadiusNormal * Math.cos(a - c));
        float tanX2 = startX - offsetX2;
        float tanY2 = startY + offsetY2;

        double d = Math.acos((y - controlY) / distance);
        float offsetX3 = (float) (mRadiusNormal * Math.sin(a - d));
        float offsetY3 = (float) (mRadiusNormal * Math.cos(a - d));
        float tanX3 = x + offsetX3;
        float tanY3 = y - offsetY3;

        double e = Math.acos((x - controlX) / distance);
        float offsetX4 = (float) (mRadiusNormal * Math.cos(a - e));
        float offsetY4 = (float) (mRadiusNormal * Math.sin(a - e));
        float tanX4 = x - offsetX4;
        float tanY4 = y + offsetY4;

        mPath.reset();
        mPath.moveTo(tanX1, tanY1);
        mPath.quadTo(controlX, controlY, tanX3, tanY3);
        mPath.lineTo(tanX4, tanY4);
        mPath.quadTo(controlX, controlY, tanX2, tanY2);
        canvas.drawPath(mPath, mPaint);

        // 辅助线
        canvas.drawCircle(tanX1, tanY1, 5, mPaint);
        canvas.drawCircle(tanX2, tanY2, 5, mPaint);
        canvas.drawCircle(tanX3, tanY3, 5, mPaint);
        canvas.drawCircle(tanX4, tanY4, 5, mPaint);
        canvas.drawLine(mCircleOneX, mCircleOneY, mCircleTwoX, mCircleTwoY, mPaint);
        canvas.drawLine(0, mCircleOneY, mCircleOneX + mRadiusNormal + 400, mCircleOneY, mPaint);
        canvas.drawLine(mCircleOneX, 0, mCircleOneX, mCircleOneY + mRadiusNormal + 50, mPaint);
        canvas.drawLine(mCircleTwoX, mCircleTwoY, mCircleTwoX, 0, mPaint);
        canvas.drawCircle(controlX, controlY, 5, mPaint);
        canvas.drawLine(startX, startY, tanX1, tanY1, mPaint);
        canvas.drawLine(tanX1, tanY1, controlX, controlY, mPaint);
    }

    private void metaBallVersion1(Canvas canvas) {
        float x = mCircleTwoX;
        float y = mCircleTwoY;
        float startX = mCircleOneX;
        float startY = mCircleOneY;

        float dx = x - startX;
        float dy = y - startY;
        double a = Math.atan(dx / dy);
        float offsetX = (float) (mCircleOneRadius * Math.cos(a));
        float offsetY = (float) (mCircleOneRadius * Math.sin(a));

        float x1 = startX + offsetX;
        float y1 = startY - offsetY;

        float x2 = x + offsetX;
        float y2 = y - offsetY;

        float x3 = x - offsetX;
        float y3 = y + offsetY;

        float x4 = startX - offsetX;
        float y4 = startY + offsetY;

        float controlX = (startX + x) / 2;
        float controlY = (startY + y) / 2;

        mPath.reset();
        mPath.moveTo(x1, y1);
        mPath.quadTo(controlX, controlY, x2, y2);
        mPath.lineTo(x3, y3);
        mPath.quadTo(controlX, controlY, x4, y4);
        mPath.lineTo(x1, y1);

        // 辅助线
        canvas.drawLine(mCircleOneX, mCircleOneY, mCircleTwoX, mCircleTwoY, mPaint);
        canvas.drawLine(0, mCircleOneY, mCircleOneX + mRadiusNormal + 400, mCircleOneY, mPaint);
        canvas.drawLine(mCircleOneX, 0, mCircleOneX, mCircleOneY + mRadiusNormal + 50, mPaint);
        canvas.drawLine(x1, y1, x2, y2, mPaint);
        canvas.drawLine(x3, y3, x4, y4, mPaint);
        canvas.drawCircle(controlX, controlY, 5, mPaint);
        canvas.drawLine(mCircleTwoX, mCircleTwoY, mCircleTwoX, 0, mPaint);
        canvas.drawLine(x1, y1, x1, mCircleOneY, mPaint);

        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void onClick(View view) {
        mAnimatorSet.start();
    }

    public float getCircleOneRadius() {
        return mCircleOneRadius;
    }

    public void setCircleOneRadius(float circleOneRadius) {
        mCircleOneRadius = circleOneRadius;
    }

    public float getCircleTwoRadius() {
        return mCircleTwoRadius;
    }

    public void setCircleTwoRadius(float circleTwoRadius) {
        mCircleTwoRadius = circleTwoRadius;
    }

    public float getCircleOneX() {
        return mCircleOneX;
    }

    public void setCircleOneX(float circleOneX) {
        mCircleOneX = circleOneX;
    }

    public float getCircleOneY() {
        return mCircleOneY;
    }

    public void setCircleOneY(float circleOneY) {
        mCircleOneY = circleOneY;
    }

    public float getCircleTwoX() {
        return mCircleTwoX;
    }

    public void setCircleTwoX(float circleTwoX) {
        mCircleTwoX = circleTwoX;
    }

    public float getCircleTwoY() {
        return mCircleTwoY;
    }

    public void setCircleTwoY(float circleTwoY) {
        mCircleTwoY = circleTwoY;
    }
}
