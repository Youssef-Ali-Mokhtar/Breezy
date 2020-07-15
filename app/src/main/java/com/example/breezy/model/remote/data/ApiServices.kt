package com.example.breezy.model.remote.data

import com.example.breezy.model.remote.pojo.contents.ContentsResponse
import com.example.breezy.model.remote.pojo.titles.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("sources")
    fun getNewsSources(@Query("apiKey") apiKey : String,
                       @Query("category") category : String,
                       @Query("country") country : String,
                       @Query("language") language : String) : Call<SourcesResponse>

    @GET("everything")
    fun getNewsContent(@Query("apiKey") apiKey : String,
                       @Query("sources") sources : String) : Call<ContentsResponse>
}