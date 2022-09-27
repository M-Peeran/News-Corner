package com.peeranm.worldnews.feature_news.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_favourite_articles")
data class FavArticle(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    @PrimaryKey(autoGenerate = false)
    val id: String = url
)