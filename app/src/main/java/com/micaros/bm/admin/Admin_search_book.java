package com.micaros.bm.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.micaros.bm.R;

public class Admin_search_book extends AppCompatActivity {
    private Button search;
    private EditText search_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search_book);
        search=findViewById(R.id.search_btn);
        search_name=findViewById(R.id.search_name);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_search_book.this, Admin_search_bookinfo.class);
               //Activity之间传递数据
                Bundle bundle=new Bundle();
                        bundle.putString("BookName",search_name.getText().toString());
                        //把数据暂存到intent中
                        intent.putExtras(bundle);
                        startActivity(intent);

            }
        });
    }
}