package com.krisna.diva.mynews.core.domain.usecase

import com.krisna.diva.mynews.core.data.Resource
import com.krisna.diva.mynews.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getAllNews(): Flow<Resource<List<News>>>

    fun getFavoriteNews(): Flow<List<News>>

    fun setFavoriteNews(news: News, state: Boolean)
}