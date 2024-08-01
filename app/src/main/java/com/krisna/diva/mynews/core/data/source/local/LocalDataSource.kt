package com.krisna.diva.mynews.core.data.source.local

import androidx.lifecycle.LiveData
import com.krisna.diva.mynews.core.data.source.local.entity.NewsEntity
import com.krisna.diva.mynews.core.data.source.local.room.NewsDao
//Kelas ini bertanggung jawab untuk mengelola data yang disimpan di database lokal.
class LocalDataSource private constructor(private val newsDao: NewsDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(newsDao: NewsDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(newsDao)
            }
    }

    fun getAllNews(): LiveData<List<NewsEntity>> = newsDao.getAllNews()

//    fun getFavoriteNews(): LiveData<List<NewsEntity>> = newsDao.getFavoriteNews()
//
    fun insertNews(newsList: List<NewsEntity>) = newsDao.insertNews(newsList)
//
//    fun setFavoriteNews(news: NewsEntity, newState: Boolean) {
//        news.isFavorite = newState
//        newsDao.updateFavoriteNews(news)
//    }
}