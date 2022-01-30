package com.harper.capital.main.model

import com.harper.capital.domain.model.Asset
import com.harper.capital.main.domain.model.Summary

data class MainState(
    val summary: Summary = Summary(expenses = 0.0, balance = 0.0),
    val assets: List<Asset> = emptyList(),
    val isLoading: Boolean = true
)
