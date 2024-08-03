package com.krisna.diva.mynews.core.data.source.remote.network

import com.krisna.diva.core.BuildConfig
import com.krisna.diva.mynews.core.data.source.remote.response.ListNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/everything")
    suspend fun getList(
        @Query("q") query: String = "indonesia",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): ListNewsResponse
}