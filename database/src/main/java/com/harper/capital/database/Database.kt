package com.harper.capital.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harper.capital.database.converter.LocalDateTimeConverter
import com.harper.capital.database.dao.AssetDao
import com.harper.capital.database.dao.TransactionDao
import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.database.entity.CreditEntity
import com.harper.capital.database.entity.GoalEntity
import com.harper.capital.database.entity.TransactionEntity

@Database(
    version = BuildConfig.DATABASE_VERSION,
    entities = [
        AssetEntity::class,
        CreditEntity::class,
        GoalEntity::class,
        TransactionEntity::class
    ]
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun assetDao(): AssetDao

    abstract fun transactionDao(): TransactionDao
}