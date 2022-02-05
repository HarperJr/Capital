package com.harper.capital.main.model

import com.harper.capital.domain.model.Account
import com.harper.capital.main.domain.model.Summary

data class MainState(
    val summary: Summary = Summary(expenses = 0.0, balance = 0.0),
    val accounts: List<Account> = emptyList(),
    val isLoading: Boolean = true
)
