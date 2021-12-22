package com.harper.capital.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.harper.capital.asset.AssetAddFragment
import com.harper.capital.expense.ExpenseCategoryAddFragment
import com.harper.capital.overview.OverviewFragment

private const val OVERVIEW_KEY = "overview"
private const val ASSET_ADD_KEY = "asset_add"
private const val EXPENSE_CATEGORY_ADD_KEY = "expense_category_add"

object Screens {

    fun overview(): Screen = FragmentScreen(OVERVIEW_KEY) { OverviewFragment.newInstance() }

    fun assetAdd(): Screen = FragmentScreen(ASSET_ADD_KEY) { AssetAddFragment.newInstance() }

    fun expenseCategoryAdd(): Screen = FragmentScreen(EXPENSE_CATEGORY_ADD_KEY) { ExpenseCategoryAddFragment.newInstance() }
}