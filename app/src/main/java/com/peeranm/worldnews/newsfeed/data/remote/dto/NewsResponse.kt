package com.peeranm.worldnews.newsfeed.data.remote.dto

data class NewsResponse(
    val articles: List<ArticleDto> = emptyList(),
    val status: String,
    val code: String?,
    val message: String?,
    val totalResults: Int
)