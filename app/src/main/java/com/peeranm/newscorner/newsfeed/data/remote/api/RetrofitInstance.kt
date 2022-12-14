package com.peeranm.newscorner.newsfeed.data.remote.api

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.peeranm.newscorner.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance(private val context: Context) {
    val newsAPI: NewsAPI by lazy {
        val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(ConnectionInterceptor(context))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(NewsAPI::class.java)
    }
}