package com.harper.capital.accounts

import com.harper.capital.accounts.model.AccountsEvent
import com.harper.capital.accounts.model.AccountsState
import com.harper.capital.accounts.model.DataSetSection
import com.harper.capital.analytics.domain.FetchAccountsUseCase
import com.harper.capital.asset.AssetManageParams
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.liability.LiabilityManageParams
import com.harper.capital.liability.model.LiabilityManageType
import com.harper.capital.navigation.GlobalRouter
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class AccountsViewModel(
    private val router: GlobalRouter,
    private val fetchAccountsUseCase: FetchAccountsUseCase
) : ComponentViewModel<AccountsState, AccountsEvent>(initialState = AccountsState()) {
    private val accountDataSetFactory: AccountDataSetFactory = AccountDataSetFactory()

    override fun onFirstComposition() {
        super.onFirstComposition()
        launch {
            fetchAccountsUseCase()
                .flowOn(Dispatchers.Main)
                .collect { accounts ->
                    update { it.copy(accountDataSets = accountDataSetFactory.create(accounts)) }
                }
        }
    }

    override fun onEvent(event: AccountsEvent) {
        when (event) {
            is AccountsEvent.BackClick -> router.back()
            is AccountsEvent.NewSourceClick -> onNewSourceClick(event)
            is AccountsEvent.SourceClick -> onSourceClick(event)
        }
    }

    private fun onSourceClick(event: AccountsEvent.SourceClick) {
        when (event.section) {
            DataSetSection.ASSET -> router.navigateToManageAsset(AssetManageParams(AssetManageMode.EDIT, event.id))
            else -> {
                val liabilityType = when (event.section) {
                    DataSetSection.LIABILITY -> LiabilityManageType.LIABILITY
                    DataSetSection.INCOME -> LiabilityManageType.INCOME
                    DataSetSection.DEBT -> LiabilityManageType.DEBT
                    else -> return
                }
                router.navigateToManageLiability(params = LiabilityManageParams(liabilityType, event.id))
            }
        }
    }

    private fun onNewSourceClick(event: AccountsEvent.NewSourceClick) {
        when (event.section) {
            DataSetSection.ASSET -> router.navigateToManageAsset(AssetManageParams(AssetManageMode.ADD))
            else -> {
                val liabilityType = when (event.section) {
                    DataSetSection.LIABILITY -> LiabilityManageType.LIABILITY
                    DataSetSection.INCOME -> LiabilityManageType.INCOME
                    DataSetSection.DEBT -> LiabilityManageType.DEBT
                    else -> return
                }
                router.navigateToManageLiability(params = LiabilityManageParams(liabilityType))
            }
        }
    }
}
