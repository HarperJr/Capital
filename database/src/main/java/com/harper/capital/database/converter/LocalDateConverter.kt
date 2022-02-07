package com.harper.capital.database.converter

import androidx.room.TypeConverter
import java.time.LocalDate

internal class LocalDateConverter {

    @TypeConverter
    fun toEntity(model: LocalDate): Long = model.toEpochDay()

    @TypeConverter
    fun toModel(entity: Long): LocalDate = LocalDate.ofEpochDay(entity)
}