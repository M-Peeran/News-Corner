package com.peeranm.worldnews.favouritearticles.usecase

import com.peeranm.worldnews.favouritearticles.data.local.entity.FavArticle
import com.peeranm.worldnews.newsfeed.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertFavArticleUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(favArticle: FavArticle)
    = withContext(Dispatchers.IO){ repository.insertFavArticle(favArticle) }
}