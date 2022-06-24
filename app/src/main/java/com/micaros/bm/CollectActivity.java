package com.micaros.bm;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.micaros.bm.utils.HttpGetRequest;
import com.micaros.bm.utils.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CollectActivity extends AppCompatActivity {

    private ListView listView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        init();


    }

    public void init(){

        //获取当前用户
        SharedPreferences perf=getSharedPreferences("data",MODE_PRIVATE);
        String uid=perf.getString("uid","");//获得当前用户uid,根据uid查询借阅信息
        System.out.println("uid:"+uid);

        listView = findViewById(R.id.show_collect);
        HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/collect/select?uid=" + uid, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CollectActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
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
//                        System.out.println();
                        System.out.println(list.get(0));
                        String from[] = {"id","BookId", "BookName", "BookAuthor","NowTime"};
                        System.out.println(from+"-------------------------------------------------");
                        int to[] = {R.id.BorId,R.id.Bbookid, R.id.Bbookname, R.id.Bbookauthor,R.id.Bnowtimae};
                        System.out.println(to+"-------------------------------------------------");
                        SimpleAdapter simpleAdapter = new SimpleAdapter(CollectActivity.this, list, R.layout.collect_item, from, to);
                        listView.setAdapter(simpleAdapter);

//                        JSONObject.parseObject()

                    }
                });
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.BorId);
                int id1 =(int)Double.parseDouble(textView.getText().toString().trim());
                System.out.println(id1);

                HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/collect/delete?id=" + id1, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CollectActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("--------------------------------------------------");
                        String string = response.body().string();
                        System.out.println(string);
                        System.out.println("--------------------------------------------------");
                        Integer integer = JSON.parseObject(string, Integer.class);
                        System.out.println(integer);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(integer > 0)
                                {
                                    Toast.makeText(CollectActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CollectActivity.this, ContentActivity.class);
                                    startActivity(intent);



                                }
                                else
                                {
                                    Toast.makeText(CollectActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });



            }
        });


//

    }
}