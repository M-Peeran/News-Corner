package com.peeranm.newscorner.utils

import com.peeranm.newscorner.favouritearticles.data.local.entity.FavArticle
import com.peeranm.newscorner.newsfeed.model.Article

fun getArticle() = Article(
    id = 0,
    title = "Newton will make upcoming engineers cry!",
    description = "Gravity made me famous, what you want else in life?",
    author = "Einstein",
    content = "Gravity made me famous, what you want else in life?",
    publishedAt = "2022-01-01T17:24:04Z",
    source = "Einstein's Personal Journal",
    url = "Newton will kill me if I publish this...",
    urlToImage = "Ask Newton"
)

fun getFavArticle() = FavArticle(
    title = "Newton will make upcoming engineers cry!",
    description = "Gravity made me famous, what you want else in life?",
    author = "Einstein",
    content = "Gravity made me famous, what you want else in life?",
    publishedAt = "2022-01-01T17:24:04Z",
    source = "Einstein's Personal Journal",
    url = "Newton will kill me if I publish this...",
    urlToImage = "Ask Newton"
)