package com.cssrc.customviewdmeo.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.cssrc.customviewdmeo.R;
import com.cssrc.customviewdmeo.Utils.MeasureUtil;

/**
 * Author liuyangchao
 * Date on 2017/4/26.11:25
 */

public class MatrixImageView extends ImageView {

    private static final int MODE_NONE = 0x00123;// 默认的触摸模式
    private static final int MODE_DRAG = 0x00321;// 拖拽模式
    private static final int MODE_ZOOM = 0x00132;// 缩放or旋转模式
    private int currentMode;                                    //当前触碰模式

    private Context context;
    private PointF startP, midP;                                      //起点  中间点
    private Matrix currentMatrix, savedMatrix;               //当前和保存的Matrix对象

    private float[] preEventCoor;                           //上一次触摸点的坐标集合
    private float preMove = 1F;                             //上一次手指移动的距离
    private float saveRotate = 0F;                          //保存的角度值
    private float rotate = 0F;                                  //旋转的角度

    public MatrixImageView(Context context) {
        super(context);
    }

    public MatrixImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    void init(){
        currentMatrix = new Matrix();
        savedMatrix = new Matrix();
        startP = new PointF();
        midP = new PointF();

        //模式初始化
        currentMode = MODE_NONE;

        //设置图片资源
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a3_mask);
        bitmap = Bitmap.createScaledBitmap(bitmap, MeasureUtil.getScreenSize((Activity)context)[0]
            ,MeasureUtil.getScreenSize((Activity)context)[1], true);
        setImageBitmap(bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:                   //单点接触屏幕
                savedMatrix.set(currentMatrix);             //保存当前数组
                startP.set(event.getX(), event.getY());
                currentMode = MODE_DRAG;                //拖拽模式
                preEventCoor = null;                            //第一次接触屏幕是要清空上一次坐标点的
                break;
            case MotionEvent.ACTION_POINTER_DOWN:// 第二个点接触屏幕时(有一个非主要的手指按下了)
                preMove = calSpacing(event);            //两次触摸移动的距离
                if(preMove > 10F){
                    savedMatrix.set(currentMatrix);
                    calMidPoint(midP, event);
                }

                break;
            case MotionEvent.ACTION_UP:// 单点离开屏幕时
                break;
            case MotionEvent.ACTION_POINTER_UP:// 第二个点离开屏幕时(有一个非主要的手指离开了)
                break;
            case MotionEvent.ACTION_MOVE:// 触摸点移动时
                break;








        }

    }


    private void calMidPoint(PointF pointF, MotionEvent motionEvent){
        float x = motionEvent.getX(0) + motionEvent.getX(1);
        float y = motionEvent.getY(0) + motionEvent.getY(1);
        pointF.set(x/2, y/2);
    }


    /**
     * 计算两个触摸点之间的距离
     * @param event
     * @return
     */
    private float calSpacing(MotionEvent event){
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x*x + y*y);
    }










}
