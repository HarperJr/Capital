package com.harper.capital.spec.domain

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
