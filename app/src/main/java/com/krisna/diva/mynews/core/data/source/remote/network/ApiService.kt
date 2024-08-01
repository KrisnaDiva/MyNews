package com.krisna.diva.mynews.core.data.source.remote.network

import com.krisna.diva.mynews.BuildConfig
import com.krisna.diva.mynews.core.data.source.remote.response.ListNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/everything")
    fun getList(
        @Query("q") query: String = "indonesia",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Call<ListNewsResponse>
}