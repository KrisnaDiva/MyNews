package com.krisna.diva.mynews.core.data

import com.krisna.diva.mynews.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@Suppress("EmptyMethod")
abstract class NetworkBoundResource<ResultType, RequestType> {

    private val resultFlow: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val localData = fetchFromDB().firstOrNull()
        if (shouldFetchFromNetwork(localData)) {
            emit(Resource.Loading())
            when (val response = makeApiCall().firstOrNull()) {
                is ApiResponse.Success -> {
                    saveApiResult(response.data)
                    emitAll(fetchFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(fetchFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    handleFetchFailure()
                    emit(Resource.Error(response.errorMessage))
                }
                null -> {
                    emit(Resource.Error("Unknown error"))
                }
            }
        } else {
            emitAll(fetchFromDB().map { Resource.Success(it) })
        }
    }

    protected open fun handleFetchFailure() {}

    protected abstract fun fetchFromDB(): Flow<ResultType>

    protected abstract fun shouldFetchFromNetwork(data: ResultType?): Boolean

    protected abstract suspend fun makeApiCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveApiResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = resultFlow
}
