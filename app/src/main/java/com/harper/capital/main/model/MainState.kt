package com.harper.capital.main.model

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.Currency

data class MainState(
    val account: Account = Account(0.0, Currency.RUB),
    val assets: List<Asset> = emptyList()
)
