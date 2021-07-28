package com.bevstudio.wolfbooksapp.request.api;

import com.bevstudio.wolfbooksapp.helper.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClass {

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RequestService getAPIInstance() {
        return getRetrofitInstance().create(RequestService.class);
    }

    public static Retrofit getNewBooksInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RequestService getNewBooksAPIInstance() {
        return getNewBooksInstance().create(RequestService.class);
    }
}
