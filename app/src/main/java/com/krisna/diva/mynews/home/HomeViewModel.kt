package com.krisna.diva.mynews.home

import androidx.lifecycle.*
import com.krisna.diva.mynews.core.domain.usecase.NewsUseCase
import com.krisna.diva.mynews.core.data.Resource
import com.krisna.diva.mynews.core.domain.model.News
import kotlinx.coroutines.launch

class HomeViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {
    private val _news = MutableLiveData<Resource<List<News>>>()
    val news: LiveData<Resource<List<News>>> = _news

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            newsUseCase.getAllNews().collect {
                _news.postValue(it)
            }
        }
    }

    fun refreshNews() {
        fetchNews()
    }
}