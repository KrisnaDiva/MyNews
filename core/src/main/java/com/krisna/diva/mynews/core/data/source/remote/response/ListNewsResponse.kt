package com.krisna.diva.mynews.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListNewsResponse(

    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("articles")
    val articles: List<NewsResponse?>? = null,

    @field:SerializedName("status")
    val status: String? = null
)