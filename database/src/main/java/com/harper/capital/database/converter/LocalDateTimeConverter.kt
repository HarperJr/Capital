package com.harper.capital.database.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateTimeConverter {

    @TypeConverter
    fun toEntity(model: LocalDateTime): Long = model.toInstant(ZoneOffset.UTC).toEpochMilli()

    @TypeConverter
    fun toModel(entity: Long): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(entity), ZoneOffset.UTC)
}
