package com.harper.capital.navigation

import com.harper.capital.analytics.AnalyticsParams
import com.harper.capital.asset.AssetManageParams
import com.harper.capital.category.CategoryManageParams
import com.harper.capital.history.HistoryListParams
import com.harper.capital.transaction.TransactionParams
import com.harper.capital.transaction.manage.TransactionManageParams

interface GlobalRouter {

    fun setSignInAsRoot()

    fun setMainAsRoot()

    fun navigateToManageAsset(params: AssetManageParams)

    fun navigateToTransaction(params: TransactionParams)

    fun navigateToManageCategory(params: CategoryManageParams)

    fun navigateToManageTransaction(params: TransactionManageParams)

    fun navigateToHistoryList(params: HistoryListParams)

    fun navigateToAnalytics(params: AnalyticsParams)

    fun navigateToSettings()

    fun back()

    fun shelter()
}
