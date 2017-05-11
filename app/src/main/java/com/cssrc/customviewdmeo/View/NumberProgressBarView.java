package com.cssrc.customviewdmeo.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.cssrc.customviewdmeo.R;
import com.cssrc.customviewdmeo.Utils.DpUtils;
import com.cssrc.customviewdmeo.Utils.MeasureUtil;

/**
 * Author liuyangchao
 * Date on 2017/4/27.14:06
 */

public class NumberProgressBarView extends View {
    //*************************数值变量*****************************
    /**
     * 总进度
     */
    private int mMaxProgress = 100;

    /**
     * 当前进度
     */
    private int mCurrentProgress = 0;

    /**
     * 右边一段没有到达的颜色
     */
    private int mUnReachedBarColor;

    /**
     * 左边一段已经到达的颜色
     */
    private int mReachBarColor;

    private int mTextColor;

    private float mTextSize;

    private float mReachedBarHeight;

    private float mUnReachedBarHeight;

    private String mSuffix = "%";

    private String mPrefix = "";
    /**
     * 进度条【上】是否显示
     */
    private boolean mDrawUnreachedBar = true;
    /**
     * 进度条【下】是否显示
     */
    private boolean mDrawReachedBar = true;
    /**
     * 进度条上的文字是否显示
     */
    private boolean mIfDrawText = true;

    //*************************常量*****************************
    private final int default_text_color = Color.rgb(66, 145, 241);
    private final int default_reached_color = Color.rgb(66, 145, 241);
    private final int default_unreached_color = Color.rgb(204, 204, 204);
    private final float default_progress_text_offset;
    private final float default_text_size;
    private final float default_reached_bar_height;
    private final float default_unreached_bar_height;

    private static final int PROGRESS_TEXT_VISIBLE = 0;

    /**
     * 移动距离
     */
    private float mOffset;
    /**
     * 绘制文字的开始位置
     */
    private float mDrawTextStart;
    /**
     * 绘制文字的结束位置
     */
    private float mDrawTextEnd;


    //*************************画图参数*****************************
    /**
     * 进度条【上】
     */
    private Paint mReachedBarPaint;
    /**
     * 进度条【下】
     */
    private Paint mUnreachedBarPaint;
    /**
     * 进度条文字
     */
    private Paint mTextPaint;
    /**
     * 进度条文字内容
     */
    private String mCurrentDrawText;
    /**
     * 进度条文字宽度
     */
    private float mDrawTextWidth;

    /**
     * 进度条【下】矩形
     */
    private RectF mUnreachedRectF = new RectF(0, 0, 0, 0);
    /**
     * 进度条【上】矩形坐标
     */
    private RectF mReachedRectF = new RectF(0, 0, 0, 0);

    private OnProgressBarListener mListener;


    public enum ProgressTextVisibility{
        Visible, InVisible
    }

    public NumberProgressBarView(Context context) {
        this(context, null);
    }

    public NumberProgressBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        default_reached_bar_height = DpUtils.dp2px(getContext(), 1.5f);
        default_unreached_bar_height = DpUtils.dp2px(getContext(), 1.0f);
        default_text_size = DpUtils.sp2dp(getContext(), 10);
        default_progress_text_offset = DpUtils.dp2px(getContext(), 3.0f);

