package com.peeranm.worldnews.feature_news.use_cases

import androidx.paging.PagingData
import com.peeranm.worldnews.feature_news.data.repository.NewsRepository
import com.peeranm.worldnews.feature_news.model.Article
import kotlinx.coroutines.flow.Flow


class GetTrendingNewsUseCase(private val repository: NewsRepository) {
    operator fun invoke(category:String, countryCode: String): Flow<PagingData<Article>>
    = repository.getTrendingNews(category, countryCode)
}