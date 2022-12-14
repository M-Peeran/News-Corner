package com.peeranm.newscorner.core.di

import com.peeranm.newscorner.favouritearticles.data.repository.FavouriteArticleRepository
import com.peeranm.newscorner.favouritearticles.usecase.*
import com.peeranm.newscorner.newsfeed.data.repository.NewsRepository
import com.peeranm.newscorner.newsfeed.usecase.GetTrendingNewsUseCase
import com.peeranm.newscorner.searchnews.data.repository.NewsSearchRepository
import com.peeranm.newscorner.searchnews.usecase.SearchNewsUseCase
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

    @ViewModelScoped
    @Provides
    fun provideIsArticleFavouriteUseCase(repository: FavouriteArticleRepository): IsArticleFavouriteUseCase {
        return IsArticleFavouriteUseCase(repository)
    }

}