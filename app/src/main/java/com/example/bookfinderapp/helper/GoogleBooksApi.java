package com.example.bookfinderapp.helper;
import com.example.bookfinderapp.model.db.VolumeBooks;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GoogleBooksApi {
    static String BASE_URL = "https://www.googleapis.com";

    @GET("/books/v1/volumes?q=subject:{}")
    public Call<VolumeBooks> getFictionBooks();
}
