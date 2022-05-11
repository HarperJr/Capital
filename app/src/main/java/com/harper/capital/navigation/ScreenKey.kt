package com.harper.capital.navigation

enum class ScreenKey(val route: String) {
    SIGN_IN("sign_in"),
    MAIN("main"),
    ASSET_MANAGE("asset_manage"),
    LIABILITY_MANAGE("liability_manage"),
    TRANSACTION("transaction"),
    TRANSACTION_MANAGE("transaction_manage"),
    HISTORY_LIST("history_list"),
    ANALYTICS("analytics"),
    SETTINGS("settings"),
    ACCOUNTS("accounts"),
    SHELTER("shelter") // Experimental
}
