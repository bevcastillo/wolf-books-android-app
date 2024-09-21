package com.bevstudio.wolfbooksapp.request.api

import com.bevstudio.wolfbooksapp.helper.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClass {
    val retrofitInstance: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @JvmStatic
    val aPIInstance: RequestService
        get() = retrofitInstance.create(RequestService::class.java)

    val newBooksInstance: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val newBooksAPIInstance: RequestService
        get() = newBooksInstance.create(RequestService::class.java)
}
