package com.cssrc.customviewdmeo.spinkit.sprite;

import android.graphics.Color;
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
        setColor(Color.WHITE);          //
    }

    /**
     * 这个颜色位移的效果至今不知道
     * 2017年5月11日16:29:44更新：其实这个是SDK包中的源码ColorDrawable.java
     * https://android.googlesource.com/platform/frameworks/base/+/5f49c30/graphics/java/android/graphics/drawable/ColorDrawable.java
     *
     * Sets the color's alpha value.
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
    public void setColor(int color) {
        mBaseColor = color;
        updateUseColor();
    }
}
