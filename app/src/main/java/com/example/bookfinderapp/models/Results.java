package com.example.bookfinderapp.models;

import com.google.gson.annotations.SerializedName;

public class Results {
    @SerializedName("name")
    private String superName;


    public Results(String name) {
        this.superName = name;
    }

    public String getName() {
        return superName;
    }
}
