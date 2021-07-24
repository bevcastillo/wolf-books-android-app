package com.example.bookfinderapp;

import com.example.bookfinderapp.models.BooksVolume;
import com.example.bookfinderapp.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceholderAPI {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("books/v1/volumes?q=sherlockholmes")
    Call<List<BooksVolume>> getVolumeBooks();
//
//    @GET("books/v1/volumes?q={key}")
//    Call<List<BooksVolume>> getVolumeSearch(@Query("q") String key);
}
