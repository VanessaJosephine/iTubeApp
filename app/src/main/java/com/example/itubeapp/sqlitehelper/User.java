package com.example.itubeapp.sqlitehelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class User {

    private int id;
    String username, name, description;
    public User(String name, String username, String description) {
        this.username = username;
        this.name = name;
        this.description = description;
    }

    public String getUsername() { return username; }
    public String getDescription() { return description; }
    public String getName() {
        return name;
    }
}
