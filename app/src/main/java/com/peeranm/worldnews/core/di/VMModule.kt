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
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class VMModule {

    @ViewModelScoped
    @Provides
    fun provideGetTrendingNewsUseCase(repository: NewsRepository): GetTrendingNewsUseCase {
        return GetTrendingNewsUseCase(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideSearchNewsUseCase(repository: NewsSearchRepository): SearchNewsUseCase {
        return SearchNewsUseCase(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideInsertFavArticleUseCase(repository: FavouriteArticleRepository): InsertFavArticleUseCase {
        return InsertFavArticleUseCase(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideGetFavArticleUseCase(repository: FavouriteArticleRepository): GetFavArticleUseCase {
        return GetFavArticleUseCase(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideGetFavArticlesUseCase(repository: FavouriteArticleRepository): GetFavArticlesUseCase {
        return GetFavArticlesUseCase(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideDeleteFavArticleUseCase(repository: FavouriteArticleRepository): DeleteFavArticleUseCase {
        return DeleteFavArticleUseCase(repository)
    }

}