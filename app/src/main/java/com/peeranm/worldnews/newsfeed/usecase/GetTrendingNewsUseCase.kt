package com.peeranm.worldnews.newsfeed.usecase

import androidx.paging.PagingData
import com.peeranm.worldnews.newsfeed.data.repository.NewsRepository
import com.peeranm.worldnews.newsfeed.model.Article
import kotlinx.coroutines.flow.Flow


class GetTrendingNewsUseCase(private val repository: NewsRepository) {
    operator fun invoke(category:String, countryCode: String): Flow<PagingData<Article>>
    = repository.getTrendingNews(category, countryCode)
}