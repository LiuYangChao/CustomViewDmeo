package com.cssrc.customviewdmeo.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.cssrc.customviewdmeo.R;
import com.cssrc.customviewdmeo.View.EraserView;

public class ActivityPath extends Activity {

    private EraserView eraser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        eraser = (EraserView) findViewById(R.id.eraser);
    }
}
