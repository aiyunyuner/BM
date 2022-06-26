package com.micaros.bm.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.micaros.bm.R;

public class Admin_manager_reader extends AppCompatActivity {
    private ImageButton back_tn, addreader, editreader, deletereader, selectReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manager_reader);
        init();
    }

    private void init() {
        //返回按钮的事件监听
        back_tn = (ImageButton) findViewById(R.id.manreader_back);
        back_tn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_manager_reader.this, Admin_content.class);
                startActivity(intent);
            }
        });
//查找读者按钮的事件监听
        selectReader=(ImageButton) findViewById(R.id.ad_selctreader);
        selectReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_manager_reader.this,Select_reader_admin.class);
                startActivity(intent);
            }
        });
        //添加读者按钮的事件监听
        addreader=(ImageButton) findViewById(R.id.ad_addreader);
        addreader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_manager_reader.this,Admin_add_reader.class);
                startActivity(intent);
            }
        });
        //编辑读者按钮的事件监听
        editreader = (ImageButton) findViewById(R.id.ad_editReader);
        editreader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_manager_reader.this, Admin_editer_reader.class);
                startActivity(intent);
            }
        });
        //删除读者按钮的事件监听
        deletereader = (ImageButton) findViewById(R.id.ad_deleteReader);
        deletereader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_manager_reader.this, Admin_delete_reader.class);
                startActivity(intent);
            }
        });

    }
}