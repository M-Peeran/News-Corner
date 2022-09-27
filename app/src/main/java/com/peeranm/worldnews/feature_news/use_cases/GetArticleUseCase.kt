package com.peeranm.worldnews.feature_news.use_cases

import com.peeranm.worldnews.feature_news.data.repository.NewsRepository
import com.peeranm.worldnews.feature_news.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetArticleUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(id: Long): Article?
    = withContext(Dispatchers.IO) { repository.getArticleById(id) }
}