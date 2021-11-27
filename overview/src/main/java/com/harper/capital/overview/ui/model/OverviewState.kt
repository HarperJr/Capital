package com.harper.capital.overview.ui.model

import com.harper.capital.spec.domain.Account
import com.harper.capital.spec.domain.Asset

sealed class OverviewState {

    object Loading : OverviewState()

    data class Data(
        val account: Account,
        val assets: List<Asset>
    ) : OverviewState()
}
