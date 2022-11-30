package com.peeranm.newscorner.articledetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.newscorner.core.constants.Constants
import com.peeranm.newscorner.favouritearticles.data.local.entity.FavArticle
import com.peeranm.newscorner.favouritearticles.usecase.DeleteFavArticleUseCase
import com.peeranm.newscorner.favouritearticles.usecase.InsertFavArticleUseCase
import com.peeranm.newscorner.favouritearticles.usecase.IsArticleFavouriteUseCase
import com.peeranm.newscorner.newsfeed.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val insertFavArticle: InsertFavArticleUseCase,
    private val isArticleFavourite: IsArticleFavouriteUseCase,
    private val deleteFavouriteArticle: DeleteFavArticleUseCase
) : ViewModel() {

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _isFavourite = MutableStateFlow(false)
    val isFavourite = _isFavourite.asStateFlow()

    var noArticleFound = false
    var articleUrl = ""
    var articleFrom = ""

    init {
        val article = savedStateHandle.get<Article>(Constants.ARG_ARTICLE)
        val favArticle = savedStateHandle.get<FavArticle>(Constants.ARG_FAV_ARTICLE)
        when {
            favArticle != null -> {
                _isFavourite.value = true
                articleFrom = favArticle.source
                articleUrl = favArticle.url
            }
            article != null -> {
                articleFrom = article.source
                articleUrl = article.url
                checkIfArticleIsFavourite(article.url)
            }
            else -> noArticleFound = true
        }
    }

    private fun checkIfArticleIsFavourite(id: String) {
        viewModelScope.launch {
            _isFavourite.value = isArticleFavourite(id) > 0
        }
    }

    fun saveOrDeleteArticle() {
        if (noArticleFound) {
            _message.value = "The article you are looking for is not found; Hence cannot be saved!"
            return
        }
        if (isFavourite.value) deleteFavouriteArticle()
        else saveArticle()
    }

    private fun deleteFavouriteArticle() {
        viewModelScope.launch {
            deleteFavouriteArticle(articleUrl)
            _message.value = "Removed from favourites"
            _isFavourite.value = false
        }
    }

    private fun saveArticle() {
        val article = savedStateHandle.get<Article>(Constants.ARG_ARTICLE) ?: return
        viewModelScope.launch {
            insertFavArticle(FavArticle(
                title = article.title,
                url = article.url,
                author = article.author,
                content  = article.content,
                description = article.description,
                publishedAt = article.publishedAt,
                source = article.source,
                urlToImage = article.urlToImage
            ))
            _message.value = "Added to favourites!"
            _isFavourite.value = true
        }
    }
}