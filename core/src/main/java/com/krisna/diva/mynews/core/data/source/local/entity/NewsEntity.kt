package com.krisna.diva.mynews.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    @ColumnInfo(name = "newsId")
    var newsId: String,

    @ColumnInfo(name = "author")
    var author: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "url")
    var url: String? = null,

    @ColumnInfo(name = "urlToImage")
    var urlToImage: String? = null,

    @ColumnInfo(name = "publishedAt")
    var publishedAt: String? = null,

    @ColumnInfo(name = "content")
    var content: String? = null,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
) : Parcelable
