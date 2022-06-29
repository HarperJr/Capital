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
import com.harper.capital.transaction.model.DataSetSection
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionPage
import com.harper.capital.transaction.model.TransactionState
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val params: TransactionParams,
    private val router: GlobalRouter,
    private val fetchAssetsUseCase: FetchAssetsUseCase,
    private val fetchLiabilitiesUseCase: FetchLiabilitiesUseCase
) : ComponentViewModel<TransactionState, TransactionEvent>(
    initialState = TransactionState(selectedPage = params.transactionType.ordinal)
) {
    var onAccountsSelect: ((Account, Account) -> Unit)? = null
    private val transactionDataSetFactory: TransactionDataSetFactory = TransactionDataSetFactory()

    override fun onFirstComposition() {
        super.onFirstComposition()
        launch {
            combine(
                fetchAssetsUseCase(),
                fetchLiabilitiesUseCase()
            ) { assets, categories -> assets + categories }
                .collect { accounts ->
                    update { it.copy(pages = fillPages(it.pages, accounts)) }
                }
        }
    }

    private fun fillPages(
        pages: List<TransactionPage>,
        accounts: List<Account>
    ): List<TransactionPage> =
        pages.map {
            it.copy(
                accountDataSets = transactionDataSetFactory.create(it.type, params.accountId, accounts)
            )
        }

    override fun onEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.BackClick -> router.back()
            is TransactionEvent.TabSelect -> onTabSelect(event)
            is TransactionEvent.AssetSourceSelect -> onAssetSourceSelect(event)
            is TransactionEvent.NewSourceClick -> onNewSourceClick(event)
        }
    }

    private fun onNewSourceClick(event: TransactionEvent.NewSourceClick) {
        when (event.type) {
            AccountType.ASSET -> {
                router.navigateToManageAsset(AssetManageParams(AssetManageMode.ADD))
            }
            AccountType.INCOME,
            AccountType.LIABILITY -> {
                val liabilityType = when (event.transactionType) {
                    TransactionType.EXPENSE -> LiabilityManageType.LIABILITY
                    TransactionType.INCOME -> LiabilityManageType.INCOME
                    TransactionType.DEBT -> LiabilityManageType.DEBT
                    else -> return
                }
                router.navigateToManageLiability(params = LiabilityManageParams(liabilityType))
            }
        }
    }

    private fun onAssetSourceSelect(event: TransactionEvent.AssetSourceSelect) {
        update {
            val pages = it.pages.map { page ->
                if (page.type == event.transactionType) {
                    page.copy(accountDataSets = page.accountDataSets.mapValues { (section, dataSet) ->
                        if (section == event.section) {
                            dataSet.copy(selectedAccountId = event.account.id)
                        } else {
                            dataSet.copy(accounts = dataSet.accounts.map { item -> item.copy(isEnabled = item.account.id != event.account.id) })
                        }
                    })
                } else {
                    page
                }
            }
            it.copy(pages = pages)
        }
        tryApply()
    }

    private fun onTabSelect(event: TransactionEvent.TabSelect) {
        update { prevState ->
            prevState.copy(selectedPage = event.tabIndex)
        }
    }

    private fun tryApply() {
        with(state.value) {
            val page = pages[selectedPage]
            val fromDataSet = page.accountDataSets[DataSetSection.FROM]
            val toDateSet = page.accountDataSets[DataSetSection.TO]
            if (fromDataSet != null && toDateSet != null) {
                val source = fromDataSet.accounts.first { it.account.id == fromDataSet.selectedAccountId }
                val receiver = toDateSet.accounts.first { it.account.id == toDateSet.selectedAccountId }
                onAccountsSelect?.invoke(source.account, receiver.account)
            }
        }
    }
}
