package com.peeranm.worldnews.core.di

import android.app.Application
import com.peeranm.worldnews.core.database.NewsDatabase
import com.peeranm.worldnews.newsfeed.data.remote.api.RetrofitInstance
import com.peeranm.worldnews.newsfeed.data.repository.NewsRepository
import com.peeranm.worldnews.newsfeed.data.repository.impl.NewsRepositoryImpl
import com.peeranm.worldnews.core.utils.ArticleMapper
import com.peeranm.worldnews.favouritearticles.data.repository.FavouriteArticleRepository
import com.peeranm.worldnews.favouritearticles.data.repository.impl.FavouriteArticleRepositoryImpl
import com.peeranm.worldnews.searchnews.data.repository.NewsSearchRepository
import com.peeranm.worldnews.searchnews.data.repository.impl.NewsSearchRepositoryImpl
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
    fun provideRetrofitInstance(app: Application): RetrofitInstance {
        return RetrofitInstance(app.applicationContext)
    }

    @Singleton
    @Provides
    fun provideArticleMapper(): ArticleMapper {
        return ArticleMapper()
    }

    @Singleton
    @Provides
    fun provideNewsRepository(
        database: NewsDatabase,
        retrofitInstance: RetrofitInstance,
        mapper: ArticleMapper
    ): NewsRepository = NewsRepositoryImpl(database, retrofitInstance, mapper)

    @Singleton
    @Provides
    fun provideNewsSearchRepository(
        retrofitInstance: RetrofitInstance,
        mapper: ArticleMapper
    ): NewsSearchRepository = NewsSearchRepositoryImpl(retrofitInstance, mapper)

    @Singleton
    @Provides
    fun provideFavouriteArticleRepository(database: NewsDatabase): FavouriteArticleRepository
    = FavouriteArticleRepositoryImpl(database)
}