package com.peeranm.newscorner.favouritearticles.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.newscorner.core.utils.ConnectionLiveData
import com.peeranm.newscorner.favouritearticles.data.local.entity.FavArticle
import com.peeranm.newscorner.favouritearticles.usecase.GetFavArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FavouriteArticlesViewModel @Inject constructor(
    getFavouriteArticles: GetFavArticlesUseCase
) : ViewModel() {

    private lateinit var _connectionLiveData: ConnectionLiveData
    val connectionLiveData: ConnectionLiveData
    get() = _connectionLiveData

    fun initializeConnectionLiveData(context: Context) {
        if (::_connectionLiveData.isInitialized) return
        _connectionLiveData = ConnectionLiveData(context)
    }


    val favArticles: StateFlow<List<FavArticle>>
    = getFavouriteArticles.invoke().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


}