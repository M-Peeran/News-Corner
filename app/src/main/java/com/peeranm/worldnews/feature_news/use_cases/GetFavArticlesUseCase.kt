package com.peeranm.worldnews.feature_news.use_cases

import com.peeranm.worldnews.feature_news.data.local.entity.FavArticle
import com.peeranm.worldnews.feature_news.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetFavArticlesUseCase(private val repository: NewsRepository) {
    operator fun invoke(): Flow<List<FavArticle>> = repository.getFavArticles()
}