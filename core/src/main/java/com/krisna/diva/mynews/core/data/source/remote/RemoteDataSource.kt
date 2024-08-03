package com.krisna.diva.mynews.core.data.source.remote

import com.krisna.diva.mynews.core.data.source.remote.network.ApiResponse
import com.krisna.diva.mynews.core.data.source.remote.network.ApiService
import com.krisna.diva.mynews.core.data.source.remote.response.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource (private val apiService: ApiService) {
    suspend fun getAllNews(): Flow<ApiResponse<List<NewsResponse>>> {
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.articles?.filterNotNull()
                if (dataArray != null) {
                    if (dataArray.isNotEmpty()) {
                        emit(ApiResponse.Success(dataArray))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}
