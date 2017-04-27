package com.cssrc.customviewdmeo.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cssrc.customviewdmeo.R;

/**
 * Author liuyangchao
 * Date on 2017/4/26.10:48
 */

public class BrickView extends View{

    private Paint mFillPaint, mStrokePaint;         //画笔（填充，描绘）
    private BitmapShader mBitmapShader;

    private float posX, posY;                               //触摸点

    public BrickView(Context context) {
        super(context);
    }

    public BrickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    /**
     * 初始化两个画笔
     */
    void initPaint(){
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mStrokePaint.setColor(0xFF000000);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(5);

        mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.brick);
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mFillPaint.setShader(mBitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1,绘制灰色背景图
        canvas.drawColor(Color.DKGRAY);
        //2,绘制小霸王图片和边框
        canvas.drawCircle(posX, posY, 300, mFillPaint);
        canvas.drawCircle(posX, posY, 300, mStrokePaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            posX = event.getX();
            posY = event.getY();
            invalidate();
        }
        return true;
    }
}
