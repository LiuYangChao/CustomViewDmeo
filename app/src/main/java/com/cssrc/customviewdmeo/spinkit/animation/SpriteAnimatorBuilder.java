package com.cssrc.customviewdmeo.spinkit.animation;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.Property;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

import com.cssrc.customviewdmeo.spinkit.animation.interpolator.KeyFrameInterpolator;
import com.cssrc.customviewdmeo.spinkit.sprite.Sprite;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Author liuyangchao
 * Date on 2017/5/11.14:22
 */

public class SpriteAnimatorBuilder {

    private int startFrame = 0;
    private long duration = 2000;
    private Sprite sprite;
    private Interpolator interpolator;
    private int repeatCount = Animation.INFINITE;           //设置重复次数，INFINITE为无限
    private Map<String, FrameData> fds = new HashMap<>();

    public SpriteAnimatorBuilder(Sprite sprite) {
        this.sprite = sprite;
    }

    class FrameData<T>{
        public FrameData(float[] fractions, Property property, T[] values){
            this.fractions = fractions;
            this.property = property;
            this.values = values;
        }
        float[] fractions;
        Property property;
        T[] values;
    }

    class IntFrameData extends FrameData<Integer>{
        public IntFrameData(float[] fractions, Property property, Integer[] values){
            super(fractions, property, values);
        }
    }

    class FloatFrameData extends FrameData<Float>{
        public FloatFrameData(float[] fractions, Property property, Float[] values){
            super(fractions, property, values);
        }
    }

    private void holder(float[] fractions, Property property, Float[] values){
        ensurePair(fractions.length, values.length);
        fds.put(property.getName(), new FloatFrameData(fractions, property, values));
    }

    private void holder(float[] fractions, Property property, Integer[] values){
        ensurePair(fractions.length, values.length);
        fds.put(property.getName(), new IntFrameData(fractions, property, values));
    }

    //确保时间序列和属性序列数量一致
    private void ensurePair(int fractionsLength, int valuesLength){
        if(fractionsLength != valuesLength){
            throw new IllegalStateException(String.format(
                    Locale.getDefault(),
                    "The fractions.length must equal values.length, " +
                            "fraction.length[%d], values.length[%d]",
                    fractionsLength,
                    valuesLength));
        }
    }

    public SpriteAnimatorBuilder interpolator(Interpolator interpolator){
        this.interpolator = interpolator;
        return this;
    }

    public SpriteAnimatorBuilder easeInOut(float... fractions){
        interpolator(KeyFrameInterpolator.easeInOut(fractions));
        return this;
    }

    public SpriteAnimatorBuilder scale(float[] fractions, Float... scale){
        holder(fractions, Sprite.SCALE, scale);
        return this;
    }

    public SpriteAnimatorBuilder scaleX(float[] fractions, Float... scaleX){
        holder(fractions, Sprite.SCALE_X, scaleX);
        return this;
    }

    public SpriteAnimatorBuilder scaleY(float[] fractions, Float... scaleY){
        holder(fractions, sprite.SCALE_Y, scaleY);
        return this;
    }

    public SpriteAnimatorBuilder alpha(float[] fractions, Integer... alpha){
        holder(fractions, Sprite.ALPHA, alpha);
        return this;
    }

    public SpriteAnimatorBuilder duration(long duration){
        this.duration = duration;
        return this;
    }


    public ObjectAnimator build(){
        PropertyValuesHolder[] holders = new PropertyValuesHolder[fds.size()];
        int i = 0;
        for(Map.Entry<String, FrameData> fd : fds.entrySet()){
            FrameData data = fd.getValue();
            Keyframe[] keyframes = new Keyframe[data.fractions.length];
            float[] fractions = data.fractions;
            float startF = fractions[startFrame];
            for(int j=startFrame; j<(startFrame+data.values.length); j++){
                int key= j;
                int vk = j%data.values.length;                      //其实感觉就是根据value的size循环
                float fraction = fractions[vk] - startF;            //key当前值减去初始值
                if(fraction<0){
                    fraction = fractions[fractions.length-1]+fraction;      //逆向，最后一个
                }
                //实际上是设置了时间-数据键值对
                if(data instanceof IntFrameData){
                    keyframes[key] = Keyframe.ofInt(fraction, (Integer) data.values[vk]);
                }else if(data instanceof FloatFrameData){
                    keyframes[key] = Keyframe.ofFloat(fraction, (Float) data.values[vk]);
                }else {
                    keyframes[key] = Keyframe.ofObject(fraction, data.values[vk]);
                }
            }
            /**
             * Keyframe kf0 = Keyframe.ofInt(0, 400);
             Keyframe kf1 = Keyframe.ofInt(0.25f, 200);
             Keyframe kf2 = Keyframe.ofInt(0.5f, 400);
             Keyframe kf4 = Keyframe.ofInt(0.75f, 100);
             Keyframe kf3 = Keyframe.ofInt(1f, 500);
             PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("width", kf0, kf1, kf2, kf4, kf3);
             ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(btn2, pvhRotation);
             rotationAnim.setDuration(2000);
             类似于这样：property（类似于kf0,kf1）  :   [fraction(时间百分比), values（数值，比如宽度）]
             */
            holders[i] = PropertyValuesHolder.ofKeyframe(data.property, keyframes);
            i++;
        }
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(sprite, holders);
        animator.setDuration(duration);
        animator.setRepeatCount(repeatCount);
        animator.setInterpolator(interpolator);
        return animator;
    }

}
