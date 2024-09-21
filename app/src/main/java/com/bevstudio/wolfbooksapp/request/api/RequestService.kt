package com.bevstudio.wolfbooksapp.request.api

import com.bevstudio.wolfbooksapp.model.api.Books
import com.bevstudio.wolfbooksapp.model.api.Item
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestService {
    @GET("/books/v1/volumes")
    fun getVolumeBooks(@Query(value = "q") searchText: String?): Call<Books>

    @GET("/books/v1/volumes/{id}")
    fun getBookItem(@Path("id") id: String?): Call<Item>

    @get:GET("/books/v1/volumes?q=categories:young+fiction&maxResults=30")
    val newReleaseBooks: Call<Books>

    @GET("/books/v1/volumes")
    fun getCategories(
        @Query(value = "q") searchText: String?,
        @Query("filter") filter: String?,
        @Query("orderBy") orderBy: String?,
        @Query("maxResults") maxResults: Int
    ): Call<Books>


    @GET("/books/v1/volumes")
    fun getSearchResults(
        @Query(value = "q") searchText: String?,
        @Query("startIndex") startIndex: Int,
        @Query("orderBy") orderBy: String?,
        @Query("maxResults") maxResults: Int
    ): Call<Books>
}
