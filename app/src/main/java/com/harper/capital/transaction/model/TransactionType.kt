package com.harper.capital.transaction.model

enum class TransactionType {
    EXPENSE,
    INCOME,
    SEND,
    GOAL,
    DUTY;

    companion object {

        fun of(ordinal: Int): TransactionType = values().first { it.ordinal == ordinal }
    }
}
