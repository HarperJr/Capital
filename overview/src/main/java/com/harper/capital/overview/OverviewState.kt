package com.harper.capital.overview

import com.harper.capital.overview.domain.model.Card

sealed class OverviewState {

    object Loading : OverviewState()

    data class Data(val cards: List<Card>) : OverviewState()
}
