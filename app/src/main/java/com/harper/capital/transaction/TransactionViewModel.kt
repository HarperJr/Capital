package com.harper.capital.transaction

import com.harper.capital.category.CategoryManageFragment
import com.harper.capital.category.model.CategoryManageType
import com.harper.capital.domain.model.Asset
import com.harper.capital.main.domain.FetchAssetsUseCase
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.domain.FetchCategoriesUseCase
import com.harper.capital.transaction.model.AssetDataSet
import com.harper.capital.transaction.model.DataSetSection
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionPage
import com.harper.capital.transaction.model.TransactionState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver
import kotlinx.coroutines.flow.collect

class TransactionViewModel(
    private val params: TransactionFragment.Params,
    private val router: GlobalRouter,
    private val fetchAssetsUseCase: FetchAssetsUseCase,
    private val fetchCategoriesUseCase: FetchCategoriesUseCase
) : ComponentViewModel<TransactionState>(
    defaultState = TransactionState(selectedPage = params.transactionType.ordinal)
), EventObserver<TransactionEvent> {

    override fun onFirstStart() {
        super.onFirstStart()
        launch {
            fetchAssetsUseCase()
                .collect { assets ->
                    mutateState { it.copy(pages = fillPages(it.pages, assets)) }
                }
        }
    }

    private fun fillPages(pages: List<TransactionPage>, assets: List<Asset>): List<TransactionPage> =
        pages.map { it.copy(assetDataSets = createExpenseDataSet(assets)) }

    private fun createExpenseDataSet(assets: List<Asset>): List<AssetDataSet> = listOf(
        AssetDataSet(
            section = DataSetSection.FROM,
            assets = assets,
            selectedAssetId = params.assetId
        ),
        AssetDataSet(
            section = DataSetSection.TO,
            assets = assets
        )
    )

    override fun onEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.BackClick -> router.back()
            is TransactionEvent.TabSelect -> onTabSelect(event)
            is TransactionEvent.AssetSourceSelect -> onAssetSourceSelect(event)
            is TransactionEvent.NewSourceClick -> onNewSourceClick(event)
        }
    }

    private fun onNewSourceClick(event: TransactionEvent.NewSourceClick) {
        // TODO remove after testing
        router.navigateToManageCategory(params = CategoryManageFragment.Params(CategoryManageType.EXPENSE))
    }

    // TODO I should think how to avoid such cases and update only what is needed not all content
    private fun onAssetSourceSelect(event: TransactionEvent.AssetSourceSelect) {
        mutateState {
            val pages = it.pages.mapIndexed { index, page ->
                if (index == it.selectedPage) {
                    page.copy(assetDataSets = page.assetDataSets.map { dataSet ->
                        if (dataSet.section == event.section) {
                            dataSet.copy(selectedAssetId = event.asset.id)
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
    }

    private fun onTabSelect(event: TransactionEvent.TabSelect) {
        mutateState {
            it.copy(selectedPage = event.tabIndex)
        }
    }
}
