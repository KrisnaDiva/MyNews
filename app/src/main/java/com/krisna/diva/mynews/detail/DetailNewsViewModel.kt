package com.krisna.diva.mynews.detail

import androidx.lifecycle.ViewModel
import com.krisna.diva.mynews.core.domain.model.News
import com.krisna.diva.mynews.core.domain.usecase.NewsUseCase

class DetailNewsViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {
    fun setFavoriteNews(news: News, newStatus:Boolean) = newsUseCase.setFavoriteNews(news, newStatus)
}