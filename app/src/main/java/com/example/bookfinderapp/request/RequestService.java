package com.example.bookfinderapp.remote;

import com.example.bookfinderapp.modelV2.Books;
import com.example.bookfinderapp.modelV2.Item;
import com.example.bookfinderapp.modelV2.VolumeInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestService {
    @GET("/books/v1/volumes")
    Call<Books> getVolumeBooks(@Query("q") String searchText);

    @GET("/books/v1/volumes?orderBy=newest&q=books")
    Call<Books> getNewBooks();
}
