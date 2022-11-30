package com.peeranm.newscorner.favouritearticles.usecase

import com.peeranm.newscorner.favouritearticles.data.repository.FavouriteArticleRepository

class IsArticleFavouriteUseCase(private val repository: FavouriteArticleRepository) {
    suspend operator fun invoke(id: String): Int {
        return repository.isArticleFavourite(id) ?: 0
    }
}