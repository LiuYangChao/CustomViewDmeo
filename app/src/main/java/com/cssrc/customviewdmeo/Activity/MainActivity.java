package com.cssrc.customviewdmeo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cssrc.customviewdmeo.R;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.activity_easy)
    TextView activity_one;
    @Bind(R.id.activity_eraser)
    TextView activity_eraser;
    @Bind(R.id.activity_alpha)
    TextView activity_alpha;
    @Bind(R.id.activity_shader)
    TextView activity_shader;
    @Bind(R.id.activity_brick)
    TextView activity_brick;

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

    @OnClick(R.id.activity_eraser)
    void eraserClick(){
        Intent intent = new Intent(this, ActivityPath.class);
        startActivity(intent);
    }

    @OnClick(R.id.activity_alpha)
    void alphaClick(){
        Intent intent = new Intent(this, ActivityAlpha.class);
        startActivity(intent);
    }

    @OnClick(R.id.activity_shader)
    void shaderClick(){
        Intent intent = new Intent(this, ActivityShader.class);
        startActivity(intent);
    }

    @OnClick(R.id.activity_brick)
    void brickClick(){
        Intent intent = new Intent(this, ActivityBrick.class);
        startActivity(intent);
    }

}
