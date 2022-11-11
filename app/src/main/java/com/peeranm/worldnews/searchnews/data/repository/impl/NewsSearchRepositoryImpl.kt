package com.peeranm.worldnews.searchnews.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.peeranm.worldnews.core.constants.Constants
import com.peeranm.worldnews.core.utils.ArticleMapper
import com.peeranm.worldnews.newsfeed.data.remote.api.RetrofitInstance
import com.peeranm.worldnews.newsfeed.model.Article
import com.peeranm.worldnews.searchnews.data.remote.paging.SearchNewsPagingSource
import com.peeranm.worldnews.searchnews.data.repository.NewsSearchRepository
import kotlinx.coroutines.flow.Flow

class NewsSearchRepositoryImpl(
    private val retrofitInstance: RetrofitInstance,
    private val mapper: ArticleMapper
) : NewsSearchRepository {
    override fun searchNews(searchQuery: String): Flow<PagingData<Article>> {
        val pagingSource = SearchNewsPagingSource(
            searchQuery = searchQuery,
            retrofitInstance = retrofitInstance,
            mapper = mapper
        )
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                maxSize = Constants.MAX_ARTICLES_FOR_PAGINATION,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { pagingSource },
        ).flow
    }
}