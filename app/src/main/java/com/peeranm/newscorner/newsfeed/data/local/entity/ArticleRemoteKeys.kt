package com.peeranm.newscorner.newsfeed.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_remote_keys")
class ArticleRemoteKeys(
    @PrimaryKey
    val articleId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)