package com.harper.capital.domain.model

enum class AccountIcon {
    TINKOFF,
    ALPHA,
    VTB,
    SBER,
    RAIFFEISEN,
    BITCOIN,
    ETHERIUM,
    DOGECOIN,
    USD,
    EUR,
    PIGGY_BANK,
    MOBILE,
    PRODUCTS,
    WALLET,
    TRAVEL,
    BUSINESS,
    CAR,
    TRANSPORT,
    BANK;

    companion object {
        private val assetIcons = listOf(TINKOFF, ALPHA, VTB, SBER, RAIFFEISEN)

        fun liabilityValues(): List<AccountIcon> = values().filter { it !in assetIcons }
    }
}
