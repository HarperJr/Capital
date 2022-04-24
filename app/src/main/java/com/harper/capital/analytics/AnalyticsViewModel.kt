package com.harper.capital.analytics

import com.harper.capital.analytics.domain.FetchAccountsUseCase
import com.harper.capital.analytics.domain.FetchBalancePartitionsByPeriodUseCase
import com.harper.capital.analytics.domain.model.BalancePartitions
import com.harper.capital.analytics.model.AnalyticsEvent
import com.harper.capital.analytics.model.AnalyticsPage
import com.harper.capital.analytics.model.AnalyticsState
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.BalancePartitionPeriod
import com.harper.capital.navigation.GlobalRouter
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class AnalyticsViewModel(
    sectionId: Int,
    private val router: GlobalRouter,
    private val fetchAccountsUseCase: FetchAccountsUseCase,
    private val fetchBalancePartitionsByPeriodUseCase: FetchBalancePartitionsByPeriodUseCase
) : ComponentViewModel<AnalyticsState, AnalyticsEvent>(
    initialState = AnalyticsState(selectedPage = sectionId)
) {
    private val analyticsDataSetFactory: AnalyticsDataSetFactory = AnalyticsDataSetFactory()

    override fun onFirstComposition() {
        super.onFirstComposition()
        launch {
            combine(
                fetchAccountsUseCase(),
                fetchBalancePartitionsByPeriodUseCase(BalancePartitionPeriod.MONTH)
            ) { accounts, partitions -> accounts to partitions }
                .flowOn(Dispatchers.Main)
                .collect { (accounts, partitions) ->
                    update { it.copy(isLoading = false, pages = fillPages(it.pages, accounts, partitions)) }
                }
        }
    }

    override fun onEvent(event: AnalyticsEvent) {
        when (event) {
            is AnalyticsEvent.BackClick -> router.back()
            is AnalyticsEvent.PageSelect -> {
            }
        }
    }

    private fun fillPages(
        pages: List<AnalyticsPage>,
        accounts: List<Account>,
        partitions: BalancePartitions
    ): List<AnalyticsPage> =
        pages.map {
            it.copy(
                dataSet = analyticsDataSetFactory.create(it.type, accounts, partitions)
            )
        }
}
