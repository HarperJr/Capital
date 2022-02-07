package com.harper.capital.transaction

import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.category.CategoryManageFragment
import com.harper.capital.category.model.CategoryManageType
import com.harper.capital.domain.model.Account
import com.harper.capital.transaction.model.TransactionType
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.domain.FetchAssetsUseCase
import com.harper.capital.transaction.manage.TransactionManageFragment
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.capital.transaction.model.DataSetSection
import com.harper.capital.transaction.model.DataSetType
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionPage
import com.harper.capital.transaction.model.TransactionState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver
import kotlinx.coroutines.flow.collect

class TransactionViewModel(
    private val params: TransactionFragment.Params,
    private val router: GlobalRouter,
    private val fetchAssetsUseCase: FetchAssetsUseCase
) : ComponentViewModel<TransactionState>(
    defaultState = TransactionState(selectedPage = params.transactionType.ordinal)
), EventObserver<TransactionEvent> {
    private val transactionDataSetFactory: TransactionDataSetFactory = TransactionDataSetFactory()

    override fun onFirstStart() {
        super.onFirstStart()
        launch {
            fetchAssetsUseCase()
                .collect { assets ->
                    mutateState { it.copy(pages = fillPages(it.pages, assets)) }
                }
        }
    }

    private fun fillPages(
        pages: List<TransactionPage>,
        accounts: List<Account>
    ): List<TransactionPage> =
        pages.map {
            it.copy(
                accountDataSets = transactionDataSetFactory.create(it.type, params.assetId, accounts)
            )
        }

    override fun onEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.BackClick -> router.back()
            is TransactionEvent.TabSelect -> onTabSelect(event)
            is TransactionEvent.AssetSourceSelect -> onAssetSourceSelect(event)
            is TransactionEvent.NewSourceClick -> onNewSourceClick(event)
            TransactionEvent.Apply -> onApply()
        }
    }

    private fun onApply() {
        val page = state.value.pages[state.value.selectedPage]
        val sourceAccountId =
            page.accountDataSets.first { it.section == DataSetSection.FROM }.selectedAccountId
        val receiverAccountId =
            page.accountDataSets.first { it.section == DataSetSection.TO }.selectedAccountId
        if (sourceAccountId != null && receiverAccountId != null) {
            router.navigateToManageTransaction(
                TransactionManageFragment.Params(
                    mode = TransactionManageMode.ADD,
                    sourceAccountId = sourceAccountId,
                    receiverAccountId = receiverAccountId
                )
            )
        }
    }

    private fun onNewSourceClick(event: TransactionEvent.NewSourceClick) {
        when (event.dataSetType) {
            DataSetType.ASSET -> {
                router.navigateToManageAsset(AssetManageFragment.Params(AssetManageMode.ADD))
            }
            DataSetType.CATEGORY -> {
                val categoryType = when (event.transactionType) {
                    TransactionType.EXPENSE -> CategoryManageType.LIABILITY
                    TransactionType.INCOME -> CategoryManageType.INCOME
                    else -> return
                }
                router.navigateToManageCategory(params = CategoryManageFragment.Params(categoryType))
            }
        }
    }

    // TODO I should think how to avoid such cases and update only what is needed not all content
    private fun onAssetSourceSelect(event: TransactionEvent.AssetSourceSelect) {
        mutateState {
            val pages = it.pages.map { page ->
                if (page.type == event.transactionType) {
                    page.copy(accountDataSets = page.accountDataSets.map { dataSet ->
                        if (dataSet.section == event.section) {
                            dataSet.copy(selectedAccountId = event.account.id)
                        } else {
                            dataSet
                        }
                    })
                } else {
                    page
                }
            }
            it.copy(
                pages = pages,
                isApplyButtonEnabled = checkApplyButtonCanBeEnabled(pages, it.selectedPage)
            )
        }
    }

    private fun onTabSelect(event: TransactionEvent.TabSelect) {
        mutateState { prevState ->
            prevState.copy(
                selectedPage = event.tabIndex,
                isApplyButtonEnabled = checkApplyButtonCanBeEnabled(prevState.pages, event.tabIndex)
            )
        }
    }

    private fun checkApplyButtonCanBeEnabled(pages: List<TransactionPage>, selectedPage: Int): Boolean {
        val page = pages[selectedPage]
        return page.accountDataSets.find { it.section == DataSetSection.FROM }?.selectedAccountId != null &&
            page.accountDataSets.find { it.section == DataSetSection.TO }?.selectedAccountId != null
    }
}
