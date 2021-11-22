package com.harper.capital.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harper.capital.database.dao.AssetDao
import com.harper.capital.database.entity.AssetEntity

@Database(
    version = BuildConfig.DATABASE_VERSION,
    entities = [AssetEntity::class]
)
abstract class Database : RoomDatabase() {

    abstract fun assetDao(): AssetDao
}