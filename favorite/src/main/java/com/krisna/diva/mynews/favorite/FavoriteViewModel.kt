package com.krisna.diva.mynews.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.krisna.diva.mynews.core.domain.usecase.NewsUseCase

class FavoriteViewModel(newsUseCase: NewsUseCase) : ViewModel() {
    val favoriteNews = newsUseCase.getFavoriteNews().asLiveData()
}