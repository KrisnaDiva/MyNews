package com.krisna.diva.mynews.core.di

import android.content.Context
import com.krisna.diva.mynews.core.data.NewsRepository
import com.krisna.diva.mynews.core.data.source.local.LocalDataSource
import com.krisna.diva.mynews.core.data.source.local.room.NewsDatabase
import com.krisna.diva.mynews.core.data.source.remote.RemoteDataSource
import com.krisna.diva.mynews.core.data.source.remote.network.ApiConfig
import com.krisna.diva.mynews.core.domain.usecase.NewsInteractor
import com.krisna.diva.mynews.core.domain.usecase.NewsUseCase
import com.krisna.diva.mynews.core.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val database = NewsDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.newsDao())
        val appExecutors = AppExecutors()

        return NewsRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideNewsUseCase(context: Context): NewsUseCase {
        val repository = provideRepository(context)
        return NewsInteractor(repository)
    }
}
