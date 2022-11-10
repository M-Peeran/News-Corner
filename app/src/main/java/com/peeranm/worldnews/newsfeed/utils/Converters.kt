package com.peeranm.worldnews.newsfeed.utils

import androidx.room.TypeConverter
import com.peeranm.worldnews.newsfeed.data.remote.dto.SourceDto

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