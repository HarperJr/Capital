package com.harper.capital.main.domain.model

import com.harper.capital.domain.model.Currency

class Summary(
    val debet: Double,
    val amount: Double,
    val currency: Currency = Currency.RUB
)
