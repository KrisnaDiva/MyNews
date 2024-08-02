package com.krisna.diva.mynews.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.krisna.diva.mynews.core.data.source.local.LocalDataSource
import com.krisna.diva.mynews.core.data.source.remote.RemoteDataSource
import com.krisna.diva.mynews.core.data.source.remote.network.ApiResponse
import com.krisna.diva.mynews.core.data.source.remote.response.NewsResponse
import com.krisna.diva.mynews.core.domain.model.News
import com.krisna.diva.mynews.core.domain.repository.INewsRepository
import com.krisna.diva.mynews.core.utils.AppExecutors
import com.krisna.diva.mynews.core.utils.DataMapper

class NewsRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : INewsRepository {

    companion object {
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllNews(): LiveData<Resource<List<News>>> =
        object : NetworkBoundResource<List<News>, List<NewsResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<News>> {
                return localDataSource.getAllNews().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<News>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<NewsResponse>>> =
                remoteDataSource.getAllNews()

            override fun saveCallResult(data: List<NewsResponse>) {
                val newsList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertNews(newsList)
            }
        }.asLiveData()

//    override fun getFavoriteNews(): LiveData<List<News>> {
//        return Transformations.map(localDataSource.getFavoriteNews()) {
//            DataMapper.mapEntitiesToDomain(it)
//        }
//    }

    override fun setFavoriteNews(news: News, state: Boolean) {
        val newsEntity = DataMapper.mapDomainToEntity(news)
        appExecutors.diskIO().execute { localDataSource.setFavoriteNews(newsEntity, state) }
    }
}

