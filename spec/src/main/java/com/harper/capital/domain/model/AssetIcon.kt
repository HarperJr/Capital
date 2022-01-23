package com.harper.capital.domain.model

enum class AssetIcon {
    TINKOFF,
    ALPHA,
    VTB,
    SBER,
    RAIFFEISEN,
    BITCOIN,
    ETHERIUM,
    USD,
    EUR,
    PIGGY_BANK,
    MOBILE,
    PRODUCTS,
    WALLET;

    companion object {
        private val bankIncons = listOf(
            TINKOFF, ALPHA, VTB, SBER, RAIFFEISEN
        )

        fun categoryValues(): List<AssetIcon> = values().filter { it !in bankIncons }
    }
}
