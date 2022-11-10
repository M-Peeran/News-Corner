package com.peeranm.worldnews.favouritearticles.usecase

import com.peeranm.worldnews.favouritearticles.data.local.entity.FavArticle
import com.peeranm.worldnews.newsfeed.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetFavArticlesUseCase(private val repository: NewsRepository) {
    operator fun invoke(): Flow<List<FavArticle>> = repository.getFavArticles()
}