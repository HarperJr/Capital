package com.harper.capital.navigation

import com.harper.capital.analytics.AnalyticsParams
import com.harper.capital.asset.AssetManageParams
import com.harper.capital.liability.LiabilityManageParams
import com.harper.capital.history.HistoryListParams
import com.harper.capital.transaction.TransactionParams
import com.harper.capital.transaction.manage.TransactionManageParams

interface GlobalRouter {

    fun setSignInAsRoot()

    fun setMainAsRoot()

    fun navigateToManageAsset(params: AssetManageParams)

    fun navigateToTransaction(params: TransactionParams)

    fun navigateToManageLiability(params: LiabilityManageParams)

    fun navigateToManageTransaction(params: TransactionManageParams)

    fun navigateToHistoryList(params: HistoryListParams)

    fun navigateToAnalytics(params: AnalyticsParams)

    fun navigateToSettings()

    fun navigateToAccounts()

    fun back()

    fun shelter()
}
