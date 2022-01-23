package com.harper.capital.transaction

import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.category.CategoryManageFragment
import com.harper.capital.category.model.CategoryManageType
import com.harper.capital.domain.model.Asset
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.domain.FetchAssetsUseCase
import com.harper.capital.transaction.manage.TransactionManageFragment
import com.harper.capital.transaction.model.DataSetSection
import com.harper.capital.transaction.model.DataSetType
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionPage
import com.harper.capital.transaction.model.TransactionState
import com.harper.capital.transaction.model.TransactionType
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
        assets: List<Asset>
    ): List<TransactionPage> =
        pages.map {
            it.copy(
                assetDataSets = transactionDataSetFactory.create(it.type, params.assetId, assets)
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
        val assetFromId =
            page.assetDataSets.first { it.section == DataSetSection.FROM }.selectedAssetId
        val assetToId =
            page.assetDataSets.first { it.section == DataSetSection.TO }.selectedAssetId
        if (assetFromId != null && assetToId != null) {
            router.navigateToManageTransaction(
                TransactionManageFragment.Params(
                    transactionType = page.type,
                    assetFromId = assetFromId,
                    assetToId = assetToId
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
                    TransactionType.EXPENSE -> CategoryManageType.EXPENSE
                    TransactionType.INCOME -> CategoryManageType.INCOME
                    TransactionType.GOAL -> CategoryManageType.GOAL
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
            it.copy(selectedPage = event.tabIndex, isApplyButtonEnabled = true)
        }
    }
}
