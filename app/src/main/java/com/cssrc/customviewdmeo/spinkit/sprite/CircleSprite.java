package com.cssrc.customviewdmeo.spinkit.sprite;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Author liuyangchao
 * Date on 2017/5/15.14:43
 */

public class CircleSprite extends ShapeSprite {

    //画了一个无边框的圆形
    @Override
    public void drawShape(Canvas canvas, Paint paint) {
        if(getDrawBounds() != null){
            int radius = Math.min(getDrawBounds().width(), getDrawBounds().height())/2;
            canvas.drawCircle(getBounds().centerX(),
                    getBounds().centerY(),
                    radius,
                    paint);
        }
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        return null;
    }
}
