package com.cssrc.customviewdmeo.spinkit.sprite;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.cssrc.customviewdmeo.spinkit.animation.AnimationUtils;

/**
 * Author liuyangchao
 * Date on 2017/5/13.14:51
 */

public abstract class SpriteContainer extends Sprite {

    private Sprite[] sprites;
    private int color;

    public SpriteContainer(){
        sprites = onCreateChild();
        initCallBack();
        onChildCreated(sprites);
    }

    public void onChildCreated(Sprite... sprites){

    }

    private void initCallBack(){
        if(sprites != null){
            for(Sprite sprite:sprites){
                sprite.setCallback(this);
            }
        }
    }

    public int getChildCount(){
        return sprites==null ? 0:sprites.length;
    }

    public Sprite getChildAt(int index){
        return sprites==null ? null : sprites[index];
    }

    public void drawChild(Canvas canvas){
        if(sprites != null){
            for(Sprite sprite : sprites){
                int count = canvas.save();          //保存Matrix和Clip
                sprite.draw(canvas);
                canvas.restoreToCount(count);
            }
        }
    }

    public abstract Sprite[] onCreateChild();

    /**
     * 测量每一个drawable的子类Sprite的边界值
     * @param bounds
     */
    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        for(Sprite sprite:sprites){
            sprite.setBounds(bounds);
        }
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        return null;
    }

    @Override
    protected void drawSelf(Canvas canvas) {
//        drawChild(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawChild(canvas);
    }

    @Override
    public void setColor(int color) {
        this.color = color;
        for(int i=0; i<getChildCount(); i++){
            getChildAt(i).setColor(color);
        }
    }

    @Override
    public void start() {
        super.start();
        AnimationUtils.start(sprites);
    }

    @Override
    public void stop() {
        super.stop();
        AnimationUtils.stop(sprites);
    }

}
