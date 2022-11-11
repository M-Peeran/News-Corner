package com.peeranm.worldnews.favouritearticles.usecase

import com.peeranm.worldnews.favouritearticles.data.repository.FavouriteArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteFavArticleUseCase(private val repository: FavouriteArticleRepository) {
    suspend operator fun invoke(id: String)
    = withContext(Dispatchers.IO) { repository.deleteFavArticleById(id) }
}