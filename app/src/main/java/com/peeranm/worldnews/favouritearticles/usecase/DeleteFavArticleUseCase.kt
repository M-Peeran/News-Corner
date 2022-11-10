package com.peeranm.worldnews.favouritearticles.usecase

import com.peeranm.worldnews.newsfeed.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteFavArticleUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(id: String)
    = withContext(Dispatchers.IO) { repository.deleteFavArticleById(id) }
}