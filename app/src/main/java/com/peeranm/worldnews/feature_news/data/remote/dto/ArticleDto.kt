package com.peeranm.worldnews.feature_news.data.remote.dto

data class ArticleDto(
    val url: String,
    val publishedAt: String,
    val source: SourceDto,
    val title: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val urlToImage: String?
)