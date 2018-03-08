package com.example.xiao.Schedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ldf.calendar.model.CalendarDate;

public class SettingActivity extends AppCompatActivity {
private String username;    // 用户名
private CalendarDate date;  // 时间信息 可以get年月日
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initdate();
    }

    private void initdate() {
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        date=(CalendarDate)intent.getSerializableExtra("date");
     //   Log.d("======",date.toString());
     //   Log.d("用户名为",username);
    }
}
