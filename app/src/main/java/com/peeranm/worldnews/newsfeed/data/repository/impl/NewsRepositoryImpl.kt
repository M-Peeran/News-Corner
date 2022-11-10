package com.peeranm.worldnews.newsfeed.data.repository.impl

import androidx.paging.*
import com.peeranm.worldnews.core.constants.Constants
import com.peeranm.worldnews.core.database.NewsDatabase
import com.peeranm.worldnews.newsfeed.data.remote.api.RetrofitInstance
import com.peeranm.worldnews.newsfeed.data.remote.paging.TrendingNewsMediatorSource
import com.peeranm.worldnews.newsfeed.data.repository.NewsRepository
import com.peeranm.worldnews.newsfeed.model.Article
import com.peeranm.worldnews.core.utils.ArticleMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl(
    private val database: NewsDatabase,
    private val retrofitInstance: RetrofitInstance,
    private val mapper: ArticleMapper
) : NewsRepository {

    override fun getTrendingNews(category: String, countryCode: String): Flow<PagingData<Article>> {
        val pagingSourceFactory = { database.newsDao().getHeadlinesPagingSource() }
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                maxSize = Constants.MAX_ARTICLES_FOR_PAGINATION,
                enablePlaceholders = false
            ),
            remoteMediator = TrendingNewsMediatorSource(
                category,
                countryCode,
                database,
                retrofitInstance,
                mapper
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow.map { pagedData -> pagedData.map { mapper.fromEntityToUiModel(it) } }
    }

}