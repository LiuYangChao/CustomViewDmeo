package com.cssrc.customviewdmeo.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.cssrc.customviewdmeo.R;
import com.cssrc.customviewdmeo.spinkit.SpinKitView;
import com.cssrc.customviewdmeo.spinkit.SpriteFactory;
import com.cssrc.customviewdmeo.spinkit.Style;
import com.cssrc.customviewdmeo.spinkit.sprite.Sprite;

public class ActivityAnimator extends Activity {

    SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        spinKitView = (SpinKitView)findViewById(R.id.custom_animator);
        Style style = Style.values()[1];
        Sprite drawable = SpriteFactory.create(style);
        spinKitView.setIndeterminateDrawable(drawable);
    }
}
