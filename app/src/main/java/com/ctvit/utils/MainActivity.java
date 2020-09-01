package com.ctvit.utils;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ctvit.c_utils.app.CtvitStatusBarUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
//        ImageView img = findViewById(R.id.img);
//        int height = CtvitScaleUtils.countScale(16, 9);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
//        img.setLayoutParams(layoutParams);

        CtvitStatusBarUtils.setStatusTransparent(this, false);
    }
}
