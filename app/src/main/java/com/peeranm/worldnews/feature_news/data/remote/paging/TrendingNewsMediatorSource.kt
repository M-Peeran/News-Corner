package com.peeranm.worldnews.feature_news.data.remote.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.peeranm.worldnews.core.Constants
import com.peeranm.worldnews.core.getResponseObject
import com.peeranm.worldnews.feature_news.data.local.NewsDatabase
import com.peeranm.worldnews.feature_news.data.local.entity.ArticleEntity
import com.peeranm.worldnews.feature_news.data.local.entity.ArticleRemoteKeys
import com.peeranm.worldnews.feature_news.data.remote.RetrofitInstance
import com.peeranm.worldnews.feature_news.data.remote.dto.ErrorDto
import com.peeranm.worldnews.feature_news.utils.ArticleMapper
import retrofit2.HttpException
import java.io.InvalidObjectException
import kotlin.Exception

@OptIn(ExperimentalPagingApi::class)
class TrendingNewsMediatorSource(
    private val category: String,
    private val countryCode: String,
    private val database: NewsDatabase,
    private val retrofitInstance: RetrofitInstance,
    private val mapper: ArticleMapper
) : RemoteMediator<Int, ArticleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {

            val currentPage = when (loadType) {
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.REFRESH -> getRemoteKeyClosestToCurrentPosition(state)?.nextKey?.minus(1) ?: Constants.INITIAL_PAGE
                LoadType.APPEND -> {
                    val remoteKeys = database.newsRemoteKeysDao().getLastRemoteKeys()
                        ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                    remoteKeys.nextKey ?: return MediatorResult.Success(true)
                }
            }

            val response = retrofitInstance.newsAPI.getHeadlineNews(
                countryCode = countryCode,
                category = category,
                page = currentPage,
                pageSize = state.config.pageSize
            )

            if (response.isSuccessful) {
                val newsResponse = response.body()
                newsResponse?.let {
                    val endOfPaginationReached = newsResponse.articles.size < state.config.pageSize
                    val prevKey = if (currentPage <= Constants.INITIAL_PAGE) null else currentPage - 1
                    val nextKey = if (endOfPaginationReached) null else currentPage + 1
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            database.newsDao().clearArticles()
                            database.newsRemoteKeysDao().clearRemoteKeys()
                        }
                        database.newsDao().insertAll(newsResponse.articles.map { mapper.fromDtoToEntity(it) })

                        val cachedArticles = database.newsDao().getArticles()
                        val keys = cachedArticles.map {
                            ArticleRemoteKeys(
                                articleId = it.id,
                                prevKey = prevKey,
                                nextKey = nextKey
                            )
                        }
                        database.newsRemoteKeysDao().insertAll(keys)
                    }
                    return MediatorResult.Success(endOfPaginationReached)
                }
            }
            MediatorResult.Error(Exception("Request is failed!"))
        } catch (exception: Exception) {
            return when (exception) {
                is HttpException -> {
                    val errorObject = exception.response()?.errorBody()?.getResponseObject<ErrorDto>()
                    val message = errorObject?.message ?: Constants.MESSAGE_SOMETHING_WENT_WRONG
                    MediatorResult.Error(Exception(message, exception))
                }
                else -> MediatorResult.Error(exception)
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ArticleEntity>): ArticleRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.newsRemoteKeysDao().getRemoteKeysByArticleId(id)
            }
        }
    }
}