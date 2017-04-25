package com.cssrc.customviewdmeo.Utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * 测绘工具类
 * Author liuyangchao
 * Date on 2017/4/25.15:39
 */

public class MeasureUtil {

    public static int[] getScreenSize(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new int[] {
                metrics.widthPixels, metrics.heightPixels
        };
    }

}
