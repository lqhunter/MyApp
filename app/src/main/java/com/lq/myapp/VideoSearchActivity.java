package com.lq.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class VideoSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_search);

        Intent intent = getIntent();
        Bundle bu = intent.getExtras();
        String searchKey = bu.getString("searchKey");
        Toast.makeText(this, searchKey, Toast.LENGTH_SHORT).show();
    }
}
