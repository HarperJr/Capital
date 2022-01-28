package com.harper.capital.main

import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.Currency
import com.harper.capital.main.domain.model.Summary
import com.harper.capital.main.model.MainEvent
import com.harper.capital.main.model.MainState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class MainMockViewModel : ComponentViewModel<MainState>(
    defaultState = MainState(
        summary = Summary(debet = 14241.24, amount = 10000.0, Currency.RUB),
        assets = listOf(
            Asset(
                0L,
                "Tinkoff Credit",
                2044.44,
                Currency.RUB,
                metadata = AssetMetadata.Credit(limit = 40000.00),
                color = AssetColor.TINKOFF,
                icon = AssetIcon.TINKOFF
            ),
            Asset(
                1L,
                "Tinkoff USD",
                24.44,
                Currency.USD,
                metadata = AssetMetadata.Goal(goal = 100000.00),
                color = AssetColor.TINKOFF,
                icon = AssetIcon.TINKOFF
            )
        )
    )
), EventObserver<MainEvent> {

    override fun onEvent(event: MainEvent) {
        /**nope**/
    }
}
