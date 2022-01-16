package com.harper.capital.transaction.model

enum class TransactionType {
    INCOME,
    EXPENSE,
    GOAL,
    DUTY;

    companion object {

        fun of(ordinal: Int): TransactionType = values().first { it.ordinal == ordinal }
    }
}
