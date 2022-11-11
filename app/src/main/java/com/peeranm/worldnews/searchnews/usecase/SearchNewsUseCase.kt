package com.peeranm.worldnews.searchnews.usecase

import androidx.paging.PagingData
import com.peeranm.worldnews.newsfeed.model.Article
import com.peeranm.worldnews.searchnews.data.repository.NewsSearchRepository
import kotlinx.coroutines.flow.Flow

class SearchNewsUseCase(private val repository: NewsSearchRepository) {
    operator fun invoke(searchQuery: String): Flow<PagingData<Article>>
    = repository.searchNews(searchQuery)
}