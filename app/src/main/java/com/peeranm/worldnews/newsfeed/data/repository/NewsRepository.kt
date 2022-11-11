package com.peeranm.worldnews.newsfeed.data.repository

import androidx.paging.PagingData
import com.peeranm.worldnews.newsfeed.model.Article
import kotlinx.coroutines.flow.Flow


interface NewsRepository {
    fun getTrendingNews(category: String, countryCode: String): Flow<PagingData<Article>>
}