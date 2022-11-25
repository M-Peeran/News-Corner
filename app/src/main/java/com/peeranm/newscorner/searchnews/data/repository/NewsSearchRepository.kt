package com.peeranm.newscorner.searchnews.data.repository

import androidx.paging.PagingData
import com.peeranm.newscorner.newsfeed.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsSearchRepository {
    fun searchNews(searchQuery: String): Flow<PagingData<Article>>
}