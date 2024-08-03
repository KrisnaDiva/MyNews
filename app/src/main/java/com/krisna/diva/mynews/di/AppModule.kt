package com.krisna.diva.mynews.di

import com.krisna.diva.mynews.core.domain.usecase.NewsInteractor
import com.krisna.diva.mynews.core.domain.usecase.NewsUseCase
import com.krisna.diva.mynews.detail.DetailNewsViewModel
import com.krisna.diva.mynews.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase> { NewsInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailNewsViewModel(get()) }
}