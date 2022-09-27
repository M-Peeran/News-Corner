package com.peeranm.worldnews.di

import android.app.Application
import com.peeranm.worldnews.feature_news.data.local.NewsDatabase
import com.peeranm.worldnews.feature_news.data.remote.RetrofitInstance
import com.peeranm.worldnews.feature_news.data.repository.NewsRepository
import com.peeranm.worldnews.feature_news.data.repository.impl.NewsRepositoryImpl
import com.peeranm.worldnews.feature_news.use_cases.*
import com.peeranm.worldnews.feature_news.utils.ArticleMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Application): NewsDatabase {
        return NewsDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideNewsRepository(database: NewsDatabase, context: Application): NewsRepository {
        return NewsRepositoryImpl(
            database = database,
            mapper = ArticleMapper(),
            retrofitInstance = RetrofitInstance(context.applicationContext)
        )
    }

    @Singleton
    @Provides
    fun provideArticleUseCases(repository: NewsRepository): ArticleUseCases {
        return ArticleUseCases(
            getTrendingNews = GetTrendingNewsUseCase(repository),
            searchNews = SearchNewsUseCase(repository),
            getArticle = GetArticleUseCase(repository),
            insertFavArticle = InsertFavArticleUseCase(repository),
            getFavArticle = GetFavArticleUseCase(repository),
            getFavArticles = GetFavArticlesUseCase(repository),
            deleteFavArticle = DeleteFavArticleUseCase(repository)
        )
    }

}