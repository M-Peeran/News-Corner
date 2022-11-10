package com.peeranm.worldnews.searchnews.usecase

import androidx.paging.PagingData
import com.peeranm.worldnews.newsfeed.data.repository.NewsRepository
import com.peeranm.worldnews.newsfeed.model.Article
import kotlinx.coroutines.flow.Flow

class SearchNewsUseCase(private val repository: NewsRepository) {
    operator fun invoke(searchQuery: String): Flow<PagingData<Article>>
    = repository.searchNews(searchQuery)
}