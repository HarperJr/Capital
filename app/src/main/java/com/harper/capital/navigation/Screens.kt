package com.harper.capital.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.auth.signin.SignInFragment
import com.harper.capital.category.CategoryManageFragment
import com.harper.capital.history.HistoryListFragment
import com.harper.capital.main.MainFragment
import com.harper.capital.settings.SettingsFragment
import com.harper.capital.shelter.ShelterFragment
import com.harper.capital.transaction.TransactionFragment
import com.harper.capital.transaction.manage.TransactionManageFragment

private const val SIGN_IN_KEY = "sign_in_key"
private const val MAIN_KEY = "main_key"
private const val ASSET_MANAGE_KEY = "asset_manage_key"
private const val CATEGORY_MANAGE_KEY = "category_manage_key"
private const val TRANSACTION_KEY = "transaction_key"
private const val TRANSACTION_MANAGE_KEY = "transaction_manage_key"
private const val HISTORY_LIST_KEY = "history_list_key"
private const val SETTINGS_KEY = "settings_key"
private const val SHELTER_KEY = "shelter_key"

object Screens {

    fun signIn(): Screen = FragmentScreen(SIGN_IN_KEY) { SignInFragment.newInstance() }

    fun main(): Screen = FragmentScreen(MAIN_KEY) { MainFragment.newInstance() }

    fun assetManage(params: AssetManageFragment.Params): Screen = FragmentScreen(ASSET_MANAGE_KEY) {
        AssetManageFragment.newInstance(params)
    }

    fun categoryManage(params: CategoryManageFragment.Params): Screen =
        FragmentScreen(CATEGORY_MANAGE_KEY) {
            CategoryManageFragment.newInstance(params)
        }

    fun transaction(params: TransactionFragment.Params): Screen = FragmentScreen(TRANSACTION_KEY) {
        TransactionFragment.newInstance(params)
    }

    fun transactionManage(params: TransactionManageFragment.Params): Screen =
        FragmentScreen(TRANSACTION_MANAGE_KEY) {
            TransactionManageFragment.newInstance(params)
        }

    fun historyList(params: HistoryListFragment.Params): Screen =
        FragmentScreen(HISTORY_LIST_KEY) {
            HistoryListFragment.newInstance(params)
        }

    fun settings(): Screen = FragmentScreen(SETTINGS_KEY) {
        SettingsFragment.newInstance()
    }

    fun shelter(): Screen = FragmentScreen(SHELTER_KEY) {
        ShelterFragment.newInstance()
    }
}
