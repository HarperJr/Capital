package com.harper.capital.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harper.capital.database.converter.LocalDateConverter
import com.harper.capital.database.converter.LocalDateTimeConverter
import com.harper.capital.database.dao.AccountDao
import com.harper.capital.database.dao.TransactionDao
import com.harper.capital.database.entity.AccountEntity
import com.harper.capital.database.entity.GoalEntity
import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.database.entity.LoanEntity
import com.harper.capital.database.entity.TransactionEntity
import com.harper.capital.database.view.AssetBalanceView

@Database(
    version = BuildConfig.DATABASE_VERSION,
    entities = [
        AccountEntity::class,
        LoanEntity::class,
        GoalEntity::class,
        TransactionEntity::class,
        LedgerEntity::class
    ],
    views = [AssetBalanceView::class]
)
@TypeConverters(LocalDateTimeConverter::class, LocalDateConverter::class)
internal abstract class Database : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    abstract fun transactionDao(): TransactionDao
}
