package com.krisna.diva.mynews

import android.app.Application
import com.krisna.diva.mynews.core.di.databaseModule
import com.krisna.diva.mynews.core.di.networkModule
import com.krisna.diva.mynews.core.di.repositoryModule
import com.krisna.diva.mynews.di.useCaseModule
import com.krisna.diva.mynews.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}