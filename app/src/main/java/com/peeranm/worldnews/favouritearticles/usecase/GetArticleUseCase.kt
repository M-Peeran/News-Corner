package com.peeranm.worldnews.favouritearticles.usecase

import com.peeranm.worldnews.newsfeed.data.repository.NewsRepository
import com.peeranm.worldnews.newsfeed.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetArticleUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(id: Long): Article?
    = withContext(Dispatchers.IO) { repository.getArticleById(id) }
}