package com.micaros.bm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.micaros.bm.pojo.Book;
import com.micaros.bm.utils.HttpGetRequest;
import com.micaros.bm.utils.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 *
 */
public class ContentActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private long mExitTime;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        init();
    }


    private void init() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
//        侧滑菜单栏的选项
        navigationView.setCheckedItem(R.id.shoucang);//设置菜单项的默认选项
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.shoucang:
//                        Intent intent2 = new Intent(contentActivity.this, collectActivity.class);
//                        startActivity(intent2);
                        break;
                    case R.id.exit:
                        finish();
                        break;
                    case R.id.jieyue:
//                        跳转到个人借书的页面
                        Intent intent = new Intent(ContentActivity.this, BorrowInfoActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.updateInfo:
                        //跳转到修改个人信息页面
                        Intent intent3 = new Intent(ContentActivity.this, ReaderInfo.class);
                        startActivity(intent3);
                        break;
                    default:

                }
                drawerLayout.closeDrawers();//将滑动菜单关闭
                return true;
            }
        });
////        insert();//插入数据往借书表中
        listView = (ListView) findViewById(R.id.list_view);
//
        HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/book/all", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ContentActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
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
                        System.out.println(list.get(0));
                        String from[] = {"id","img", "bookName", "bookWriter"};
                        int to[] = {R.id.id,R.id.book_image, R.id.book_name, R.id.book_author};
                        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplication(), list, R.layout.book_item, from, to);
                        listView.setAdapter(simpleAdapter);
                    }
                });
            }
        });

        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = position + 1;
//                setTitle("点击" + i + "的item");

                textView = view.findViewById(R.id.id);
                CharSequence viewText = textView.getText();
                String string = viewText.toString();
//                System.out.println(string);
                double parseDouble = Double.parseDouble(string);
                int parseId = (int)parseDouble;
                System.out.println("准备传给BooInfo的id值:"+parseId); //转换为int类型的id


                Intent intent = new Intent(ContentActivity.this, BookInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", parseId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    //toolbar的菜单栏的选项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.search:
                Toast.makeText(ContentActivity.this, "search", Toast.LENGTH_LONG).show();
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(ContentActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}