        //引入attrs.xml
        final TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.NumberProgressBar,
                defStyleAttr,
                0);

        //行动的进度条【上】颜色（蓝色）
        mReachBarColor = typedArray.getColor(R.styleable.NumberProgressBar_progress_reached_color,
                default_reached_color);
        //进度条【下】底色(灰色)
        mUnReachedBarColor = typedArray.getColor(
                R.styleable.NumberProgressBar_progress_unreached_color,
                default_unreached_color);
        //文字颜色
        mTextColor = typedArray.getColor(
                R.styleable.NumberProgressBar_progress_text_color,
                default_text_color);
        //文字大小
        mTextSize = typedArray.getDimension(
                R.styleable.NumberProgressBar_progress_text_size,
                default_text_size);
        //进度条【下】高度
        mReachedBarHeight = typedArray.getDimension(
                R.styleable.NumberProgressBar_progress_reached_bar_height,
                default_reached_bar_height);
        //进度条【上】高度
        mUnReachedBarHeight = typedArray.getDimension(
                R.styleable.NumberProgressBar_progress_unreached_bar_height,
                default_unreached_bar_height);
        //未知
        mOffset = typedArray.getDimension(
                R.styleable.NumberProgressBar_progress_text_offset,
                default_progress_text_offset);
        //进度条上的文字是否显示
        int textVisible = typedArray.getInt(R.styleable.NumberProgressBar_progress_text_visibility,
                PROGRESS_TEXT_VISIBLE);
        if(textVisible != PROGRESS_TEXT_VISIBLE){
            mIfDrawText = false;
        }

        //初始化进度条【上】的数值
        setProgress(typedArray.getInt(R.styleable.NumberProgressBar_progress_current, 0));
        //初始化进度条【下】的数值
        setMax(typedArray.getInt(R.styleable.NumberProgressBar_progress_max, 100));

        typedArray.recycle();
        initializePainters();

    }

    /**
     *  初始化进度条【上】【下】文字的画笔
     */
    private void initializePainters(){
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(mReachBarColor);

        mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedBarPaint.setColor(mUnReachedBarColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    /**
     * 设置当前进度数值，刷新页面
     * @param progress
     */
    public void setProgress(int progress){
        if(progress <= getMax() && progress>=0){
            this.mCurrentProgress = progress;
            invalidate();           //刷新页面
        }
    }

    public void setProgressTextVisible(ProgressTextVisibility visibility){
        mIfDrawText = visibility == ProgressTextVisibility.Visible;
    }

    /**
     * 设置进度数值显示，计算出上下两个进度条的坐标值
     */
    private void calculateDrawRectF(){
        //计算百分比数值，加入前后缀
        mCurrentDrawText = String.format("%d", getmCurrentProgress() * 100 / getMax());
        mCurrentDrawText = mPrefix + mCurrentDrawText + mSuffix;
        mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);

        if(getmCurrentProgress() == 0){
            mDrawReachedBar = false;                        //进度为0，进度就不用显示了
            mDrawTextWidth = getPaddingLeft();
        }else{
            mDrawReachedBar = true;
            mReachedRectF.left = getPaddingLeft();
            mReachedRectF.top = getHeight()/2.0f - mReachedBarHeight/2.0f;
            //这里有问题，最大长度只能到达总长度-offset的数值
            mReachedRectF.right = (getWidth()-getPaddingLeft()-getPaddingRight())/
                    (getMax()*1.0f)*getmCurrentProgress()-mOffset+getPaddingLeft();
            mReachedRectF.bottom = getHeight()/2.0f + mReachedBarHeight/2.0f;
            mDrawTextStart = mReachedRectF.right + mOffset;
        }
        mDrawTextEnd = getHeight()/2.0f -(mTextPaint.descent()+mTextPaint.ascent())/2.0f;
        //进度已经大于100%
        if((mDrawTextStart + mDrawTextWidth)>=getWidth()-getPaddingRight()){
            //计算出百分比数字矩形的最左边坐标点
            mDrawTextStart = getWidth()-getPaddingRight()-mDrawTextWidth;
            //计算出矩形进度条的最右边点的值
            mReachedRectF.right = mDrawTextStart - mOffset;
        }
        //计算出右边进度条的起点位置
        float unreachedBarStart = mDrawTextStart + mDrawTextWidth + mOffset;
        if(unreachedBarStart >= getWidth() - getPaddingRight()){
            mDrawUnreachedBar = false;      //进度超过100%就不显示
        }else{
            mDrawUnreachedBar = true;       //进度没有超过100%
            mUnreachedRectF.left = unreachedBarStart;
            mUnreachedRectF.right = getWidth() - getPaddingRight();
            mUnreachedRectF.top = getHeight()/2.0f - mUnReachedBarHeight/2.0f;
            mUnreachedRectF.bottom = getHeight()/2.0f + mUnReachedBarHeight/2.0f;
        }
    }

    /**
     * 设置进度数值不显示
     */
    private void calculateDrawRectFWithoutProgressText(){
        mReachedRectF.left = getPaddingLeft();
        mReachedRectF.top = getHeight()/2.0f - mReachedBarHeight/2.0f;
        mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight())/getMax()
                * getmCurrentProgress() + getPaddingLeft();
        mReachedRectF.bottom = getHeight()/2.0f + mReachedBarHeight/2.0f;

        mUnreachedRectF.left = mReachedRectF.right;
        mUnreachedRectF.top = getHeight()/2.0f - mUnReachedBarHeight/2.0f;
        mUnreachedRectF.right = getWidth() - getPaddingRight();
        mUnreachedRectF.bottom = getHeight()/2.0f + mUnReachedBarHeight/2.0f;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return (int) mTextSize;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) Math.max(mTextSize, Math.max(mReachedBarHeight, mUnReachedBarHeight));
    }

    /**
     * 测量View的宽度和高度
     * @param measureSpec       系统绘制必须的一个参数，32位Int型参数
     * @param isWidth                是否是测量宽度？高度
     * @return
     */
    private int measure(int measureSpec, boolean isWidth){
        int result;         //最终测量得到的数值
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        int padding = isWidth?getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if(mode == MeasureSpec.EXACTLY){
            result = size;
        }else{
            result = isWidth?getSuggestedMinimumWidth():getSuggestedMinimumHeight();
            result+=padding;
            if(mode == MeasureSpec.AT_MOST){
                if(isWidth){
                    result = Math.max(result, size);            //宽度测量取最大值
                }else{
                    result = Math.min(result, size);            //高度测量取最小值
                }
            }
        }
        return result;
    }

    //*************************measure-layout-draw*****************************

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //进度条上的文字显示/不显示，计算进度条显示值
        if(mIfDrawText){
            calculateDrawRectF();
        }else{
            calculateDrawRectFWithoutProgressText();
        }
        if(mDrawReachedBar){            //左侧进度条是否显示
            canvas.drawRect(mReachedRectF, mReachedBarPaint);
        }
        if(mDrawUnreachedBar){         //右侧进度条是否显示
            canvas.drawRect(mUnreachedRectF, mUnreachedBarPaint);
        }
        if(mIfDrawText){                      //进度数值是否显示
            canvas.drawText(mCurrentDrawText, mDrawTextStart, mDrawTextEnd, mTextPaint);
        }
    }

    public int getMax(){
        return mMaxProgress;
    }

    public void setMax(int maxProgress){
        if(maxProgress > 0){
            this.mMaxProgress = maxProgress;
            invalidate();               //刷新页面
        }
    }


    public int getmCurrentProgress() {
        return mCurrentProgress;
    }

    public int getmUnReachedBarColor() {
        return mUnReachedBarColor;
    }

    public int getmReachBarColor() {
        return mReachBarColor;
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public float getmTextSize() {
        return mTextSize;
    }

    public float getmReachedBarHeight() {
        return mReachedBarHeight;
    }

    public float getmUnReachedBarHeight() {
        return mUnReachedBarHeight;
    }

    public String getmSuffix() {
        return mSuffix;
    }

    public String getmPrefix() {
        return mPrefix;
    }

    /**
     *
     * @param by  每次递增的数值
     */
    public void incrementProgressBy(int by){
        if(by > 0){
            setProgress(getmCurrentProgress()+by);
        }
        if(mListener != null){
            mListener.onProgressChange(getmCurrentProgress(), getMax());
        }
    }

    public void setOnProgressBarListener(OnProgressBarListener listener){
        mListener = listener;
    }

}
