package com.peeranm.newscorner.favouritearticles.usecase

import com.peeranm.newscorner.favouritearticles.data.local.entity.FavArticle
import com.peeranm.newscorner.favouritearticles.data.repository.FavouriteArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetFavArticleUseCase(private val repository: FavouriteArticleRepository) {
    suspend operator fun invoke(id: String): FavArticle?
    = withContext(Dispatchers.IO) { repository.getFavArticleById(id) }
}