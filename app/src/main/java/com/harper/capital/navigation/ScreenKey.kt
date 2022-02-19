package com.harper.capital.navigation

enum class ScreenKey(val route: String) {
    SIGN_IN("sign_in"),
    MAIN("main"),
    ASSET_MANAGE("asset_manage"),
    CATEGORY_MANAGE("category_manage"),
    TRANSACTION("transaction"),
    TRANSACTION_MANAGE("transaction_manage"),
    HISTORY_LIST("history_list"),
    SETTINGS("settings"),
    SHELTER("shelter") // Experimental
}
