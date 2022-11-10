package com.peeranm.worldnews.articledetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.worldnews.core.constants.Constants
import com.peeranm.worldnews.favouritearticles.data.local.entity.FavArticle
import com.peeranm.worldnews.favouritearticles.usecase.InsertFavArticleUseCase
import com.peeranm.worldnews.newsfeed.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val insertFavArticle: InsertFavArticleUseCase
) : ViewModel() {

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun saveArticle() {
        val article = savedStateHandle.get<Article>(Constants.ARG_ARTICLE)
        if (article == null) {
            _message.value = "The article you are looking for is not found; Hence cannot be saved!"
            return
        }
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
        }
    }
}