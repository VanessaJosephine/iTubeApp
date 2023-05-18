package com.example.itubeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.itubeapp.sqlitehelper.DatabaseHelper;
import com.example.itubeapp.sqlitehelper.User;
import com.example.itubeapp.sqlitehelper.Video;

public class HomeActivity extends AppCompatActivity {
    EditText getUrl;
    Button play, addVideo, playlist;
    String videoUrl;
    Boolean flag = false;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // findViewById
        getUrl = findViewById(R.id.editTextText4);
        play = findViewById(R.id.button4);
        addVideo = findViewById(R.id.button5);
        playlist = findViewById(R.id.button6);
        // DatabaseHelper
        databaseHelper = new DatabaseHelper(this);
        // SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        Log.i("TAG", sharedPreferences.getString("username", ""));
        videoUrl = getUrl.getText().toString();
        getUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                videoUrl = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get video ID
                if (videoUrl.contains("https://www.youtube.com/embed/"))
                {
                    videoUrl = videoUrl.substring(30, videoUrl.length());
                    Log.i("TAG", videoUrl);
                    flag = true;
                }
                else if (videoUrl.contains("https://youtu.be/"))
                {
                    videoUrl = videoUrl.substring(17, videoUrl.length());
                    Log.i("TAG", videoUrl);
                    flag = true;
                }
                else Toast.makeText(HomeActivity.this, "Please enter a valid YouTube link!", Toast.LENGTH_LONG).show();
                if (flag == true)
                {
                    SharedPreferences.Editor myEditor = sharedPreferences.edit();
                    myEditor.putString("videoId", videoUrl);
                    myEditor.apply();
                    // Intent
                    Intent intent = new Intent(HomeActivity.this, PlayVideoActivity.class);
                    startActivity(intent);
                }
            }
        });

        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get video ID
                if (videoUrl.contains("https://www.youtube.com/embed/"))
                {
                    videoUrl = videoUrl.substring(30, videoUrl.length());
                    flag = true;
                }
                else if (videoUrl.contains("https://youtu.be/"))
                {
                    videoUrl = videoUrl.substring(17, videoUrl.length());
                    flag = true;
                }
                if (flag == true)
                {
                    long result = databaseHelper.insertVideo(new Video(sharedPreferences.getString("username", ""), videoUrl));
                    if(result > 0)
                    {
                        Toast.makeText(HomeActivity.this, "Video added successfully!", Toast.LENGTH_LONG).show();
                    }
                    else Toast.makeText(HomeActivity.this, "Unsuccessful!", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(HomeActivity.this, "Invalid YouTube link!", Toast.LENGTH_LONG).show();
            }
        });

        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PlaylistActivity.class);
                startActivity(intent);
            }
        });
    }
}