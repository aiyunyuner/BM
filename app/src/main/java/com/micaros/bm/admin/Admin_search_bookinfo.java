package com.micaros.bm.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.micaros.bm.R;
import com.micaros.bm.ReaderInfo;
import com.micaros.bm.pojo.Book;
import com.micaros.bm.utils.HttpGetRequest;
import com.micaros.bm.utils.HttpPostRequest;
import com.micaros.bm.utils.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Admin_search_bookinfo extends AppCompatActivity {
    private ListView listView;
    private String BookName;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search_bookinfo);
            init();
    }

    private void init(){
            listView=(ListView) findViewById(R.id.select_book_list);
        Bundle bundle=this.getIntent().getExtras();
        BookName=bundle.getString("BookName");

        HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address +"/book/name?BookName="+BookName, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Admin_search_bookinfo.this, "post请求失败", Toast.LENGTH_SHORT).show();
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
//                        System.out.println(list.get(0));
                        String from[]={"img","bookName","bookWriter","bookType","bookPrice","bookRank","bookPublicer"};
                        int to[]={R.id.BookImg,R.id.BookName,R.id.BookWriter,R.id.BookType,R.id.BookPrice,R.id.BookRank,R.id.BookPublicer};
                        //创建适配器进行数据适配
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(Admin_search_bookinfo.ACTIVITY_SERVICE,string);
                                SimpleAdapter simpleAdapter = new SimpleAdapter(getApplication(), list, R.layout.activity_admin_search_bookinfo1, from, to);
                                listView.setAdapter(simpleAdapter);
                            }
                        });


                    }



                });
            }
        });

    }
}