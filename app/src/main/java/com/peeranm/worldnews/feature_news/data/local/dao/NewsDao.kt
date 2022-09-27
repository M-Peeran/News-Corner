package com.peeranm.worldnews.feature_news.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peeranm.worldnews.feature_news.data.local.entity.ArticleEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<ArticleEntity>)

    @Query("select * from table_articles where id =:id")
    suspend fun getArticleById(id: Long): ArticleEntity?

    @Query("select * from table_articles")
    fun getHeadlinesPagingSource(): PagingSource<Int, ArticleEntity>

    @Query("select * from table_articles")
    fun getArticles(): List<ArticleEntity>

    @Query("delete from table_articles")
    fun clearArticles()

}