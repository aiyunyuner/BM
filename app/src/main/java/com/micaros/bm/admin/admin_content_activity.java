package com.micaros.bm.admin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.micaros.bm.R;

public class admin_content_activity extends AppCompatActivity {
    private long mExitTime;
    private ImageButton selct_bt, manReader_bt, manBook_bt;

    //    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_content);
        init();//界面初始化

    }

    private void init() {
        //查询信息
        selct_bt=(ImageButton) findViewById(R.id.ad_select);
        selct_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(admin_content_activity.this,Admin_select_message.class);
                startActivity(intent);
            }
        });
        //管理读者
        manReader_bt=(ImageButton)findViewById(R.id.ad_manager_reader);
        manReader_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(admin_content_activity.this,Admin_manager_reader.class);
                startActivity(intent);
            }
        });
        //管理图书
        manBook_bt=(ImageButton)findViewById(R.id.ad_manager_book);
        manBook_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(admin_content_activity.this,Admin_manager_book.class);
                startActivity(intent);
            }
        });


    }
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(admin_content_activity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {

            finish();
            System.exit(0);
        }
    }


}
