package com.peeranm.worldnews.feature_news.utils

import androidx.room.TypeConverter
import com.peeranm.worldnews.feature_news.data.remote.dto.SourceDto

class Converters {

    @TypeConverter
    fun fromSource(source: SourceDto): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): SourceDto {
        return SourceDto(name, name)
    }
}