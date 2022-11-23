package com.peeranm.newscorner.favouritearticles.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.newscorner.favouritearticles.data.local.entity.FavArticle
import com.peeranm.newscorner.favouritearticles.usecase.GetFavArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FavouriteArticlesViewModel @Inject constructor(
    getFavouriteArticles: GetFavArticlesUseCase
) : ViewModel() {

    val favArticles: StateFlow<List<FavArticle>>
    = getFavouriteArticles.invoke().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


}