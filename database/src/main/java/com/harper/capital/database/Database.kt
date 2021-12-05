package com.harper.capital.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harper.capital.database.dao.AssetDao
import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.database.entity.CreditEntity
import com.harper.capital.database.entity.GoalEntity

@Database(
    version = BuildConfig.DATABASE_VERSION,
    entities = [
        AssetEntity::class,
        CreditEntity::class,
        GoalEntity::class
    ]
)
abstract class Database : RoomDatabase() {

    abstract fun assetDao(): AssetDao
}