package com.cssrc.customviewdmeo.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.cssrc.customviewdmeo.R;
import com.cssrc.customviewdmeo.Utils.MeasureUtil;

/**
 * Author liuyangchao
 * Date on 2017/4/26.8:46
 */

public class BlurMaskFilterView extends View {

    private Paint mPaint;
    private Context mContext;
    private Bitmap mBitmap, paintBitmap;

    private float x, y;

    public BlurMaskFilterView(Context context) {
        super(context);
    }

    public BlurMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        initPaint();                //初始化画笔
        initRes(context);                  //初始化资源
    }

    void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);          //抗锯齿，防抖动
        mPaint.setColor(Color.DKGRAY);
        //滤镜(模糊，浮雕), 10:圆角角度
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
    }

    void initRes(Context context){
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.a);
        //获取位图的Alpha通道图(边缘光晕效果)
        paintBitmap = mBitmap.extractAlpha();
//        x = MeasureUtil.getScreenSize((Activity)context)[0]/2 - mBitmap.getWidth()/2;
//        y = MeasureUtil.getScreenSize((Activity)context)[1]/2 - mBitmap.getHeight()/2;
//        x = MeasureUtil.getScreenSize((Activity)context)[0]/2;
//        y = MeasureUtil.getScreenSize((Activity)context)[1]/2;
        x = 0;
        y = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(paintBitmap, x, y, mPaint);
        canvas.drawBitmap(mBitmap, x, y, null);
    }
}
