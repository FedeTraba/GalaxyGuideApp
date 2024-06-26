package com.example.myapplication.data

import com.example.myapplication.model.Publication
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PublicationsAPI {
    @GET("planetary/apod?")
    suspend fun getTodayPublication(@Query("api_key") apiKey: String): Response<Publication?>

    @GET("planetary/apod?")
    suspend fun getPublicationByDate(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Response<Publication?>

    @GET("planetary/apod?")
    suspend fun getPublicationsByDateRange(
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Response<List<Publication>?>


}