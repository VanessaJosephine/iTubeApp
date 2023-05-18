package com.example.itubeapp.sqlitehelper;

public class Video {
    private int id;
    String name, videoUrl;

    public Video(String name, String videoUrl) {
        this.name = name;
        this.videoUrl = videoUrl;
    }

    public String getName() {
        return name;
    }
    public String getVideoUrl() { return videoUrl; }
}
