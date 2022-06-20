package com.micaros.bm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
 * 注册页
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText r_name,r_password,user_name,r_sex,r_birthday,r_phone;
    private Button r_back,r_register;
    private String name,password,username,sex,birthday,phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }
    public void init(){
        setContentView(R.layout.activity_register);
        r_name = findViewById(R.id.r_name);
        r_password = findViewById(R.id.r_password);
        user_name = findViewById(R.id.user_name);
        r_sex = findViewById(R.id.r_sex);
        r_birthday = findViewById(R.id.r_birthday);
        r_phone = findViewById(R.id.r_phone);
        r_register = findViewById(R.id.r_register);

        r_register.setOnClickListener(this);



        r_back = findViewById(R.id.r_back);
        //返回按钮
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }

    //获取输入信息
    private void getData()
    {
        name = r_name.getText().toString().trim();
        password = r_password.getText().toString().trim();
        username = user_name.getText().toString().trim();
        sex = r_sex.getText().toString().trim();
        birthday = r_birthday.getText().toString().trim();
        phone = r_phone.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.r_register://注册按钮的点击事件
                getData();
                if (TextUtils.isEmpty(name))
                {
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(username))
                {
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(sex))
                {
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(birthday))
                {
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(phone))
                {
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else
                {

                    Log.i("RegisterActivity","注册信息");
                    RequestBody requestBody = new FormBody.Builder()
                            .add("uid",name)
                            .add("name",username)
                            .add("password",password)
                            .add("sex",sex)
                            .add("phone",phone)
                            .add("birthday",birthday)
                            .build();


                    HttpPostRequest.okhttpPost(HttpUtils.address + "/user/add", requestBody, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                 @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String string = response.body().string();
                            User user = JSON.parseObject(string, User.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                break;
        }
    }
}