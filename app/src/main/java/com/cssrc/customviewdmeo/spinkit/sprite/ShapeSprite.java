package com.cssrc.customviewdmeo.spinkit.sprite;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;

/**
 * Author liuyangchao
 * Date on 2017/5/11.15:08
 */

public abstract class ShapeSprite extends Sprite {

    private Paint mPaint;
    private int mUseColor;
    private int mBaseColor;

    public ShapeSprite(){
        setColor(Color.WHITE);          //设置Drawable的颜色是白色
        mPaint = new Paint();
        mPaint.setAntiAlias(true);      //抗锯齿
        mPaint.setColor(mUseColor);
    }

    /**
     * Sprite中的抽象方法,为了接下的继承类还可以调用canvas，所以要定义一个抽象方法
     * @param canvas
     */
    @Override
    protected void drawSelf(Canvas canvas) {
        mPaint.setColor(mUseColor);
        drawShape(canvas, mPaint);
    }

    public abstract void drawShape(Canvas canvas, Paint paint);

    /**
     * 这个颜色位移的效果至今不知道
     * 2017年5月11日16:29:44更新：其实这个是SDK包中的源码ColorDrawable.java
     * https://android.googlesource.com/platform/frameworks/base/+/5f49c30/graphics/java/android/graphics/drawable/ColorDrawable.java
     *
     * Sets the color's alpha value.        设置颜色的透明度数值
     * The alpha value to set, between 0 and 255.
     */
    private void updateUseColor(){
        int alpha = getAlpha();
        alpha += alpha>>7;          // make it 0..256
        final int baseAlpha = mBaseColor >>> 24;
        final int useAlpha = baseAlpha * alpha >> 8;
        mUseColor = (mBaseColor << 8 >>> 8) | (useAlpha << 24);
    }

    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
        updateUseColor();
    }

    @Override
    public void setColor(int color) {
        mBaseColor = color;
        updateUseColor();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }
}
