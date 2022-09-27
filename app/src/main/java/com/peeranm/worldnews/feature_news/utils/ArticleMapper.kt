package com.peeranm.worldnews.feature_news.utils

import com.peeranm.worldnews.feature_news.data.local.entity.ArticleEntity
import com.peeranm.worldnews.feature_news.data.remote.dto.ArticleDto
import com.peeranm.worldnews.feature_news.data.remote.dto.SourceDto
import com.peeranm.worldnews.feature_news.model.Article

class ArticleMapper {

    fun fromEntityToUiModel(entity: ArticleEntity): Article {
        return Article(
            id = entity.id,
            author = entity.author,
            description = entity.description,
            urlToImage = entity.urlToImage,
            content = entity.content,
            publishedAt = entity.publishedAt,
            title = entity.title,
            url = entity.url,
            source = entity.source.name,
        )
    }

    fun fromDtoToEntity(dto: ArticleDto): ArticleEntity {
        return ArticleEntity(
            author = dto.author ?: "Unknown Author",
            description = dto.description ?: "No description available",
            urlToImage = dto.urlToImage ?: "",
            content = dto.content ?: "No content available",
            publishedAt = dto.publishedAt,
            title = dto.title,
            url = dto.url,
            source = dto.source
        )
    }

    fun fromDtoToUiModel(dto: ArticleDto): Article {
        return Article(
            id = 0,
            author = dto.author ?: "Unknown Author",
            description = dto.description ?: "No description available",
            urlToImage = dto.urlToImage ?: "",
            content = dto.content ?: "No content available",
            publishedAt = dto.publishedAt,
            title = dto.title,
            url = dto.url,
            source = dto.source.name
        )
    }

}