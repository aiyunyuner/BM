package com.micaros.bm.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.micaros.bm.R;

public class Admin_manager_book extends AppCompatActivity {
    private ImageButton back_bt, addbook,  searchbook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manager_book);
            init();


    }

        private void init(){
            back_bt=(ImageButton)findViewById(R.id.manbook_back);
            back_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Admin_manager_book.this,admin_content_activity.class);
                        startActivity(intent);
                }
            });

            addbook=(ImageButton)findViewById(R.id.ad_add);
            addbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Admin_manager_book.this, Admin_add_book.class);
                    startActivity(intent);
                }
            });
            searchbook=(ImageButton)findViewById(R.id.ad_search);
            searchbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(Admin_manager_book.this,Admin_search_book.class);
                    startActivity(intent);
                }
            });
        }
}