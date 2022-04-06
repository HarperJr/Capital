package com.harper.capital.repository.account

import com.harper.capital.database.DatabaseTx
import com.harper.capital.database.dao.AccountDao
import com.harper.capital.database.dao.TransactionDao
import com.harper.capital.database.entity.AccountEntityMetadataType
import com.harper.capital.database.entity.GoalEntity
import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.database.entity.LedgerEntityType
import com.harper.capital.database.entity.LoanEntity
import com.harper.capital.database.entity.TransactionEntity
import com.harper.capital.database.entity.embedded.AssetEntityEmbedded
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.AccountType
import com.harper.capital.repository.account.mapper.AccountEntityMapper
import com.harper.capital.repository.account.mapper.AccountEntityTypeMapper
import com.harper.capital.repository.account.mapper.AccountMapper
import com.harper.core.ext.cast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

internal class AccountRepositoryImpl(
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao,
    private val databaseTx: DatabaseTx
) : AccountRepository {

    override suspend fun insert(account: Account) = databaseTx.runSuspended(context = Dispatchers.IO) {
        val accountEntity = AccountEntityMapper(account)
        val accountId = accountDao.insert(accountEntity)
        when (accountEntity.metadataType) {
            AccountEntityMetadataType.LOAN -> {
                val metadata = account.metadata.cast<AccountMetadata.LoanAsset>()
                accountDao.insertLoan(LoanEntity(accountId = accountId, limit = metadata.limit))
            }
            AccountEntityMetadataType.GOAL -> {
                val metadata = account.metadata.cast<AccountMetadata.GoalAsset>()
                accountDao.insertGoal(GoalEntity(accountId = accountId, goal = metadata.goal))
            }
            else -> {
            }
        }
        insertBalanceTransaction(accountId, account.balance)
    }

    override fun fetchByType(type: AccountType): Flow<List<Account>> =
        accountDao.selectByType(AccountEntityTypeMapper(type))
            .map { entities -> entities.map { mapToAccount(it) } }
            .flowOn(Dispatchers.IO)

    override fun fetchAll(): Flow<List<Account>> =
        accountDao.selectAll()
            .map { entities -> entities.map { mapToAccount(it) } }
            .flowOn(Dispatchers.IO)

    override suspend fun fetchById(id: Long): Account = withContext(Dispatchers.IO) {
        mapToAccount(accountDao.selectById(id))
    }

    override suspend fun update(account: Account) {
        withContext(Dispatchers.IO) {
            val existedAccount = fetchById(account.id)
            accountDao.update(AccountEntityMapper(account))
            insertBalanceTransaction(account.id, account.balance - existedAccount.balance)
        }
    }

    private suspend fun mapToAccount(entity: AssetEntityEmbedded): Account = entity.let {
        val metadata = when (it.account.metadataType) {
            AccountEntityMetadataType.LOAN -> {
                val loan = accountDao.selectLoanByAccountId(it.account.id)
                AccountMetadata.LoanAsset(loan.limit)
            }
            AccountEntityMetadataType.GOAL -> {
                val goal = accountDao.selectGoalByAccountId(it.account.id)
                AccountMetadata.GoalAsset(goal.goal)
            }
            else -> null
        }
        AccountMapper(it.account, metadata, it.balance)
    }

    private suspend fun insertBalanceTransaction(accountId: Long, balance: Double) {
        if (balance == 0.0) {
            return
        }
        val ledgerType = if (balance > 0) LedgerEntityType.DEBIT else LedgerEntityType.CREDIT
        val transactionId = transactionDao.insert(TransactionEntity(dateTime = LocalDateTime.now()))
        val ledger = LedgerEntity(
            transactionId = transactionId,
            accountId = accountId,
            type = ledgerType,
            amount = balance
        )
        transactionDao.insertLedgers(listOf(ledger))
    }
}