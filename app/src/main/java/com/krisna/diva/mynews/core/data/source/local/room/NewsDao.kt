package com.krisna.diva.mynews.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.krisna.diva.mynews.core.data.source.local.entity.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAllNews(): LiveData<List<NewsEntity>>

    @Query("SELECT * FROM news where isFavorite = 1")
    fun getFavoriteNews(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: List<NewsEntity>)

    @Update
    fun updateFavoriteNews(news: NewsEntity)
}
