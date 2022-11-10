package com.peeranm.worldnews.favouritearticles.data.repository

import com.peeranm.worldnews.favouritearticles.data.local.entity.FavArticle
import kotlinx.coroutines.flow.Flow

interface FavouriteArticleRepository {
    fun getFavArticles(): Flow<List<FavArticle>>
    suspend fun insertFavArticle(favArticle: FavArticle)
    suspend fun deleteFavArticleById(id: String)
    suspend fun getFavArticleById(id: String): FavArticle?
}