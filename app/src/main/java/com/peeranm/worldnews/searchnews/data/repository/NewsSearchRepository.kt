package com.peeranm.worldnews.searchnews.data.repository

import androidx.paging.PagingData
import com.peeranm.worldnews.newsfeed.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsSearchRepository {
    fun searchNews(searchQuery: String): Flow<PagingData<Article>>
}