package com.example.bookfinderapp.helper;

import com.example.bookfinderapp.models.Results;
import com.example.bookfinderapp.models.VolumeBooks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public class Api {
    static String BASE_URL = "https://simplifiedcoding.net/demos/";

    @GET("marvel")
    Call<List<Results>> getsuperHeroes();
}
