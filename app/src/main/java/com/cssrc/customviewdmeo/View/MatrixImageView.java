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
                    currentMode = MODE_ZOOM;       //缩放或者旋转模式（因为用到了两个手指，这里只处理了两个）
                }
                preEventCoor = new float[4];
                preEventCoor[0] = event.getX(0);
                preEventCoor[1] = event.getX(1);
                preEventCoor[2] = event.getY(0);
                preEventCoor[3] = event.getY(1);
                saveRotate = calRotation(event);
                break;
            case MotionEvent.ACTION_UP:// 单点离开屏幕时
                break;
            case MotionEvent.ACTION_POINTER_UP:// 第二个点离开屏幕时(有一个非主要的手指离开了)
                currentMode = MODE_NONE;
                preEventCoor = null;
                break;
            case MotionEvent.ACTION_MOVE:// 触摸点移动时
                if(currentMode == MODE_DRAG){           //单点触摸拖拽
                    currentMatrix.set(savedMatrix);
                    float dx = event.getX() - startP.x;
                    float dy = event.getY() - startP.y;
                    currentMatrix.postTranslate(dx, dy);        //平行变换移动
                }else if(currentMode == MODE_ZOOM && event.getPointerCount() == 2){         //缩放或者旋转
                    float currentMove = calSpacing(event);          //两个手指之间的直线距离
                    currentMatrix.set(savedMatrix);
                    if(currentMove > 10){
                        float scale = currentMove / preMove;
                        currentMatrix.postScale(scale, scale, midP.x, midP.y);              //放大或者缩小
                    }
                    if(preEventCoor != null){
                        rotate = calRotation(event);
                        float r = rotate - saveRotate;
                        currentMatrix.postRotate(r, getMeasuredWidth()/2, getMeasuredHeight()/2);               //旋转
                    }
                }
                break;
        }
        setImageMatrix(currentMatrix);
        return true;
    }

    /**
     * 计算前后两个触摸点的角度
     * @param event
     * @return
     */
    private float calRotation(MotionEvent event){
        double deltaX = event.getX(0) - event.getX(1);
        double deltaY = event.getY(0) - event.getY(1);
        double radius = Math.atan2(deltaX, deltaY);         //计算出弧度，弧度的范围是(-π, π]
        return (float) Math.toDegrees(radius);                  //radius(弧度)*180/π  计算出角度
    }


    /**
     * 计算两个触摸点（手指）的中间点坐标，getX(0),getX(1),getX(2)就是指第1,2,3根手指触摸点
     * @param pointF
     * @param motionEvent
     */
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
