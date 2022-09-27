package com.peeranm.worldnews.feature_news.data.repository

import androidx.paging.PagingData
import com.peeranm.worldnews.feature_news.data.local.entity.FavArticle
import com.peeranm.worldnews.feature_news.model.Article
import kotlinx.coroutines.flow.Flow


interface NewsRepository {
    fun getTrendingNews(category: String, countryCode: String): Flow<PagingData<Article>>
    fun searchNews(searchQuery: String): Flow<PagingData<Article>>
    fun getFavArticles(): Flow<List<FavArticle>>
    suspend fun getArticleById(id: Long): Article?
    suspend fun insertFavArticle(favArticle: FavArticle)
    suspend fun deleteFavArticleById(id: String)
    suspend fun getFavArticleById(id: String): FavArticle?
}