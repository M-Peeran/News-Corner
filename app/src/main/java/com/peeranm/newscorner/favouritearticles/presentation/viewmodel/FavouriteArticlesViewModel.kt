package com.peeranm.newscorner.favouritearticles.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.newscorner.core.utils.ConnectionLiveData
import com.peeranm.newscorner.favouritearticles.data.local.entity.FavArticle
import com.peeranm.newscorner.favouritearticles.usecase.DeleteFavArticleUseCase
import com.peeranm.newscorner.favouritearticles.usecase.GetFavArticlesUseCase
import com.peeranm.newscorner.favouritearticles.usecase.InsertFavArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteArticlesViewModel @Inject constructor(
    private val getFavouriteArticles: GetFavArticlesUseCase,
    private val deleteFavArticle: DeleteFavArticleUseCase,
    private val insertFavArticle: InsertFavArticleUseCase
) : ViewModel() {

    private lateinit var _connectionLiveData: ConnectionLiveData
    val connectionLiveData: ConnectionLiveData
    get() = _connectionLiveData

    private val _favArticles = MutableStateFlow<List<FavArticle>>(emptyList())
    val favArticles: StateFlow<List<FavArticle>> = _favArticles

    fun initializeConnectionLiveData(context: Context) {
        if (::_connectionLiveData.isInitialized) return
        _connectionLiveData = ConnectionLiveData(context)
    }

    fun getFavArticles() {
        getFavouriteArticles().onEach {
            _favArticles.value = it
        }.launchIn(viewModelScope)
    }

    fun removeFavArticle(id: String) {
        viewModelScope.launch { deleteFavArticle(id) }
    }

    fun addFavouriteArticle(favArticle: FavArticle) {
        viewModelScope.launch { insertFavArticle(favArticle) }
    }


}