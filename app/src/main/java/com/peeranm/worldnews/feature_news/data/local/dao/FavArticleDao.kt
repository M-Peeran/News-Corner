package com.peeranm.worldnews.feature_news.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peeranm.worldnews.feature_news.data.local.entity.FavArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface FavArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favArticle: FavArticle)

    @Query("select * from table_favourite_articles")
    fun getFavArticles(): Flow<List<FavArticle>>

    @Query("select * from table_favourite_articles where id =:id")
    suspend fun getFavArticleById(id: String): FavArticle?

    @Query("delete from table_favourite_articles where id =:id")
    suspend fun deleteFavArticleById(id: String)
}