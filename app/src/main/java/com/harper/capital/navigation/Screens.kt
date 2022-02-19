package com.harper.capital.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.asset.AssetManageNavArgsHolder
import com.harper.capital.category.CategoryManageFragment
import com.harper.capital.history.HistoryListFragment
import com.harper.capital.navigation.animation.ScreenAppearanceAnimation
import com.harper.capital.navigation.animation.SlideInLeftSlideOutRightAppearanceAnimation
import com.harper.capital.navigation.v1.ComposableScreen
import com.harper.capital.settings.SettingsFragment
import com.harper.capital.shelter.ShelterFragment
import com.harper.capital.transaction.TransactionFragment
import com.harper.capital.transaction.manage.TransactionManageFragment

object ScreenAppearanceAnimations {

    // Doesn't work, has glitches (
    operator fun get(key: String): ScreenAppearanceAnimation? = when (ScreenKey.valueOf(key)) {
        ScreenKey.SETTINGS -> SlideInLeftSlideOutRightAppearanceAnimation()
        else -> null
    }
}

object Screens {

    fun signIn(): Screen = ComposableScreen(ScreenKey.SIGN_IN.name)

    fun main(): Screen = ComposableScreen(ScreenKey.MAIN.name)

    fun assetManage(params: AssetManageFragment.Params): Screen =
        ComposableScreen(ScreenKey.ASSET_MANAGE.name, arguments = AssetManageNavArgsHolder.getArguments(params))

    fun categoryManage(params: CategoryManageFragment.Params): Screen =
        FragmentScreen(ScreenKey.CATEGORY_MANAGE.name) {
            CategoryManageFragment.newInstance(params)
        }

    fun transaction(params: TransactionFragment.Params): Screen =
        FragmentScreen(ScreenKey.TRANSACTION.name) {
            TransactionFragment.newInstance(params)
        }

    fun transactionManage(params: TransactionManageFragment.Params): Screen =
        FragmentScreen(ScreenKey.TRANSACTION_MANAGE.name) {
            TransactionManageFragment.newInstance(params)
        }

    fun historyList(params: HistoryListFragment.Params): Screen =
        FragmentScreen(ScreenKey.HISTORY_LIST.name) {
            HistoryListFragment.newInstance(params)
        }

    fun settings(): Screen = FragmentScreen(ScreenKey.SETTINGS.name) {
        SettingsFragment.newInstance()
    }

    fun shelter(): Screen = FragmentScreen(ScreenKey.SHELTER.name) {
        ShelterFragment.newInstance()
    }
}
