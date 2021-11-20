package com.harper.capital.ui.overview

sealed class OverviewState {

    object Loading : OverviewState()

    data class Data(val data: Any) : OverviewState()
}
