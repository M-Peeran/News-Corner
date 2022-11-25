package com.peeranm.newscorner.searchnews.usecase

import androidx.paging.PagingData
import com.peeranm.newscorner.newsfeed.model.Article
import com.peeranm.newscorner.searchnews.data.repository.NewsSearchRepository
import kotlinx.coroutines.flow.Flow

class SearchNewsUseCase(private val repository: NewsSearchRepository) {
    operator fun invoke(searchQuery: String): Flow<PagingData<Article>>
    = repository.searchNews(searchQuery)
}