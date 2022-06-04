package com.harper.capital.domain.model

enum class AccountColor {
    TINKOFF,
    TINKOFF_PLATINUM,
    SBER,
    ALPHA,
    VTB_OLD,
    VTB,
    RAIFFEIZEN,
    LIABILITY,
    DEBT;

    companion object {

        fun assetColors(): List<AccountColor> = values().filter { it !in listOf(LIABILITY, DEBT) }
    }
}
