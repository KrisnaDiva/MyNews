package com.krisna.diva.mynews.core.domain.repository

import androidx.lifecycle.LiveData
import com.krisna.diva.mynews.core.data.Resource
import com.krisna.diva.mynews.core.domain.model.News

interface INewsRepository {

    fun getAllNews(): LiveData<Resource<List<News>>>

//    fun getFavoriteNews(): LiveData<List<News>>
//
    fun setFavoriteNews(news: News, state: Boolean)

}