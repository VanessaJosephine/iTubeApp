
package com.example.itubeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.itubeapp.sqlitehelper.DatabaseHelper;
import com.example.itubeapp.sqlitehelper.Video;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {

    private ArrayList<Video> videoArrayList;
    private DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    VideoAdapter videoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        // SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        // Get Videos
        databaseHelper = new DatabaseHelper(PlaylistActivity.this);
        videoArrayList = databaseHelper.getMyVideos(sharedPreferences.getString("username", ""));

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerViewVideos);
        layoutManager = new LinearLayoutManager(PlaylistActivity.this, LinearLayoutManager.VERTICAL, false);
        videoAdapter = new VideoAdapter(PlaylistActivity.this, videoArrayList);
        recyclerView.setAdapter(videoAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}