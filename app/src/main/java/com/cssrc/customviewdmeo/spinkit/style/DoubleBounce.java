package com.cssrc.customviewdmeo.spinkit.style;

import android.animation.ValueAnimator;
import android.os.Build;

import com.cssrc.customviewdmeo.spinkit.animation.SpriteAnimatorBuilder;
import com.cssrc.customviewdmeo.spinkit.sprite.CircleSprite;
import com.cssrc.customviewdmeo.spinkit.sprite.Sprite;
import com.cssrc.customviewdmeo.spinkit.sprite.SpriteContainer;

/**
 * Author liuyangchao
 * Date on 2017/5/15.14:40
 */

public class DoubleBounce extends SpriteContainer {

    //绘制每一个图形的边界
    @Override
    public Sprite[] onCreateChild() {
        return new Sprite[]{
                new Bounce(), new Bounce(), new Bounce()
        };
    }

    @Override
    public void onChildCreated(Sprite... sprites) {
        super.onChildCreated(sprites);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sprites[1].setAnimationDelay(1000);
        } else {
            sprites[1].setAnimationDelay(-1000);
        }
    }

    private class Bounce extends CircleSprite {
        Bounce(){
            setAlpha(153);
            setScale(0f);
        }

        @Override
        public ValueAnimator onCreateAnimation() {
            float fractions[] = new float[]{0f, 0.5f, 1f};
            return new SpriteAnimatorBuilder(this).scale(fractions, 0f, 1f, 0f).
                    duration(3000).
                    easeInOut(fractions)
                    .build();
        }
    }

}
