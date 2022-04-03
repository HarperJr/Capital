package com.harper.capital.navigation

import com.github.terrakok.cicerone.Screen
import com.harper.capital.analytics.AnalyticsNavArgsSpec
import com.harper.capital.analytics.AnalyticsParams
import com.harper.capital.asset.AssetManageNavArgsSpec
import com.harper.capital.asset.AssetManageParams
import com.harper.capital.category.CategoryManageNavArgsSpec
import com.harper.capital.category.CategoryManageParams
import com.harper.capital.history.HistoryListManageNavArgsSpec
import com.harper.capital.history.HistoryListParams
import com.harper.capital.transaction.TransactionNavArgsSpec
import com.harper.capital.transaction.TransactionParams
import com.harper.capital.transaction.manage.TransactionManageNavArgsSpec
import com.harper.capital.transaction.manage.TransactionManageParams

object Screens {

    fun signIn(): Screen = ComposableScreen(ScreenKey.SIGN_IN.route)

    fun main(): Screen = ComposableScreen(ScreenKey.MAIN.route)

    fun assetManage(params: AssetManageParams): Screen =
        ComposableScreen(ScreenKey.ASSET_MANAGE.route, arguments = AssetManageNavArgsSpec.args(params))

    fun categoryManage(params: CategoryManageParams): Screen =
        ComposableScreen(ScreenKey.CATEGORY_MANAGE.route, arguments = CategoryManageNavArgsSpec.args(params))

    fun transaction(params: TransactionParams): Screen =
        ComposableScreen(ScreenKey.TRANSACTION.route, arguments = TransactionNavArgsSpec.args(params))

    fun transactionManage(params: TransactionManageParams): Screen =
        ComposableScreen(ScreenKey.TRANSACTION_MANAGE.route, arguments = TransactionManageNavArgsSpec.args(params))

    fun historyList(params: HistoryListParams): Screen =
        ComposableScreen(ScreenKey.HISTORY_LIST.route, arguments = HistoryListManageNavArgsSpec.args(params))

    fun analytics(params: AnalyticsParams): Screen =
        ComposableScreen(ScreenKey.ANALYTICS.route, arguments = AnalyticsNavArgsSpec.args(params))

    fun settings(): Screen = ComposableScreen(ScreenKey.SETTINGS.route)

    fun shelter(): Screen = ComposableScreen(ScreenKey.SHELTER.route)
}
