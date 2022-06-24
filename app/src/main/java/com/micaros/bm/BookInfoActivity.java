

package com.micaros.bm;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.micaros.bm.pojo.Book;
import com.micaros.bm.pojo.User;
import com.micaros.bm.utils.HttpGetRequest;
import com.micaros.bm.utils.HttpPostRequest;
import com.micaros.bm.utils.HttpUtils;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;


/*
*
*    点击书籍后跳转的详情页面
*    显示书籍信息  借阅按钮以及收藏按钮
*
* */


public class BookInfoActivity extends AppCompatActivity {

    private TextView bid,bname,bauthor,bprice;
    private Button borrow_bt,collect_bt;
    private String str;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
/*
        获取当前系统时间
        用作借书日期time
         */
        SimpleDateFormat formatter = new SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        str = formatter.format(curDate);

        bid=(TextView)findViewById(R.id.bookbid);
        bname=(TextView)findViewById(R.id.bookbname);
        bauthor=(TextView)findViewById(R.id.bookbauthor);
        bprice=(TextView)findViewById(R.id.bookbprice);

        Bundle bundle = this.getIntent().getExtras();//获取上一个activity传过来的id
        int id = bundle.getInt("id");

//        System.out.println("传过来的bundle id:"+id);

        HttpGetRequest.sendOkHttpGetRequest(HttpUtils.address + "/book/findBook?id="+id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BookInfoActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Book book = JSON.parseObject(string, Book.class);
                        System.out.println(book);
                        if (book != null) {

                            bid.setText(book.getBookId());
                            bname.setText(book.getBookName());
                            bauthor.setText(book.getBookWriter());
                            bprice.setText(book.getBookPrice());

                        }
                        else
                        {

                        }
                    }
                });

            }
        });



        //借阅按钮

        borrow_bt = findViewById(R.id.borroe_bt);
        borrow_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences perf=getSharedPreferences("data",MODE_PRIVATE);
                // String datetime=perf.getString("time","");//获得当前系统时间
                String username=perf.getString("uid","");//获得当前用户名称
//                System.out.println(username);
                String strbid=bid.getText().toString(); //获取图书编号
//                System.out.println(strbid);
                String strbname=bname.getText().toString(); //获取图书名称
//                System.out.println(strbname);
                String strbauthor=bauthor.getText().toString();//获取书籍作者
//                System.out.println(strbauthor);
//                System.out.println(str);
                int intbid=Integer.parseInt(strbid);

                RequestBody requestBody = new FormBody.Builder()
                        .add("BorName", username)
                        .add("BookId", strbid)
                        .add("BookName", strbname)
                        .add("BookAuthor", strbauthor)
                        .add("NowTime",str).build();

                HttpPostRequest.okhttpPost(HttpUtils.address+"/borrow/add", requestBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
//                        System.out.println("666");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BookInfoActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
//                        System.out.println("there");
                        String string = response.body().string();

//                        JSON.parseObject(string,Bor)
                        Integer integer = JSON.parseObject(string, Integer.class);

                        runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (integer > 0)
                            {
                                Toast.makeText(BookInfoActivity.this, "借阅成功！", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(BookInfoActivity.this, "借阅失败!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    }
                });
//
                ContentValues values=new ContentValues();
                values.put("Bookid",intbid);
                values.put("bookname",strbname);
                values.put("bookauthor",strbauthor);
                values.put("Borname",username);
                values.put("nowtime",str);
//

                Toast.makeText(BookInfoActivity.this,"借阅成功",Toast.LENGTH_LONG).show();
//
            }
        });


        //收藏按钮
        collect_bt = findViewById(R.id.collect_bt);
        collect_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences perf=getSharedPreferences("data",MODE_PRIVATE);
                 String datetime=perf.getString("time","");//获得当前系统时间
                String username=perf.getString("uid","");//获得当前用户名称
                System.out.println(username);
                String strbid=bid.getText().toString(); //获取图书编号
                System.out.println(strbid);
                String strbname=bname.getText().toString(); //获取图书名称
                System.out.println(strbname);
                String strbauthor=bauthor.getText().toString();//获取书籍作者
                System.out.println(strbauthor);
                System.out.println(str);
                int intbid=Integer.parseInt(strbid);

                RequestBody requestBody = new FormBody.Builder()
                        .add("BorName", username)
                        .add("BookId", strbid)
                        .add("BookName", strbname)
                        .add("BookAuthor", strbauthor)
                        .add("NowTime",str).build();

                HttpPostRequest.okhttpPost(HttpUtils.address + "/collect/add", requestBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BookInfoActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
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
                                if(integer >0)
                                {
                                    Toast.makeText(BookInfoActivity.this,"收藏成功",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(BookInfoActivity.this,"收藏失败",Toast.LENGTH_LONG).show();
                                }
                            }
                        });


                    }
                });

            }
        });




    }
}
