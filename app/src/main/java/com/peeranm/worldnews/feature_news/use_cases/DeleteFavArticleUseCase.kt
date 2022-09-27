package com.peeranm.worldnews.feature_news.use_cases

import com.peeranm.worldnews.feature_news.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteFavArticleUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(id: String)
    = withContext(Dispatchers.IO) { repository.deleteFavArticleById(id) }
}