package com.cssrc.customviewdmeo.spinkit.animation.interpolator;

import android.view.animation.Interpolator;

/**
 * Author liuyangchao
 * Date on 2017/5/13.16:06
 */

public class Ease {

    public static Interpolator inOut(){
        return PathInterpolatorCompat.create(0.42f, 0f, 0.58f, 1f);
    }

}
