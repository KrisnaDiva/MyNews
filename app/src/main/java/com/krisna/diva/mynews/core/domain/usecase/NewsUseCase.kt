package com.krisna.diva.mynews.core.domain.usecase

import androidx.lifecycle.LiveData
import com.krisna.diva.mynews.core.data.Resource
import com.krisna.diva.mynews.core.domain.model.News

interface NewsUseCase {
    fun getAllNews(): LiveData<Resource<List<News>>>
//    fun getFavoriteTourism(): LiveData<List<Tourism>>
//    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
}