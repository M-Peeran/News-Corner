package com.peeranm.worldnews.favouritearticles.usecase

import com.peeranm.worldnews.favouritearticles.data.local.entity.FavArticle
import com.peeranm.worldnews.favouritearticles.data.repository.FavouriteArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertFavArticleUseCase(private val repository: FavouriteArticleRepository) {
    suspend operator fun invoke(favArticle: FavArticle)
    = withContext(Dispatchers.IO){ repository.insertFavArticle(favArticle) }
}