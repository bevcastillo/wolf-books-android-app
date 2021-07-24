package com.example.bookfinderapp.request;

import com.example.bookfinderapp.modelV2.Books;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestService {
    @GET("/books/v1/volumes")
    Call<Books> getVolumeBooks(@Query("q") String searchText);

    @GET("/books/v1/volumes?orderBy=newest&q=books")
    Call<Books> getNewBooks();
}
