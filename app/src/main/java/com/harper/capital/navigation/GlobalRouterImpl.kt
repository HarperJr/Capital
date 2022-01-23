package com.harper.capital.navigation

import com.github.terrakok.cicerone.Router
import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.category.CategoryManageFragment
import com.harper.capital.transaction.TransactionFragment
import com.harper.capital.transaction.manage.TransactionManageFragment

class GlobalRouterImpl : Router(), GlobalRouter {

    override fun setSignInAsRoot() = newRootScreen(Screens.signIn())

    override fun setMainAsRoot() = newRootScreen(Screens.main())

    override fun navigateToManageAsset(params: AssetManageFragment.Params) = navigateTo(Screens.assetManage(params))

    override fun navigateToTransaction(params: TransactionFragment.Params) = navigateTo(Screens.transaction(params))

    override fun navigateToManageCategory(params: CategoryManageFragment.Params) =
        navigateTo(Screens.categoryManage(params))

    override fun navigateToManageTransaction(params: TransactionManageFragment.Params) =
        navigateTo(Screens.transactionManage(params))

    override fun navigateToSettings() = navigateTo(Screens.settings())

    override fun back() = exit()
}
