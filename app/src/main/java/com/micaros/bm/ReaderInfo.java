package com.micaros.bm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.micaros.bm.pojo.User;
import com.micaros.bm.utils.HttpPostRequest;
import com.micaros.bm.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 修改读者信息的页面
 */

public class ReaderInfo extends AppCompatActivity {
    private EditText user_ed, username, pwd_ed, birthday, phone, sex;
    private Button update_bt;
    String uname2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_info);
        init();
    }


    private void init() {
        //将id返回的记录查询在edittext
        user_ed = (EditText) findViewById(R.id.u_name);
        pwd_ed = (EditText) findViewById(R.id.u_password);
        username = findViewById(R.id.user_name);
        birthday = findViewById(R.id.u_birthday);
        phone = findViewById(R.id.u_phone);
        sex = findViewById(R.id.u_sex);
        update_bt = (Button) findViewById(R.id.r_register);
        SharedPreferences perf = getSharedPreferences("data", MODE_PRIVATE);
        uname2 = perf.getString("uid", "");//获得当前用户名称
        Log.i("sss", uname2);
        RequestBody requestBody = new FormBody.Builder()
                .add("uid", uname2)
                .build();
        HttpPostRequest.okhttpPost(HttpUtils.address + "/user/user", requestBody, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ReaderInfo.this, "post请求失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        User u = JSON.parseObject(string, User.class);
                        user_ed.setText(u.getUid());
                        pwd_ed.setText(u.getPassword());
                        username.setText(u.getName());
                        birthday.setText(u.getBirthday());
                        phone.setText(u.getPhone());
                        sex.setText(u.getSex());
                    }
                });
            }
        });
        //修改按钮的事件监听
        update_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strpwd = pwd_ed.getText().toString();
                String uname = username.getText().toString();
                String birth = birthday.getText().toString();
                String phonenum = phone.getText().toString();
                String usersex = sex.getText().toString();

                RequestBody requestBody = new FormBody.Builder()
                        .add("uid", uname2)
                        .add("password", strpwd)
                        .add("name", uname)
                        .add("birthday", birth)
                        .add("phone", phonenum)
                        .add("sex", usersex)
                        .build();
                HttpPostRequest.okhttpPost(HttpUtils.address + "/user/update", requestBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReaderInfo.this, "post请求失败", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(ReaderInfo.this, "除用户名外信息修改成功!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ReaderInfo.this, "信息修改失败!!!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}