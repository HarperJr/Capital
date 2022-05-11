package com.harper.capital.main.model

enum class ActionCardType {
    ACCOUNTS,
    ANALYTICS_BALANCE,
    ANALYTICS_INCOME,
    ANALYTICS_INCOME_LIABILITY,
    ANALYTICS_LIABILITY;

    companion object {

        fun of(ordinal: Int): ActionCardType = values().first { it.ordinal == ordinal }
    }
}
