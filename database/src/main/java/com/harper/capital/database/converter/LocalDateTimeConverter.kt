package com.harper.capital.database.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {
    private val formatter = DateTimeFormatter.ISO_DATE

    @TypeConverter
    fun toEntity(model: LocalDateTime): String = model.format(formatter)

    @TypeConverter
    fun toModel(entity: String): LocalDateTime = LocalDateTime.parse(entity)
}
