package com.peeranm.worldnews.feature_news.use_cases

import com.peeranm.worldnews.feature_news.data.local.entity.FavArticle
import com.peeranm.worldnews.feature_news.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertFavArticleUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(favArticle: FavArticle)
    = withContext(Dispatchers.IO){ repository.insertFavArticle(favArticle) }
}