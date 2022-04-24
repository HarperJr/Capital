package com.harper.capital.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harper.capital.database.converter.LocalDateConverter
import com.harper.capital.database.converter.LocalDateTimeConverter
import com.harper.capital.database.dao.AccountDao
import com.harper.capital.database.dao.CurrencyDao
import com.harper.capital.database.dao.TransactionDao
import com.harper.capital.database.entity.*
import com.harper.capital.database.view.AssetBalanceView

@Database(
    version = BuildConfig.DATABASE_VERSION,
    entities = [
        AccountEntity::class,
        LoanEntity::class,
        GoalEntity::class,
        TransactionEntity::class,
        LedgerEntity::class,
        CurrencyRateEntity::class
    ],
    views = [AssetBalanceView::class]
)
@TypeConverters(LocalDateTimeConverter::class, LocalDateConverter::class)
internal abstract class Database : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    abstract fun transactionDao(): TransactionDao

    abstract fun currencyDao(): CurrencyDao
}
