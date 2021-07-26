package com.example.bookfinderapp.request.api;

import com.example.bookfinderapp.model.api.Books;
import com.example.bookfinderapp.model.api.Item;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestService {
    @GET("/books/v1/volumes")
    Call<Books> getVolumeBooks(@Query(value = "q") String searchText);

    @GET("/books/v1/volumes/{id}")
    Call<Item> getBookItem(@Path("id") String id);

    @GET("/books/v1/volumes?q=categories:young+fiction&maxResults=30")
    Call<Books> getNewReleaseBooks();

    @GET("/books/v1/volumes")
    Call<Books> getSearchResults(@Query(value = "q") String searchText, @Query("startIndex") int startIndex, @Query("orderBy") String orderBy, @Query("maxResults") int maxResults);
}
