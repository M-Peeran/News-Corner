package com.peeranm.worldnews.core.di

import com.peeranm.worldnews.favouritearticles.data.repository.FavouriteArticleRepository
import com.peeranm.worldnews.favouritearticles.usecase.DeleteFavArticleUseCase
import com.peeranm.worldnews.favouritearticles.usecase.GetFavArticleUseCase
import com.peeranm.worldnews.favouritearticles.usecase.GetFavArticlesUseCase
import com.peeranm.worldnews.favouritearticles.usecase.InsertFavArticleUseCase
import com.peeranm.worldnews.newsfeed.data.repository.NewsRepository
import com.peeranm.worldnews.newsfeed.usecase.GetTrendingNewsUseCase
import com.peeranm.worldnews.searchnews.data.repository.NewsSearchRepository
import com.peeranm.worldnews.searchnews.usecase.SearchNewsUseCase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class VMModule {

    @ViewModelScoped
    fun provideGetTrendingNewsUseCase(repository: NewsRepository): GetTrendingNewsUseCase {
        return GetTrendingNewsUseCase(repository)
    }

    @ViewModelScoped
    fun provideSearchNewsUseCase(repository: NewsSearchRepository): SearchNewsUseCase {
        return SearchNewsUseCase(repository)
    }

    @ViewModelScoped
    fun provideInsertFavArticleUseCase(repository: FavouriteArticleRepository): InsertFavArticleUseCase {
        return InsertFavArticleUseCase(repository)
    }

    @ViewModelScoped
    fun provideGetFavArticleUseCase(repository: FavouriteArticleRepository): GetFavArticleUseCase {
        return GetFavArticleUseCase(repository)
    }

    @ViewModelScoped
    fun provideGetFavArticlesUseCase(repository: FavouriteArticleRepository): GetFavArticlesUseCase {
        return GetFavArticlesUseCase(repository)
    }

    @ViewModelScoped
    fun provideDeleteFavArticleUseCase(repository: FavouriteArticleRepository): DeleteFavArticleUseCase {
        return DeleteFavArticleUseCase(repository)
    }

}