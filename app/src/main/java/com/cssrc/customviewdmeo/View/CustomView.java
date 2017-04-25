package com.cssrc.customviewdmeo.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.cssrc.customviewdmeo.Utils.MeasureUtil;

/**
 * Author liuyangchao
 * Date on 2017/4/25.15:44
 */

public class CustomView extends View implements Runnable {

    private Paint paint;
    private Context context;

    private int radius;         //圆环半径

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();
    }

    private void initPaint(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);           //抗锯齿
        /**
         * FILL_AND_STROKE
         * FILL
         * STROKE
         */
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);       //设置描边的宽度

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(MeasureUtil.getScreenSize((Activity) context)[0]/2,
                MeasureUtil.getScreenSize((Activity) context)[1]/2,
                radius,
                paint);
    }


    @Override
    public void run() {
        while (true){
            try{
                if(radius <= 200){
                    radius += 5;
                    postInvalidate();       //刷新View
                }else{
                    radius = 0;
                }
                Thread.sleep(50);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
