package com.harper.capital.transaction

import com.harper.capital.asset.AssetManageParams
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountType
import com.harper.capital.liability.LiabilityManageParams
import com.harper.capital.liability.model.LiabilityManageType
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.domain.FetchAssetsUseCase
import com.harper.capital.transaction.domain.FetchLiabilitiesUseCase
import com.harper.capital.transaction.model.PagedAccountSelection
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionPage
import com.harper.capital.transaction.model.TransactionState
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TransactionViewModel(
    params: TransactionParams,
    private val router: GlobalRouter,
    private val fetchAssetsUseCase: FetchAssetsUseCase,
    private val fetchLiabilitiesUseCase: FetchLiabilitiesUseCase
) : ComponentViewModel<TransactionState, TransactionEvent>(
    initialState = TransactionState(
        selectedPage = params.transactionType.ordinal,
        accountSelection = PagedAccountSelection(
            type = params.transactionType,
            sourceId = params.accountId.takeIf { params.transactionType != TransactionType.INCOME },
            receiverId = params.accountId.takeIf { params.transactionType == TransactionType.INCOME } // TODO set from outer call
        )
    )
) {
    private val transactionPageFactory: TransactionPageFactory = TransactionPageFactory()

    override fun onFirstComposition() {
        super.onFirstComposition()
        launch {
            combine(
                fetchAssetsUseCase(),
                fetchLiabilitiesUseCase()
            ) { assets, liabilities -> assets + liabilities }
                .map { accounts -> createPages(accounts) }
                .collect { pages -> update { it.copy(pages = pages) } }
        }
    }

    private fun createPages(accounts: List<Account>): List<TransactionPage> = TransactionType.values()
        .map { transactionPageFactory.create(it, accounts) }

    override fun onEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.BackClick -> router.back()
            is TransactionEvent.TabSelect -> onTabSelect(event)
            is TransactionEvent.NewAccountClick -> onNewAccountClick(event)
            is TransactionEvent.SourceAccountSelect -> onSourceAccountSelect(event)
            is TransactionEvent.ReceiverAccountSelect -> onReceiverAccountSelect(event)
        }
    }

    private fun onSourceAccountSelect(event: TransactionEvent.SourceAccountSelect) {
        update {
            it.copy(accountSelection = it.accountSelection?.copy(type = event.type, sourceId = event.accountId))
        }
    }

    private fun onReceiverAccountSelect(event: TransactionEvent.ReceiverAccountSelect) {
        update {
            it.copy(accountSelection = it.accountSelection?.copy(type = event.type, receiverId = event.accountId))
        }
    }

    private fun onNewAccountClick(event: TransactionEvent.NewAccountClick) {
        when (event.type) {
            AccountType.ASSET -> router.navigateToManageAsset(AssetManageParams(AssetManageMode.ADD))
            AccountType.INCOME,
            AccountType.LIABILITY -> {
                val liabilityType = when (event.transactionType) {
                    TransactionType.LIABILITY -> LiabilityManageType.LIABILITY
                    TransactionType.INCOME -> LiabilityManageType.INCOME
                    TransactionType.DEBT -> LiabilityManageType.DEBT
                    else -> return
                }
                router.navigateToManageLiability(params = LiabilityManageParams(liabilityType))
            }
        }
    }

    private fun onTabSelect(event: TransactionEvent.TabSelect) {
        update { prevState ->
            prevState.copy(selectedPage = event.tabIndex)
        }
    }
}
