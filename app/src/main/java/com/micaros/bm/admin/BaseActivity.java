//package com.micaros.bm.admin;
//
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.micaros.bm.utils.ActivityCollector;
//
///**
// * 随时随地退出程序
// */
//
//public class BaseActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ActivityCollector.addActivity(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ActivityCollector.removeActivity(this);
//    }
//}
