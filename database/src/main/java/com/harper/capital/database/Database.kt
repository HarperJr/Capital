package com.harper.capital.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harper.capital.database.converter.LocalDateConverter
import com.harper.capital.database.converter.LocalDateTimeConverter
import com.harper.capital.database.dao.AssetDao
import com.harper.capital.database.dao.TransactionDao
import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.database.entity.CreditEntity
import com.harper.capital.database.entity.GoalEntity
import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.database.entity.TransactionEntity
import com.harper.capital.database.view.AssetBalanceView

@Database(
    version = BuildConfig.DATABASE_VERSION,
    entities = [
        AssetEntity::class,
        CreditEntity::class,
        GoalEntity::class,
        TransactionEntity::class,
        LedgerEntity::class
    ],
    views = [AssetBalanceView::class]
)
@TypeConverters(LocalDateTimeConverter::class, LocalDateConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun assetDao(): AssetDao

    abstract fun transactionDao(): TransactionDao
}