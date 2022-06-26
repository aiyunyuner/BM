package com.micaros.bm.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.micaros.bm.R;

public class Admin_select_message extends AppCompatActivity {
    private ImageButton back_bt,book_info,borrow_bt,pay_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_select_message);
            init();
    }
    private void init() {
        back_bt = (ImageButton) findViewById(R.id.sel_back);
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_select_message.this, admin_content_activity.class);
                startActivity(intent);
            }

        });

        book_info = (ImageButton) findViewById(R.id.ad_select_bookinfo);
        book_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_select_message.this, admin_select_bookinfo.class);
                startActivity(intent);
            }
        });

        //查询借书信息
        borrow_bt = (ImageButton) findViewById(R.id.ad_select_brrow);
        borrow_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_select_message.this, admin_borrow_info.class);
                startActivity(intent);
            }
        });

        pay_bt = (ImageButton) findViewById(R.id.ad_manager_reader);
        pay_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_select_message.this, Admin_pay_info.class);
                startActivity(intent);
            }
        });
    }

}