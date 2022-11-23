package com.peeranm.newscorner.newsfeed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.peeranm.newscorner.newsfeed.model.Article
import com.peeranm.newscorner.newsfeed.usecase.GetTrendingNewsUseCase
import com.peeranm.newscorner.newsfeed.utils.CountryCode
import com.peeranm.newscorner.newsfeed.utils.NewsCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalPagingApi::class)
class NewsViewModel @Inject constructor(private val getTrendingNews: GetTrendingNewsUseCase): ViewModel() {

    private val _articles = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val articles: StateFlow<PagingData<Article>> = _articles

    private var selectedCountryCode: CountryCode = CountryCode.INDIA
    private var selectedNewsCategory: NewsCategory = NewsCategory.General

    init { getTrendingNews() }

    fun getTrendingNews() {
        getTrendingNews(selectedNewsCategory.categoryName, selectedCountryCode.code)
            .cachedIn(viewModelScope)
            .onEach { _articles.value = it }
            .launchIn(viewModelScope)
    }

    fun setCountryCode(countryCode: CountryCode) {
        selectedCountryCode = countryCode
    }

    fun setNewsCategory(category: NewsCategory) {
        selectedNewsCategory = category
    }

}