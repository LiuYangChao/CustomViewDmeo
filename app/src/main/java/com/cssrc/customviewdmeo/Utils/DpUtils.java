package com.cssrc.customviewdmeo.Utils;

import android.content.Context;

/**
 * Author liuyangchao
 * Date on 2017/4/27.14:19
 */

public class DpUtils {

    public static float dp2px(Context context, float dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2dp(Context context, float sp){
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

}
