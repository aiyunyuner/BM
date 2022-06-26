package com.micaros.bm.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.micaros.bm.R;
import com.micaros.bm.RegisterActivity;
import com.micaros.bm.pojo.Book;
import com.micaros.bm.utils.HttpPostRequest;
import com.micaros.bm.utils.HttpUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Admin_add_book extends AppCompatActivity  implements View.OnClickListener {

    private ImageButton back_bt;
    private ArrayAdapter<String> adapter;
    private Spinner spinner;  //下拉框
    private List<String> list = new ArrayList<String>();
    private ImageView bookimg;
    private String pub;
    Uri uri;
    private EditText et_bookid,et_bookname,et_booktype,et_bookwriter,et_bookpublicer,et_bookprice,et_bookrank,et_bookcomment;
    private  String BookId,BookName,BookType,BookWriter,BookPublic,BookPrice,BookRank,BookComment;
    private Button btn_bookcommit,btn_bookback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_book);
        list.add("南京出版社");
        list.add("天津出版社");
        list.add("上海出版社");
        list.add("北京出版社");
        list.add("四川出版社");
        spinner = (Spinner) findViewById(R.id.spinner2);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pub = adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        init();

    }

    public  void init() {
        bookimg = findViewById(R.id.bookimg);

        et_bookid = findViewById(R.id.et_bookid);
        et_bookname = findViewById(R.id.et_bookname);
        et_booktype = findViewById(R.id.et_booktype);
        et_bookwriter = findViewById(R.id.et_bookwriter);
        et_bookprice = findViewById(R.id.et_bookprice);
        et_bookrank = findViewById(R.id.et_bookrank);
        et_bookcomment = findViewById(R.id.et_bookcomment);


        btn_bookcommit = findViewById(R.id.btn_bookcommit);
        btn_bookback = findViewById(R.id.btn_bookback);
        bookimg.setOnClickListener(this);
        btn_bookback.setOnClickListener(this);
        btn_bookcommit.setOnClickListener(this);
    }
                //获取输入信息
                private void getData()
                {
                    BookId= et_bookid.getText().toString().trim();
                    BookName = et_bookname.getText().toString().trim();
                    BookType = et_booktype.getText().toString().trim();
                    BookWriter =et_bookwriter.getText().toString().trim();
                    BookPrice =et_bookprice.getText().toString().trim();
                    BookRank =et_bookrank.getText().toString().trim();
                    BookComment =et_bookcomment.getText().toString().trim();


                }

            //对管理员输入进行验证。全部符合才能通过
    boolean testid=true,testother=true;
                @Override

                public void onClick(View view){
                    switch (view.getId()){
                        case  R.id.btn_bookcommit:
                            getData();
                            //ISBN为十位，且不为空
                            if (et_bookid.getText().length()!=10) {
                                Toast.makeText(Admin_add_book.this,"请输入10位图书ISBN",Toast.LENGTH_SHORT).show();
                                testid=false;
                                break;
                            }

                            if(et_bookname.getText().length()==0){
                                Toast.makeText(Admin_add_book.this,"请输入完整图书信息",Toast.LENGTH_SHORT).show();
                                testother=false;
                                break;
                            }
                            if(testid==true&&testother==true){
                            RequestBody requestBody=new  FormBody.Builder()
                                    .add("BookId",BookId)
                                    .add("BookName",BookName)
                                    .add("BookType",BookType)
                                    .add("BookWriter",BookWriter)
                                    .add("BookPublicer",pub)
                                    .add("BookPrice",BookPrice)
                                    .add("BookRank",BookRank)
                                    .add("BookComment",BookComment).build();
                HttpPostRequest.okhttpPost(HttpUtils.address + "/book/Ibook", requestBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Admin_add_book.this, "post请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Book book= JSON.parseObject(string, Book.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Admin_add_book.this,"添加成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
                });

                            break;

                    }
                        case R.id.bookimg:
                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                            intent.setType("image/*");
                            startActivityForResult(intent,1);  // 第二个参数是请求码
                            break;

                        case R.id.btn_bookback:
                            Intent intentback=new Intent();
                            intentback.setClass(Admin_add_book.this,Admin_manager_book.class);
                            startActivity(intentback);


                }


    }  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:  // 请求码
                parseUri(data);
                break;
            default:
        }
    }

    public void parseUri(Intent data) {
        uri=data.getData();
        InputStream is=null;
        Bitmap bmp=null;
        if(uri.getAuthority()!=null){
            try {
                is=Admin_add_book.this.getContentResolver().openInputStream(uri);
                bmp= BitmapFactory.decodeStream(is);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        bookimg.setImageBitmap(bmp);
    }

}


