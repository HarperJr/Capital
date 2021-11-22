package com.harper.capital.overview.ui.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.harper.capital.spec.domain.Asset
import com.harper.capital.spec.domain.Currency

class PreviewStateProvider : PreviewParameterProvider<OverviewState.Data> {

    override val values: Sequence<OverviewState.Data> = sequenceOf(
        OverviewState.Data(
            assets = listOf(
                Asset(
                    0L,
                    "Tinkoff Credit",
                    2044.44,
                    Currency.RUR
                ),
                Asset(
                    1L,
                    "Tinkoff USD",
                    24.44,
                    Currency.USD
                )
            )
        )
    )
}