package com.harper.capital.overview

import com.harper.capital.overview.domain.model.Asset

sealed class OverviewState {

    object Loading : OverviewState()

    data class Data(val assets: List<Asset>) : OverviewState()
}
