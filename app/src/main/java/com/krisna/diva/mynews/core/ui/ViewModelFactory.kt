package com.krisna.diva.mynews.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krisna.diva.mynews.core.di.Injection
import com.krisna.diva.mynews.core.domain.usecase.NewsUseCase
import com.krisna.diva.mynews.home.HomeViewModel

class ViewModelFactory private constructor(private val newsUseCase: NewsUseCase) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideNewsUseCase(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(newsUseCase) as T
            }
//            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
//                FavoriteViewModel(newsUseCase) as T
//            }
//            modelClass.isAssignableFrom(DetailNewsViewModel::class.java) -> {
//                DetailNewsViewModel(newsUseCase) as T
//            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}