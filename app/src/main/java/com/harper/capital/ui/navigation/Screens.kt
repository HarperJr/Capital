package com.harper.capital.ui.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.harper.capital.overview.asset.AssetAddFragment
import com.harper.capital.overview.ui.OverviewFragment

private const val OVERVIEW_KEY = "overview"
private const val ASSET_ADD_KEY = "asset_add"

object Screens {

    fun overview(): Screen = FragmentScreen(OVERVIEW_KEY) { OverviewFragment.newInstance() }

    fun assetAdd(): Screen = FragmentScreen(ASSET_ADD_KEY) { AssetAddFragment.newInstance() }
}