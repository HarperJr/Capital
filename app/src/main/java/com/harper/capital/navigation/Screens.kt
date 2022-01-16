package com.harper.capital.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.auth.signin.SignInFragment
import com.harper.capital.category.CategoryManageFragment
import com.harper.capital.main.MainFragment
import com.harper.capital.transaction.TransactionFragment

private const val SIGN_IN_KEY = "sign_in_key"
private const val MAIN_KEY = "main_key"
private const val ASSET_MANAGE_KEY = "asset_manage_key"
private const val CATEGORY_MANAGE_KEY = "category_manage_key"
private const val TRANSACTION_KEY = "transaction_key"

object Screens {

    fun signIn(): Screen = FragmentScreen(SIGN_IN_KEY) { SignInFragment.newInstance() }

    fun main(): Screen = FragmentScreen(MAIN_KEY) { MainFragment.newInstance() }

    fun assetManage(params: AssetManageFragment.Params): Screen = FragmentScreen(ASSET_MANAGE_KEY) {
        AssetManageFragment.newInstance(params)
    }

    fun categoryManage(): Screen = FragmentScreen(CATEGORY_MANAGE_KEY) { CategoryManageFragment.newInstance() }

    fun transaction(params: TransactionFragment.Params): Screen = FragmentScreen(TRANSACTION_KEY) {
        TransactionFragment.newInstance(params)
    }
}