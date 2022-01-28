package com.harper.capital.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harper.capital.database.converter.LocalDateTimeConverter
import com.harper.capital.database.dao.AssetDao
import com.harper.capital.database.dao.TransactionDao
import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.database.entity.AssetCreditMetadataEntity
import com.harper.capital.database.entity.AssetGoalMetadataEntity
import com.harper.capital.database.entity.CreditView
import com.harper.capital.database.entity.DebetView
import com.harper.capital.database.entity.TransactionEntity

@Database(
    version = BuildConfig.DATABASE_VERSION,
    entities = [
        AssetEntity::class,
        AssetCreditMetadataEntity::class,
        AssetGoalMetadataEntity::class,
        TransactionEntity::class
    ],
    views = [DebetView::class, CreditView::class]
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun assetDao(): AssetDao

    abstract fun transactionDao(): TransactionDao
}