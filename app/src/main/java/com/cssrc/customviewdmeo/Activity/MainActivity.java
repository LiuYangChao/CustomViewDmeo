package com.cssrc.customviewdmeo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cssrc.customviewdmeo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {
    @Bind(R.id.activity_easy)
    TextView activity_one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_easy)
    void oneClick(){
        Intent intent = new Intent(this, ActivityEasy.class);
        startActivity(intent);
    }

}
