package com.peeranm.newscorner.favouritearticles.usecase

import com.peeranm.newscorner.favouritearticles.data.repository.FavouriteArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteFavArticleUseCase(private val repository: FavouriteArticleRepository) {
    suspend operator fun invoke(id: String)
    = withContext(Dispatchers.IO) { repository.deleteFavArticleById(id) }
}