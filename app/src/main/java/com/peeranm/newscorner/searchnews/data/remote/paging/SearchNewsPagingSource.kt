package com.peeranm.newscorner.searchnews.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.peeranm.newscorner.core.constants.Constants
import com.peeranm.newscorner.core.utils.getResponseObject
import com.peeranm.newscorner.newsfeed.data.remote.api.RetrofitInstance
import com.peeranm.newscorner.newsfeed.data.remote.dto.ErrorDto
import com.peeranm.newscorner.newsfeed.model.Article
import com.peeranm.newscorner.core.utils.ArticleMapper
import retrofit2.HttpException

class SearchNewsPagingSource(
    private val searchQuery: String,
    private val retrofitInstance: RetrofitInstance,
    private val mapper: ArticleMapper
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val currentPage = params.key ?: Constants.INITIAL_PAGE

            val response = retrofitInstance.newsAPI.searchForNews(
                query = searchQuery,
                page = currentPage,
                pageSize = params.loadSize
            )

            if (response.isSuccessful) {
                val newsResponse = response.body()
                newsResponse?.let {
                    return LoadResult.Page(
                        data = newsResponse.articles.map { mapper.fromDtoToUiModel(it) },
                        prevKey = if (currentPage == Constants.INITIAL_PAGE) null else currentPage - 1,
                        nextKey = if (newsResponse.articles.isEmpty()) null else currentPage + 1
                    )
                }
            }
            LoadResult.Error(Exception("Received failed response"))
        } catch (exception: Exception) {
            LoadResult.Error(
                if (exception is HttpException) {
                    val errorObject = exception.response()?.errorBody()?.getResponseObject<ErrorDto>()
                    Exception(errorObject?.message ?: Constants.MESSAGE_SOMETHING_WENT_WRONG)
                } else exception
            )
        }
    }
}