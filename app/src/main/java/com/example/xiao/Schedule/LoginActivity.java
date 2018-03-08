package com.example.xiao.Schedule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import memberClass.User;


import static sqlClient.LoginAndRegister.loginQuery;

public class LoginActivity extends AppCompatActivity {
    private EditText __username;
    private EditText __password;
    private TextView errors;
    private View mProgressView;
    private View mLoginFormView;
    private Button login;
    private Button registered;

    static final int Success = 0;
    static final int Failed = 1;
    static final int Wrong = 2;
    static final int NotExist = 3;


    private boolean checkup(final String username, final String password) throws Exception {
        if (password.length() < 8) {
            errors.setText("密码长度不足8位");
            return false;
        }

        if ( !User.checkup_username(username) ) {
            errors.setText("用户名违法");
            return false;
        }

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_login);
        final Intent loginActivity = new Intent(this,ShowActivity.class);
        final Intent registerActivity = new Intent(this, ZhuceActivity.class);
        init();
        isLogin();


        @SuppressLint("HandlerLeak") final Handler showErrors = new Handler() {
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case Success:
                        errors.setText("登陆成功");
                        saveuserinfo();
                        startActivity(loginActivity);
                        break;
                    case Failed:
                        errors.setText("连接失败");
                        break;
                    case Wrong:
                        errors.setText("密码错误");
                        break;
                    case NotExist:
                        errors.setText("用户不存在");
                        break;
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 检查输入的账户名和密码
                final String username = __username.getText().toString();
                final String password = __password.getText().toString();

                try {
                    if (checkup(username, password)) {
                        Thread t = new Thread() {
                            public void run() {
                                try {
                                    int res = loginQuery(username, password);
                                    Message msg = new Message();
                                    msg.what = res;

                                    showErrors.sendMessage(msg);

                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        t.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 进入注册页面
                startActivity(registerActivity);
            }
        });
    }


    private void init(){
        login = (Button) findViewById(R.id.login);
        registered = (Button) findViewById(R.id.registered);
        __username = (EditText) findViewById(R.id.accountText);
        __password = (EditText) findViewById(R.id.passwordText);
        errors = (TextView) findViewById(R.id.ErrorText);
    }
    private void saveuserinfo() {
        SharedPreferences mySharedPreferences= getSharedPreferences("userinfo",
               Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString("username", __username.getText().toString());
        editor.putString("password",  __password.getText().toString());
        //提交当前数据
        editor.commit();
    }
    private void isLogin() {
        SharedPreferences sharedPreferences= getSharedPreferences("userinfo",
                Activity.MODE_PRIVATE);
        String name =sharedPreferences.getString("username", null);
        String password=sharedPreferences.getString("password", null);
        if (name!=null&&password!=null){
            Log.d("password=",password);
            final Intent loginActivity = new Intent(this,ShowActivity.class);
             startActivity(loginActivity);

        }else Log.d("==================","NULL");
    }

}
