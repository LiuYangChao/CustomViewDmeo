package com.cssrc.customviewdmeo.spinkit.style;

import android.animation.ValueAnimator;

import com.cssrc.customviewdmeo.spinkit.animation.SpriteAnimatorBuilder;
import com.cssrc.customviewdmeo.spinkit.animation.interpolator.KeyFrameInterpolator;
import com.cssrc.customviewdmeo.spinkit.sprite.RingSprite;

/**
 * Author liuyangchao
 * Date on 2017/5/12.11:28
 */

public class PulseRing extends RingSprite {

    public PulseRing(){
        setScale(0f);
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        float fractions[] = new float[]{0f, 0.2f, 0.5f, 0.7f, 1f};      //时间序列
        return new SpriteAnimatorBuilder(this).scale(fractions, 0f, 0.5f, 0.7f, 1f, 1f)
                .alpha(fractions, 255, (int)(255*0.8), (int)(255*0.6), (int)(255*0.3), 0)
                .duration(3000)
                .interpolator(KeyFrameInterpolator.pathInterpolator(0.21f, 0.53f, 0.56f, 0.8f, fractions))
                .build();
//        float fractions[] = new float[]{0f, 0.7f, 1f};
//        return new SpriteAnimatorBuilder(this).
//                scale(fractions, 0f, 1f, 1f).
//                alpha(fractions, 255, (int) (255 * 0.7), 0).
//                duration(1000).
//                interpolator(KeyFrameInterpolator.pathInterpolator(0.21f, 0.53f, 0.56f, 0.8f, fractions)).
//                build();
    }

    /**
     *
     * @param x  time
     * @return
     */
    private float testAnimation(float x){
        float tension = 2.0f;
        return x * x * ((tension + 1) * x - tension);
    }
}
