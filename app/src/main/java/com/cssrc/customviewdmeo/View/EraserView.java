package com.cssrc.customviewdmeo.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cssrc.customviewdmeo.R;
import com.cssrc.customviewdmeo.Utils.MeasureUtil;

/**
 * Author liuyangchao
 * Date on 2017/4/25.16:24
 */

public class EraserView extends View {

    private static final int MIN_MOVE_DIS = 5;          //最小移动距离，手指移动小于这个距离就不会绘制图像

    private Path mPath;
    private Paint mPaint;
    private Bitmap fgBitmap, bgBitmap;                    //前景橡皮擦，底图背景
    private Canvas mCanvas;                                     //绘制橡皮擦路径的画布

    private int screenWidth, screenHeight;                 //屏幕宽度和高度
    private float preX, preY;                                       //记录上一次手指触碰到的坐标系

    public EraserView(Context context) {
        super(context);
    }

    public EraserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        cal(context);
        init(context);
    }

    void cal(Context context){
        int[] screenSize = MeasureUtil.getScreenSize((Activity) context);
        screenWidth = screenSize[0];
        screenHeight = screenSize[1];
    }

    void init(Context context){
        mPath = new Path();
        //抗锯齿，抗抖动
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setARGB(128, 255, 0, 0);
        //混合颜色模型
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //画笔 描边
        mPaint.setStyle(Paint.Style.STROKE);
        //笔触 圆角
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        //笔触 类型
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //描边宽度
        mPaint.setStrokeWidth(50);

        //生成前景图
        fgBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(fgBitmap);
        mCanvas.drawColor(0xFF808080);          //画布是中色灰
        //获取背景图Bitmap
        bgBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.a4);
        //设置背景图为占满屏幕大小
        bgBitmap = Bitmap.createScaledBitmap(bgBitmap, screenWidth, screenHeight, true);
    }

    @Override
    protected void onDraw(Canvas canvas){
        //绘制背景图
        canvas.drawBitmap(bgBitmap, 0, 0, null);
        //绘制前景图
        canvas.drawBitmap(fgBitmap, 0 , 0, null);

        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x-preX);            //绝对值
                float dy = Math.abs(y-preY);
                //X,Y轴位移都大于5px
                if(dx >= MIN_MOVE_DIS || dy>= MIN_MOVE_DIS){
                    mPath.quadTo(preX, preY, (x+preX)/2, (y+preY)/2);
                    preX = x;
                    preY = y;
                }
                break;
        }
        invalidate();           //重新绘制View
        return true;
    }
}
