package com.cssrc.customviewdmeo.spinkit.sprite;

import android.animation.ValueAnimator;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

import com.cssrc.customviewdmeo.spinkit.animation.AnimationUtils;

/**
 * Author liuyangchao
 * Date on 2017/5/11.15:05
 */

public abstract class Sprite extends Drawable implements
        ValueAnimator.AnimatorUpdateListener,
        Animatable,
        Drawable.Callback{

    private float scale = 1;
    private float scaleX = 1;
    private float scaleY = 1;
    private float pivotX;
    private float pivotY;
    private int animationDelay;
    private int rotateX;
    private int rotateY;
    private int translateX;
    private int translateY;
    private int rotate;
    private float translateXPercentage;
    private float translateYPercentage;
    private ValueAnimator animator;
    private int alpha = 255;
    private static final Rect ZERO_BOUNDS_RECT = new Rect();
    protected Rect drawBounds = ZERO_BOUNDS_RECT;
    private Camera mCamera;
    private Matrix mMatrix;

    public Sprite() {
        mCamera = new Camera();
        mMatrix = new Matrix();
    }

    //抽象方法
    //--------------------------------start------------------------------
    public abstract ValueAnimator onCreateAnimation();

    protected abstract void drawSelf(Canvas canvas);

    public abstract void setColor(int color);

    //--------------------------------end------------------------------

    //Drawable要实现的方法
    //--------------------------------start------------------------------
    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.RGBA_8888;           //设置色彩模式
    }

    @Override
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
    //--------------------------------end------------------------------

    //Animatable要实现的方法,Animatable就是为了Drawable设计的
    //--------------------------------start------------------------------
    public ValueAnimator obtainAnimation(){
        if(animator == null){
            animator = onCreateAnimation();
        }
        if(animator != null){
            animator.addUpdateListener(this);
            animator.setStartDelay(animationDelay);
        }
        return animator;
    }

    //重置所有参数，缩放，旋转，平移
    public void reset(){
        scale = 1;
        rotateX = 0;
        rotateY = 0;
        translateX = 0;
        translateY = 0;
        rotate = 0;
        translateXPercentage = 0f;
        translateYPercentage = 0f;
    }

    @Override
    public void start() {
        if(AnimationUtils.isStarted(animator)){
            return ;
        }
        animator = obtainAnimation();
        if(animator == null){
            return;
        }
        AnimationUtils.start(animator);
        invalidateSelf();
    }

    @Override
    public void stop() {
        if(AnimationUtils.isStarted(animator)){
            animator.removeAllUpdateListeners();
            animator.end();
            reset();
        }
    }

    @Override
    public boolean isRunning() {
        return AnimationUtils.isRunning(animator);
    }
    //--------------------------------end------------------------------

    @Override
    public void draw(Canvas canvas) {
        int tx = getTranslateX();
        tx = tx == 0? (int) (getBounds().width() * getTranslateXPercentage()) : tx;
        int ty = getTranslateY();
        ty = ty == 0? (int) (getBounds().height() * getTranslateYPercentage()) :ty;
        canvas.translate(tx, ty);
        canvas.scale(getScaleX(), getScaleY(), getPivotX(), getPivotY());
        canvas.rotate(getRotate(), getPivotX(), getPivotY());

        if(getRotateX()!=0 || getRotateY() != 0){
            mCamera.save();
            mCamera.rotateX(getRotateX());
            mCamera.rotateY(getRotateY());
            mCamera.getMatrix(mMatrix);
            mMatrix.preTranslate(-getPivotX(), -getPivotY());
            mMatrix.postTranslate(getPivotX(), getPivotY());
            mCamera.restore();
            canvas.concat(mMatrix);         //设置变换矩阵
        }
        drawSelf(canvas);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getPivotX() {
        return pivotX;
    }

    public void setPivotX(float pivotX) {
        this.pivotX = pivotX;
    }

    public float getPivotY() {
        return pivotY;
    }

    public void setPivotY(float pivotY) {
        this.pivotY = pivotY;
    }

    public int getAnimationDelay() {
        return animationDelay;
    }

    public void setAnimationDelay(int animationDelay) {
        this.animationDelay = animationDelay;
    }

    public int getRotateX() {
        return rotateX;
    }

    public void setRotateX(int rotateX) {
        this.rotateX = rotateX;
    }

    public int getRotateY() {
        return rotateY;
    }

    public void setRotateY(int rotateY) {
        this.rotateY = rotateY;
    }

    public int getTranslateX() {
        return translateX;
    }

    public void setTranslateX(int translateX) {
        this.translateX = translateX;
    }

    public int getTranslateY() {
        return translateY;
    }

    public void setTranslateY(int translateY) {
        this.translateY = translateY;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    public float getTranslateXPercentage() {
        return translateXPercentage;
    }

    public void setTranslateXPercentage(float translateXPercentage) {
        this.translateXPercentage = translateXPercentage;
    }

    public float getTranslateYPercentage() {
        return translateYPercentage;
    }

    public void setTranslateYPercentage(float translateYPercentage) {
        this.translateYPercentage = translateYPercentage;
    }

    public ValueAnimator getAnimator() {
        return animator;
    }

    public void setAnimator(ValueAnimator animator) {
        this.animator = animator;
    }

    @Override
    public int getAlpha() {
        return alpha;
    }

    public static Rect getZeroBoundsRect() {
        return ZERO_BOUNDS_RECT;
    }

    public Rect getDrawBounds() {
        return drawBounds;
    }

    public void setDrawBounds(Rect drawBounds) {
        this.drawBounds = drawBounds;
    }

    public Camera getmCamera() {
        return mCamera;
    }

    public void setmCamera(Camera mCamera) {
        this.mCamera = mCamera;
    }

    public Matrix getmMatrix() {
        return mMatrix;
    }

    public void setmMatrix(Matrix mMatrix) {
        this.mMatrix = mMatrix;
    }
}
