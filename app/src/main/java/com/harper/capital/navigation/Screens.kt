package com.harper.capital.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.harper.capital.asset.AssetAddFragment
import com.harper.capital.auth.signin.SignInFragment
import com.harper.capital.category.CategoryManageFragment
import com.harper.capital.main.MainFragment

private const val SIGN_IN_KEY = "sign_in"
private const val MAIN_KEY = "main"
private const val ASSET_ADD_KEY = "asset_add"
private const val EXPENSE_CATEGORY_ADD_KEY = "expense_category_add"

object Screens {

    fun signIn(): Screen = FragmentScreen(SIGN_IN_KEY) { SignInFragment.newInstance() }

    fun main(): Screen = FragmentScreen(MAIN_KEY) { MainFragment.newInstance() }

    fun assetAdd(): Screen = FragmentScreen(ASSET_ADD_KEY) { AssetAddFragment.newInstance() }

    fun expenseCategoryAdd(): Screen = FragmentScreen(EXPENSE_CATEGORY_ADD_KEY) { CategoryManageFragment.newInstance() }
}