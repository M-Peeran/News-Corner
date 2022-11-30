package com.peeranm.newscorner.favouritearticles.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "table_favourite_articles")
data class FavArticle(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    @PrimaryKey(autoGenerate = false)
    val id: String = url
): Parcelable