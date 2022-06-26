package com.micaros.bm.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.micaros.bm.MainActivity;
import com.micaros.bm.R;
import com.micaros.bm.RegisterActivity;
import com.micaros.bm.pojo.User;
import com.micaros.bm.utils.HttpPostRequest;
import com.micaros.bm.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Admin_add_reader extends AppCompatActivity implements View.OnClickListener {
    private EditText r_name,r_password,user_name,r_sex,r_birthday,r_phone;
    private Button add_user;
    private String name,password,username,sex,birthday,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            init();

    }

    public void init() {
        setContentView(com.micaros.bm.R.layout.activity_admin_add_reader);
        r_name = findViewById(R.id.r_name);
        r_password = findViewById(R.id.r_password);
        user_name = findViewById(R.id.user_name);
        r_sex = findViewById(R.id.r_sex);
        r_birthday = findViewById(R.id.r_birthday);
        r_phone = findViewById(R.id.r_phone);


        add_user = (Button) findViewById(R.id.add_user);
        add_user.setOnClickListener(this);
    }


        private  void  getData(){

            name=r_name.getText().toString().trim();
            password=r_password.getText().toString().trim();
            username=user_name.getText().toString().trim();
            sex=r_sex.getText().toString().trim();
            birthday=r_birthday.getText().toString().trim();
            phone=r_phone.getText().toString().trim();

        }


    boolean testid=true,testother=true;
            @Override
            public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.add_user:
                        getData();
                        if(r_name.getText().length()!=6){
                            Toast.makeText(Admin_add_reader.this,"请输入6位帐号",Toast.LENGTH_SHORT).show();
                            testid=false;
                            break;
                        }if(r_phone.getText().length()!=11){
                            Toast.makeText(Admin_add_reader.this,"请输入11位手机号",Toast.LENGTH_SHORT).show();
                           testother=false;
                           break;
                        }if (testother==true&&testid==true){

                            RequestBody requestBody = new FormBody.Builder()
                                    .add("uid",name)
                                    .add("password",password)
                                    .add("name",username)
                                    .add("sex",sex)
                                    .add("birthday",birthday)
                                    .add("phone",phone)
                                    .build();



                            HttpPostRequest.okhttpPost(HttpUtils.address + "/user/add", requestBody, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Admin_add_reader.this, "post请求失败", Toast.LENGTH_SHORT).show();
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
                                            Intent intent = new Intent(Admin_add_reader.this, Admin_manager_reader.class);
                                            startActivity(intent);
                                            Toast.makeText(Admin_add_reader.this,"添加成功",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }

                    break;

                    }}}








