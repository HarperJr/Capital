package com.harper.capital.navigation

import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.category.CategoryManageFragment
import com.harper.capital.history.HistoryListFragment
import com.harper.capital.transaction.TransactionFragment
import com.harper.capital.transaction.manage.TransactionManageFragment

interface GlobalRouter {

    fun setSignInAsRoot()

    fun setMainAsRoot()

    fun navigateToManageAsset(params: AssetManageFragment.Params)

    fun navigateToTransaction(params: TransactionFragment.Params)

    fun navigateToManageCategory(params: CategoryManageFragment.Params)

    fun navigateToManageTransaction(params: TransactionManageFragment.Params)

    fun navigateToHistoryList(params: HistoryListFragment.Params)

    fun navigateToSettings()

    fun back()
}
