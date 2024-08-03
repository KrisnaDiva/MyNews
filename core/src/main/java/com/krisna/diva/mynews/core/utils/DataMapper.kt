package com.krisna.diva.mynews.core.utils

import com.krisna.diva.mynews.core.data.source.local.entity.NewsEntity
import com.krisna.diva.mynews.core.data.source.remote.response.NewsResponse
import com.krisna.diva.mynews.core.domain.model.News

object DataMapper {
    fun mapResponsesToEntities(input: List<NewsResponse>): List<NewsEntity> {
        val newsList = ArrayList<NewsEntity>()
        input.map {
            val uniqueId = "${it.title}_${it.publishedAt}".hashCode().toString()
            val news = NewsEntity(
                newsId = uniqueId,
                author = it.author ?: "",
                title = it.title ?: "",
                description = it.description ?: "",
                url = it.url ?: "",
                urlToImage = it.urlToImage ?: "",
                publishedAt = it.publishedAt ?: "",
                content = it.content ?: "",
                isFavorite = false
            )
            newsList.add(news)
        }
        return newsList
    }

    fun mapEntitiesToDomain(input: List<NewsEntity>): List<News> =
        input.map {
            News(
                newsId = it.newsId,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: News) = NewsEntity(
        newsId = input.newsId,
        author = input.author,
        title = input.title,
        description = input.description,
        url = input.url,
        urlToImage = input.urlToImage,
        publishedAt = input.publishedAt,
        content = input.content,
        isFavorite = input.isFavorite
    )
}