package com.micaros.bm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.micaros.bm.admin.AdminContentActivity;
import com.micaros.bm.utils.ActivityCollector;
import com.micaros.bm.utils.HttpPostRequest;
import com.micaros.bm.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AdminActivity extends AppCompatActivity {

    private EditText aid;
    private EditText name;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        init();
    }

    public void init() {
        aid = findViewById(R.id.aid);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        System.out.println("aid：" + aid.getText());
    }

    public void adminBack(View view) {
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);
        //如果我们想要退出程序，而此时的返回栈里面又不止一个活动，那么我们需要不止一次的点击返回键才能彻底退出，
        // 这样的体验并不良好。我们可以创建一个ActivityCollector 类来一键退出所有活动。
        ActivityCollector.finishAll();
    }


    public void adminLogin(View view) {
        String strAid = aid.getText().toString();
        String strPassword = password.getText().toString();
        String strName = name.getText().toString();

        System.out.println("adminLogin.....");
        RequestBody requestBody = new FormBody.Builder()
                .add("aid", strAid)
                .add("password", strPassword)
                .add("name", strName)
                .build();
        HttpPostRequest.okhttpPost(HttpUtils.address + "/admin/login", requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AdminActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AdminActivity.this, "登录成功,用户名为：" + strAid, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(AdminActivity.this, AdminContentActivity.class);
                            intent.putExtra("aid", strAid);
                            intent.putExtra("password", strPassword);
                            intent.putExtra("name", strName);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AdminActivity.this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}