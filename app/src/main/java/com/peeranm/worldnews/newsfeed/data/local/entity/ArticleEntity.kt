package com.peeranm.worldnews.newsfeed.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.peeranm.worldnews.newsfeed.data.remote.dto.SourceDto

@Entity(tableName = "table_articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceDto,
    val title: String,
    val url: String,
    val urlToImage: String
)