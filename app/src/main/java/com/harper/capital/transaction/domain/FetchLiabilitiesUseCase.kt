package com.harper.capital.transaction.domain

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountType
import com.harper.capital.repository.account.AccountRepository
import com.harper.capital.repository.transaction.TransactionRepository
import com.harper.core.ext.orElse
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class FetchLiabilitiesUseCase(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) {

    operator fun invoke(): Flow<List<Account>> {
        val currentDate = LocalDate.now()
        val startOfMonth = currentDate.withDayOfMonth(1).atStartOfDay()
        val endOfMonth = startOfMonth.plusMonths(1L)
        return combine(
            accountRepository.fetchByType(AccountType.LIABILITY),
            transactionRepository.fetchLiabilitiesBetween(dateTimeAfter = startOfMonth, dateTimeBefore = endOfMonth)
        ) { accounts, liabilities ->
            accounts.map { account ->
                account.copy(balance = liabilities.find { it.id == account.id }?.balance.orElse(0.0))
            }
        }
    }
}
