package com.micaros.bm.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.micaros.bm.ContentActivity;
import com.micaros.bm.R;
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

public class admin_select_bookinfo extends AppCompatActivity {
    private ImageButton back_bt;
    private ListView listView;
    private TextView textView;
    private List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_select_bookinfo);
            init();
            del();

    }

    public void init() {

        back_bt = (ImageButton) findViewById(R.id.sel_book_back);
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_select_bookinfo.this, Admin_select_message.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.select_book_list);

        HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/book/all", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(admin_select_bookinfo.this, "post请求失败", Toast.LENGTH_SHORT).show();
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
                                String from[] = {"img","id", "bookName", "bookWriter","bookType","bookPrice","bookRank","bookPublicer"};
                                int to[] = {R.id.book_image, R.id.book_id,R.id.book_name, R.id.book_writer,R.id.book_type,R.id.book_price,R.id.book_rank,R.id.book_publicer};
                               SimpleAdapter simpleAdapter = new SimpleAdapter(getApplication(), list, R.layout.activity_admin_select_bookinfo1, from, to);
                                listView.setAdapter(simpleAdapter);
                            }
                        });
                    }
                });
            }
    public  void del(){
        AlertDialog.Builder builder = new AlertDialog.Builder(admin_select_bookinfo.this);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    textView = view.findViewById(R.id.book_id);
                    CharSequence text = textView.getText();
                    String string = text.toString();
                    double book = Double.parseDouble(string);
                    int dbook = (int) book;


                    builder.setMessage("确定要删除该图书吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/book/dbook?id=" + dbook, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            Toast.makeText(admin_select_bookinfo.this, "post请求失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/book/all", new Callback() {
                                                @Override
                                                public void onFailure(Call call, IOException e) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(admin_select_bookinfo.this, "post请求失败", Toast.LENGTH_SHORT).show();
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
                                                            String from[] = {"img", "id", "bookName", "bookWriter", "bookType", "bookPrice", "bookRank", "bookPublicer"};
                                                            int to[] = {R.id.book_image, R.id.book_id, R.id.book_name, R.id.book_writer, R.id.book_type, R.id.book_price, R.id.book_rank, R.id.book_publicer};
                                                            SimpleAdapter simpleAdapter = new SimpleAdapter(getApplication(), list, R.layout.activity_admin_select_bookinfo1, from, to);
                                                            listView.setAdapter(simpleAdapter);
                                                        }
                                                    });
                                                }


                                            });
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

                }
            });}}