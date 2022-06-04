package com.harper.capital.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.harper.capital.database.dao.AccountDao
import com.harper.capital.database.dao.TransactionDao
import com.harper.capital.database.entity.AccountEntity
import com.harper.capital.database.entity.AccountEntityMetadataType
import com.harper.capital.database.entity.AccountEntityType
import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.database.entity.LedgerEntityType
import com.harper.capital.database.entity.TransactionEntity
import java.io.IOException
import java.time.LocalDateTime
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TransactionTest {
    private lateinit var database: Database
    private lateinit var accountDao: AccountDao
    private lateinit var transactionDao: TransactionDao

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, Database::class.java).build()
        accountDao = database.accountDao()
        transactionDao = database.transactionDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun testAccountsBalance() = runBlocking {
        val amount = 1000.0
        val source = createTestAccount(1L)
        val receiver = createTestAccount(2L)
        accountDao.insert(source)
        accountDao.insert(receiver)

        val transaction = createTestTransaction()
        transactionDao.insert(transaction)
        transactionDao.insertLedgers(
            listOf(
                LedgerEntity(1L, transaction.id, source.id, LedgerEntityType.CREDIT, amount),
                LedgerEntity(2L, transaction.id, receiver.id, LedgerEntityType.DEBIT, amount)
            )
        )
        val balance = transactionDao.selectBalance().firstOrNull()

        assertNotNull(balance)
    }

    private fun createTestAccount(id: Long): AccountEntity {
        return AccountEntity(
            id = id,
            name = "test",
            type = AccountEntityType.ASSET,
            color = "test",
            icon = "test",
            currency = "RUB",
            metadataType = AccountEntityMetadataType.UNDEFINED,
            isIncluded = true,
            isArchived = false
        )
    }

    private fun createTestTransaction(): TransactionEntity {
        return TransactionEntity(
            id = 1L,
            dateTime = LocalDateTime.of(2022, 5, 11, 22, 19),
            comment = null,
            isScheduled = false
        )
    }
}
