package com.peeranm.worldnews.favouritearticles.usecase

import com.peeranm.worldnews.favouritearticles.data.local.entity.FavArticle
import com.peeranm.worldnews.favouritearticles.data.repository.FavouriteArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetFavArticleUseCase(private val repository: FavouriteArticleRepository) {
    suspend operator fun invoke(id: String): FavArticle?
    = withContext(Dispatchers.IO) { repository.getFavArticleById(id) }
}