package com.krisna.diva.mynews.core.domain.usecase

import com.krisna.diva.mynews.core.domain.model.News
import com.krisna.diva.mynews.core.domain.repository.INewsRepository

class NewsInteractor(private val newsRepository: INewsRepository): NewsUseCase {

    override fun getAllNews() = newsRepository.getAllNews()

    override fun getFavoriteNews() = newsRepository.getFavoriteNews()

    override fun setFavoriteNews(news: News, state: Boolean) = newsRepository.setFavoriteNews(news, state)
}