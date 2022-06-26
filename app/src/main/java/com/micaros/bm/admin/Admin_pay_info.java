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

public class Admin_pay_info extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pay_info);
            init();
    }
    public void init(){
        listView=(ListView) findViewById(R.id.ad_show_pay);
        HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/pay/allpay", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Admin_pay_info.this, "post请求失败", Toast.LENGTH_SHORT).show();
                        }
                    });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                   String  string=response.body().string();
                List list=new Gson().fromJson(string, new TypeToken<ArrayList<Map>>() {
                }.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String from[]={"id","BookName","BookId","BookName","NowTime"};
                        int to[]={R.id.back_id,R.id.back_read,R.id.back_nameid,R.id.bookName,R.id.back_time};
                        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplication(), list, R.layout.activity_admin_pay_info, from, to);
                        listView.setAdapter(simpleAdapter);
                    }
                });
            }
        });
    }
}