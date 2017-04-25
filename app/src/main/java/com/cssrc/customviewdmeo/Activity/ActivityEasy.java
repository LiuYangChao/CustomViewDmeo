package com.cssrc.customviewdmeo.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.cssrc.customviewdmeo.R;
import com.cssrc.customviewdmeo.View.CustomView;

public class ActivityEasy extends Activity {

    private CustomView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        view = (CustomView) findViewById(R.id.custom_circle);
        new Thread(view).start();
    }
}
