package com.krisna.diva.mynews.core.di

import androidx.room.Room
import com.krisna.diva.core.BuildConfig
import com.krisna.diva.mynews.core.data.NewsRepository
import com.krisna.diva.mynews.core.data.source.local.LocalDataSource
import com.krisna.diva.mynews.core.data.source.local.room.NewsDatabase
import com.krisna.diva.mynews.core.data.source.remote.RemoteDataSource
import com.krisna.diva.mynews.core.data.source.remote.network.ApiService
import com.krisna.diva.mynews.core.domain.repository.INewsRepository
import com.krisna.diva.mynews.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<NewsDatabase>().newsDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("krisna".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java, "News.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }
}

val networkModule = module {
    single {
        val hostname = "newsapi.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/WbJ0rqhZTn/oss8Ki+pfzpfZuSAwuh/o5E/kj4qjwSE=")
            .add(hostname, "sha256/UHYOXs5BxRVKGG7ykhBYGxgne9rRrDaUTXC1MpEtwZU=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
            .create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<INewsRepository> { NewsRepository(get(), get(), get()) }
}
