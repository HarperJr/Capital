package com.harper.capital.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harper.capital.database.converter.AssetTypeConverter
import com.harper.capital.database.dao.AssetDao
import com.harper.capital.database.entity.AssetEntity

@Database(
    version = BuildConfig.DATABASE_VERSION,
    entities = [AssetEntity::class]
)
@TypeConverters(value = [AssetTypeConverter::class])
abstract class Database : RoomDatabase() {

    abstract fun assetDao(): AssetDao
}