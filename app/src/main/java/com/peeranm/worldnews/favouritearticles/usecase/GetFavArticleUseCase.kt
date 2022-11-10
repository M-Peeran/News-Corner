package com.peeranm.worldnews.favouritearticles.usecase

import com.peeranm.worldnews.favouritearticles.data.local.entity.FavArticle
import com.peeranm.worldnews.newsfeed.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetFavArticleUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(id: String): FavArticle?
    = withContext(Dispatchers.IO) { repository.getFavArticleById(id) }
}