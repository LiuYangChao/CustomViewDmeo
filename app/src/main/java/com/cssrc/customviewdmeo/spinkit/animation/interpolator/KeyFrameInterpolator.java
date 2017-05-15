package com.cssrc.customviewdmeo.spinkit.animation.interpolator;

import android.animation.TimeInterpolator;
import android.view.animation.Interpolator;

/**
 *
 *  插值器
 * Author liuyangchao
 * Date on 2017/5/11.14:23
 */

public class KeyFrameInterpolator implements Interpolator {

    private TimeInterpolator interpolator;
    private float[] fractions;

    public static KeyFrameInterpolator easeInOut(float... fractions){
        KeyFrameInterpolator interpolator = new KeyFrameInterpolator(Ease.inOut());
        interpolator.setFractions(fractions);
        return interpolator;
    }

    public KeyFrameInterpolator(TimeInterpolator timeInterpolator, float... fractions) {
        this.interpolator = timeInterpolator;
        this.fractions = fractions;
    }

    public static KeyFrameInterpolator pathInterpolator(float controlX1,
                                                        float controlY1,
                                                        float controlX2,
                                                        float controlY2,
                                                        float... fractions){
        KeyFrameInterpolator interpolator = new KeyFrameInterpolator(
                PathInterpolatorCompat.create(controlX1, controlY1, controlX2, controlY2));
        interpolator.setFractions(fractions);
        return interpolator;
    }

    @Override
    public synchronized float getInterpolation(float input) {
        if(fractions.length > 1){
            for(int i=0; i<fractions.length-1; i++){
                float start = fractions[i];
                float end = fractions[i+1];
                float duration = end - start;
                if(input >= start && input <= end){
                    input = (input-start)/duration;
                    return start+interpolator.getInterpolation(input*duration);
                }
            }
        }
        return interpolator.getInterpolation(input);
    }

    public float[] getFractions() {
        return fractions;
    }

    public void setFractions(float[] fractions) {
        this.fractions = fractions;
    }

    public TimeInterpolator getInterpolator() {
        return interpolator;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
    }

}
