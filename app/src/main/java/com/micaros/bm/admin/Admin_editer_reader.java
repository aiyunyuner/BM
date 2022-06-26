package com.micaros.bm.admin;

import androidx.appcompat.app.AppCompatActivity;

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
import com.micaros.bm.ReaderInfo;
import com.micaros.bm.utils.HttpGetRequest;
import com.micaros.bm.utils.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Admin_editer_reader extends AppCompatActivity {
    private ListView listView;
    private ImageButton back_bt;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.micaros.bm.R.layout.activity_admin_editer_reader);
        init();
    }
        private  void init(){
            back_bt = (ImageButton) findViewById(R.id.deletereader_back);
            back_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    String struser = user_ed.getText().toString();
                    Intent intent = new Intent(Admin_editer_reader.this, Admin_manager_reader.class);
                    startActivity(intent);
                }
            });

            listView = (ListView) findViewById(R.id.delete_reader_list);
            HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/user/all", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Admin_editer_reader.this, "post请求失败", Toast.LENGTH_SHORT).show();
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

                            String from[] = {"id", "uid", "password", "phone"};
                            int to[] = {R.id.Tid, R.id.uid, R.id.upassword, R.id.uphone};
                            SimpleAdapter simpleAdapter = new SimpleAdapter(getApplication(), list, R.layout.activity_admin_delete_reader1, from, to);
                            listView.setAdapter(simpleAdapter);
                            //listview的单击事件
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                                    传值到修改界面
                                    int i = position + 1;
                                    textView=view.findViewById(R.id.uid);
                                    Log.i(Admin_editer_reader.ACTIVITY_SERVICE,textView.getText().toString());
                                    Intent intent = new Intent(Admin_editer_reader.this, Admin_update_reader.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("uid", textView.getText().toString());

                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                        }
                    });

                }
            });
        }
}