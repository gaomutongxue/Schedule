package com.example.xiao.Schedule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import memberClass.User;

import static sqlClient.LoginAndRegister.registerQuery;

public class ZhuceActivity extends AppCompatActivity {
    static final int Success = 0;
    static final int Failed = 1;
    static final int Wrong = 2;
    static final int NotExist = 3;
    static final int AlExist = 4;

    private EditText nameText;
    private EditText telephoneText;
    private EditText ageText;
    private EditText passwordText;
    private EditText confirmpassword;
    private TextView errors;
    private Button confirm;
    private Button cancel;
    private RadioButton nan_rb;
    private RadioButton nv_rb;
    private RadioGroup sex;
    private EditText yonghuming;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        final Intent intent = new Intent(this, LoginActivity.class);
        initdata();

        @SuppressLint("HandlerLeak") final Handler showErrors = new Handler() {
            public void handleMessage(Message msg) {
                int res = msg.what;
                if (res == Success) {
                    errors.setText("注册成功");
                    startActivity(intent);
                }
                else if (res == AlExist)
                    errors.setText("该用户名已存在");
                else if (res == Failed)
                    errors.setText("网络连接出错");
            }
        };



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 保存用户信息
                final User newUser = new User();
                if (yanzheng(newUser)) {
                    Thread t = new Thread() {
                        public void run() {
                            try {
                                int rs = registerQuery(newUser);
                                Message msg = new Message();

                                msg.what = rs;
                                showErrors.sendMessage(msg);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    t.start();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

    }


    private boolean yanzheng(User newUser) {
        String username;
        String password;
        String re_password;
        String name;
        int age;
        int sex;
        String phoneNumber;

        if (TextUtils.isEmpty(yonghuming.getText())) {
            errors.setText("请输入用户名");
            return false;
        }

        username = yonghuming.getText().toString();
        if ( !User.checkup_username(username) ) {
            errors.setText("用户名违法");
            return false;
        }

        if (TextUtils.isEmpty(passwordText.getText())) {
            errors.setText("请输入密码");
            return false;
        }

        password = passwordText.getText().toString();
        if (password.length() < 8) {
            errors.setText("密码长度不足8位");
            return false;
        }

        if (!User.checkup_password(password)) {
            errors.setText("密码违法");
            return false;
        }

        if (TextUtils.isEmpty(confirmpassword.getText())) {
            errors.setText("请再次输入密码");
            return false;
        }

        re_password = confirmpassword.getText().toString();
        if ( !re_password.equals(password) ) {
            errors.setText("两次输入密码不同");
            return false;
        }

        if (TextUtils.isEmpty(nameText.getText())) {
            errors.setText("请输入昵称");
        }
        name = nameText.getText().toString();

        if (TextUtils.isEmpty(ageText.getText())) {
            age = -1;
        }
        else
            age = Integer.parseInt(ageText.getText().toString());

        if (TextUtils.isEmpty(telephoneText.getText())) {
            phoneNumber = "";
        }
        else
            phoneNumber = telephoneText.getText().toString();

        if (nan_rb.isChecked())
            sex = 1;
        else
            sex = 2;

        newUser.registerUser(username, password, sex, name, age, phoneNumber);
        return true;
    }


    private void initdata() {
        yonghuming = (EditText) findViewById(R.id.username);
        nameText = (EditText) findViewById(R.id.zhuce_name);
        ageText = (EditText) findViewById(R.id.zhuce_age);
        passwordText = (EditText) findViewById(R.id.zhuece_password);
        confirmpassword = (EditText) findViewById(R.id.confirm_password);
        telephoneText = (EditText) findViewById(R.id.dianhua);

        sex = (RadioGroup) findViewById(R.id.sex_group);
        nan_rb = (RadioButton) findViewById(R.id.nan);
        nv_rb = (RadioButton) findViewById(R.id.nv);

        confirm = (Button) findViewById(R.id.zhuce_queding);
        cancel = (Button) findViewById(R.id.zhuce_quxiao);
        errors = (TextView) findViewById(R.id.zhuce_errors);

        nan_rb.setChecked(true);
    }
}
