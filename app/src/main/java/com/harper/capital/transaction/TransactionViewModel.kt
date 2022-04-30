package com.harper.capital.transaction

import com.harper.capital.asset.AssetManageParams
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.category.CategoryManageParams
import com.harper.capital.category.model.CategoryManageType
import com.harper.capital.domain.model.Account
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.domain.FetchAssetsUseCase
import com.harper.capital.transaction.manage.TransactionManageParams
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.capital.transaction.model.DataSetSection
import com.harper.capital.transaction.model.DataSetType
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionPage
import com.harper.capital.transaction.model.TransactionState
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val params: TransactionParams,
    private val router: GlobalRouter,
    private val fetchAssetsUseCase: FetchAssetsUseCase
) : ComponentViewModel<TransactionState, TransactionEvent>(
    initialState = TransactionState(selectedPage = params.transactionType.ordinal)
) {
    private val transactionDataSetFactory: TransactionDataSetFactory = TransactionDataSetFactory()

    override fun onFirstComposition() {
        super.onFirstComposition()
        launch {
            fetchAssetsUseCase()
                .collect { assets ->
                    update { it.copy(pages = fillPages(it.pages, assets)) }
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
        when (event.dataSetType) {
            DataSetType.ASSET -> {
                router.navigateToManageAsset(AssetManageParams(AssetManageMode.ADD))
            }
            DataSetType.CATEGORY -> {
                val categoryType = when (event.transactionType) {
                    TransactionType.EXPENSE -> CategoryManageType.LIABILITY
                    TransactionType.INCOME -> CategoryManageType.INCOME
                    else -> return
                }
                router.navigateToManageCategory(params = CategoryManageParams(categoryType))
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
                            dataSet
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
            val source = page.accountDataSets[DataSetSection.FROM]?.selectedAccountId
            val receiver = page.accountDataSets[DataSetSection.TO]?.selectedAccountId
            if (source != null && receiver != null) {
                router.navigateToManageTransaction(
                    TransactionManageParams(
                        mode = TransactionManageMode.ADD,
                        sourceAccountId = source,
                        receiverAccountId = receiver
                    )
                )
            }
        }
    }
}
