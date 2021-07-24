package com.example.bookfinderapp.request;

import com.example.bookfinderapp.helper.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClass {
    private static String BASE_URL = "https://www.googleapis.com/";

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RequestService getAPIInstance() {
        return getRetrofitInstance().create(RequestService.class);
    }

    public static Retrofit getNewBooksInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RequestService getNewBooksAPIInstance() {
        return getNewBooksInstance().create(RequestService.class);
    }
}
