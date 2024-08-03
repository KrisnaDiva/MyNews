package com.krisna.diva.mynews.core.data.source.remote.network
//menangani respons dari API
sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : com.krisna.diva.mynews.core.data.source.remote.network.ApiResponse<T>()
    data class Error(val errorMessage: String) : com.krisna.diva.mynews.core.data.source.remote.network.ApiResponse<Nothing>()
    object Empty : com.krisna.diva.mynews.core.data.source.remote.network.ApiResponse<Nothing>()
}