package com.micaros.bm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

/*
*     个人借书页面，展示当前uid的借阅信息
* */

public class BorrowInfoActivity extends AppCompatActivity  {

    private ListView ad_borrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_info);

        //获取当前用户id
        SharedPreferences perf=getSharedPreferences("data",MODE_PRIVATE);
        String uid=perf.getString("uid","");//获得当前用户uid,根据uid查询借阅信息
        System.out.println(uid);

        ad_borrow=(ListView)findViewById(R.id.show_borrow);

        HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/borrow/find?uid=" + uid, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BorrowInfoActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
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
//                        System.out.println(list.get(0));
                        String from[] = {"id","BookId", "BookName", "BookAuthor","NowTime"};
                        System.out.println(from+"-------------------------------------------------");
                        int to[] = {R.id.BorId,R.id.Bbookid, R.id.Bbookname, R.id.Bbookauthor,R.id.Bnowtimae};
                        System.out.println(to+"-------------------------------------------------");
                        SimpleAdapter simpleAdapter = new SimpleAdapter(BorrowInfoActivity.this, list, R.layout.borrow_item, from, to);
                        ad_borrow.setAdapter(simpleAdapter);

//                        JSONObject.parseObject()

                    }
                });
            }
        });


//
//
//          对列表借阅信息的点击事件------>还书功能

        ad_borrow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                System.out.println("点击成功！");
//                System.out.println(position+1);


//                获取订单编号
                TextView View = view.findViewById(R.id.BorId);
                CharSequence viewText = View.getText();
                System.out.println("CharSequence转换为string："+viewText);

                String string = viewText.toString();
                double parseDouble = Double.parseDouble(string);
                int id1 = (int)parseDouble;
                System.out.println("最后转换为Int类型的id为："+id1);

                HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/borrow/delete?id="+id1, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BorrowInfoActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String string = response.body().string();
//                        JSON.parseObject(string,Bor)
                        Integer integer = JSON.parseObject(string, Integer.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (integer > 0)
                                {
                                    Toast.makeText(BorrowInfoActivity.this, "归还成功！", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(BorrowInfoActivity.this, ContentActivity.class);
                                    startActivity(intent);

                                }
                                else{
                                    Toast.makeText(BorrowInfoActivity.this, "归还失败!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });


            }
        });



    }

}
