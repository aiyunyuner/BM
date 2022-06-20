package com.micaros.bm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;


import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.micaros.bm.utils.HttpPostRequest;
import com.micaros.bm.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 登录页
 */
public class MainActivity extends AppCompatActivity {
    private EditText user_ed, pwd_ed;
    private Button login_bt, register_bt;
    private Button im_bt;
    private CheckBox rember, auto_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();//界面初始化
    }

    private void init() {
        user_ed = (EditText) findViewById(R.id.name);
        pwd_ed = (EditText) findViewById(R.id.password);
        //复选框的监听事件
        rember = (CheckBox) findViewById(R.id.rmber_pwd);//记住密码
        auto_login = (CheckBox) findViewById(R.id.auto_login);//自动登录
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        String Rusername = sp.getString("uid", "");
        String Rpassword = sp.getString("passwords", "");
        boolean choseRemember = sp.getBoolean("remember", false);
        boolean choseAutoLogin = sp.getBoolean("autologin", false);
        //      Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if (choseRemember) {
            user_ed.setText(Rusername);
            pwd_ed.setText(Rpassword);
            rember.setChecked(true);
        }
        //如果上次登录选了自动登录，那进入登录页面也自动勾选自动登录
        if (choseAutoLogin) {
            auto_login.setChecked(true);
        }
        if (auto_login.isChecked()) {
            Intent intent1 = new Intent(MainActivity.this, ContentActivity.class);
            startActivity(intent1);
        }
        //注册按钮的事件监听
        register_bt = (Button) findViewById(R.id.register);
        register_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //切换按钮的事件监听
        im_bt = (Button) findViewById(R.id.admin);
        im_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
        //登录按钮的事件监听
        login_bt = (Button) findViewById(R.id.login);
        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String struser = user_ed.getText().toString();
                String strpwd = pwd_ed.getText().toString();

                RequestBody requestBody = new FormBody.Builder()
                        .add("uid", struser)
                        .add("password", strpwd)
                        .build();

                HttpPostRequest.okhttpPost(HttpUtils.address + "/user/login", requestBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Integer integer = JSON.parseObject(string, Integer.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (integer > 0) {
                              /*
                               将用户名存储到sharedpreferences中
                               获取用户名和密码，方便在记住密码时使用
                            */
                                    Toast.makeText(MainActivity.this, "成功,用户名为：" + struser, Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                    editor.putString("uid", struser);
                                    editor.putString("passwords", strpwd);
                                    //是否记住密码
                                    if (rember.isChecked()) {
                                        editor.putBoolean("remember", true);
                                    } else {
                                        editor.putBoolean("remember", false);
                                    }
                                    //是否自动登录
//                           12
                                    editor.apply();

                                    if (auto_login.isChecked()) {
                                        editor.putBoolean("autologin", true);
                                        Intent intent1 = new Intent(MainActivity.this, ContentActivity.class);
                                        startActivity(intent1);
                                    } else {
                                        editor.putBoolean("autologin", false);
                                        Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                                        startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }

        });
    }
}