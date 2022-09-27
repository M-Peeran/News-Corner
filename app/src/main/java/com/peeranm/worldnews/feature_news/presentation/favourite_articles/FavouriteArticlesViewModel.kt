package com.peeranm.worldnews.feature_news.presentation.favourite_articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.worldnews.feature_news.data.local.entity.FavArticle
import com.peeranm.worldnews.feature_news.use_cases.ArticleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FavouriteArticlesViewModel @Inject constructor(
    articleUseCases: ArticleUseCases
) : ViewModel() {

    val favArticles: StateFlow<List<FavArticle>>
    = articleUseCases.getFavArticles().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


}