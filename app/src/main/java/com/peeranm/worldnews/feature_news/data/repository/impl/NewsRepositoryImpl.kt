package com.peeranm.worldnews.feature_news.data.repository.impl

import androidx.paging.*
import com.peeranm.worldnews.core.Constants
import com.peeranm.worldnews.feature_news.data.local.NewsDatabase
import com.peeranm.worldnews.feature_news.data.local.entity.FavArticle
import com.peeranm.worldnews.feature_news.data.remote.RetrofitInstance
import com.peeranm.worldnews.feature_news.data.remote.paging.TrendingNewsMediatorSource
import com.peeranm.worldnews.feature_news.data.remote.paging.SearchNewsPagingSource
import com.peeranm.worldnews.feature_news.data.repository.NewsRepository
import com.peeranm.worldnews.feature_news.model.Article
import com.peeranm.worldnews.feature_news.utils.ArticleMapper
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

    override suspend fun getArticleById(id: Long): Article? {
        return database.newsDao().getArticleById(id)?.let {
            mapper.fromEntityToUiModel(it)
        }
    }

    override fun getFavArticles(): Flow<List<FavArticle>> {
        return database.favArticlesDao().getFavArticles()
    }

    override suspend fun insertFavArticle(favArticle: FavArticle) {
        database.favArticlesDao().insert(favArticle)
    }

    override suspend fun deleteFavArticleById(id: String) {
        database.favArticlesDao().deleteFavArticleById(id)
    }

    override suspend fun getFavArticleById(id: String): FavArticle? {
        return database.favArticlesDao().getFavArticleById(id)
    }
}