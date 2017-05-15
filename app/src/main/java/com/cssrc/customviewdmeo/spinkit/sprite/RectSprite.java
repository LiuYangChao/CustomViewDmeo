package com.cssrc.customviewdmeo.spinkit.sprite;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Author liuyangchao
 * Date on 2017/5/13.15:45
 */

public class RectSprite extends ShapeSprite {

    @Override
    public void drawShape(Canvas canvas, Paint paint) {
        if(getDrawBounds() != null){
            canvas.drawRect(getDrawBounds(), paint);
        }
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        return null;
    }
}
