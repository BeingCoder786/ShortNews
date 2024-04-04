package com.example.newsinshort.data.api

import com.example.newsinshort.data.entity.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// all endpoint

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getNewsHeadLine(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = "4b0666599894482a895932b5ef9ce2d3",
    ): Response<NewsResponse>
}

// url -  https://newsapi.org/v2/top-headlines?country=us&apiKey=4b0666599894482a895932b5ef9ce2d3
