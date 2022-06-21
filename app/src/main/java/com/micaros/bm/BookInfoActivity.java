

package com.micaros.bm;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.micaros.bm.pojo.Book;
import com.micaros.bm.pojo.User;
import com.micaros.bm.utils.HttpGetRequest;
import com.micaros.bm.utils.HttpUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;


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

        System.out.println("传过来的bundle id:"+id);

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
}
