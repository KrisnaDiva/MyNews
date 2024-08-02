package com.krisna.diva.mynews.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.krisna.diva.mynews.core.data.source.local.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

}