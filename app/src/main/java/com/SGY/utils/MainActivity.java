package com.SGY.utils;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.SGY.c_utils.device.DeviceUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
//        ImageView img = findViewById(R.id.img);
//        int height = ScaleUtils.countScale(16, 9);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
//        img.setLayoutParams(layoutParams);

        System.out.println(DeviceUtils.getImei() + " getImei");
        System.out.println(DeviceUtils.getSerial() + " getSerial");
        System.out.println(DeviceUtils.getAndroidId() + " getAndroidId");
        System.out.println(DeviceUtils.getDeviceId() + " getDeviceId");
    }
}
