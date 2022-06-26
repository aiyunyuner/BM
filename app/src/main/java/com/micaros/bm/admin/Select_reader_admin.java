package com.micaros.bm.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.micaros.bm.R;
import com.micaros.bm.utils.HttpGetRequest;
import com.micaros.bm.utils.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Select_reader_admin extends AppCompatActivity {
        private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.micaros.bm.R.layout.activity_select_reader_admin);
        init();
    }

    public  void init(){
            listView =(ListView) findViewById(R.id.sel_reader_list);
        HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/user/all", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Select_reader_admin.this, "post请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                List list = new Gson().fromJson(string, new TypeToken<ArrayList<Map>>() {
                }.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String form[]={"uid","password","name","sex","birthday","phone"};
                        int to[]={R.id.u_id,R.id.u_password,R.id.u_Name,R.id.u_sex,R.id.u_birthdays,R.id.uphone};
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //创建适配器进行数据适配
                                SimpleAdapter simpleAdapter=new SimpleAdapter(getApplication(),list,R.layout.activity_select_reader_admin, form,to);
                                listView.setAdapter(simpleAdapter);
                            }
                        });
                    }
                });
            }
        });
    }
}