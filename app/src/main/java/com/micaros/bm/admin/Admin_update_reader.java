package com.micaros.bm.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.micaros.bm.R;
import com.micaros.bm.ReaderInfo;
import com.micaros.bm.pojo.User;
import com.micaros.bm.utils.HttpGetRequest;
import com.micaros.bm.utils.HttpPostRequest;
import com.micaros.bm.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Admin_update_reader extends AppCompatActivity {
    private EditText user_ed, username, pwd_ed, birthday, phone, sex;
    private Button update_bt;
    String uname1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_reader);
                init();
    }
    private   void init(){
        //将id返回的记录查询在edittext
        user_ed=(EditText)findViewById(R.id.r_name);
        pwd_ed=(EditText)findViewById(R.id.r_password);
        username=findViewById(R.id.user_name);
        sex=findViewById(R.id.r_sex);
        birthday=findViewById(R.id.r_birthday);
        phone=findViewById(R.id.r_phone);
        update_bt = (Button) findViewById(R.id.r_register);

//
//        RequestBody requestBody = new FormBody.Builder()
//                .add("uid", uname)
//                .build();

        Bundle bundle=this.getIntent().getExtras();
         uname1=bundle.getString("uid");
        Log.i(Admin_update_reader.ACTIVITY_SERVICE, uname1);
//        HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address+"/user/userN?uid="+uname,new Callback() {

        RequestBody requestBody=new FormBody.Builder().add("uid", String.valueOf(uname1)).build();
        HttpPostRequest.okhttpPost(HttpUtils.address+"/user/user",requestBody,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Admin_update_reader.this, "post请求失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i(Admin_update_reader.ACTIVITY_SERVICE,"\n\n\n\n\n\n"+string);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (string!=""){
                            Log.i(Admin_update_reader.ACTIVITY_SERVICE,"ssssssss"+string);
                            User u = JSON.parseObject(string, User.class);
                            user_ed.setText(u.getUid());
                            pwd_ed.setText(u.getPassword());
                            username.setText(u.getName());
                            birthday.setText(u.getBirthday());
                            phone.setText(u.getPhone());
                            sex.setText(u.getSex());
                        }else {
                            Toast.makeText(Admin_update_reader.this, "无返回值", Toast.LENGTH_SHORT).show();
                        }

//


                    }
                });
            }
        });

        //修改按钮的事件监听
        update_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strpwd = pwd_ed.getText().toString();
                String uname = username.getText().toString();
                String birth = birthday.getText().toString();
                String phonenum = phone.getText().toString();
                String usersex = sex.getText().toString();

                RequestBody requestBody = new FormBody.Builder()
                        .add("uid", uname1)
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
                                Toast.makeText(Admin_update_reader.this, "post请求失败", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(Admin_update_reader.this, "除用户名外信息修改成功!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(Admin_update_reader.this, "信息修改失败!!!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}