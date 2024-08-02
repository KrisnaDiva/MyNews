package com.krisna.diva.mynews.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.krisna.diva.mynews.core.domain.usecase.NewsUseCase

class HomeViewModel(newsUseCase: NewsUseCase) : ViewModel() {
    val news = newsUseCase.getAllNews().asLiveData()
}
