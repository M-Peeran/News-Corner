package com.peeranm.newscorner.newsfeed.data.remote.api

import com.peeranm.newscorner.BuildConfig
import com.peeranm.newscorner.newsfeed.data.remote.dto.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("/v2/top-headlines")
    suspend fun getHeadlineNews(
        @Query("country")
        countryCode: String,
        @Query("category")
        category: String,
        @Query("page")
        page: Int,
        @Query("pageSize")
        pageSize: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ) : Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun searchForNews(
        @Query("q")
        query: String,
        @Query("page")
        page: Int,
        @Query("pageSize")
        pageSize: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<NewsResponse>

}