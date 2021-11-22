package com.harper.capital.overview.ui.model

import com.harper.capital.spec.domain.Asset

sealed class OverviewState {

    object Loading : OverviewState()

    data class Data(val assets: List<Asset>) : OverviewState() {
        val shouldShowAddAssetButton: Boolean
            get() = assets.size >= 4
    }
}
