package com.peeranm.newscorner.favouritearticles.data.repository.impl

import com.peeranm.newscorner.core.database.NewsDatabase
import com.peeranm.newscorner.favouritearticles.data.local.entity.FavArticle
import com.peeranm.newscorner.favouritearticles.data.repository.FavouriteArticleRepository
import kotlinx.coroutines.flow.Flow

class FavouriteArticleRepositoryImpl(private val database: NewsDatabase) : FavouriteArticleRepository {

    override fun getFavArticles(): Flow<List<FavArticle>> {
        return database.favArticlesDao().getFavArticles()
    }

    override suspend fun insertFavArticle(favArticle: FavArticle) {
        database.favArticlesDao().insert(favArticle)
    }

    override suspend fun deleteFavArticleById(id: String) {
        database.favArticlesDao().deleteFavArticleById(id)
    }

    override suspend fun getFavArticleById(id: String): FavArticle? {
        return database.favArticlesDao().getFavArticleById(id)
    }

}