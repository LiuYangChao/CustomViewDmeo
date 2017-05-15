package com.cssrc.customviewdmeo.spinkit.sprite;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Author liuyangchao
 * Date on 2017/5/11.21:00
 */

public class RingSprite extends ShapeSprite {

    //ShapeSprite中的抽象方法
    @Override
    public void drawShape(Canvas canvas, Paint paint) {
        if(getDrawBounds() != null){            //drawable的边界不为null
            paint.setStyle(Paint.Style.STROKE);             //边界
            //取边界宽度和高度最小值的一般
            int radius = Math.min(getDrawBounds().width(), getDrawBounds().height())/2;
            paint.setStrokeWidth(radius/12);        //边界宽度为半径的十二分之一
            canvas.drawCircle(getDrawBounds().centerX(),            //绘制圆形
                    getDrawBounds().centerY(),
                    radius,
                    paint);
        }
    }

    //Sprite中的抽象方法
    @Override
    public ValueAnimator onCreateAnimation() {
        return null;
    }

}
