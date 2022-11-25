package com.peeranm.newscorner.favouritearticles.usecase

import com.peeranm.newscorner.favouritearticles.data.local.entity.FavArticle
import com.peeranm.newscorner.favouritearticles.data.repository.FavouriteArticleRepository
import kotlinx.coroutines.flow.Flow

class GetFavArticlesUseCase(private val repository: FavouriteArticleRepository) {
    operator fun invoke(): Flow<List<FavArticle>> = repository.getFavArticles()
}