package com.example.xiao.Schedule;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class LuncherActivity extends AppCompatActivity {
  private   Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luncher);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startloginactivity();
            }


        },1000);
    }

    private void startloginactivity() {
        //TODO 加载页面  判断是否曾经登入过
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //当触碰屏幕 直接进入页面
        startloginactivity();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();

    }
}
