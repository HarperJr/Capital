package com.harper.capital.overview.model

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.Asset

sealed class OverviewState {

    object Loading : OverviewState()

    data class Data(
        val account: Account,
        val assets: List<Asset>
    ) : OverviewState()
}
