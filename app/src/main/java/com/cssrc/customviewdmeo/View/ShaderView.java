package com.cssrc.customviewdmeo.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.cssrc.customviewdmeo.R;
import com.cssrc.customviewdmeo.Utils.MeasureUtil;

/**
 * Author liuyangchao
 * Date on 2017/4/26.10:18
 */

public class ShaderView extends View {

    private static final int RECT_SIZE = 400;
    private Paint mPaint;

    private float left, top, right, bottom;
    private int  screenX, screenY;

    public ShaderView(Context context) {
        super(context);
    }

    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initPaint();
        initRes(context);

    }

    void initRes(Context context){
        screenX = MeasureUtil.getScreenSize((Activity)context)[0]/2;
        screenY = MeasureUtil.getScreenSize((Activity)context)[1]/2;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a);         //获取位示图的对象
        //CLAMP、MIRROR和REPETA  镜像 重复
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
        Matrix matrix = new Matrix();
        //设置矩阵变换
//        matrix.postTranslate(50, 50);
//        matrix.setValues(new float[]{
//				1,0,screenX,
//				0,1,screenY,
//				0,0,1
//		});

        bitmapShader.setLocalMatrix(matrix);            //设置矩阵

        float[] values = new float[9];
        matrix.getValues(values);
        mPaint.setShader(bitmapShader);

    }

    void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);          //实例画笔（抗锯齿）
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //左上角和右下角的坐标值
        canvas.drawRect(0, 0, screenX*2, screenY*2, mPaint);
    }
}
