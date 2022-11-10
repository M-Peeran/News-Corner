package com.peeranm.worldnews.feature_news.use_cases

import com.peeranm.worldnews.favouritearticles.usecase.DeleteFavArticleUseCase
import com.peeranm.worldnews.favouritearticles.usecase.GetArticleUseCase
import com.peeranm.worldnews.favouritearticles.usecase.GetFavArticleUseCase
import com.peeranm.worldnews.favouritearticles.usecase.InsertFavArticleUseCase
import com.peeranm.worldnews.newsfeed.usecase.GetTrendingNewsUseCase
import com.peeranm.worldnews.searchnews.usecase.SearchNewsUseCase

class ArticleUseCases(
    val getTrendingNews: GetTrendingNewsUseCase,
    val searchNews: SearchNewsUseCase,
    val getArticle: GetArticleUseCase,
    val insertFavArticle: InsertFavArticleUseCase,
    val getFavArticle: GetFavArticleUseCase,
    val deleteFavArticle: DeleteFavArticleUseCase,
    val getFavArticles: GetFavArticlesUseCase
)