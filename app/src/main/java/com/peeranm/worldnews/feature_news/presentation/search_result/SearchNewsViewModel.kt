package com.peeranm.worldnews.feature_news.presentation.search_result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.peeranm.worldnews.core.Constants
import com.peeranm.worldnews.feature_news.model.Article
import com.peeranm.worldnews.feature_news.use_cases.ArticleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.security.PrivateKey
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val articleUseCases: ArticleUseCases,
) : ViewModel() {

    private val _searchResults = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val searchResults: StateFlow<PagingData<Article>> = _searchResults

    init {
        val query = savedStateHandle.get<String>(Constants.SEARCH_QUERY)
        query?.let { searchQuery ->
            articleUseCases.searchNews(searchQuery)
                .cachedIn(viewModelScope)
                .onEach { _searchResults.value = it }
                .launchIn(viewModelScope)
        }
    }

}