package com.micaros.bm.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.micaros.bm.R;
import com.micaros.bm.pojo.User;
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

public class Admin_delete_reader extends AppCompatActivity {
    private ListView listView;
    private ImageButton back_bt;
    private TextView textView;

    private String UserId;
    private List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.micaros.bm.R.layout.activity_admin_delete_reader);
        init();
        del();
    }

    public void init() {
        back_bt = (ImageButton) findViewById(R.id.editreader_back);
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_delete_reader.this, Admin_manager_reader.class);
                startActivity(intent);

            }
        });


        listView = (ListView) findViewById(R.id.edit_reader_list);
        HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/user/all", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Admin_delete_reader.this, "post请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                list = new Gson().fromJson(string, new TypeToken<ArrayList<Map>>() {
                }.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String from[] = {"id", "uid", "password", "phone"};

                        int to[] = {R.id.Tid, R.id.uid, R.id.upassword, R.id.uphone};
                        //创建适配器进行数据适配
                        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplication(), list, R.layout.activity_admin_delete_reader1, from, to);
                        listView.setAdapter(simpleAdapter);


                    }
                });
            }

        });
    }


    public void del() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_delete_reader.this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView = view.findViewById(R.id.Tid);
                CharSequence text = textView.getText();
                String string = text.toString();
                double v = Double.parseDouble(string);
                int id1 = (int)v;

                builder.setMessage("确定要删除该用户吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/user/delete?id="+id1, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        Toast.makeText(Admin_delete_reader.this, "post请求失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/user/all", new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Admin_delete_reader.this, "post请求失败", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {
                                                String string = response.body().string();
                                                list = new Gson().fromJson(string, new TypeToken<ArrayList<Map>>() {
                                                }.getType());
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        String from[] = {"id", "uid", "password", "phone"};

                                                        int to[] = {R.id.Tid, R.id.uid, R.id.upassword, R.id.uphone};
                                                        //创建适配器进行数据适配
                                                        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplication(), list, R.layout.activity_admin_delete_reader1, from, to);
                                                        listView.setAdapter(simpleAdapter);


                                                    }
                                                });
                                            }

                                        });
//                                        Log.i("ddddd", response.body().string());
                                        String from[] = {"id", "uid", "password", "phone"};
                                        int to[] = {R.id.Tid, R.id.uid, R.id.upassword, R.id.uphone};
                                        //创建适配器进行数据适配
                                        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplication(), list, R.layout.activity_admin_delete_reader1, from, to);
                                        listView.setAdapter(simpleAdapter);
                                    }
                                });
                            }
                        });


                    }
                }); builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                ;

            }
        });
    }
}