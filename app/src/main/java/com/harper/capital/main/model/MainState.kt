package com.harper.capital.main.model

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.Asset

sealed class MainState {

    object Loading : MainState()

    data class Data(
        val account: Account,
        val assets: List<Asset>
    ) : MainState()
}
