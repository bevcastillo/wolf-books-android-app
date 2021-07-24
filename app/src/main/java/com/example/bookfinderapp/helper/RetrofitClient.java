package com.example.bookfinderapp.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private GoogleBooksApi googleBooksApiRequest;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GoogleBooksApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        googleBooksApiRequest = retrofit.create(GoogleBooksApi.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public GoogleBooksApi getMyApi() {
        return googleBooksApiRequest;
    }

}
