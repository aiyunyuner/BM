package com.micaros.bm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Map;

public class BorrowInfoActivity extends Activity  {

    private List<Map<String,Object>> list;
    private SimpleAdapter adapter;
    private ListView listView;

//    private username,

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_info);


    }

}
