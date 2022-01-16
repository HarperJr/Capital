package com.harper.capital.navigation

import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.transaction.TransactionFragment

interface GlobalRouter {

    fun setSignInAsRoot()

    fun setMainAsRoot()

    fun navigateToManageAsset(params: AssetManageFragment.Params)

    fun navigateToTransaction(params: TransactionFragment.Params)

    fun back()
}