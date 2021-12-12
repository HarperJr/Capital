package com.harper.capital.domain.model

enum class Currency {
    USD,
    RUB,
    EUR,
    JPY,
    GBP,
    AUD,
    CNY;

    companion object {

        fun of(id: Int): Currency = values().find { it.ordinal == id }
            ?: throw IllegalArgumentException("Unable to find currency with id=$id")
    }
}
