package com.harper.capital.overview.ui.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.harper.capital.spec.domain.Account
import com.harper.capital.spec.domain.Asset
import com.harper.capital.spec.domain.AssetMetadata
import com.harper.capital.spec.domain.Currency

class PreviewStateProvider : PreviewParameterProvider<OverviewState.Data> {

    override val values: Sequence<OverviewState.Data> = sequenceOf(
        OverviewState.Data(
            account = Account(14241.24, Currency.RUB),
            assets = listOf(
                Asset(
                    0L,
                    "Tinkoff Credit",
                    2044.44,
                    Currency.RUB,
                    metadata = AssetMetadata.Credit(limit = 40000.00)
                ),
                Asset(
                    1L,
                    "Tinkoff USD",
                    24.44,
                    Currency.USD,
                    metadata = AssetMetadata.Goal(goal = 100000.00)
                )
            )
        )
    )
}