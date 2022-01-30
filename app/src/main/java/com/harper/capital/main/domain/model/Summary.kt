package com.harper.capital.main.domain.model

import com.harper.capital.domain.model.Currency

class Summary(
    val expenses: Double,
    val balance: Double,
    val currency: Currency = Currency.RUB
)
