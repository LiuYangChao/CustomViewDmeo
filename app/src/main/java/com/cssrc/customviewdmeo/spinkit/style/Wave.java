package com.cssrc.customviewdmeo.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Build;

import com.cssrc.customviewdmeo.spinkit.animation.SpriteAnimatorBuilder;
import com.cssrc.customviewdmeo.spinkit.sprite.RectSprite;
import com.cssrc.customviewdmeo.spinkit.sprite.Sprite;
import com.cssrc.customviewdmeo.spinkit.sprite.SpriteContainer;

/**
 * Author liuyangchao
 * Date on 2017/5/13.14:50
 */

public class Wave extends SpriteContainer {

    //执行顺序2
    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        bounds = clipSquare(bounds);                            //100*100
        int rw = bounds.width() / getChildCount();
        int width = bounds.width() /5*3/5;                      //宽度五分之三，左右各有五分之一的间距
        for(int i=0; i<getChildCount(); i++){
            Sprite sprite = getChildAt(i);
            int l = bounds.left + i*rw + rw/5;                    //左边坐标为原始+五分之一
            int r = l+width;
            sprite.setDrawBounds(l, bounds.top, r, bounds.bottom);
        }
    }

    //执行顺序1
    @Override
    public Sprite[] onCreateChild() {
        WaveItem[] waveItems = new WaveItem[5];
        for(int i=0;i<waveItems.length; i++){
            waveItems[i] = new WaveItem();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                waveItems[i].setAnimationDelay(600+i*100);
            }else{
                waveItems[i].setAnimationDelay(-1200+i*100);
            }
        }
        return waveItems;
    }

    private class WaveItem extends RectSprite {

        WaveItem(){
            setScaleY(0.4f);
        }

        @Override
        public ValueAnimator onCreateAnimation() {
            float fractions[] = new float[]{0f, 0.2f, 0.4f, 1f};
            return new SpriteAnimatorBuilder(this)
                    .scaleY(fractions, 0.4f, 1f, 0.4f, 0.4f)
                    .duration(1200)
                    .easeInOut(fractions)
                    .build();
        }
    }

}
