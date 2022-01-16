package com.harper.capital.navigation

import com.github.terrakok.cicerone.Router
import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.category.CategoryManageFragment
import com.harper.capital.transaction.TransactionFragment

class GlobalRouterImpl : Router(), GlobalRouter {

    override fun setSignInAsRoot() = newRootScreen(Screens.signIn())

    override fun setMainAsRoot() = newRootScreen(Screens.main())

    override fun navigateToManageAsset(params: AssetManageFragment.Params) = navigateTo(Screens.assetManage(params))

    override fun navigateToTransaction(params: TransactionFragment.Params) = navigateTo(Screens.transaction(params))

    override fun navigateToManageCategory(params: CategoryManageFragment.Params) =
        navigateTo(Screens.categoryManage(params))

    override fun back() = exit()
}