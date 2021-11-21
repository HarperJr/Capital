package com.harper.capital.ui.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.harper.capital.overview.OverviewFragment

private const val OVERVIEW_KEY = "overview"

object Screens {

    fun overview(): Screen = FragmentScreen(OVERVIEW_KEY) { com.harper.capital.overview.OverviewFragment.newInstance() }
}