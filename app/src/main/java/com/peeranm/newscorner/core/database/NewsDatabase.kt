package com.peeranm.newscorner.core.database

import android.content.Context
import androidx.room.*
import com.peeranm.newscorner.core.constants.Constants
import com.peeranm.newscorner.favouritearticles.data.local.dao.FavArticleDao
import com.peeranm.newscorner.newsfeed.data.local.dao.NewsDao
import com.peeranm.newscorner.newsfeed.data.local.dao.NewsRemoteKeysDao
import com.peeranm.newscorner.newsfeed.data.local.entity.ArticleEntity
import com.peeranm.newscorner.newsfeed.data.local.entity.ArticleRemoteKeys
import com.peeranm.newscorner.favouritearticles.data.local.entity.FavArticle
import com.peeranm.newscorner.newsfeed.utils.Converters

@Database(entities = [ArticleEntity::class, ArticleRemoteKeys::class, FavArticle::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun newsRemoteKeysDao(): NewsRemoteKeysDao
    abstract fun favArticlesDao(): FavArticleDao

    companion object {

        @Volatile
        private var INSTANCE: NewsDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): NewsDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = synchronized(lock) {
                    Room.databaseBuilder(context, NewsDatabase::class.java, Constants.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
            }
            return instance
        }
    }
}