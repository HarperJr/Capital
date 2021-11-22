package com.harper.capital.ui.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.harper.capital.overview.ui.OverviewFragment

private const val OVERVIEW_KEY = "overview"

object Screens {

    fun overview(): Screen = FragmentScreen(OVERVIEW_KEY) { OverviewFragment.newInstance() }
}