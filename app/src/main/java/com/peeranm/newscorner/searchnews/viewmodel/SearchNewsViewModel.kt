package com.peeranm.newscorner.searchnews.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.peeranm.newscorner.core.constants.Constants
import com.peeranm.newscorner.newsfeed.model.Article
import com.peeranm.newscorner.searchnews.usecase.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchNews: SearchNewsUseCase,
) : ViewModel() {

    private val _searchResults = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val searchResults: StateFlow<PagingData<Article>> = _searchResults

    init {
        val query = savedStateHandle.get<String>(Constants.SEARCH_QUERY)
        query?.let { searchQuery ->
            searchNews(searchQuery)
                .cachedIn(viewModelScope)
                .onEach { _searchResults.value = it }
                .launchIn(viewModelScope)
        }
    }

}