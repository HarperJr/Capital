package com.harper.capital.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.harper.capital.database.entity.AssetMetadataEntity
import com.harper.capital.database.entity.AssetMetadataCode

class AssetTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun toString(entity: AssetMetadataEntity): String = gson.toJson(entity)

    @TypeConverter
    fun toEntity(data: String): AssetMetadataEntity = gson.fromJson(data, AssetMetadataEntity::class.java)
        .let {
            when (it.code) {
                AssetMetadataCode.credit -> gson.fromJson(data, AssetMetadataEntity.Credit::class.java)
                AssetMetadataCode.goal -> gson.fromJson(data, AssetMetadataEntity.Goal::class.java)
                else -> AssetMetadataEntity.Default
            }
        }

}