package com.peeranm.worldnews.feature_news.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peeranm.worldnews.feature_news.data.local.entity.ArticleRemoteKeys

@Dao
interface NewsRemoteKeysDao {

    @Query("select * from table_remote_keys where articleId = :articleId")
    suspend fun getRemoteKeysByArticleId(articleId: Long): ArticleRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<ArticleRemoteKeys>)

    @Query("select * from table_remote_keys order by articleId desc limit 1")
    suspend fun getLastRemoteKeys(): ArticleRemoteKeys?

    @Query("delete from table_remote_keys")
    fun clearRemoteKeys()

}