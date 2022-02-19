package com.harper.capital.navigation

import com.github.terrakok.cicerone.Router
import com.harper.capital.asset.AssetManageParams
import com.harper.capital.category.CategoryManageParams
import com.harper.capital.history.HistoryListParams
import com.harper.capital.transaction.TransactionParams
import com.harper.capital.transaction.manage.TransactionManageParams

class GlobalRouterImpl : Router(), GlobalRouter {

    override fun setSignInAsRoot() = newRootScreen(Screens.signIn())

    override fun setMainAsRoot() = newRootScreen(Screens.main())

    override fun navigateToManageAsset(params: AssetManageParams) =
        navigateTo(Screens.assetManage(params))

    override fun navigateToTransaction(params: TransactionParams) =
        navigateTo(Screens.transaction(params))

    override fun navigateToManageCategory(params: CategoryManageParams) =
        navigateTo(Screens.categoryManage(params))

    override fun navigateToManageTransaction(params: TransactionManageParams) =
        navigateTo(Screens.transactionManage(params))

    override fun navigateToHistoryList(params: HistoryListParams) =
        navigateTo(Screens.historyList(params))

    override fun navigateToSettings() = navigateTo(Screens.settings())

    override fun back() = exit()

    override fun shelter() = navigateTo(Screens.shelter())
}
