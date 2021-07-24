package com.example.bookfinderapp.network;

import com.example.bookfinderapp.models.BooksVolume;
import com.example.bookfinderapp.models.RetroPhoto;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET("/books/v1/volumes?q=bible")
    Call<List<BooksVolume>> getAllBooks();
}
